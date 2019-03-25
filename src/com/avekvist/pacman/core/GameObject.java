package com.avekvist.pacman.core;

import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;

public class GameObject {
    private boolean alive;
    private Sprite sprite;
    protected Vector2 position;
    private Vector2 velocity;
    private Direction direction;

    public void update() {
        setPosition(getPosition().add(getVelocity()));

        Sprite sprite = getSprite();
        if(sprite != null)
            sprite.update();
    }

    public void render(int[] pixels) {
        Sprite sprite = getSprite();
        if(sprite != null)
            sprite.render(pixels, getPosition());
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
