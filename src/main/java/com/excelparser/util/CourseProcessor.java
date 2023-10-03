package com.excelparser.util;

import com.excelparser.model.*;
import com.excelparser.model.enums.*;

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
        course.setSubject(Subject.valueOf(row[1]));
        course.setCourseNumber(row[2]);
        CourseInfo courseInfo = processCourseInfo(row);
        course.setCourseInfo(courseInfo);
        return course;
    }


    private static CourseInfo processCourseInfo(String[] row) {
        CourseInfo courseInfo = new CourseInfo();

        courseInfo.setPartOfTerm(PartOfTerm.valueOf(row[6]));
        courseInfo.setInstructionMethod(InstructionMethod.valueOf(row[9]));
        courseInfo.setDays(processDays(row[18]));
        courseInfo.setCampus(Campus.valueOf(row[7]));
        courseInfo.setTimeRange(processTimeRange(row[19], row[20]));

        return courseInfo;
    }

    private static ArrayList<Day> processDays(String cell) {
        ArrayList<Day> days = new ArrayList<>(7);
        char[] daysOfWeek = cell.toCharArray();
        for (char c: daysOfWeek) {
            String day = String.valueOf(c);
            days.add(Day.valueOf(day));
        }
        return days;
    }

    private static TimeRange processTimeRange(String startTime, String endTime) {
        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        LocalTime formattedStartTime = LocalTime.parse(startTime, formatter);
        LocalTime formattedEndTime = LocalTime.parse(endTime, formatter);

        return new TimeRange(formattedStartTime, formattedEndTime);
    }
}
