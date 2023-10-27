package com.excelparser.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.TreeSet;

public class SectionSet implements Serializable {

    // Bill Pugh Singleton Implementation
    private static class Holder {
        private static final SectionSet INSTANCE = new SectionSet();
    }
    private TreeSet<Section> sectionSet;

    private SectionSet() {
        sectionSet = new TreeSet<>();
    }

    public static SectionSet getInstance() {
        return SectionSet.Holder.INSTANCE;
    }

    public void add(Section section) {
        sectionSet.add(section);
    }

    public Optional<Section> search(int CRN) {
        return sectionSet.stream().filter(i -> i.getCRN() == (CRN)).findFirst();
    }

    public void copy(SectionSet instance) {
        this.sectionSet = instance.sectionSet;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-----------------\n");
        for (Section section : sectionSet) {
            result.append(section);
            result.append("\n-----------------\n");
        }
        return result.toString();
    }
}
