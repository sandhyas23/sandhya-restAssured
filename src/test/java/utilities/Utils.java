package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestObjects.Address;
import requestObjects.User;
import org.json.JSONObject;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
	
	  
	  public void validateResponse(Response response, Map<String,String> row, String sheetName) {
		    int expectedStatusCode = (int)Double.parseDouble(row.get("statusCode"));
		    String expectedStatusLine = row.get("statusLine");
		    String schemaFileForObject = "user-schema.json";
		    String schemaFileForArray = "user-array-schema.json";
		    String scenario = row.get("scenario");
		    String expectedMessage = row.get("message");
		    Map<String,Object> userDetails = new HashMap<>();
		    
		    if((!sheetName.equals("Delete") && !sheetName.equals("Get") )) {
		    	userDetails.put("userFirstName", row.get("userFirstName"));
		    	userDetails.put("userLastName", row.get("userLastName"));
		    	userDetails.put("userContactNumber", (long)Double.parseDouble(row.get("userContactNumber")));
		    	userDetails.put("userEmailId", row.get("userEmailId"));
		    	
		    	 String[] addressFields = {"plotNumber", "street", "state", "country", "zipCode"};
		    	    for (String field : addressFields) {
		    	        String key = "userAddress." + field;
		    	        Object value = scenario.equals("validMandatory") ? null :
		    	            ("zipCode".equals(field) ? (int) Double.parseDouble(row.get(field)) : row.get(field));
		    	        userDetails.put(key, value);
		    	    }
		    	 	
//		    	String userFirstName= row.get("userFirstName");
		    }
		    
		    
		    validateResponseHeader(response);

		    if (scenario.toLowerCase().contains("invalid") || scenario.toLowerCase().contains("existing") ||scenario.toLowerCase().contains("delete"))
		    {
		      validateNegativeAndDeleteResponse(response, expectedStatusCode, expectedStatusLine,expectedMessage);
		    } else if (scenario.equalsIgnoreCase("allUsers") || scenario.equalsIgnoreCase("validUserFirstname")) {
		        validateGetAllOrManyResponse(response, expectedStatusCode, expectedStatusLine, schemaFileForArray);
		    } else if (scenario.toLowerCase().startsWith("valid") && schemaFileForObject != null) {
		        validatePositiveResponse(response, expectedStatusCode, expectedStatusLine, schemaFileForObject, userDetails);
		    } else if(scenario.toLowerCase().contains("wrong")) {
		    	validateErrorResponse(response,expectedStatusCode, expectedStatusLine,expectedMessage);
		    }
		    else {
		        response.then().statusCode(expectedStatusCode).statusLine(expectedStatusLine);
		    }
		}
	  
	  
	  public void validateResponseHeader(Response response) {
		  response.then().header("Content-Type", "application/json");
	  }
	  
	  public void validatePositiveResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile, Map<String,Object> userDetails) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(matchesJsonSchemaInClasspath(schemaFile));
	        for (Map.Entry<String, Object> entry : userDetails.entrySet()) {
	            response.then().body(entry.getKey(), equalTo(entry.getValue()));
	        }
	        
	    }

	    // Negative: Validate error status code, status line, and error message
	    public void validateNegativeAndDeleteResponse(Response response, int expectedStatusCode, String expectedStatusLine, String expectedMessage) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body("message", equalTo(expectedMessage));
	    }

	    // GET all: Validate status code, status line, and schema for list response
	    public void validateGetAllOrManyResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(matchesJsonSchemaInClasspath(schemaFile));
	    }
	    
	    public void validateErrorResponse(Response response, int expectedStatusCode, String expectedStatusLine, String expectedMessage) {
	    	response.then()
	    		.statusCode(expectedStatusCode)
	    		.statusLine(expectedStatusLine)
	    		//.body("status",equalTo(404))
	    		.body("error",equalTo(expectedMessage));
	    }
	  
	  
	  public RequestSpecification createAuthorizedRequest() {
		    return RestAssured.given().auth().basic(
		        ConfigReader.getProperty("username"),
		        ConfigReader.getProperty("password")
		    ).log().all();
		}

		public void addJsonHeader(RequestSpecification request) {
		    request.header("Content-Type", "application/json");
		}

		public Map<String,String> getScenarioRow(List<Map<String,String>> data, String scenario) {
			
			 for (Map<String, String> row : data) {
			        String scenarioValue = row.get("scenario");
			        if (scenarioValue != null && scenarioValue.equalsIgnoreCase(scenario)) {
			            return row;
			        }
			    }
			    return null;
		}
		
		

		public Address buildAddress(Map<String,String> row, boolean isMandatory) {
		    if (isMandatory) {
		        return new Address();
		    }
		    return new Address(row.get("plotNumber"), row.get("street"), row.get("state"), row.get("country"),
		    		(int)Double.parseDouble(row.get("zipCode")));
		}

		public User buildUser(Map<String,String> row, Address address) {
		    return new User(row.get("userFirstName"), row.get("userLastName"), 
		    		(long)Double.parseDouble(row.get("userContactNumber")), row.get("userEmailId"), address);
		}
		
		public JSONObject buildUserForPatch(Map<String,String> row, String scenario) {
			 JSONObject userPatch = new JSONObject();
			    switch (scenario) {
			        case "validFirstNamePatch":
			            userPatch.put("userFirstName", row.get("userFirstName"));
			            break;
			        case "validEmailIdPatch":
			            userPatch.put("userEmailId", row.get("userEmailId"));
			            break;
			        case "validPlotPatch":
			        	JSONObject address1 = new JSONObject();
			            address1.put("plotNumber", row.get("plotNumber"));
			            userPatch.put("userAddress", address1);
			            break;
			        case "existingContactNo":
			            userPatch.put("userContactNumber", (long)Double.parseDouble(row.get("userContactNumber")));
			            break;
			        case "existingEmailId":
			         
			            userPatch.put("userEmailId", row.get("userEmailId"));
			            break;
			        case "invalidContactNo":
			            userPatch.put("userContactNumber", (long)Double.parseDouble(row.get("userContactNumber")));
			            
			            break;
			        case "invalidEmailId":
			            userPatch.put("userEmailId", row.get("userEmailId"));
			            
			            break;
			        case "invalidPlotNumber":
			        	JSONObject address2 = new JSONObject();
			            address2.put("plotNumber", row.get("plotNumber"));
			            userPatch.put("userAddress", address2);
			            
			            break;
			        case "invalidUserId":
			            userPatch.put("userFirstName", row.get("userFirstName"));
			            
			            break;
			        case "wrongEndpoint":
			            userPatch.put("userFirstName", row.get("userFirstName"));
			            
			            break;
			        default:
			            // Return empty object for unknown scenarios
			            break;
			    }
			    return userPatch;
			
		}


}
