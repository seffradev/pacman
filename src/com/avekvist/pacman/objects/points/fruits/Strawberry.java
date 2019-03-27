package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Strawberry extends Fruit {
    public Strawberry() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 1, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(300);
    }
}
