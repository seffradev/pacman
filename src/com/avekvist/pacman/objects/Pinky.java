package com.avekvist.pacman.objects;

import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Sprite;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Pinky extends Ghost {
    public Pinky() {
        rightSprite = new Sprite();
        rightSprite.setAnimation(new Animation(graphics, 0, 8, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        rightSprite.setAnimationDelay(0.3);

        leftSprite = new Sprite();
        leftSprite.setAnimation(new Animation(graphics, 4, 8, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        leftSprite.setAnimationDelay(0.3);

        upSprite = new Sprite();
        upSprite.setAnimation(new Animation(graphics, 6, 8, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        upSprite.setAnimationDelay(0.3);

        downSprite = new Sprite();
        downSprite.setAnimation(new Animation(graphics, 2, 8, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        downSprite.setAnimationDelay(0.3);

        rightDamagedSprite = new Sprite();
        rightDamagedSprite.setAnimation(new Animation(graphics, 8, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        rightDamagedSprite.setAnimationDelay(0.3);

        leftDamagedSprite = new Sprite();
        leftDamagedSprite.setAnimation(new Animation(graphics, 12, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        leftDamagedSprite.setAnimationDelay(0.3);

        upDamagedSprite = new Sprite();
        upDamagedSprite.setAnimation(new Animation(graphics, 14, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        upDamagedSprite.setAnimationDelay(0.3);

        downDamagedSprite = new Sprite();
        downDamagedSprite.setAnimation(new Animation(graphics, 10, 9, 12 * 3, 12 * 3, 12 * 3 * 2, 12 * 3));
        downDamagedSprite.setAnimationDelay(0.3);

        vulnerableSprite = new Sprite();
        vulnerableSprite.setAnimation(new Animation(graphics, 6, 4, 12 * 3, 12 * 3, 12 * 3 * 4, 12 * 3));
        vulnerableSprite.setAnimationDelay(0.3);

        init();
    }
}
