package com.excelparser.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FrequencyParser {

    private FrequencyParser () {}

    public static void parse(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;

            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                List<String> rowData = new ArrayList<>(11); // each row is 11 cells

                for (int i = 0; i < 11; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        rowData.add("");
                    } else {
                        rowData.add(cell.toString());
                    }
                }

                FrequencyProcessor.process(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
