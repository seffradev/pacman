package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Cherry extends Fruit {
    public Cherry() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 0, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(100);
    }
}
