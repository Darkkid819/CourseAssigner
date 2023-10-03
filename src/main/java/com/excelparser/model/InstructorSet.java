package com.excelparser.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.TreeSet;

public class InstructorSet implements Serializable {

    // Bill Pugh Singleton Implementation
    private static class Holder {
        private static final InstructorSet INSTANCE = new InstructorSet();
    }

    private static InstructorSet instance;
    private TreeSet<Instructor> instructorSet;

    private InstructorSet() {
        instructorSet = new TreeSet<>();
    }

    public static InstructorSet getInstance() {
        return Holder.INSTANCE;
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