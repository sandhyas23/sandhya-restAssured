package stepDefinitions;

import java.util.List;

import java.util.Map;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestObjects.Address;
import requestObjects.User;
import utilities.ConfigReader;
import utilities.LoggerLoad;
import utilities.Utils;


public class UserApiStepDef extends Utils {
	
	private RequestSpecification request;	
	private Response response;
	private static int newUserId;
	private static String newUserFirstName;
	String currentScenario;
	private Map<String,List<Map<String,String>>> excel = Hooks.excelData;
	List<Map<String,String>> requestData;
	Map<String,String> scenarioRow;
	//         Get,[ [scenario, statusCode, statusMsg, endPoint],[allUsers,200,Found,/users],
	//                [validUserId,200,Found,/user{userId}] ]
	
	
	
	@Given("The User is authorized")
    public void the_user_is_authorized() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        request = createAuthorizedRequest();
    }

    @Given("the user has valid request header")
    public void the_user_has_valid_request_header() {
        request.header("Content-Type", "application/json");

    }
    

	@When("the user send Post request from {string}")
	public void the_user_send_post_request_from(String scenario) {
		    currentScenario = scenario;
		    requestData = excel.get("Post");
		    //LoggerLoad.info("requestData: " + requestData);
		    scenarioRow = getScenarioRow(requestData, scenario);
		    String endPoint = scenarioRow.get("endPoint");

		    Address addressObject = buildAddress(scenarioRow, scenario.equals("validMandatory"));
		    User userObject = buildUser(scenarioRow, addressObject);

		   System.out.println(userObject);

		    response = request.body(userObject).post(endPoint);

		    if (response.statusCode()== 201 && scenario.equals("validDataAll")) {
		    	LoggerLoad.info("User created successfully with ID: " + response.then().extract().path("userId"));
		        newUserId = response.then().extract().path("userId");
		        newUserFirstName = response.then().extract().path("userFirstName");
			}
	    
	}
	


	@When("the user send Get request from {string}")
	public void the_user_send_get_request_from(String scenario) {
		   currentScenario = scenario;
		   requestData = excel.get("Get");
		   scenarioRow = getScenarioRow(requestData, scenario);
		   String endPoint = scenarioRow.get("endPoint");
		   
		   if (scenario.equals("allUsers") || scenario.equals("wrongEndpoint") ||
			        scenario.equals("invalidUserId") || scenario.equals("invalidUserFirstname")) {
			        response = request.get(endPoint);
			    } else if (scenario.equals("validUserFirstname")) {
			        response = request.get(endPoint, newUserFirstName);
			    } else {
			        response = request.get(endPoint, newUserId);
			    }
			}
	
	
	@When("the user send Put request from {string}")
	public void the_user_send_put_request_from(String scenario) {
		   currentScenario = scenario;
		   requestData = excel.get("Put");
		   scenarioRow = getScenarioRow(requestData, scenario);
		   String endPoint = scenarioRow.get("endPoint");
		   
		    Address addressObject = buildAddress(scenarioRow, scenario.equals("validMandatory"));
		    User userObject = buildUser(scenarioRow, addressObject);

		   System.out.println(userObject);
		   
		   if (scenario.equals("wrongEndpoint") || scenario.equals("invalidUserId")) {
			        response = request.body(userObject).put(endPoint);
			    } 
			 else {
				 LoggerLoad.info("UserID in put: " + newUserId+" ,  "+newUserFirstName);
			        response = request.body(userObject).put(endPoint, newUserId);
			    }
			}
	
	
	
	@When("the user send Delete request from {string}")
	public void the_user_send_delete_request_from(String scenario) {
		   currentScenario = scenario;
		   requestData = excel.get("Delete");
		   scenarioRow = getScenarioRow(requestData, scenario);
		   String endPoint = scenarioRow.get("endPoint");
		   
		   if (scenario.equals("wrongEndpoint") || scenario.equals("invalidUserId")) {
			        response = request.delete(endPoint);
			    } else if (scenario.equals("validUserFirstname")) {
			        response = request.delete(endPoint, newUserFirstName);
			    } else {
			    	LoggerLoad.info("UserID in delete: " + newUserId+" ,  "+newUserFirstName);
			        response = request.delete(endPoint, newUserId);
			    }
			}

		
	
	// Then check for all requests.
	@Then("The user receives status code and valid response")
	public void the_user_receives_status_code_and_valid_response() {
		
		validateResponse(response, scenarioRow);
		
	}

	

}
