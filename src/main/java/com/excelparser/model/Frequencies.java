package com.excelparser.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Frequencies implements Serializable {

    private Map<Course, Integer> courseFrequencies;

    public Frequencies() {
        this.courseFrequencies = new TreeMap<>();
    }

    public void addFrequency(Course course, int frequency) {
        courseFrequencies.put(course, frequency);
    }

    public Integer getFrequency(Course course) {
        return courseFrequencies.get(course);
    }

    public Map<Course, Integer> getAllFrequencies() {
        return courseFrequencies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Course, Integer> entry : courseFrequencies.entrySet()) {
            sb.append(entry.getKey().toString())
                    .append(": ")
                    .append(entry.getValue())
                    .append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

}
