package hooks;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.cucumber.java.*;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import stepDefinitions.UserApiStepDef;
import utilities.LoggerLoad;
import utilities.ConfigReader;
import utilities.ExcelReader;

public class Hooks {
	
	    public static Map<String,List<Map<String,String>>> excelData;
	    private static ExcelReader excelReader;
	    @BeforeAll
	    public static void setUp() {
	        // Load Excel data once before all tests in the class
	        excelReader = new ExcelReader("swagger-testData.xlsx");
	        excelData = excelReader.getAllSheetsData();
	        //LoggerLoad.info(excelData.toString());
	        
	    }
	    
	    

//	    @After
//	    public void deleteCreatedUser() {
//	        if (UserApiStepDef.newUserId > 1) {
//	        	System.out.println("inside hooks after");
//	        	
//	            RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
//	            RestAssured.given().auth().basic(ConfigReader.getProperty("username"),
//	    		        ConfigReader.getProperty("password"))
//	            		.when().delete("/deleteuser/{userID}",UserApiStepDef.newUserId)
//	            		.then().statusCode(200)
//	            		.body("message", equalTo("User is deleted successfully"));
//	                         
//	            UserApiStepDef.newUserId = 0; // reset after deletion
//	        }
//	    }
	




	    @AfterAll
	    public static void tearDown() {
	    	
	    	  if (UserApiStepDef.newUserId > 1) {
		        	System.out.println("inside hooks after");
		        	
		            RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
		            RestAssured.given().auth().basic(ConfigReader.getProperty("username"),
		    		        ConfigReader.getProperty("password"))
		            		.when().delete("/deleteuser/{userID}",UserApiStepDef.newUserId)
		            		.then().statusCode(200)
		            		.body("message", equalTo("User is deleted successfully"));
		                         
		            UserApiStepDef.newUserId = 0; // reset after deletion
		        }
	  
	        try {
	            if (excelReader != null) {
	                excelReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
