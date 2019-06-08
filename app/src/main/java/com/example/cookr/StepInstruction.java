package com.example.cookr;

public class StepInstruction extends Instruction {
    public StepInstruction(String line) {
        super(line);
    }

    public String evaluate() {
        return content.split(":")[1];
    }
}
