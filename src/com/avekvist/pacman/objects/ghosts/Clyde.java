package com.avekvist.pacman.objects.ghosts;

import com.avekvist.pacman.core.graphics.Animation;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Clyde extends Ghost {
    public Clyde() {
        super();

        rightSprite.setAnimation(new Animation(graphics, 0, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        leftSprite.setAnimation(new Animation(graphics, 4, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        upSprite.setAnimation(new Animation(graphics, 6, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        downSprite.setAnimation(new Animation(graphics, 2, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        rightDamagedSprite.setAnimation(new Animation(graphics, 8, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        leftDamagedSprite.setAnimation(new Animation(graphics, 12, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        upDamagedSprite.setAnimation(new Animation(graphics, 14, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        downDamagedSprite.setAnimation(new Animation(graphics, 10, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
    }

    public void doAChase() {
        // TODO: Random.
    }

    public void doAScatter() {
        // TODO: Bottom left
    }

    public void doAFrighten() {
        // TODO: Uh I guess this comes next.
    }
}
