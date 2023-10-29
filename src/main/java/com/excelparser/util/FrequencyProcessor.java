package com.excelparser.util;

import com.excelparser.model.Course;
import com.excelparser.model.Frequencies;
import com.excelparser.model.InstructorSet;
import com.excelparser.model.enums.Subject;

import java.util.Arrays;
import java.util.List;

final class FrequencyProcessor {

    private static final int INSTRUCTOR_ID_INDEX = 0;
    private static final int FREQUENCY_INDEX = 10;

    private FrequencyProcessor () {}

    static void process(List<String> frequencyData) {
        InstructorSet.getInstance().search(frequencyData.get(INSTRUCTOR_ID_INDEX)).ifPresentOrElse(
                instructor -> instructor.setFrequencies(createFrequenciesFromData(frequencyData)),
                () -> System.out.println("Instructor not found"));
    }

    private static Frequencies createFrequenciesFromData(List<String> frequencyData) {
        String[] courseFrequencyPairs = frequencyData.get(FREQUENCY_INDEX).split(",");
        Frequencies frequencies = new Frequencies();

        for (String data: courseFrequencyPairs) {
            String[] courseFrequencyPair = data.split(":");

            String courseCode = courseFrequencyPair[0].trim();
            int frequency = Integer.parseInt(courseFrequencyPair[1].trim());

            String subject = courseCode.substring(0, 3);
            String number = courseCode.substring(3).replaceAll("\\D+", "");
            boolean hasLab = courseCode.endsWith("L");

            Course course = new Course();
            course.setSubject(Subject.valueOf(subject));
            course.setCourseNumber(number);
            course.setLab(hasLab);
            frequencies.addFrequency(course, frequency);
        }

        return frequencies;
    }
}
