package com.excelparser.util;

import java.io.File;

public final class ConfigurationManager {

    private static final String COURSE_SET_FILE = "data/courses.dat";
    private static final String INSTRUCTOR_SET_FILE = "data/instructors.dat";

    private ConfigurationManager() {}

    public static void configure(String[] args) {
        Config.configure(args);
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
}
