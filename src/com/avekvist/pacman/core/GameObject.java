package com.avekvist.pacman.core;

import com.avekvist.pacman.Game;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;

public class GameObject {
    private boolean alive;
    private Sprite sprite;
    protected Vector2 position;
    private Vector2 velocity;
    private Direction direction;
    private double maxSpeed;
    private String type;

    public GameObject() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        setAlive(true);
    }

    public void update(Game game) {
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

    public boolean collidesAt(Vector2 position, String type, int margin) {
        return Level.collidesAt(this, position, type, margin);
    }

    public GameObject collidesAtGameObject(Vector2 position, String type, int margin) {
        return Level.collidesAtGameObject(this, position, type, margin);
    }

    public int getWidth() {
        if(sprite != null)
            return (int) sprite.getWidth();
        return 0;
    }

    public int getHeight() {
        if(sprite != null)
            return (int) sprite.getHeight();
        return 0;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean getVulnerable() {
        return false;
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

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void eaten(Game game) {}

    public boolean getDamaged() {
        return false;
    }

    public void setWindowDimensions(int width, int height) {
        getSprite().setWindowDimensions(width, height);
    }

    public Vector2 getStartPosition() {
        return new Vector2(0, 0);
    }
}
