package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Apple extends Fruit {
    public Apple() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 4, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(700);
    }
}
