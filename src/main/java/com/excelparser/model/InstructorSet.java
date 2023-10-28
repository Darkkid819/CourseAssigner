package com.excelparser.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.TreeSet;

public class InstructorSet implements Serializable, Cloneable {

    private static class Holder {
        private static final InstructorSet INSTANCE = new InstructorSet();
    }
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

    public Optional<Instructor> search(String id) {
        return instructorSet.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public void copy(InstructorSet instance) {
        this.instructorSet = instance.instructorSet;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        InstructorSet cloned = (InstructorSet) super.clone();
        cloned.instructorSet = new TreeSet<>(this.instructorSet);
        return cloned;
    }
}