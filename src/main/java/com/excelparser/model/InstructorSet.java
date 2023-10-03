package com.excelparser.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.TreeSet;

public class InstructorSet {
    private static InstructorSet instance;
    private TreeSet<Instructor> instructorSet;

    private InstructorSet() {
        instructorSet = new TreeSet<>();
    }

    // Synchronized singleton pattern
    public synchronized static InstructorSet getInstance() {
        return (instance == null) ? instance = new InstructorSet() : instance;
    }

    public void add(Instructor instructor) {
        instructorSet.add(instructor);
    }

    public void remove(String id) {
        instructorSet.removeIf(i -> i.getId().equals(id));
    }

    public Optional<Instructor> search(String id) {
        return instructorSet.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Instructor[] toArray() {return instructorSet.toArray(new Instructor[0]);}

    // Used for the controller class when using side buttons
    public int index(Instructor instructor) {
        return instructorSet.headSet(instructor, false).size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-----------------\n");
        for (Instructor instructor : instructorSet) {
            result.append(instructor.toString());
            result.append("\n-----------------\n");
        }
        return result.toString();
    }
}