package com.excelparser.util;

import com.excelparser.model.*;
import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.Rank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// only accessed in util package
final class InstructorProcessor{

    // Instructor information indexes
    private static final int INSTRUCTOR_ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int RANK_INDEX = 3;
    private static final int ONLINE_CERTIFIED_INDEX = 4;
    private static final int PREFERRED_CAMPUS_INDEX = 5;
    private static final int HOME_CAMPUS_INDEX = 15;
    private static final int SECOND_COURSE_REQUESTED_INDEX = 6;
    private static final int THIRD_COURSE_REQUESTED_INDEX = 21;
    private static final int AVAILABILITY_EARLY_MORNING_INDEX = 8;
    private static final int AVAILABILITY_MORNING_INDEX = 23;
    private static final int AVAILABILITY_AFTERNOON_INDEX = 9;
    private static final int AVAILABILITY_LATE_AFTERNOON_INDEX = 24;
    private static final int AVAILABILITY_EVENING_INDEX = 11;
    private static final int AVAILABILITY_LATE_EVENING_INDEX = 12;
    private static final int AVAILABILITY_SAT_INDEX = 10;
    private static final int AVAILABILITY_SUN_INDEX = 25;
    private static final int COURSES_CERTIFIED_START_INDEX = 32;

    private InstructorProcessor() {}

    static void process(List<String> instructorData) throws IOException {
        Instructor instructor = createInstructorFromData(instructorData);
        InstructorSet.getInstance().add(instructor);
    }

    private static Instructor createInstructorFromData(List<String> instructorData) {
        Instructor instructor = new Instructor(instructorData.get(INSTRUCTOR_ID_INDEX));
        instructor.setName(parseName(instructorData.get(NAME_INDEX)));
        instructor.setHomeCampus(instructorData.get(HOME_CAMPUS_INDEX));
        instructor.setCoursesRequested(parseCoursesRequested(
                instructorData.get(SECOND_COURSE_REQUESTED_INDEX),
                instructorData.get(THIRD_COURSE_REQUESTED_INDEX)
        ));
        InstructorInfo instructorInfo = processInstructorInfo(instructorData);
        instructor.setInstructorInfo(instructorInfo);
        boolean[][] availability = processAvailability(instructorData);
        instructor.setAvailability(availability);
        return instructor;
    }

    private static InstructorInfo processInstructorInfo(List<String> instructorData) {
        InstructorInfo instructorInfo = new InstructorInfo();
        instructorInfo.setRank(Rank.valueOf(instructorData.get(RANK_INDEX)));
        instructorInfo.setPreferredCampuses(parsePreferredCampuses(instructorData.get(PREFERRED_CAMPUS_INDEX)));
        instructorInfo.setOnlineCertified(parseOnlineCertified(instructorData.get(ONLINE_CERTIFIED_INDEX)));
        instructorInfo.setCoursesCertified(parseCoursesCertified(instructorData, COURSES_CERTIFIED_START_INDEX));
        return instructorInfo;
    }

    private static boolean[][] processAvailability(List<String> instructorData) {
        boolean availableSaturday = instructorData.get(AVAILABILITY_SAT_INDEX).contains("Sat");
        boolean availableSunday = instructorData.get(AVAILABILITY_SUN_INDEX).contains("Sun");

        // Parse availability as Strings before processing boolean array
        boolean[] availability7to8 = parseAvailability(instructorData.get(AVAILABILITY_EARLY_MORNING_INDEX), true, availableSaturday, availableSunday);
        boolean[] availability8to12 = parseAvailability(instructorData.get(AVAILABILITY_MORNING_INDEX), false, availableSaturday, availableSunday);
        boolean[] availability12to3 = parseAvailability(instructorData.get(AVAILABILITY_AFTERNOON_INDEX), false, availableSaturday, availableSunday);
        boolean[] availability3to4 = parseAvailability(instructorData.get(AVAILABILITY_LATE_AFTERNOON_INDEX), true, availableSaturday, availableSunday);
        boolean[] availability4to6 = parseAvailability(instructorData.get(AVAILABILITY_EVENING_INDEX), false, availableSaturday, availableSunday);
        boolean[] availability6to10 = parseAvailability(instructorData.get(AVAILABILITY_LATE_EVENING_INDEX), false, availableSaturday, availableSunday);

        return new boolean[][] {
                availability7to8,
                availability8to12,
                availability12to3,
                availability3to4,
                availability4to6,
                availability6to10
        };
    }

    private static Name parseName(String name) {
        String[] parts = name.split(",");
        return new Name(parts[0].trim(), parts[1].trim());
    }

    private static ArrayList<Campus> parsePreferredCampuses(String preferredCampuses) {
        ArrayList<Campus> campuses = new ArrayList<>();

        for (int i = 0; i < preferredCampuses.length(); i++) {
            char currentChar = preferredCampuses.charAt(i);

            if (currentChar == 'A' || currentChar == 'E' || currentChar == 'W' || currentChar == 'O') {
                String campus = String.valueOf(currentChar);
                campuses.add(Campus.valueOf(campus));
            }
        }

        return campuses;
    }

    private static boolean parseOnlineCertified(String onlineCertified) {
        return onlineCertified != null && onlineCertified.equalsIgnoreCase("Y");
    }

    private static ArrayList<String> parseCoursesCertified(List<String> instructorData, int startIndex) {
        ArrayList<String> courses = new ArrayList<>();

        for (int i = startIndex; i < instructorData.size(); i++) {
            String cellValue = instructorData.get(i);
            if (cellValue != null && !cellValue.isEmpty())
                courses.add(cellValue);
        }

        return courses;
    }

    private static int parseCoursesRequested(String secondCourseRequested, String thirdCourseRequested) {
        boolean isSecondRequested = "Y".equalsIgnoreCase(secondCourseRequested);
        boolean isThirdRequested = "Y".equalsIgnoreCase(thirdCourseRequested);

        if (isSecondRequested && isThirdRequested) {
            return 3;
        } else if (isSecondRequested || isThirdRequested) {
            return 2;
        } else {
            return 1;
        }
    }

    private static boolean[] parseAvailability(String cell, boolean isSpecial, boolean availableSaturday, boolean availableSunday) {
        boolean[] availability = new boolean[7];

        if (isSpecial) {
            parseWeekDay(cell, availability, true);
        } else {
            parseWeekDay(cell, availability, false);
        }

        availability[5] = availableSaturday;
        availability[6] = availableSunday;

        return availability;
    }

    private static void parseWeekDay(String cell, boolean[] availability, boolean isSpecial) {
        if (cell == null || cell.length() < 5) return;

        char[] days = {'M', 'T', 'W', 'T', 'F'};
        String reference = isSpecial ? cell.substring(1) : cell;

        for (int i = 0; i < days.length; i++) {
            if (reference.indexOf(days[i]) != -1) {
                availability[i] = true;
            }
        }
    }
}
