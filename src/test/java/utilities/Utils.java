package utilities;

import io.restassured.response.Response;

public class Utils {
	
	  public void validateResponse(Response response, int expectedStatusCode){
	        LoggerLoad.info("response ---> " +response.getStatusCode()+"---"+response.getStatusLine()+"---"+response.getBody().asPrettyString());
	         response.then().statusCode(expectedStatusCode);
	    }

}
