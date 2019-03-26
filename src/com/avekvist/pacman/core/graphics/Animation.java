package com.avekvist.pacman.core.graphics;

import com.avekvist.pacman.core.math.Vector2;

import static com.avekvist.pacman.Game.HEIGHT;
import static com.avekvist.pacman.Game.WIDTH;

public class Animation {
    private int[] pixels;
    private int numOfIndices;
    private Vector2 indexDimensions;
    private Vector2 animationDimensions;

    public Animation(SpriteSheet sheet, int horizontalOffset, int verticalOffset, int width, int height, int animationWidth, int animationHeight) {
        indexDimensions = new Vector2(width, height);
        animationDimensions = new Vector2(animationWidth, animationHeight);

        pixels = new int[animationWidth * animationHeight];

        numOfIndices = animationWidth / width;

        for(int y = 0; y < animationHeight; y++) {
            for(int x = 0; x < animationWidth; x++) {
                pixels[x + y * animationWidth] = sheet.getPixel(x + horizontalOffset * width, y + verticalOffset * height);
            }
        }
    }

    public void render(int[] pixels, int index, double x, double y) {
        int width = (int) getIndexWidth();
        int height = (int) getIndexHeight();
        int animationWidth = (int) getAnimationWidth();
        int animationHeight = (int) getAnimationHeight();

        for(int ty = 0; ty < height; ty++) {
            for(int tx = 0; tx < width; tx++) {
                if(tx + (int) x + (ty + (int) y) * WIDTH < WIDTH * HEIGHT && tx + (int) x + (ty + (int) y) * WIDTH >= 0) {
                    int i = tx + (ty) * animationWidth + index * animationHeight;
                    if(this.pixels[i] != 0xFF000000)
                        pixels[tx + (int) x + (ty + (int) y) * WIDTH] = this.pixels[i];
                }
            }
        }
    }

    public double getIndexWidth() {
        return indexDimensions.getX();
    }

    public double getIndexHeight() {
        return indexDimensions.getY();
    }

    public double getAnimationWidth() {
        return animationDimensions.getX();
    }

    public double getAnimationHeight() {
        return animationDimensions.getY();
    }

    public int getNumOfIndices() {
        return numOfIndices;
    }
}
