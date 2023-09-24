package com.excelparser.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Instructor implements Comparable<Instructor> {
    private final String ID;
    private String name, rank, homeCampus;
    private ArrayList<Character> preferredCampuses;
    private ArrayList<String> coursesCertified;
    private boolean onlineCertified;
    private int coursesRequested;
    private boolean[][] availability;
    private final int TIME_SLOTS = 6, DAYS = 7;

    // need to use setters to set the values of the other fields
    public Instructor(String id) {
        this.ID = id;
        preferredCampuses = new ArrayList<>(4); // only 4 options
        coursesCertified = new ArrayList<>(20);
        this.availability = new boolean[TIME_SLOTS][DAYS];
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return ID;
    }

    public String getRank() {
        return rank;
    }

    public String getHomeCampus() {
        return homeCampus;
    }

    public ArrayList<Character> getPreferredCampuses() {
        return preferredCampuses;
    }

    public boolean isOnlineCertified() {
        return onlineCertified;
    }

    public ArrayList<String> getCoursesCertified() {
        return coursesCertified;
    }

    public int getCoursesRequested() {
        return coursesRequested;
    }
    public boolean[][] getAvailability() {
        return availability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setHomeCampus(String homeCampus) {
        this.homeCampus = homeCampus;
    }

    public void setPreferredCampuses(ArrayList<Character> preferredCampuses) {
        this.preferredCampuses = preferredCampuses;
    }

    public void setOnlineCertified(boolean onlineCertified) {
        this.onlineCertified = onlineCertified;
    }

    public void setCoursesCertified(ArrayList<String> coursesCertified) {
        this.coursesCertified = coursesCertified;
    }

    public void setCoursesRequested(int coursesRequested) {
        this.coursesRequested = coursesRequested;
    }

    public void setAvailability(boolean[][] availability) {this.availability = availability;}

    @Override
    public String toString() {
        return "Instructor ID: " + ID +
                "\nName: " + name +
                "\nRank: " + rank +
                "\nHome Campus: " + homeCampus +
                "\nPreferred Campuses: " + preferredCampuses +
                "\nOnline Certified: " + (onlineCertified ? "Yes" : "No") +
                "\nCourses Certified: " + coursesCertified +
                "\nCourses Requested: " + coursesRequested;
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
