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
    private double maxSpeed;
    private String type;

    public GameObject() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        setAlive(true);
    }

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

    // TODO: Update the collision system
    // Check for objects at a specific position instead of checking for a specific object at a position.
    // e.g getObjectAt(Vector2 position) -> GameObject
    // instead of collidesAt(Vector2 position, GameObject gameObject) -> boolean
/*
    public boolean collides(GameObject gameObject) {
        Vector2 myPosition = getPosition();
        int myX = (int) myPosition.getX();
        int myY = (int) myPosition.getY();

        Vector2 otherPosition = gameObject.getPosition();
        int otherX = (int) otherPosition.getX();
        int otherY = (int) otherPosition.getY();

        int myWidth = getWidth();
        int myHeight = getHeight();
        int otherWidth = gameObject.getWidth();
        int otherHeight = gameObject.getHeight();

//        System.out.println("My { " + myX + ", " + myY + ", " + myWidth + ", " + myHeight + ", " + (myX + myWidth) + ", " + (myY + myHeight) + " }, Other { " + otherX + ", " + otherY + ", " + otherWidth + ", " + otherHeight + ", " + (otherX + otherWidth) + ", " + (otherY + otherHeight) + " }");

        boolean topLeft = myX >= otherX && myX <= otherX + otherWidth && myY >= otherY && myY <= otherY + otherHeight;
        boolean topRight = myX + myWidth >= otherX && myX + myWidth <= otherX + otherWidth && myY >= otherY && myY <= otherY + otherHeight;
        boolean bottomLeft = myX >= otherX && myX <= otherX + otherWidth && myY + myHeight >= otherY && myY + myHeight <= otherY + otherHeight;
        boolean bottomRight = myX + myWidth >= otherX && myX + myWidth <= otherX + otherWidth && myY + myHeight >= otherY && myY + myHeight <= otherY + otherHeight;

//        System.out.println("top left: " + topLeft + ", top right: " + topRight + ", bottom left: " + bottomLeft + ", bottom right: " + bottomRight);

        return topLeft || topRight || bottomLeft || bottomRight;
    }
*/
/*
    public boolean collidesAt(GameObject gameObject, Vector2 position) {
        Vector2 tempPosition = getPosition();
        setPosition(position);
        boolean collides = collides(gameObject);
        setPosition(tempPosition);
        return collides;
    }
*/

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

    public void eaten() {}

    public boolean getDamaged() {
        return false;
    }
}
