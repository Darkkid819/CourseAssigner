package com.excelparser.model.enums;

public enum Day {
    M, T, W, R, F, S, U, O;

    @Override
    public String toString() {
        return name();
    }
}