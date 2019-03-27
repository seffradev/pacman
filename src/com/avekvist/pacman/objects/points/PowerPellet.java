package com.avekvist.pacman.objects.points;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.objects.PacMan;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class PowerPellet extends GameObject {
    private Sprite pelletSprite;

    public PowerPellet() {
        super();

        pelletSprite = new Sprite();
        pelletSprite.setAnimation(new Animation(graphics, 20, 0, 6 * 3, 6 * 3, 6 * 3, 6 * 3));
        setSprite(pelletSprite);
    }

    public void update() {
        if(collidesAt(getPosition(), "PacMan", 8)) {
            PacMan pacman = Level.getPacMan();
            pacman.setPoweredUp(true);
            pacman.addScore(50);
            setAlive(false);
        }
    }
}
