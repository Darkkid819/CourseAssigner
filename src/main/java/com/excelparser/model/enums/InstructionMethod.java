package com.excelparser.model.enums;

public enum InstructionMethod {
    TR("In Person"), BLBD("Online or Blended");
    private String instructionMethod;

    private InstructionMethod(String instructionMethod) {
        this.instructionMethod = instructionMethod;
    }

    @Override
    public String toString() {
        return instructionMethod;
    }
}
