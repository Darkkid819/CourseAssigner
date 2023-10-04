package com.excelparser.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public final class ConfigurationManager {

    private static final String COURSE_SET_FILE = "data/courses.dat";
    private static final String INSTRUCTOR_SET_FILE = "data/instructors.dat";
    private static Scanner scanner = new Scanner(System.in);
    private static String instructorPath;
    private static String coursePath;

    private ConfigurationManager() {}

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

    public static boolean serializedFilesExist() {
        return new File(COURSE_SET_FILE).exists() && new File(INSTRUCTOR_SET_FILE).exists();
    }

    public static String getCourseSetFile() {
        return COURSE_SET_FILE;
    }

    public static String getInstructorSetFile() {
        return INSTRUCTOR_SET_FILE;
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
