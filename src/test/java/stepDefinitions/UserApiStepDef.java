package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class UserApiStepDef {
	
	//ConfigReader configFileReader = new ConfigReader();
	private RequestSpecification request;	
	private Response response;
	
	@Given("The User is authorized")
    public void the_user_is_authorized() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        request = RestAssured.given().auth().basic(
        		ConfigReader.getProperty("username"),ConfigReader.getProperty("password"));
    }

    @Given("the user has valid request header")
    public void the_user_has_valid_request_header() {
        request.header("Content-Type", "application/json");

    }


	@When("the user send Post request from {string}")
	public void the_user_send_post_request_from(String string) {
	    
	}

	@Then("The user receives status code and valid response")
	public void the_user_receives_status_code_and_valid_response() {
	   
	}

	@When("the user send Get request from {string}")
	public void the_user_send_get_request_from(String string) {
	

	}

}
