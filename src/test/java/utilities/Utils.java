package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestObjects.Address;
import requestObjects.User;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

public class Utils {
	
	  
	  public void validateResponse(Response response, Map<String,String> row) {
		    int expectedStatusCode = (int)Double.parseDouble(row.get("statusCode"));
		    String expectedStatusLine = row.get("statusLine");
		    //System.out.println(row);
		    String schemaFile = "user-schema.json";
		   // String userFirstName =
//		    String expectedValue = row.size() > 6 ? row.get(6) : null;
//		    String errorJsonPath = row.size() > 7 ? row.get(7) : null;
//		    String expectedErrorMessage = row.size() > 8 ? row.get(8) : null;
		    String scenario = row.get("scenario");

		    if (scenario.toLowerCase().contains("invalid") || scenario.toLowerCase().contains("existing")) {
		        validateNegativeResponse(response, expectedStatusCode, expectedStatusLine);
		    } else if (scenario.equalsIgnoreCase("allUsers")) {
		        validateGetAllResponse(response, expectedStatusCode, expectedStatusLine, schemaFile);
		    } else if (scenario.toLowerCase().startsWith("valid") && schemaFile != null) {
		        validatePositiveResponse(response, expectedStatusCode, expectedStatusLine, schemaFile);
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
	            .statusLine(expectedStatusLine);
	            //.body(jsonPath, equalTo(expectedValue))
	           // .body(matchesJsonSchemaInClasspath(schemaFile));
	    }

	    // Negative: Validate error status code, status line, and error message
	    public void validateNegativeResponse(Response response, int expectedStatusCode, String expectedStatusLine) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine);
	           // .body(jsonPath, equalTo(expectedErrorMessage));
	    }

	    // GET all: Validate status code, status line, and schema for list response
	    public void validateGetAllResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile) {
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


}
