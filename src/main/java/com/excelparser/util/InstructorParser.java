package com.excelparser.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class InstructorParser {
    private InstructorParser() {}

    public static void parse(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            int currentRow = 0;
            while (currentRow < sheet.getLastRowNum()) {
                Row row = sheet.getRow(currentRow);

                if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue().startsWith("-")) {
                    currentRow++;
                    continue;
                }

                DataResult result = processData(sheet, currentRow);
                InstructorProcessor.process(result.getData());

                currentRow += result.getProcessedRows();
            }
        }
    }

    private static DataResult processData(Sheet sheet, int startRow) {
        List<String> instructorData = new ArrayList<>();

        int rowIndex;
        for (rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            Cell firstCell = row.getCell(0);
            if (firstCell != null && firstCell.getStringCellValue().startsWith("-")) {
                break;
            }

            for (int colIndex = 0; colIndex < 15; colIndex++) {
                Cell cell = row.getCell(colIndex);
                instructorData.add(getCellValueAsString(cell));
            }
        }

        return new DataResult(instructorData, rowIndex - startRow);
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
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
                    return sdf.format(date);
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private static class DataResult {
        private final List<String> data;
        private final int processedRows;

        public DataResult(List<String> data, int processedRows) {
            this.data = data;
            this.processedRows = processedRows;
        }

        public List<String> getData() {
            return data;
        }

        public int getProcessedRows() {
            return processedRows;
        }
    }
}
