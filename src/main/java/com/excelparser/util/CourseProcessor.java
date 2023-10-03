package com.excelparser.util;

import com.excelparser.model.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class CourseProcessor {

    private static final int MIN_ROW_LENGTH = 24;

    public static void processCourses(String csvFilePath) throws IOException {
        List<String[]> data = CourseCSVParser.parse(csvFilePath);
        addCourses(data);
    }

    private static void addCourses(List<String[]> data) {
        data.remove(0); // remove header row
        for (String[] row : data) {
            if (row.length < MIN_ROW_LENGTH) continue; // skip rows with missing data
            Course course = createCourseFromRow(row);
            CourseSet.getInstance().add(course);
        }
    }

    private static Course createCourseFromRow(String[] row) {
        Course course = new Course(Integer.parseInt(row[4]));
        course.setCourseTitle(row[3]);
        course.setSubject(row[1]);
        course.setCourseNumber(row[2]);
        CourseInfo courseInfo = processCourseInfo(row);
        course.setCourseInfo(courseInfo);
        return course;
    }


    private static CourseInfo processCourseInfo(String[] row) {
        CourseInfo courseInfo = new CourseInfo();

        courseInfo.setPartOfTerm(row[5]);
        courseInfo.setInstructionMethod(row[6]);
        courseInfo.setDays(processDays(row[18]));
        courseInfo.setCampus(row[7].charAt(0));
        courseInfo.setBeginningTime(processTime(row[19]));
        courseInfo.setEndTime(processTime(row[20]));

        return courseInfo;
    }

    private static ArrayList<Character> processDays(String cell) {
        ArrayList<Character> days = new ArrayList<>(7);
        char[] daysOfWeek = cell.toCharArray();
        for (char c: daysOfWeek) { days.add(c); }
        return days;
    }

    private static LocalTime processTime(String time) {
        if (time == null || time.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        LocalTime formattedTime = LocalTime.parse(time, formatter);
        return formattedTime;
    }
}
