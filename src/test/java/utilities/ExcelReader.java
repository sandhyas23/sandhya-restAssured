package utilities;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

	private Workbook workbook;

    public ExcelReader(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + fileName);
            }
            workbook = new XSSFWorkbook(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + fileName, e);
        }
    }
    
//    public List<List<String>> getSheetData(String sheetName) {
//        List<List<String>> sheetData = new ArrayList<>();
//        Sheet sheet = workbook.getSheet(sheetName);
//        if (sheet == null) {
//            throw new RuntimeException("Sheet " + sheetName + " not found");
//        }
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        for (int i = 0; i < rowCount; i++) {
//            Row row = sheet.getRow(i);
//            List<String> rowData = new ArrayList<>();
//            if (row != null) {
//                int colCount = row.getPhysicalNumberOfCells();
//                for (int j = 0; j < colCount; j++) {
//                    Cell cell = row.getCell(j);
//                    rowData.add(cell != null ? cell.toString() : null);
//                }
//            }
//            sheetData.add(rowData);
//        }
//        return sheetData;
//    }
    
    public List<Map<String, String>> getSheetData(String sheetName) {
        List<Map<String, String>> sheetData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet " + sheetName + " not found");
        }
        int rowCount = sheet.getPhysicalNumberOfRows();
        if (rowCount < 1) {
            return sheetData; // empty sheet
        }
        // Read header
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getPhysicalNumberOfCells();
        List<String> headers = new ArrayList<>();
        for (int j = 0; j < colCount; j++) {
            Cell cell = headerRow.getCell(j);
            headers.add(cell != null ? cell.toString() : null);
        }
        // Read data rows
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
            	Cell cell = row.getCell(j);
            	String value = null;
            	if (cell != null) {
            	    if (cell.getCellType() == CellType.NUMERIC) {
            	        value = new java.math.BigDecimal(cell.getNumericCellValue()).toPlainString();
            	    } else {
            	        value = cell.toString();
            	    }
            	}
                rowData.put(headers.get(j), cell != null ? cell.toString() : null);
            }
            sheetData.add(rowData);
        }
        return sheetData;
    }
    
    
//    public Map<String, List<List<String>>> getAllSheetsData() {
//        Map<String, List<List<String>>> allData = new HashMap<>();
//        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//            String sheetName = workbook.getSheetName(i);
//            allData.put(sheetName, getSheetData(sheetName));
//        }
//        return allData;
//    }
    
    
    public Map<String, List<Map<String, String>>> getAllSheetsData() {
        Map<String, List<Map<String, String>>> allData = new HashMap<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String sheetName = workbook.getSheetName(i);
            allData.put(sheetName, getSheetData(sheetName));
        }
        return allData;
    }
    
    
    
    
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
