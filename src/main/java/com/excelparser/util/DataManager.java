package com.excelparser.util;

import com.excelparser.model.InstructorSet;
import com.excelparser.model.SectionSet;

import java.io.IOException;

public final class DataManager {

    private DataManager() {}

    public static void loadData() {
        if (!ConfigurationManager.serializedFilesExist()) {
            init();
            return;
        }
        try {
            SectionSet loadedCourseSet = (SectionSet) Serializer.deserializeFromFile(ConfigurationManager.getSectionsFile());
            SectionSet.getInstance().copy(loadedCourseSet);

            InstructorSet loadedInstructorSet = (InstructorSet) Serializer.deserializeFromFile(ConfigurationManager.getInstructorsFile());
            InstructorSet.getInstance().copy(loadedInstructorSet);
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
    }

    private static void init() {
        parseFiles();
        saveData();
    }

    public static void saveData() {
        try {
            Serializer.serializeToFile(SectionSet.getInstance(), ConfigurationManager.getSectionsFile());
            Serializer.serializeToFile(InstructorSet.getInstance(), ConfigurationManager.getInstructorsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseFiles() {
        parseCourses();
        parseInstructors();
    }

    private static void parseCourses() {
        try {
            CourseParser.parse(ConfigurationManager.getCoursePath());
            System.out.println("Course data parsed successfully\n");
        } catch (IOException e) {
            System.err.println("Error reading or parsing the courses file\n");
            e.printStackTrace();
        }
    }

    private static void parseInstructors() {
        try {
            InstructorParser.parse(ConfigurationManager.getInstructorPath());
            System.out.println("Instructor data parsed successfully\n");
        } catch (IOException e) {
            System.err.println("Error reading or parsing the instructors file\n");
            e.printStackTrace();
        }
    }
}
