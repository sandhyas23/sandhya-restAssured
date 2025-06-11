package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestObjects.Address;
import requestObjects.User;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

public class Utils {
	
	  
	  public void validateResponse(Response response, List<String> row) {
		    int expectedStatusCode = Integer.parseInt(row.get(1));
		    String expectedStatusLine = row.get(2);
		    String schemaFile = row.size() > 5 ? row.get(5) : null;
		    String jsonPath = row.get(4);
//		    String expectedValue = row.size() > 6 ? row.get(6) : null;
//		    String errorJsonPath = row.size() > 7 ? row.get(7) : null;
//		    String expectedErrorMessage = row.size() > 8 ? row.get(8) : null;
		    String scenario = row.get(0);

		    if (scenario.toLowerCase().contains("invalid") || scenario.toLowerCase().contains("existing")) {
		        validateNegativeResponse(response, expectedStatusCode, expectedStatusLine, jsonPath);
		    } else if (scenario.equalsIgnoreCase("allUsers")) {
		        validateGetAllResponse(response, expectedStatusCode, expectedStatusLine, schemaFile);
		    } else if (schemaFile != null && jsonPath !=null) {
		        validatePositiveResponse(response, expectedStatusCode, expectedStatusLine, jsonPath, schemaFile);
		    } else {
		        response.then().statusCode(expectedStatusCode).statusLine(expectedStatusLine);
		    }
		}
	  
	  
	  
	  public void validatePositiveResponse(Response response, int expectedStatusCode, String expectedStatusLine, String jsonPath,  String schemaFile) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(jsonPath, equalTo(expectedValue))
	            .body(matchesJsonSchemaInClasspath(schemaFile));
	    }

	    // Negative: Validate error status code, status line, and error message
	    public void validateNegativeResponse(Response response, int expectedStatusCode, String expectedStatusLine, String jsonPath) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(jsonPath, equalTo(expectedErrorMessage));
	    }

	    // GET all: Validate status code, status line, and schema for list response
	    public void validateGetAllResponse(Response response, int expectedStatusCode, String expectedStatusLine, String schemaFile) {
	        response.then()
	            .statusCode(expectedStatusCode)
	            .statusLine(expectedStatusLine)
	            .body(matchesJsonSchemaInClasspath(schemaFile));
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

		public List<String> getScenarioRow(List<List<String>> data, String scenario) {
		    for (int i = 1; i < data.size(); i++) {
		        if (data.get(i).get(0).equals(scenario)) {
		            return data.get(i);
		        }
		    }
		    return null;
		}
		
		

		public Address buildAddress(List<String> row, boolean isMandatory) {
		    if (isMandatory) {
		        return new Address();
		    }
		    return new Address(row.get(8), row.get(9), row.get(10), row.get(11), (int) Double.parseDouble(row.get(12)));
		}

		public User buildUser(List<String> row, Address address) {
		    return new User(row.get(4), row.get(5), row.get(6), row.get(7), address);
		}


}
