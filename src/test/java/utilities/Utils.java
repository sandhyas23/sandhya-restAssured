package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestObjects.Address;
import requestObjects.User;
import org.json.JSONObject;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

public class Utils {
	
	  
	  public void validateResponse(Response response, Map<String,String> row, String sheetName) {
		    int expectedStatusCode = (int)Double.parseDouble(row.get("statusCode"));
		    String expectedStatusLine = row.get("statusLine");
		    //System.out.println(row);
		    String schemaFileForObject = "user-schema.json";
		    String schemaFileForArray = "user-array-schema.json";
		   // String userFirstName =
//		    String expectedValue = row.size() > 6 ? row.get(6) : null;
//		    String errorJsonPath = row.size() > 7 ? row.get(7) : null;
//		    String expectedErrorMessage = row.size() > 8 ? row.get(8) : null;
		    String scenario = row.get("scenario");

		    if (scenario.toLowerCase().contains("invalid") || scenario.toLowerCase().contains("existing") ||
		    		scenario.toLowerCase().contains("delete")) {
		            validateNegativeAndDeleteResponse(response, expectedStatusCode, expectedStatusLine);
		    } else if (scenario.equalsIgnoreCase("allUsers") || scenario.equalsIgnoreCase("validUserFirstname")) {
		        validateGetAllOrManyResponse(response, expectedStatusCode, expectedStatusLine, schemaFileForArray);
		    } else if (scenario.toLowerCase().startsWith("valid") && schemaFileForObject != null) {
		        validatePositiveResponse(response, expectedStatusCode, expectedStatusLine, schemaFileForObject);
		    } else if(scenario.toLowerCase().contains("wrong")) {
		    	validateErrorResponse(response,expectedStatusCode, expectedStatusLine);
		    }
		    else {
		        response.then().statusCode(expectedStatusCode).statusLine(expectedStatusLine);
		    }
		}
	  
	  
	  
	  public void validatePositiveResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            //.body(jsonPath, equalTo(expectedValue))
	            .body(matchesJsonSchemaInClasspath(schemaFile));
	    }

	    // Negative: Validate error status code, status line, and error message
	    public void validateNegativeAndDeleteResponse(Response response, int expectedStatusCode, String expectedStatusLine) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine);
	           // .body(jsonPath, equalTo(expectedErrorMessage));
	    }

	    // GET all: Validate status code, status line, and schema for list response
	    public void validateGetAllOrManyResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(matchesJsonSchemaInClasspath(schemaFile));
	    }
	    
	    public void validateErrorResponse(Response response, int expectedStatusCode, String expectedStatusLine) {
	    	response.then()
	    		.statusCode(expectedStatusCode)
	    		.statusLine(expectedStatusLine)
	    		.body("status",equalTo(404))
	    		.body("error",equalTo("Not Found"));
	    }
	  
	  
	  
	  
	  public RequestSpecification createAuthorizedRequest() {
		    return RestAssured.given().auth().basic(
		        ConfigReader.getProperty("username"),
		        ConfigReader.getProperty("password")
		    );
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
			
//		    for (int i = 1; i < data.size(); i++) {
//		        if (data.get(i).get(0).equals(scenario)) {
//		            return data.get(i);
//		        }
//		    }
//		    return null;
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
