package com.excelparser.model;

import java.util.LinkedList;
import java.util.TreeSet;

public class InstructorList {
    private static InstructorList instance;
    private TreeSet<Instructor> instructorList;
    private LinkedList<Instructor> seniority; // needs implementation

    private InstructorList() {
        instructorList = new TreeSet<>();
    }

    // Singleton pattern
    public static InstructorList getInstance() {
        return (instance == null) ? instance = new InstructorList() : instance;
    }

    public void add(Instructor instructor) {
        instructorList.add(instructor);
    }

    public TreeSet<Instructor> getInstructorList() {
        return instructorList;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-----------------\n");
        for (Instructor instructor : instructorList) {
            result.append(instructor.toString());
            result.append("\n-----------------\n");
        }
        return result.toString();
    }
}