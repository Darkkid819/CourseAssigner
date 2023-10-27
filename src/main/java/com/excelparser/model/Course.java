package com.excelparser.model;

import com.excelparser.model.enums.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable {

    private String courseNumber;
    private Subject subject;
    private Set<Day> days;
    private TimeRange timeRange;
    private DateRange dateRange;
    private int credits;


    public Course() { days = new HashSet<>(8); }

    private String formatCourseNumber(String input) { return String.format("%03d", Integer.parseInt(input));}

    public String getCourseNumber() { return courseNumber; }

    public void setCourseNumber(String courseNumber) { this.courseNumber = formatCourseNumber(courseNumber); }

    public Subject getSubject() { return subject; }

    public void setSubject(Subject subject) { this.subject = subject; }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public TimeRange getTimeRange() { return timeRange; }

    public void setTimeRange(TimeRange timeRange) { this.timeRange = timeRange; }

    public DateRange getDateRange() { return dateRange; }

    public void setDateRange(DateRange dateRange) { this.dateRange = dateRange; }

    public int getCredits() { return credits; }

    public void setCredits(int credits) { this.credits = credits; }

    @Override
    public String toString() {
        return subject + formatCourseNumber(courseNumber);
    }
}

