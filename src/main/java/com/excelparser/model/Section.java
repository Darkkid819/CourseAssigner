package com.excelparser.model;

import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.InstructionMethod;
import com.excelparser.model.enums.PartOfTerm;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Section implements Comparable<Section>, Serializable {

    private final int CRN;
    private CourseList courseList;
    private String courseTitle;
    private Campus campus;
    private InstructionMethod instructionMethod;
    private PartOfTerm partOfTerm;

    public Section(int CRN) {
        this.CRN = CRN;
        courseList = new CourseList();
    }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public int getCRN() { return CRN; }

    public PartOfTerm getPartOfTerm() {
        return partOfTerm;
    }

    public void setPartOfTerm(PartOfTerm partOfTerm) {
        this.partOfTerm = partOfTerm;
    }

    public InstructionMethod getInstructionMethod() {
        return instructionMethod;
    }

    public void setInstructionMethod(InstructionMethod instructionMethod) { this.instructionMethod = instructionMethod; }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public void addCourse(Course course) { courseList.add(course); }

    public List<Course> getCourseList() { return courseList.getCourseList(); }

    @Override
    public String toString() { return courseTitle + " - " + CRN + " " + courseList.getCourseList().get(0); }

    @Override
    public int compareTo(Section section) { return Integer.compare(this.CRN, section.CRN); }
}