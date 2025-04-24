package utils;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelWriter {

    public static void writeData(String filePath, String sheetName, int rowIndex, String columnHeader, String value) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        Row headerRow = sheet.getRow(0);
        int colIndex = -1;

        // Find the column index for the given header
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnHeader)) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }

        // If column doesn't exist, create it
        if (colIndex == -1) {
            colIndex = headerRow.getLastCellNum();
            Cell newHeaderCell = headerRow.createCell(colIndex);
            newHeaderCell.setCellValue(columnHeader);
        }

        // Write the value in the specific cell
        Row dataRow = sheet.getRow(rowIndex);
        if (dataRow == null) dataRow = sheet.createRow(rowIndex);
        Cell cell = dataRow.createCell(colIndex);
        cell.setCellValue(value);

        // Write the changes to the Excel file
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close(); 
        workbook.close(); 
    }
}
