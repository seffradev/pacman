package com.avekvist.pacman.objects;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.Level;
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

    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;

    public PacMan() {
        super();

        timer = new Timer();
        isDying = false;

        goUp = false;
        goDown = false;
        goLeft = false;
        goRight = false;

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

        if(position.add(getVelocity()).getX() >= Level.getWidth())
            position.setX(5);
        if(position.add(getVelocity()).getX() < 0)
            position.setX(Level.getWidth() - 5);

        if(timer.getDelay() <= 0)
            setPoweredUp(false);

        isMoving = !collidesAt(getPosition().add(getVelocity()), "Wall", 2);

        if(isMoving)
            super.update();

        if(!isDying) {
            deathSprite.setAnimationIndex(0);

            GameObject pelletCollider = collidesAtGameObject(getPosition(), "Pellet", 8);
            if(pelletCollider != null) {
                addScore(10);
                pelletCollider.setAlive(false);
            }

            GameObject powerCollider = collidesAtGameObject(getPosition(), "PowerPellet", 8);
            if(powerCollider != null) {
                setPoweredUp(true);
                addScore(50);
                powerCollider.setAlive(false);
            }

            GameObject collider = collidesAtGameObject(getPosition(), "Ghost", 1);
            if(collider != null) {
                if(collider.getVulnerable() && !collider.getDamaged()) {
                    collider.eaten();
                    addDeadGhosts(1);
                } else if(!collider.getDamaged())
                    isDying = true;
            }

            if(goUp) {
                boolean canGo = !collidesAt(getPosition().add(new Vector2(0, -1)), "Wall", 1);

                if(canGo)
                    setDirection(Direction.UP);
            } else if(goDown) {
                boolean canGo = !collidesAt(getPosition().add(new Vector2(0, 1)), "Wall", 1);

                if(canGo)
                    setDirection(Direction.DOWN);
            } else if(goLeft) {
                boolean canGo = !collidesAt(getPosition().add(new Vector2(-1, 0)), "Wall", 1);

                if(canGo)
                    setDirection(Direction.LEFT);
            } else if(goRight) {
                boolean canGo = !collidesAt(getPosition().add(new Vector2(1, 0)), "Wall", 1);

                if(canGo)
                    setDirection(Direction.RIGHT);
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

        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            Direction direction = getDirection();
            if(direction == Direction.DOWN || !isMoving) {
                setDirection(Direction.UP);
                goUp = false;
                goDown = false;
                goLeft = false;
                goRight = false;
            } else if(direction == Direction.RIGHT || direction == Direction.LEFT) {
                goUp = true;
                goDown = false;
                goLeft = false;
                goRight = false;
            }
        }

        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            Direction direction = getDirection();
            if(direction == Direction.UP || !isMoving) {
                setDirection(Direction.DOWN);
                goUp = false;
                goDown = false;
                goLeft = false;
                goRight = false;
            } else if(direction == Direction.RIGHT || direction == Direction.LEFT) {
                goUp = false;
                goDown = true;
                goLeft = false;
                goRight = false;
            }
        }

        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            Direction direction = getDirection();
            if(direction == Direction.RIGHT || !isMoving) {
                setDirection(Direction.LEFT);
                goUp = false;
                goDown = false;
                goLeft = false;
                goRight = false;
            } else if(direction == Direction.UP || direction == Direction.DOWN) {
                goUp = false;
                goDown = false;
                goLeft = true;
                goRight = false;
            }
        }

        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            Direction direction = getDirection();
            if(direction == Direction.LEFT || !isMoving) {
                setDirection(Direction.RIGHT);
                goUp = false;
                goDown = false;
                goLeft = false;
                goRight = false;
            } else if(direction == Direction.UP || direction == Direction.DOWN) {
                goUp = false;
                goDown = false;
                goLeft = false;
                goRight = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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

    public void addScore(int score) {
        this.score += score;
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

    public void setIsDying(boolean isDying) {
        this.isDying = isDying;
    }

    public int getScore() {
        return score;
    }
}
