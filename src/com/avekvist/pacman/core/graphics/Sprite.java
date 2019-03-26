package com.avekvist.pacman.core.graphics;

import com.avekvist.pacman.core.math.Vector2;

public class Sprite {
    private Animation animation;
    private Vector2 dimensions;
    private int index;
    private int animationDelay;
    private int count;
    private int numOfIndices;
    private int animationDirection;

    public Sprite() {
        index = 0;
        count = 0;
        animationDirection = 1;
        animationDelay = 0;
        dimensions = new Vector2(0, 0);

        if(animation != null) {
            numOfIndices = animation.getNumOfIndices();
            dimensions = new Vector2(animation.getIndexWidth(), animation.getIndexHeight());
        }
    }

    public void render(int[] pixels, double x, double y) {
        if(animation != null)
            animation.render(pixels, index, x, y);
    }

    public void update() {
        count++;
        if(count > animationDelay) {
            count = 0;
            index += animationDirection;

            if(index > numOfIndices - 1)
                index = 0;
            else if(index < 0)
                index = numOfIndices - 1;
        }
    }

    public void render(int[] pixels, Vector2 position) {
        if(pixels != null && position != null)
            render(pixels, position.getX(), position.getY());
    }

    public double getWidth() {
        return dimensions.getX();
    }

    public double getHeight() {
        return dimensions.getY();
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;

        if(animation != null) {
            numOfIndices = animation.getNumOfIndices();
            dimensions = new Vector2(animation.getIndexWidth(), animation.getIndexHeight());
        }
    }

    public void setAnimationDelay(double seconds) {
        animationDelay = (int) Math.round(seconds * 60);
    }

    public double getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDirection(int animationDirection) {
        this.animationDirection = animationDirection;
    }

    public int getAnimationDirection() {
        return animationDirection;
    }

    public int getAnimationIndex() {
        return index;
    }

    public void setAnimationIndex(int index) {
        this.index = index;
    }
}
