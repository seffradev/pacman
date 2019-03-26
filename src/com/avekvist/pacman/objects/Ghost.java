package com.avekvist.pacman.objects;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;

public class Ghost extends GameObject {
    protected Sprite rightSprite;
    protected Sprite leftSprite;
    protected Sprite upSprite;
    protected Sprite downSprite;
    protected Sprite vulnerableSprite;
    protected Sprite rightDamagedSprite;
    protected Sprite leftDamagedSprite;
    protected Sprite upDamagedSprite;
    protected Sprite downDamagedSprite;

    private boolean damaged;
    private boolean vulnerable;

    public Ghost() {

    }

    protected void init() {
        setMaxSpeed(1);
        setDirection(Direction.RIGHT);
        setSprite(rightSprite);
        setAlive(true);
    }

    public void update() {
        super.update();

        if(damaged) {
            switch (getDirection()) {
                case UP:
                    if(getSprite() != upDamagedSprite)
                        setSprite(upDamagedSprite);
                    break;
                case DOWN:
                    if(getSprite() != downDamagedSprite)
                        setSprite(downDamagedSprite);
                    break;
                case LEFT:
                    if(getSprite() != leftDamagedSprite)
                        setSprite(leftDamagedSprite);
                    break;
                case RIGHT:
                    if(getSprite() != rightDamagedSprite)
                        setSprite(rightDamagedSprite);
                    break;
            }
        } else if(vulnerable) {
            if(getSprite() != vulnerableSprite)
                setSprite(vulnerableSprite);

            switch(getDirection()) {
                case UP:
                    break;
                case DOWN:
                    break;
                case LEFT:
                    break;
                case RIGHT:
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
}
