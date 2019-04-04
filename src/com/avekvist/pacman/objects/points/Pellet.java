package com.avekvist.pacman.objects.points;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Pellet extends GameObject {
    private Sprite pelletSprite;

    public Pellet() {
        super();
        pelletSprite = new Sprite();
        pelletSprite.setAnimation(new Animation(graphics, 16, 0, 6 * 3, 6 * 3, 6 * 3, 6 * 3));
        setSprite(pelletSprite);
        setType("Pellet");
    }
}
