package com.avekvist.pacman.objects;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.helper.Timer;
import com.avekvist.pacman.core.math.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class PacMan extends GameObject implements KeyListener {
    Sprite rightSprite;
    Sprite leftSprite;
    Sprite upSprite;
    Sprite downSprite;
    Sprite deathSprite;

    private boolean isDying;
    private boolean isMoving;
    private boolean isPoweredUp;
    private int score;
    private Timer timer;
    private int deadGhosts;

    public PacMan() {
        super();

        timer = new Timer();
        isDying = false;

        rightSprite = new Sprite();
        rightSprite.setAnimation(new Animation(graphics, 4, 3, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        rightSprite.setAnimationDelay(0.2);

        leftSprite = new Sprite();
        leftSprite.setAnimation(new Animation(graphics, 0, 3, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        leftSprite.setAnimationDelay(0.2);

        upSprite = new Sprite();
        upSprite.setAnimation(new Animation(graphics, 2, 3, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        upSprite.setAnimationDelay(0.2);

        downSprite = new Sprite();
        downSprite.setAnimation(new Animation(graphics, 6, 3, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        downSprite.setAnimationDelay(0.2);

        deathSprite = new Sprite();
        deathSprite.setAnimation(new Animation(graphics, 4, 7, 12 * 3, 12 * 3, 12 * 3 * 12, 12 * 3));
        deathSprite.setAnimationDelay(0.1);

        setDirection(Direction.RIGHT);
        setMaxSpeed(2);
        setType("PacMan");
    }

    public void update() {
        timer.update();

        if(timer.getDelay() <= 0)
            setPoweredUp(false);

        isMoving = !collidesAt(getPosition().add(getVelocity()), "Wall", 1);

        if(isMoving)
            super.update();

        if(!isDying) {
            GameObject collider = collidesAtGameObject(getPosition(), "Ghost", 1);
            if(collider != null) {
                if(collider.getVulnerable() && !collider.getDamaged()) {
                    collider.eaten();
                    addDeadGhosts(1);
                } else if(!collider.getDamaged())
                    isDying = true;
            }

            switch (getDirection()) {
                case UP:
                    setVelocity(new Vector2(0, -getMaxSpeed()));

                    if (getSprite() != upSprite)
                        setSprite(upSprite);
                    break;
                case DOWN:
                    setVelocity(new Vector2(0, getMaxSpeed()));

                    if (getSprite() != downSprite)
                        setSprite(downSprite);
                    break;
                case LEFT:
                    setVelocity(new Vector2(-getMaxSpeed(), 0));

                    if (getSprite() != leftSprite)
                        setSprite(leftSprite);
                    break;
                case RIGHT:
                    setVelocity(new Vector2(getMaxSpeed(), 0));

                    if (getSprite() != rightSprite)
                        setSprite(rightSprite);
                    break;
            }
        } else {
            if (getSprite() != deathSprite) {
                setSprite(deathSprite);
                setVelocity(new Vector2(0, 0));
            } else {
                if (getSprite().getAnimationIndex() >= getSprite().getAnimation().getNumOfIndices() - 1) {
                    setAlive(false);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
            setDirection(Direction.UP);
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
            setDirection(Direction.DOWN);
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            setDirection(Direction.LEFT);
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            setDirection(Direction.RIGHT);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isPoweredUp() {
        return isPoweredUp;
    }

    public void setPoweredUp(boolean poweredUp) {
        if(poweredUp)
            timer.setDelay(10);
        else
            setDeadGhosts(0);

        isPoweredUp = poweredUp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
        System.out.println(this.score);
    }

    public int getDelay() {
        return timer.getDelay();
    }

    public void setDeadGhosts(int deadGhosts) {
        this.deadGhosts = deadGhosts;
    }

    public int getDeadGhosts() {
        return deadGhosts;
    }

    public void addDeadGhosts(int deadGhosts) {
        setDeadGhosts(getDeadGhosts() + deadGhosts);
    }
}
