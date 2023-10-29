package com.excelparser.model.enums;

public enum Day {
    M("Monday"), T("Tuesday"), W("Wednesday"), R("Thursday"),
    F("Friday"), S("Saturday"), U("Sunday"), O("Online");

    private String day;

    private Day(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return day;
    }
}