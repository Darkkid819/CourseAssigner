package com.excelparser.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Instructor implements Comparable<Instructor>, Serializable {
    private final String ID;
    private Name name;
    private InstructorInfo instructorInfo;
    private String homeCampus;
    private int coursesRequested;
    private boolean[][] availability;
    private final int PERIOD = 6, DAYS = 7;

    // need to use setters to set the values of the other fields
    public Instructor(String id) {
        this.ID = id;
        this.availability = new boolean[PERIOD][DAYS];
    }

    public String getId() {
        return ID;
    }

    public Name getName() {
        return name;
    }


    public InstructorInfo getInstructorInfo() { return instructorInfo; }

    public String getHomeCampus() {
        return homeCampus;
    }

    public int getCoursesRequested() {
        return coursesRequested;
    }
    public boolean[][] getAvailability() {
        return availability;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setInstructorInfo(InstructorInfo instructorInfo) {
        this.instructorInfo = instructorInfo;
    }

    public void setHomeCampus(String homeCampus) {
        this.homeCampus = homeCampus;
    }

    public void setCoursesRequested(int coursesRequested) {
        this.coursesRequested = coursesRequested;
    }

    public void setAvailability(boolean[][] availability) {this.availability = availability;}

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public int compareTo(Instructor other) {
        return this.ID.compareTo(other.ID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;
        Instructor instructor = (Instructor) o;
        return this.ID.equals(instructor.ID);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ID);
        result = 31 * result + Arrays.hashCode(availability);
        return result;
    }
}
