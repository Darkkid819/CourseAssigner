package com.excelparser.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.LinkedList;

public class SeniorityList implements Serializable {

    private static class Holder {
        private static final SeniorityList INSTANCE = new SeniorityList();
    }
    private LinkedList<Instructor> seniorityList;

    private SeniorityList() { seniorityList = new LinkedList<>(); }

    public static SeniorityList getInstance() { return SeniorityList.Holder.INSTANCE; }

    public void add(Instructor instructor) { seniorityList.add(instructor); }

    public ObservableList<Instructor> toObservableList() { return FXCollections.observableList(seniorityList); }

    public void copy(SeniorityList instance) {
        this.seniorityList = instance.seniorityList;
    }
}
