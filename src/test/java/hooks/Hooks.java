package hooks;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utilities.ExcelReader;

public class Hooks {
	
	 protected static Map<String,List<List<String>>> excelData;
	    private static ExcelReader excelReader;

	    @BeforeClass
	    public void setUp() {
	        // Load Excel data once before all tests in the class
	        excelReader = new ExcelReader("Data.xlsx");
	        excelData = excelReader.getAllSheetsData();
	    }



	    @AfterClass
	    public void tearDown() {
	  
	        try {
	            if (excelReader != null) {
	                excelReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
