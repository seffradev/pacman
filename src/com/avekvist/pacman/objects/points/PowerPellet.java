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
        pelletSprite.setAnimation(new Animation(graphics, 21, 0, 6 * 3, 6 * 3, 6 * 3 * 2, 6 * 3));
        pelletSprite.setAnimationDirection(1);
        pelletSprite.setAnimationDelay(0.1);
        setSprite(pelletSprite);
        setType("PowerPellet");
    }

    public void update() {
        super.update();
    }
}
