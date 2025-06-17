package stepDefinitions;

import java.util.List;

import java.util.Map;

import org.json.JSONObject;

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
	private static int mandatoryUserId;
	private static String newUserFirstName;
	private static String mandatoryUserFirstName;
	String currentScenario;
	String endPoint;
	private Map<String,List<Map<String,String>>> excel = Hooks.excelData;
	List<Map<String,String>> requestData;
	Map<String,String> scenarioRow;
	private String sheetName = "";
	//         Get,[ [scenario, statusCode, statusMsg, endPoint],[allUsers,200,Found,/users],
	//                [validUserId,200,Found,/user{userId}] ]
	
	
	
	@Given("Admin is authorized")
    public void the_user_is_authorized() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        request = createAuthorizedRequest();
    }
	
	@Given("Admin is not authorized")
	public void the_user_is_not_authorized() {
		RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        request = createUnauthorizedRequest();
	}
	
	
	@Given("{string} request with BaseURL and valid EndPoint from {string}")
	public void request_with_base_url_and_valid_end_point_from(String requestName, String scenario) {
		    currentScenario = scenario;
		    sheetName = requestName;
		    requestData = excel.get(sheetName);
		    //LoggerLoad.info("requestData: " + requestData);
		    scenarioRow = getScenarioRow(requestData, scenario);
		    endPoint = scenarioRow.get("endPoint");
		    request.header("Content-Type", "application/json");
	}


	@When("Admin sends Post request")
	public void the_user_sends_post_request() {

		    Address addressObject = buildAddress(scenarioRow, currentScenario.equals("validMandatory"));
		    User userObject = buildUser(scenarioRow, addressObject);

		   System.out.println(userObject);

		    response = request.body(userObject).post(endPoint);

		    if (response.statusCode()== 201 && currentScenario.equals("validDataAll")) {
		    	LoggerLoad.info("User created successfully with ID: " + response.then().extract().path("userId"));
		        newUserId = response.then().extract().path("userId");
		        newUserFirstName = response.then().extract().path("userFirstName");
			}
		    else if (response.statusCode()== 201 && currentScenario.equals("validMandatory")) {
		    	LoggerLoad.info("User created successfully with mandatory fields only with ID: " + response.then().extract().path("userId")+" -- "+response.then().extract().path("userAddress.zipCode"));
		        mandatoryUserId = response.then().extract().path("userId");
		        mandatoryUserFirstName = response.then().extract().path("userFirstName");
		    }
	    
	}
	


	@When("Admin sends Get request")
	public void the_user_send_get_request() {
		   
		   if (currentScenario.equals("allUsers") || currentScenario.equals("wrongEndpoint") ||
				   currentScenario.equals("invalidUserId") || currentScenario.equals("invalidUserFirstname")
				   || currentScenario.equals("noAuth")) {
			        response = request.get(endPoint);
			    } else if (currentScenario.equals("validUserFirstname")) {
			        response = request.get(endPoint, newUserFirstName);
			    } else {
			        response = request.get(endPoint, newUserId);
			    }
			}
	
	
	@When("Admin sends Put request")
	public void the_user_send_put_request() {
		   
		    Address addressObject = buildAddress(scenarioRow, currentScenario.equals("validMandatory"));
		    User userObject = buildUser(scenarioRow, addressObject);

		   System.out.println(userObject);
		   
		   if (currentScenario.equals("wrongEndpoint") || currentScenario.equals("invalidUserId")) {
			        response = request.body(userObject).put(endPoint);
			    } 
			 else {
				 LoggerLoad.info("UserID in put: " + newUserId+" ,  "+newUserFirstName);
			        response = request.body(userObject).put(endPoint, newUserId);
			    }
			}
	
	
	@When("Admin sends Patch request")
	public void the_user_send_patch_request() {
           
		JSONObject userObj = buildUserForPatch(scenarioRow,currentScenario);
		LoggerLoad.info(userObj);
		   
		   if (currentScenario.equals("wrongEndpoint") || currentScenario.equals("invalidUserId")) {
			        response = request.body(userObj).patch(endPoint);
			    } 
			 else {
				 //LoggerLoad.info("UserID in patch: " + newUserId+" ,  "+newUserFirstName);
			        response = request.body(userObj.toString()).patch(endPoint, newUserId);
			      LoggerLoad.info(response.asString());
			    }
			}
	
	
	
	@When("Admin sends Delete request")
	public void the_user_send_delete_request() {
		
		   if (currentScenario.equals("wrongEndpoint") || currentScenario.equals("invalidUserId")) {
			        response = request.delete(endPoint);
			    } else if (currentScenario.equals("deleteValidUserFirstname")) {
			        response = request.delete(endPoint, mandatoryUserFirstName);
			    } else {
			    	//LoggerLoad.info("UserID in delete: " + newUserId+" ,  "+newUserFirstName);
			        response = request.delete(endPoint, newUserId);
			    }
			}

		
	
	// Then check for all requests.
	@Then("Admin receives status code and valid response")
	public void the_user_receives_status_code_and_valid_response() {
		
		validateResponse(response, scenarioRow, sheetName);
		
	}

	

}
