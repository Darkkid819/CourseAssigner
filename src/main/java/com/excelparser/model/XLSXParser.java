package com.excelparser.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLSXParser {
    private String filePath;

    public XLSXParser(String filePath) {
        this.filePath = filePath;
    }

    public void parse() throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet

            int currentRow = 0;

            while (currentRow < sheet.getLastRowNum()) {
                Row row = sheet.getRow(currentRow);
                currentRow++;

                // Skip rows until a row starting with "—" is encountered
                if (row == null || row.getCell(0) == null || !row.getCell(0).getStringCellValue().startsWith("—")) {
                    continue;
                }

                // Process instructor data and call the add methodInstructors.
                processInstructorData(sheet, currentRow);
            }
        }
    }

    private void processInstructorData(Sheet sheet, int currentRow) {
        List<String> instructorData = new ArrayList<>(75); // Store cell values for each instructor

        while (currentRow <= sheet.getLastRowNum() + 1) {
            Row row = sheet.getRow(currentRow);

            if (row == null || row.getCell(0).getStringCellValue().startsWith("—")) {
                // End of instructor data, process and add to InstructorList object
                addInstructor(instructorData);
                break;
            } else {
                // Process and add all cell values in the row to the list
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    instructorData.add(cellValue);
                }
                currentRow++;
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Handle empty cells
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private void addInstructor(List<String> instructorData) {
        Instructor instructor = new Instructor(instructorData.get(0));
        instructor.setName(instructorData.get(1));
        instructor.setRank(instructorData.get(3));
        instructor.setHomeCampus(instructorData.get(15));
        instructor.setPreferredCampuses(parsePreferredCampuses(instructorData.get(5)));
        instructor.setOnlineCertified(parseOnlineCertified(instructorData.get(4)));
        instructor.setCoursesCertified(parseCoursesCertified(instructorData, 32));
        instructor.setCoursesRequested(parseCoursesRequested(instructorData.get(6), instructorData.get(21)));

        boolean availableSaturday = instructorData.get(10).contains("Sat") || instructorData.get(25).contains("Sat");
        boolean availableSunday = instructorData.get(10).contains("Sun") || instructorData.get(25).contains("Sun");

        // Parse availability as Strings before processing boolean array
        boolean[] availability7to8 = parseAvailability(instructorData.get(8), instructorData.get(23), true, availableSaturday, availableSunday);
        boolean[] availability8to12 = parseAvailability(instructorData.get(8), instructorData.get(23), false, availableSaturday, availableSunday);
        boolean[] availability12to3 = parseAvailability(instructorData.get(9), instructorData.get(24), false, availableSaturday, availableSunday);
        boolean[] availability3to4 = parseAvailability(instructorData.get(9), instructorData.get(24), true, availableSaturday, availableSunday);
        boolean[] availability4to6 = parseAvailability(instructorData.get(11), "", false, availableSaturday, availableSunday); // only one cell
        boolean[] availability6to10 = parseAvailability(instructorData.get(12), "", false, availableSaturday, availableSunday); // only one cell

        boolean[][] availability = {
                availability7to8,
                availability8to12,
                availability12to3,
                availability3to4,
                availability4to6,
                availability6to10
        };
        instructor.setAvailability(availability);

        InstructorList.getInstance().add(instructor);
    }

    private String parsePreferredCampuses(String preferredCampuses) {
        StringBuilder fullCampusNames = new StringBuilder();

        if (preferredCampuses.contains("A")) {
            fullCampusNames.append("Ammerman, ");
        }
        if (preferredCampuses.contains("E")) {
            fullCampusNames.append("Eastern, ");
        }
        if (preferredCampuses.contains("W")) {
            fullCampusNames.append("Western, ");
        }
        if (preferredCampuses.contains("O")) {
            fullCampusNames.append("Online, ");
        }

        // Remove the trailing comma and space if any
        if (fullCampusNames.length() > 0) {
            fullCampusNames.delete(fullCampusNames.length() - 2, fullCampusNames.length());
        }

        return fullCampusNames.toString();
    }

    private boolean parseOnlineCertified(String onlineCertified) {
        return onlineCertified != null && onlineCertified.equalsIgnoreCase("Y");
    }

    private String parseCoursesCertified(List<String> instructorData, int startIndex) {
        StringBuilder courses = new StringBuilder();

        for (int i = startIndex; i < instructorData.size(); i++) {
            String cellValue = instructorData.get(i);
            if (cellValue != null && !cellValue.isEmpty()) {
                courses.append(cellValue).append(", ");
            }
        }

        if (courses.length() > 0) {
            // Remove the trailing ", " if courses were added
            courses.setLength(courses.length() - 2);
        }

        return courses.toString();
    }

    private int parseCoursesRequested(String secondCourseRequested, String thirdCourseRequested) {
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

    private boolean[] parseAvailability(String firstCell, String secondCell, boolean isSpecial, boolean availableSaturday, boolean availableSunday) {
        boolean[] availability = new boolean[7];

        // Excel cells either have a star in first or second cell so this is necessary for now
        if (isSpecial) {
            if (firstCell.contains("*")) checkWeekDay(firstCell, availability);
            if (secondCell.contains("*")) checkWeekDay(secondCell, availability);
        } else {
            if (!firstCell.contains("*")) checkWeekDay(firstCell, availability);
            if (!secondCell.contains("*")) checkWeekDay(secondCell, availability);
        }

        availability[5] = availableSaturday;
        availability[6] = availableSunday;

        return availability;
    }

    private void checkWeekDay(String cell, boolean[] availability) {
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

