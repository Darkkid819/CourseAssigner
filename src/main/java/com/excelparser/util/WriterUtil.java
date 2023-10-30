package com.excelparser.util;

import com.excelparser.model.Course;
import com.excelparser.model.Instructor;
import com.excelparser.model.InstructorSet;
import com.excelparser.model.SectionSet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class WriterUtil {

    private WriterUtil() {}

    public static void writeTo(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            // Write instructors and their assigned courses
            InstructorSet instructorSet = InstructorSet.getInstance();
            for (Instructor instructor : instructorSet.getInstructorSet()) {
                writer.write(instructor.toString() + "\n");
                List<Course> courses = instructor.getAssignedCourses();
                for (Course course : courses) {
                    writer.write("\t" + course.getFormattedToString() + "\n");
                }
                writer.write("-----------------\n");
            }

            // Write unassigned courses
            writer.write("Unassigned Courses:\n");
            SectionSet sectionSet = SectionSet.getInstance();
            List<Course> allCourses = sectionSet.toList().stream()
                    .flatMap(section -> section.getCourseList().stream())
                    .collect(Collectors.toList());

            List<Course> assignedCourses = instructorSet.getInstructorSet().stream()
                    .flatMap(instructor -> instructor.getAssignedCourses().stream())
                    .collect(Collectors.toList());

            for (Course course : allCourses) {
                if (!assignedCourses.contains(course) && !course.isAssigned()) {
                    writer.write("\t" + course.toString() + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
