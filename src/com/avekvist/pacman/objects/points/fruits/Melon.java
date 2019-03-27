package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Melon extends Fruit {
    public Melon() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 5, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(1000);
    }
}
