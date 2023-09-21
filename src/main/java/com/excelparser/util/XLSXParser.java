package com.excelparser.util;

import com.excelparser.model.Instructor;
import com.excelparser.model.InstructorList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class XLSXParser {

    private XLSXParser() {}

    public static void parse(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet

            int currentRow = 0;

            while (currentRow < sheet.getLastRowNum()) {
                Row row = sheet.getRow(currentRow);
                currentRow++;

                // Skip rows until a row starting with "—" is encountered
                if (row == null || row.getCell(0) == null || !row.getCell(0).getStringCellValue().startsWith("—")) {
                    continue;
                }

                // Process instructor data and call the add methodInstructors.
                processInstructorData(sheet, currentRow);
            }
        }
    }

    private static void processInstructorData(Sheet sheet, int currentRow) {
        List<String> instructorData = new ArrayList<>(75); // Store cell values for each instructor

        while (currentRow <= sheet.getLastRowNum() + 1) {
            Row row = sheet.getRow(currentRow);

            if (row == null || row.getCell(0).getStringCellValue().startsWith("—")) {
                // End of instructor data, process and add to InstructorList object
                InstructorProcessor.addInstructor(instructorData);
                break;
            } else {
                // Process and add all cell values in the row to the list
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    instructorData.add(cellValue);
                }
                currentRow++;
            }
        }
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Handle empty cells
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}

