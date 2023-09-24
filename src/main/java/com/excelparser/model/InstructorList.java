package com.excelparser.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.TreeSet;

public class InstructorList {
    private static InstructorList instance;
    private TreeSet<Instructor> instructorList;
    private LinkedList<Instructor> seniority; // needs implementation

    private InstructorList() {
        instructorList = new TreeSet<>();
    }

    // Synchronized singleton pattern
    public synchronized static InstructorList getInstance() {
        return (instance == null) ? instance = new InstructorList() : instance;
    }

    public void add(Instructor instructor) {
        instructorList.add(instructor);
    }

    public void remove(String id) {
        instructorList.removeIf(i -> i.getId().equals(id));
    }

    public Optional<Instructor> search(String id) {
        return instructorList.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Instructor[] toArray() {return instructorList.toArray(new Instructor[0]);}

    // Used for the controller class when using side buttons
    public int index(Instructor instructor) {
        return instructorList.headSet(instructor, false).size();
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