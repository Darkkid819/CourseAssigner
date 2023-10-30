package com.excelparser.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SeniorityList implements Serializable {

    private static class Holder {
        private static final SeniorityList INSTANCE = new SeniorityList();
    }
    private List<Instructor> seniorityList;
    private List<Instructor> subList;

    private SeniorityList() {
        seniorityList = new LinkedList<>();
        subList = seniorityList;
    }

    public static SeniorityList getInstance() { return SeniorityList.Holder.INSTANCE; }

    public void add(Instructor instructor) { seniorityList.add(instructor); }

    public List<Instructor> getSubList(Predicate<Instructor> predicate) {
        if (predicate == null) return seniorityList;
        return seniorityList.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Instructor> getSeniorityList() { return seniorityList; }

    public void copy(SeniorityList instance) {
        this.seniorityList = instance.seniorityList;
        this.subList = instance.subList;
    }
}
