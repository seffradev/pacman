package com.avekvist.pacman.core.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    private int[] pixels;
    private int width, height;

    public static SpriteSheet graphics = new SpriteSheet("/spritesheet.png", 576, 360);

    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;

        pixels = new int[width * height];
        loadSpriteSheet();
    }

    private void loadSpriteSheet() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));

            int w = image.getWidth();
            int h = image.getHeight();

            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixel(int x, int y) {
        return pixels[x + y * width];
    }
}
