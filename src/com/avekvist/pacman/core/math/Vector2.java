package com.avekvist.pacman.core.math;

public class Vector2 {
    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2 add(Vector2 rhs) {
        if(rhs != null)
            return new Vector2(this.x + rhs.x, this.y + rhs.y);
        return new Vector2(0, 0);
    }
}
