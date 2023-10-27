package com.excelparser.util;

import com.excelparser.model.*;
import com.excelparser.model.enums.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// only accessed within util
final class CourseProcessor {

    // CourseData index values
    private static final int COURSE_NUMBER_INDEX = 2;
    private static final int SUBJECT_INDEX = 1;
    private static final int DAYS_INDEX = 18;
    private static final int START_TIME_INDEX = 19;
    private static final int END_TIME_INDEX = 20;
    private static final int START_DATE_INDEX = 16;
    private static final int END_DATE_INDEX = 17;
    private static final int SECTION_ID_INDEX = 4;
    private static final int COURSE_TITLE_INDEX = 3;
    private static final int CAMPUS_INDEX = 7;
    private static final int PART_OF_TERM_INDEX = 6;
    private static final int INSTRUCTION_METHOD_INDEX = 9;

    private CourseProcessor() {}

    static void process(List<String> courseData) throws IOException {
        final int CRN = Integer.parseInt(courseData.get(SECTION_ID_INDEX));
        SectionSet.getInstance().search(CRN).ifPresent(section -> {
            Course course = createCourseFromData(courseData);
            section.addCourse(course);
        });
        Section section = createSectionFromData(courseData);
        SectionSet.getInstance().add(section);
    }

    private static Section createSectionFromData(List<String> courseData) {
        Section section = new Section(Integer.parseInt(courseData.get(SECTION_ID_INDEX)));
        section.setCourseTitle(courseData.get(COURSE_TITLE_INDEX));
        section.setCampus(Campus.valueOf(courseData.get(CAMPUS_INDEX)));
        section.setPartOfTerm(PartOfTerm.valueOf(courseData.get(PART_OF_TERM_INDEX)));
        section.setInstructionMethod(InstructionMethod.valueOf(courseData.get(INSTRUCTION_METHOD_INDEX)));
        Course course = createCourseFromData(courseData);
        section.addCourse(course);
        return section;
    }

    private static Course createCourseFromData(List<String> courseData) {
        Course course = new Course();
        course.setCourseNumber(courseData.get(COURSE_NUMBER_INDEX));
        course.setSubject(Subject.valueOf(courseData.get(SUBJECT_INDEX)));
        course.setDays(processDays(courseData.get(DAYS_INDEX)));
        course.setTimeRange(processTimeRange(courseData.get(START_TIME_INDEX), courseData.get(END_TIME_INDEX)));
        course.setDateRange(new DateRange(courseData.get(START_DATE_INDEX), courseData.get(END_DATE_INDEX)));
        course.setCredits(4); // all are 4 credits
        if (course.getCourseNumber().equals("103")
                || course.getCourseNumber().equals("210"))
            course.setCredits(3);
        return course;
    }

    private static Set<Day> processDays(String cell) {
        HashSet<Day> days = new HashSet<>(8);
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
