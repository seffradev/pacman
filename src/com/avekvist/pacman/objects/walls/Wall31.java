package com.avekvist.pacman.objects.walls;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Wall31 extends GameObject {
    Sprite wallSprite;

    public Wall31() {
        wallSprite = new Sprite();
        wallSprite.setAnimation(new Animation(graphics, 24, 4, 6 * 3, 6 * 3, 6 * 3, 6 * 3));
        setSprite(wallSprite);
        setAlive(true);
        setType("Wall");
    }
}
