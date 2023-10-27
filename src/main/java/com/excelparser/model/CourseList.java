package com.excelparser.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// only accessed within model
class CourseList implements Serializable {
    private List<Course> courseList;

    CourseList() {
        courseList = new LinkedList<>();
    }

    void add(Course course) {
        courseList.add(course);
    }

    List<Course> getCourseList() {
        return courseList;
    }
}
