package com.excelparser.model.enums;

public enum Campus {
    A("Ammerman"), W("Western"), E("Eastern"), O("Online");

    private String campus;

    private Campus(String campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return campus;
    }
}
