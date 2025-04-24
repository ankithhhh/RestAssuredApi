package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    public static Map<String, String> readData(String filePath, String sheetName, int rowIndex) {
        Map<String, String> data = new HashMap<>();
//        try (InputStream is = ExcelReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
//            if (is == null) {
//                throw new RuntimeException("Could not find the Excel file at: " + resourcePath);
//            }
        //filePath = "src/test/resources/data/PostData.xlsx";
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowIndex);

            if (headerRow == null || dataRow == null) {
                throw new IllegalArgumentException("Row not found in sheet " + sheetName);
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String key = headerRow.getCell(i).getStringCellValue();
                Cell cell = dataRow.getCell(i);
                String value = cell != null ? cell.toString() : "";
                data.put(key, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String readCellValue(String filePath, String sheetName, int rowIndex, String columnHeader) {
        Map<String, String> rowData = readData(filePath, sheetName, rowIndex);
        return rowData.getOrDefault(columnHeader, "");
    }
}
