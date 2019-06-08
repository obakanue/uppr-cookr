package com.example.cookr;

public class TitleInstruction extends Instruction {
    public TitleInstruction(String line) {
        super(line);
    }

    public String evaluate() {
        return content.split(":")[1];
    }
}
