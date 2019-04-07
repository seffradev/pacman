package com.avekvist.pacman.objects.ghosts;

import com.avekvist.pacman.Game;
import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.helper.Timer;
import com.avekvist.pacman.core.math.Vector2;
import com.avekvist.pacman.objects.PacMan;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Ghost extends GameObject {
    protected Sprite rightSprite;
    protected Sprite leftSprite;
    protected Sprite upSprite;
    protected Sprite downSprite;
    protected Sprite vulnerableSprite1;
    protected Sprite vulnerableSprite2;
    protected Sprite rightDamagedSprite;
    protected Sprite leftDamagedSprite;
    protected Sprite upDamagedSprite;
    protected Sprite downDamagedSprite;
    protected Sprite pointSprite;

    private boolean damaged;
    private boolean vulnerable;
    private boolean isMoving;
    private Timer showPointTimer;
    private boolean hasBeenEaten;
    private Vector2 startPosition;
    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;

    public Ghost() {
        super();
        setType("Ghost");
        setMaxSpeed(2);
        setSprite(rightSprite);
        showPointTimer = new Timer();

        rightSprite = new Sprite();
        rightSprite.setAnimationDelay(0.3);

        leftSprite = new Sprite();
        leftSprite.setAnimationDelay(0.3);

        downSprite = new Sprite();
        downSprite.setAnimationDelay(0.3);

        upSprite = new Sprite();
        upSprite.setAnimationDelay(0.3);

        leftDamagedSprite = new Sprite();
        leftDamagedSprite.setAnimationDelay(0.3);

        rightDamagedSprite = new Sprite();
        rightDamagedSprite.setAnimationDelay(0.3);

        downDamagedSprite = new Sprite();
        downDamagedSprite.setAnimationDelay(0.3);

        upDamagedSprite = new Sprite();
        upDamagedSprite.setAnimationDelay(0.3);

        vulnerableSprite1 = new Sprite();
        vulnerableSprite1.setAnimation(new Animation(graphics, 8, 4, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        vulnerableSprite1.setAnimationDelay(0.3);

        vulnerableSprite2 = new Sprite();
        vulnerableSprite2.setAnimation(new Animation(graphics, 12, 5, 12 * 3, 12 * 3, 12 * 3 * 4, 12 * 3));
        vulnerableSprite2.setAnimationDelay(0.3);

        pointSprite = new Sprite();
        pointSprite.setAnimation(new Animation(graphics, 8, 6, 12 * 3, 12 * 3, 12 * 3 * 4, 12 * 3));
        pointSprite.setAnimationDirection(0);
    }

    public void update(Game game) {
        isMoving = !collidesAt(getPosition().add(getVelocity()), "Wall", 1);
        if(Level.getPacMan() != null && Level.getPacMan().isPoweredUp()) {
            setVulnerable(true);

            if(getMaxSpeed() != 1)
                setMaxSpeed(1);
        } else {
            setVulnerable(false);

            if(getMaxSpeed() != 2)
                setMaxSpeed(2);
        }

        if(getDamaged())
            setMaxSpeed(4);

        if(isMoving)
            super.update(game);
        else if(getSprite() == vulnerableSprite2)
            getSprite().update();

        int rng = (int) (Math.random() * 1000);

        if (goUp) {
            boolean canGo = !collidesAt(getPosition().add(new Vector2(0, -1)), "Wall", 1);

            if (canGo)
                setDirection(Direction.UP);
        } else if (goDown) {
            boolean canGo = !collidesAt(getPosition().add(new Vector2(0, 1)), "Wall", 1);

            if (canGo)
                setDirection(Direction.DOWN);
        } else if (goLeft) {
            boolean canGo = !collidesAt(getPosition().add(new Vector2(-1, 0)), "Wall", 1);

            if (canGo)
                setDirection(Direction.LEFT);
        } else if (goRight) {
            boolean canGo = !collidesAt(getPosition().add(new Vector2(1, 0)), "Wall", 1);

            if (canGo)
                setDirection(Direction.RIGHT);
        }

        if(!isMoving)
            rng = (int) (Math.random() * 10);

        if(rng < 2) {
            int dir = (int) (Math.random() * 4);
            switch (dir) {
                case 0:
                    goRight = true;
                    goUp = false;
                    goLeft = false;
                    goDown = false;
                    break;
                case 1:
                    goRight = false;
                    goUp = true;
                    goLeft = false;
                    goDown = false;
                    break;
                case 2:
                    goRight = false;
                    goUp = false;
                    goLeft = true;
                    goDown = false;
                    break;
                case 3:
                    goRight = false;
                    goUp = false;
                    goLeft = false;
                    goDown = true;
                    break;
            }
        }

        if(damaged) {
            showPointTimer.update();
            if(showPointTimer.getDelay() > 0) {
                setSprite(pointSprite);
            } else {
                switch (getDirection()) {
                    case UP:
                        if (getSprite() != upDamagedSprite)
                            setSprite(upDamagedSprite);
                        setVelocity(new Vector2(0, -getMaxSpeed()));
                        break;
                    case DOWN:
                        if (getSprite() != downDamagedSprite)
                            setSprite(downDamagedSprite);
                        setVelocity(new Vector2(0, getMaxSpeed()));
                        break;
                    case LEFT:
                        if (getSprite() != leftDamagedSprite)
                            setSprite(leftDamagedSprite);
                        setVelocity(new Vector2(-getMaxSpeed(), 0));
                        break;
                    case RIGHT:
                        if (getSprite() != rightDamagedSprite)
                            setSprite(rightDamagedSprite);
                        setVelocity(new Vector2(getMaxSpeed(), 0));
                        break;
                }
                setPosition(startPosition);
                setDamaged(false);
            }

            if(hasBeenEaten) {
                Game.getTimer().setDelay(0.3);
                hasBeenEaten = false;
            }
        } else if(vulnerable) {
            int delay = Level.getPacMan().getDelay();

            if(delay > 180)
                setSprite(vulnerableSprite1);
            else
                setSprite(vulnerableSprite2);

            switch(getDirection()) {
                case UP:
                    setVelocity(new Vector2(0, -getMaxSpeed()));
                    break;
                case DOWN:
                    setVelocity(new Vector2(0, getMaxSpeed()));
                    break;
                case LEFT:
                    setVelocity(new Vector2(-getMaxSpeed(), 0));
                    break;
                case RIGHT:
                    setVelocity(new Vector2(getMaxSpeed(), 0));
                    break;
            }
        } else {
            switch(getDirection()) {
                case UP:
                    if(getSprite() != upSprite)
                        setSprite(upSprite);
                    setVelocity(new Vector2(0, -getMaxSpeed()));
                    break;
                case DOWN:
                    if(getSprite() != downSprite)
                        setSprite(downSprite);
                    setVelocity(new Vector2(0, getMaxSpeed()));
                    break;
                case LEFT:
                    if(getSprite() != leftSprite)
                        setSprite(leftSprite);
                    setVelocity(new Vector2(-getMaxSpeed(), 0));
                    break;
                case RIGHT:
                    if(getSprite() != rightSprite)
                        setSprite(rightSprite);
                    setVelocity(new Vector2(getMaxSpeed(), 0));
                    break;
            }
        }
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean getVulnerable() {
        return vulnerable;
    }

    public boolean getDamaged() {
        return damaged;
    }

    public void eaten(Game game) {
        setDamaged(true);
        setVulnerable(false);

        PacMan pacman = Level.getPacMan();

        int deadGhosts = pacman.getDeadGhosts();
        if(deadGhosts > 3)
            pacman.setDeadGhosts(0);

        pacman.addScore(game, 200 * (int) Math.pow(2, pacman.getDeadGhosts()));
        pointSprite.setAnimationIndex(pacman.getDeadGhosts());
        showPointTimer.setDelay(0.5);
        hasBeenEaten = true;
    }

    public void setStartPosition(Vector2 position) {
        this.startPosition = position;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }
}
