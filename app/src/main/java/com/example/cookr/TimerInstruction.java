package com.example.cookr;

public class TimerInstruction extends Instruction {
    public TimerInstruction(String line) {
        super(line);
    }

    public String evaluate() {
        /** Evaluates string based on format: "timer:[time]:[title] */
        String[] timerComps = content.split(":");

        /*
        int h      = Integer.parseInt(timerComps[1].split("\\.")[0]);
        int m      = Integer.parseInt(timerComps[1].split("\\.")[1]);
        int s      = Integer.parseInt(timerComps[1].split("\\.")[2]);
        */

        return timerComps[1];
    }
}
