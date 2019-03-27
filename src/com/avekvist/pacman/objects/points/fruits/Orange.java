package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Orange extends Fruit {
    public Orange() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 2, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(500);
    }
}
