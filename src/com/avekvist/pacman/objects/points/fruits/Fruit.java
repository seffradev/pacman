package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Timer;

public class Fruit extends GameObject {
    private Timer timer;

    private int points;
    protected Sprite fruitSprite;

    public Fruit() {
        super();

        timer = new Timer();
        timer.setDelay(10);

        fruitSprite = new Sprite();
        setSprite(fruitSprite);
    }

    public void update() {
        timer.update();
        if(timer.getDelay() <= 0)
            setAlive(false);

        if(collidesAt(getPosition(), "PacMan", 8)) {
            Level.getPacMan().addScore(getPoints());
            Level.addFruitTaken(1);
            setAlive(false);
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDelay() {
        return timer.getDelay();
    }
}
