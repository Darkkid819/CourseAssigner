package com.excelparser.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class CourseCSVParser {

    private static final String DELIMITER = ",";

    private CourseCSVParser() {}

    public static List<String[]> parse(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(DELIMITER);
                data.add(fields);
            }
        }

        return data;
    }

    // Keeping the display method in case it's needed for debugging or visualization
    private static void display(List<String[]> data) {
        for (String[] row : data) {
            for (String field : row) {
                System.out.print(field + " | ");
            }
            System.out.println();
        }
    }
}