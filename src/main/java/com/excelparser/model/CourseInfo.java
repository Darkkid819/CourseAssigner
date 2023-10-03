package com.excelparser.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class CourseInfo {

    private String partOfTerm, instructionMethod;
    private ArrayList<Character> days;
    private char campus;
    private LocalTime beginningTime, endTime;

    public CourseInfo() {
        days = new ArrayList<>(8);
    }

    public String getPartOfTerm() {
        return partOfTerm;
    }

    public void setPartOfTerm(String partOfTerm) {
        this.partOfTerm = partOfTerm;
    }

    public String getInstructionMethod() {
        return instructionMethod;
    }

    public void setInstructionMethod(String instructionMethod) {
        this.instructionMethod = instructionMethod;
    }

    public ArrayList<Character> getDays() {
        return days;
    }

    public void setDays(ArrayList<Character> days) {
        this.days = days;
    }

    public char getCampus() {
        return campus;
    }

    public void setCampus(char campus) {
        this.campus = campus;
    }

    public LocalTime getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(LocalTime beginningTime) {
        this.beginningTime = beginningTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
