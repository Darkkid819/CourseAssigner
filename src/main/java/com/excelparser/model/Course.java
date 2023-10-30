package com.excelparser.model;

import com.excelparser.model.enums.*;

import java.io.Serializable;
import java.util.*;

public class Course implements Comparable<Course>, Serializable {

    private String courseNumber;
    private Subject subject;
    private List<Day> days;
    private TimeRange timeRange;
    private DateRange dateRange;
    private int credits;
    private boolean hasLab;
    private boolean isAssigned;


    public Course() {
        days = new ArrayList<>(8);
    }

    private String formatCourseNumber(String input) { return String.format("%03d", Integer.parseInt(input));}

    public String getCourseNumber() { return courseNumber; }

    public void setCourseNumber(String courseNumber) { this.courseNumber = formatCourseNumber(courseNumber); }

    public Subject getSubject() { return subject; }

    public void setSubject(Subject subject) { this.subject = subject; }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public TimeRange getTimeRange() { return timeRange; }

    public void setTimeRange(TimeRange timeRange) { this.timeRange = timeRange; }

    public DateRange getDateRange() { return dateRange; }

    public void setDateRange(DateRange dateRange) { this.dateRange = dateRange; }

    public int getCredits() { return credits; }

    public void setCredits(int credits) { this.credits = credits; }

    public boolean hasLab() { return hasLab; }

    public void setLab(boolean hasLab) { this.hasLab = hasLab; }

    public boolean isAssigned() { return isAssigned; }

    public void setAssigned(boolean isAssigned) { this.isAssigned = isAssigned; }

    public String getFormattedToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject)
                .append(formatCourseNumber(courseNumber))
                .append(hasLab ? "L" : "")
                .append(" - ")
                .append(credits)
                .append(" credits - ")
                .append(days)
                .append(" - ")
                .append(timeRange)
                .append(" - ")
                .append(dateRange);
        return sb.toString();
    }

    @Override
    public String toString() {
        return subject + formatCourseNumber(courseNumber) + (hasLab ? "L" : "");
    }

    @Override
    public int compareTo(Course o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (credits != course.credits) return false;
        if (!this.toString().equals(course.toString())) return false;
        if (!days.equals(course.days)) return false;
        if (!timeRange.equals(course.timeRange)) return false;
        return dateRange.equals(course.dateRange);
    }

    @Override
    public int hashCode() {
        int result = toString().hashCode();
        result = 31 * result + days.hashCode();
        result = 31 * result + timeRange.hashCode();
        result = 31 * result + dateRange.hashCode();
        result = 31 * result + credits;
        return result;
    }
}

