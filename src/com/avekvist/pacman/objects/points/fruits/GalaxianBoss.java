package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class GalaxianBoss extends Fruit {
    public GalaxianBoss() {
        super();

        fruitSprite.setAnimation(new Animation(graphics, 5, 5, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        setPoints(2000);
    }
}
