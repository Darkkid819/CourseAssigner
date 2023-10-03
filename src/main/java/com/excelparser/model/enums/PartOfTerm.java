package com.excelparser.model.enums;

public enum PartOfTerm {
    FE, FD, SS, LSD, ONL, LS, LSE, ED; // LS and ED are not supposed to be valid

    @Override
    public String toString() {
        return this.name();
    }
}
