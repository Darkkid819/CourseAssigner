package com.excelparser.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.TreeSet;

public class CourseSet implements Serializable {

    // Bill Pugh Singleton Implementation
    private static class Holder {
        private static final CourseSet INSTANCE = new CourseSet();
    }
    private TreeSet<Course> courseSet;

    private CourseSet() {
        courseSet = new TreeSet<>();
    }

    public static CourseSet getInstance() {
        return Holder.INSTANCE;
    }

    public void add(Course course) {
        courseSet.add(course);
    }

    public void remove(int CRN) {
        courseSet.removeIf(i -> i.getCRN() == CRN);
    }

    public Optional<Course> search(int CRN) {
        return courseSet.stream().filter(i -> i.getCRN() == CRN).findFirst();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-----------------\n");
        for (Course course : courseSet) {
            result.append(course.toString());
            result.append("\n-----------------\n");
        }
        return result.toString();
    }
}
