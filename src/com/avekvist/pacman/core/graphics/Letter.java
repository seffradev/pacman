package com.avekvist.pacman.core.graphics;

import com.avekvist.pacman.core.GameObject;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Letter extends GameObject {
    protected Sprite textSprite;

    public Letter(String letter) {
        super();
        textSprite = new Sprite();
        textSprite.setAnimation(new Animation(graphics, 0, 2, 6 * 3, 6 * 3, 6 * 3 * 27, 6 * 3));
        textSprite.setAnimationDirection(0);
        setSprite(textSprite);
        setIndex(letter);
    }

    public void setIndex(String letter) {
        int charLetter;

        switch(letter) {
            case "A":
                charLetter = 1;
                break;
            case "B":
                charLetter = 2;
                break;
            case "C":
                charLetter = 3;
                break;
            case "D":
                charLetter = 4;
                break;
            case "E":
                charLetter = 5;
                break;
            case "F":
                charLetter = 6;
                break;
            case "G":
                charLetter = 7;
                break;
            case "H":
                charLetter = 8;
                break;
            case "I":
                charLetter = 9;
                break;
            case "J":
                charLetter = 10;
                break;
            case "K":
                charLetter = 11;
                break;
            case "L":
                charLetter = 12;
                break;
            case "M":
                charLetter = 13;
                break;
            case "N":
                charLetter = 14;
                break;
            case "O":
                charLetter = 15;
                break;
            case "P":
                charLetter = 16;
                break;
            case "Q":
                charLetter = 17;
                break;
            case "R":
                charLetter = 18;
                break;
            case "S":
                charLetter = 19;
                break;
            case "T":
                charLetter = 20;
                break;
            case "U":
                charLetter = 21;
                break;
            case "V":
                charLetter = 22;
                break;
            case "W":
                charLetter = 23;
                break;
            case "X":
                charLetter = 24;
                break;
            case "Y":
                charLetter = 25;
                break;
            case "Z":
                charLetter = 26;
                break;
            default:
                charLetter = 0;
                break;
        }

        textSprite.setAnimationIndex(charLetter);
        setSprite(textSprite);
        System.out.println("Changed number: " + charLetter + ", x: " + getPosition().getX() + ", y: " + getPosition().getY());
    }
}
