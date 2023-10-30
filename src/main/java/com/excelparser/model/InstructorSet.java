package com.excelparser.model;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class InstructorSet implements Serializable {

    private static class Holder {
        private static final InstructorSet INSTANCE = new InstructorSet();
    }
    private TreeSet<Instructor> instructorSet;

    public int courseAssignment = 1;

    private InstructorSet() {
        instructorSet = new TreeSet<>();
    }

    public static InstructorSet getInstance() {
        return Holder.INSTANCE;
    }

    public void add(Instructor instructor) {
        instructorSet.add(instructor);
    }

    public Optional<Instructor> search(String id) {
        return instructorSet.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Set<Instructor> getInstructorSet() { return instructorSet; }

    public void copy(InstructorSet instance) {
        this.instructorSet = instance.instructorSet;
        this.courseAssignment = instance.courseAssignment;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-----------------\n");
        for (Instructor instructor : instructorSet) {
            result.append(instructor);
            result.append("\n-----------------\n");
        }
        return result.toString();
    }
}