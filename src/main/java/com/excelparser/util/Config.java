package com.excelparser.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public final class Config {
    private static Scanner scanner = new Scanner(System.in);
    private static String instructorPath;
    private static String coursePath;

    private Config() {}

    public static void configure(String[] args) {
        // Check if a command-line argument for the file path is provided
        if (args.length > 0) {
            instructorPath = args[0];
            if (args.length > 1) {
                coursePath = args[1];
            }
        }

        while (instructorPath == null || !isValidXLSXFilePath(instructorPath)) {
            System.out.print("Enter the XLSX file path: ");
            instructorPath = scanner.nextLine();
        }

        while (coursePath == null || !isValidCSVFilePath(coursePath)) {
            System.out.print("Enter the CSV file path: ");
            coursePath = scanner.nextLine();
        }
    }

    public static String getInstructorPath() {
        return instructorPath;
    }

    public static String getCoursePath() {
        return coursePath;
    }

    private static boolean isValidXLSXFilePath(String path) {
        return Files.exists(Paths.get(path)) && path.toLowerCase().endsWith(".xlsx");
    }

    private static boolean isValidCSVFilePath(String path) {
        return Files.exists(Paths.get(path)) && path.toLowerCase().endsWith(".csv");
    }
}
