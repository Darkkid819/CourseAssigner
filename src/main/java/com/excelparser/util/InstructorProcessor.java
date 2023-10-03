package com.excelparser.util;

import com.excelparser.model.*;
import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.Rank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// only accessed in util package
public final class InstructorProcessor {

    private InstructorProcessor() {}

    public static void processInstructors(String xlsxFilePath) throws IOException {
        List<List<String>> data = InstructorXLSXParser.parse(xlsxFilePath);
        for (List<String> instructorData : data) {
            addInstructor(instructorData);
        }
    }

    private static void addInstructor(List<String> instructorData) {
        Instructor instructor = createInstructorFromData(instructorData);
        InstructorSet.getInstance().add(instructor);
    }

    private static Instructor createInstructorFromData(List<String> instructorData) {
        Instructor instructor = new Instructor(instructorData.get(0));
        instructor.setName(parseName(instructorData.get(1)));
        instructor.setHomeCampus(instructorData.get(15));
        instructor.setCoursesRequested(parseCoursesRequested(instructorData.get(6), instructorData.get(21)));
        InstructorInfo instructorInfo = processInstructorInfo(instructorData);
        instructor.setInstructorInfo(instructorInfo);
        boolean[][] availability = processAvailability(instructorData);
        instructor.setAvailability(availability);
        return instructor;
    }

    private static InstructorInfo processInstructorInfo(List<String> instructorData) {
        InstructorInfo instructorInfo = new InstructorInfo();
        instructorInfo.setRank(Rank.valueOf(instructorData.get(3)));
        instructorInfo.setPreferredCampuses(parsePreferredCampuses(instructorData.get(5)));
        instructorInfo.setOnlineCertified(parseOnlineCertified(instructorData.get(4)));
        instructorInfo.setCoursesCertified(parseCoursesCertified(instructorData, 32));
        return instructorInfo;
    }

    private static boolean[][] processAvailability(List<String> instructorData) {
        boolean availableSaturday = instructorData.get(10).contains("Sat") || instructorData.get(25).contains("Sat");
        boolean availableSunday = instructorData.get(10).contains("Sun") || instructorData.get(25).contains("Sun");

        // Parse availability as Strings before processing boolean array
        boolean[] availability7to8 = parseAvailability(instructorData.get(8), instructorData.get(23), true, availableSaturday, availableSunday);
        boolean[] availability8to12 = parseAvailability(instructorData.get(8), instructorData.get(23), false, availableSaturday, availableSunday);
        boolean[] availability12to3 = parseAvailability(instructorData.get(9), instructorData.get(24), false, availableSaturday, availableSunday);
        boolean[] availability3to4 = parseAvailability(instructorData.get(9), instructorData.get(24), true, availableSaturday, availableSunday);
        boolean[] availability4to6 = parseAvailability(instructorData.get(11), "", false, availableSaturday, availableSunday); // only one cell
        boolean[] availability6to10 = parseAvailability(instructorData.get(12), "", false, availableSaturday, availableSunday); // only one cell

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
        String[] parts = name.split(" ");
        return new Name(parts[0], parts[1]);
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

        // Remove duplicates
        return (ArrayList<Campus>) campuses.stream().distinct().collect(Collectors.toList());
    }

    private static boolean parseOnlineCertified(String onlineCertified) {
        return onlineCertified != null && onlineCertified.equalsIgnoreCase("Y");
    }

    private static ArrayList<String> parseCoursesCertified(List<String> instructorData, int startIndex) {
        ArrayList<String> courses = new ArrayList<>();

        for (int i = startIndex; i < instructorData.size(); i++) {
            String cellValue = instructorData.get(i);
            if (cellValue != null && !cellValue.isEmpty()) {
                String[] splitCourses = cellValue.split("\\s+"); // split by whitespace
                for (String course : splitCourses)
                    courses.add(course);
            }
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

    private static boolean[] parseAvailability(String firstCell, String secondCell, boolean isSpecial, boolean availableSaturday, boolean availableSunday) {
        boolean[] availability = new boolean[7];

        // Excel cells either have a star in first or second cell so this is necessary for now
        if (isSpecial) {
            if (firstCell.contains("*")) parseWeekDay(firstCell, availability);
            if (secondCell.contains("*")) parseWeekDay(secondCell, availability);
        } else {
            if (!firstCell.contains("*")) parseWeekDay(firstCell, availability);
            if (!secondCell.contains("*")) parseWeekDay(secondCell, availability);
        }

        availability[5] = availableSaturday;
        availability[6] = availableSunday;

        return availability;
    }

    private static void parseWeekDay(String cell, boolean[] availability) {
        if (cell.contains("M")) {
            availability[0] = true;
        }
        // Tuesday and Thursday have a T in the cell and the index of the cell is inconsistent so this will do for now
        int count = (int) cell.chars().filter(ch -> ch == 'T').count();
        if (count == 1 || count == 2) {
            availability[1] = true;
        }
        if (cell.contains("W")) {
            availability[2] = true;
        }
        if (count == 2) {
            availability[3] = true; // Thursday
        }
        if (cell.contains("F")) {
            availability[4] = true;
        }
    }
}
