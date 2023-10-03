package com.excelparser.model;

import java.util.ArrayList;

public class InstructorInfo {
    private String rank;
    private boolean onlineCertified;
    private ArrayList<String> coursesCertified;
    private ArrayList<Character> preferredCampuses;

    public InstructorInfo() {
        preferredCampuses = new ArrayList<>(4); // only 4 options
        coursesCertified = new ArrayList<>(20);
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isOnlineCertified() {
        return onlineCertified;
    }

    public void setOnlineCertified(boolean onlineCertified) {
        this.onlineCertified = onlineCertified;
    }

    public ArrayList<String> getCoursesCertified() {
        return coursesCertified;
    }

    public void setCoursesCertified(ArrayList<String> coursesCertified) {
        this.coursesCertified = coursesCertified;
    }

    public ArrayList<Character> getPreferredCampuses() {
        return preferredCampuses;
    }

    public void setPreferredCampuses(ArrayList<Character> preferredCampuses) {
        this.preferredCampuses = preferredCampuses;
    }
}
