package com.excelparser.model;

import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.Day;
import com.excelparser.model.enums.InstructionMethod;
import com.excelparser.model.enums.PartOfTerm;

import java.util.ArrayList;
import java.util.List;

public class CourseInfo {

    private PartOfTerm partOfTerm;
    private InstructionMethod instructionMethod;
    private List<Day> days;
    private Campus campus;
    private TimeRange timeRange;

    public CourseInfo() {
        days = new ArrayList<>(8);
    }

    public PartOfTerm getPartOfTerm() {
        return partOfTerm;
    }

    public void setPartOfTerm(PartOfTerm partOfTerm) {
        this.partOfTerm = partOfTerm;
    }

    public InstructionMethod getInstructionMethod() {
        return instructionMethod;
    }

    public void setInstructionMethod(InstructionMethod instructionMethod) {
        this.instructionMethod = instructionMethod;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public TimeRange getTimeRange() { return timeRange; }
    public void setTimeRange(TimeRange timeRange) { this.timeRange = timeRange; }
}
