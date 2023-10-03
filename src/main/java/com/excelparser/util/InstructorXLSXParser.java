package com.excelparser.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class InstructorXLSXParser {

    private InstructorXLSXParser() {}

    public static List<List<String>> parse(String filePath) throws IOException {
        List<List<String>> instructorsData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            int currentRow = 0;
            while (currentRow < sheet.getLastRowNum()) {
                Row row = sheet.getRow(currentRow);
                currentRow++;

                if (row == null || row.getCell(0) == null || !row.getCell(0).getStringCellValue().startsWith("—")) {
                    continue;
                }

                instructorsData.add(processInstructorData(sheet, currentRow));
            }
        }

        return instructorsData;
    }

    private static List<String> processInstructorData(Sheet sheet, int currentRow) {
        List<String> instructorData = new ArrayList<>(75);

        while (currentRow <= sheet.getLastRowNum() + 1) {
            Row row = sheet.getRow(currentRow);

            if (row == null || row.getCell(0).getStringCellValue().startsWith("—")) {
                break;
            } else {
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    instructorData.add(cellValue);
                }
                currentRow++;
            }
        }

        return instructorData;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
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

