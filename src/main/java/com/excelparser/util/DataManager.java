package com.excelparser.util;

import com.excelparser.model.CourseSet;
import com.excelparser.model.InstructorSet;

import java.io.IOException;

public final class DataManager {

    private DataManager() {}

    public static void loadData(String[] args) {
        if (ConfigurationManager.serializedFilesExist()) {
            try {
                CourseSet loadedCourseSet = (CourseSet) Serializer.deserializeFromFile(ConfigurationManager.getCourseSetFile());
                CourseSet.getInstance().copy(loadedCourseSet);

                InstructorSet loadedInstructorSet = (InstructorSet) Serializer.deserializeFromFile(ConfigurationManager.getInstructorSetFile());
                InstructorSet.getInstance().copy(loadedInstructorSet);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                parseOriginalFiles();
            }
        } else {
            ConfigurationManager.configure(args);
            parseOriginalFiles();
            saveData();
        }
    }

    public static void saveData() {
        try {
            Serializer.serializeToFile(CourseSet.getInstance(), ConfigurationManager.getCourseSetFile());
            Serializer.serializeToFile(InstructorSet.getInstance(), ConfigurationManager.getInstructorSetFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseOriginalFiles() {
        parseCourses();
        parseInstructors();
    }

    private static void parseCourses() {
        try {
            CourseProcessor.processCourses(ConfigurationManager.getCoursePath());
            System.out.println("Course data parsed successfully\n");
        } catch (IOException e) {
            System.err.println("Error reading or parsing the csv file\n");
            e.printStackTrace();
        }
    }

    private static void parseInstructors() {
        try {
            InstructorProcessor.processInstructors(ConfigurationManager.getInstructorPath());
            System.out.println("Instructor data parsed successfully\n");
        } catch (IOException e) {
            System.err.println("Error reading or parsing the xlsx file\n");
            e.printStackTrace();
        }
    }
}
