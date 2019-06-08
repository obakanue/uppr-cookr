package com.example.cookr;

abstract class Instruction {
    String content;

    public Instruction(String content) {
        this.content = content;
    }

    abstract String evaluate();

    public String toString() {
        return content;
    }
}