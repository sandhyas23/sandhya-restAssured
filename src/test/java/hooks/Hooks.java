package hooks;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.cucumber.java.*;

import utilities.LoggerLoad;

import utilities.ExcelReader;

public class Hooks {
	
	    public static Map<String,List<List<String>>> excelData;
	    private static ExcelReader excelReader;

	    @BeforeAll
	    public static void setUp() {
	        // Load Excel data once before all tests in the class
	        excelReader = new ExcelReader("swagger-testData.xlsx");
	        excelData = excelReader.getAllSheetsData();
	        //LoggerLoad.info(excelData.toString());
	        
	    }



	    @AfterAll
	    public static void tearDown() {
	  
	        try {
	            if (excelReader != null) {
	                excelReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
