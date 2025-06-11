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
	int newUserId;
	String newUserFirstName;
	String currentScenario;
	private Map<String,List<List<String>>> excel = Hooks.excelData;
	List<List<String>> requestData;
	List<String> eachRowData;
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
		    eachRowData = getScenarioRow(requestData, scenario);
		    String endPoint = eachRowData.get(3);

		    Address addressObject = buildAddress(eachRowData, scenario.equals("validMandatory"));
		    User userObject = buildUser(eachRowData, addressObject);

		    LoggerLoad.info("address and user obej" + userObject);

		    response = request.body(userObject).post(endPoint);

		    if (response.statusCode() == 201 && scenario.equals("validDataAll")) {
		        newUserId = response.then().extract().path("userId");
		        newUserFirstName = response.then().extract().path("userFirstName");
			}
	    
	}
	


	@When("the user send Get request from {string}")
	public void the_user_send_get_request_from(String scenario) {
		   currentScenario = scenario;
		   requestData = excel.get("Get");
		   eachRowData = getScenarioRow(requestData, scenario);
		   String endPoint = eachRowData.get(3);
		   
		   if (scenario.equals("allUsers") || scenario.equals("wrongEndpoint") ||
			        scenario.equals("invalidUserId") || scenario.equals("invalidUserFirstname")) {
			        response = request.get(endPoint);
			    } else if (scenario.equals("validUserFirstname")) {
			        response = request.get(endPoint, newUserFirstName);
			    } else {
			        response = request.get(endPoint, newUserId);
			    }
			}
	
	@When("the user send Delete request from {string}")
	public void the_user_send_delete_request_from(String scenario) {
		   currentScenario = scenario;
		   requestData = excel.get("Delete");
		   eachRowData = getScenarioRow(requestData, scenario);
		   String endPoint = eachRowData.get(3);
		   
		   if (scenario.equals("wrongEndpoint") || scenario.equals("invalidUserId")) {
			        response = request.delete(endPoint);
			    } else if (scenario.equals("validUserFirstname")) {
			        response = request.get(endPoint, newUserFirstName);
			    } else {
			        response = request.get(endPoint, newUserId);
			    }
			}

		
	
	// Then check for all requests.
	@Then("The user receives status code and valid response")
	public void the_user_receives_status_code_and_valid_response() {
		
		validateResponse(response, eachRowData);
		
	}

	

}
