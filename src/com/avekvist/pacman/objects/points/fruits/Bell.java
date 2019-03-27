package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Bell extends Fruit {
    public Bell() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 3, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(3000);
    }
}
