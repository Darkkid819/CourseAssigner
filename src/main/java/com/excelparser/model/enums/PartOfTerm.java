package com.excelparser.model.enums;

public enum PartOfTerm {
    FE("Evening"), FD("Day"), SS("Saturday or Sunday"), LSD("Late Start Day"),
    ONL("Online"), LS("Late Start"), LSE("Late Start Evening"), ED("Early Day");

    private String partOfTerm;

    private PartOfTerm(String partOfTerm) {
        this.partOfTerm = partOfTerm;
    }

    @Override
    public String toString() {
        return partOfTerm;
    }
}
