package com.excelparser.model;

import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.Rank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InstructorInfo implements Serializable {
    private Rank rank;
    private boolean onlineCertified;
    private List<String> coursesCertified;
    private List<Campus> preferredCampuses;

    public InstructorInfo() {
        preferredCampuses = new ArrayList<>(4); // only 4 options
        coursesCertified = new ArrayList<>(20);
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean isOnlineCertified() {
        return onlineCertified;
    }

    public void setOnlineCertified(boolean onlineCertified) {
        this.onlineCertified = onlineCertified;
    }

    public List<String> getCoursesCertified() {
        return coursesCertified;
    }

    public void setCoursesCertified(List<String> coursesCertified) {
        this.coursesCertified = coursesCertified;
    }

    public List<Campus> getPreferredCampuses() {
        return preferredCampuses;
    }

    public void setPreferredCampuses(List<Campus> preferredCampuses) {
        this.preferredCampuses = preferredCampuses;
    }
}
