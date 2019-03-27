package com.avekvist.pacman.core.helper;

public class Timer {
    private int delay;

    public void setDelay(double seconds) {
        delay = (int) Math.round(seconds * 60);
        System.out.println(delay);
    }

    public int getDelay() {
        return delay;
    }

    public void update() {
        if(delay > 0)
            delay--;
    }
}
