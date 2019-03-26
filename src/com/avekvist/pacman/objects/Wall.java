package com.avekvist.pacman.objects;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Wall extends GameObject {
    Sprite wallSprite;

    public Wall() {
        wallSprite = new Sprite();
        wallSprite.setAnimation(new Animation(graphics, 0, 4, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setSprite(wallSprite);
        setAlive(true);
    }
}
