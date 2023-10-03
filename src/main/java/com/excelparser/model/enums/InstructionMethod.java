package com.excelparser.model.enums;

public enum InstructionMethod {
    TR, BLBD;

    @Override
    public String toString() {
        return this.name();
    }
}
