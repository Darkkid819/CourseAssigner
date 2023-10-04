package com.excelparser.model;

import com.excelparser.model.enums.Subject;

import java.io.Serializable;

public class Course implements Comparable<Course>, Serializable {

    private String courseNumber, courseTitle;
    private Subject subject;
    private CourseInfo courseInfo;
    private final int CRN;

    public Course(int crn) {
        CRN = crn;
    }

    private String formatCourseNumber(String input) { return String.format("%03d", Integer.parseInt(input));}

    public String getCourseNumber() { return courseNumber; }

    public void setCourseNumber(String courseNumber) { this.courseNumber = formatCourseNumber(courseNumber); }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Subject getSubject() { return subject; }

    public void setSubject(Subject subject) { this.subject = subject; }
    public CourseInfo getCourseInfo() { return courseInfo; }
    public void setCourseInfo(CourseInfo courseInfo) { this.courseInfo = courseInfo; }

    public int getCRN () { return CRN; }

    @Override
    public String toString() {
        return courseTitle + " - " + CRN + subject + formatCourseNumber(courseNumber);
    }

    @Override
    public int compareTo(Course course) {
        return Integer.compare(this.CRN, course.CRN);
    }
}

