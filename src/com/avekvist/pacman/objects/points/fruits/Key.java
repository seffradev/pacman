package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Key extends Fruit {
    public Key() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 7, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(5000);
    }
}
