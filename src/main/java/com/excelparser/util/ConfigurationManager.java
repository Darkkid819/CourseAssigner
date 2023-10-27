package com.excelparser.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public final class ConfigurationManager {
    private static Scanner scanner = new Scanner(System.in);
    private static String instructorPath;
    private static String coursePath;
    private static final String SECTIONS_FILE = "./backup/sections.dat";
    private static final String INSTRUCTORS_FILE = "./backup/instructors.dat";

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
            System.out.print("Enter the file path for instructor data: ");
            instructorPath = scanner.nextLine();
        }

        while (coursePath == null || !isValidXLSXFilePath(coursePath)) {
            System.out.print("Enter the file path for course data: ");
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

    public static boolean serializedFilesExist() {
        return Files.exists(Paths.get(SECTIONS_FILE)) && Files.exists(Paths.get(INSTRUCTORS_FILE));
    }

    public static String getSectionsFile() {
        return SECTIONS_FILE;
    }

    public static String getInstructorsFile() {
    return INSTRUCTORS_FILE;
    }
}
