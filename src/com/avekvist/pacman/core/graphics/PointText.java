package com.avekvist.pacman.core.graphics;

import com.avekvist.pacman.core.GameObject;
import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class PointText extends GameObject {
    protected Sprite textSprite;

    public PointText(String number) {
        super();
        textSprite = new Sprite();
        textSprite.setAnimation(new Animation(graphics, 0, 0, 6 * 3, 6 * 3, 6 * 3 * 10, 6 * 3));
        textSprite.setAnimationDirection(0);
        setSprite(textSprite);
        setIndex(number);
    }

    public void setIndex(String number) {
        textSprite.setAnimationIndex(Integer.parseInt(number));
        setSprite(textSprite);
    }
}
