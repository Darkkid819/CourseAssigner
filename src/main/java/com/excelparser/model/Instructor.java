package com.excelparser.model;

public class Instructor implements Comparable<Instructor> {
    private final String ID;
    private String name;
    private String rank;
    private String homeCampus;
    private String preferredCampuses;
    private boolean onlineCertified;
    private String coursesCertified;
    private int coursesRequested;
    private boolean[][] availability;

    // need to use setters to set the values of the other fields
    public Instructor(String id) {
        this.ID = id;
        this.availability = new boolean[6][7];
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

    public String getPreferredCampuses() {
        return preferredCampuses;
    }

    public boolean isOnlineCertified() {
        return onlineCertified;
    }

    public String getCoursesCertified() {
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

    public void setPreferredCampuses(String preferredCampuses) {
        this.preferredCampuses = preferredCampuses;
    }

    public void setOnlineCertified(boolean onlineCertified) {
        this.onlineCertified = onlineCertified;
    }

    public void setCoursesCertified(String coursesCertified) {
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
}
