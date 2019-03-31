package com.avekvist.leveleditor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class Map {
    private String path;
    public int width, height;
    public int[] pixels;

    public Map(String path) {
        this.path = path;

        try {
            BufferedImage image = ImageIO.read(new File(this.path));

            width = image.getWidth();
            height = image.getHeight();

            LevelEditor.setHorizontalTiles(width);
            LevelEditor.setVerticalTiles(height);

            pixels = new int[width * height];

            image.getRGB(0, 0, width, height, pixels, 0, width);
            for(int i = 0; i < width * height; i++)
                pixels[i] = pixels[i] - 0xFF000000;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        LevelEditor.setHorizontalTiles(width);
        LevelEditor.setVerticalTiles(height);

        pixels = new int[width * height];
    }

    public void generateImage() {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int[] biPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

            for(int i = 0; i < width * height; i++)
                biPixels[i] = pixels[i];

            File outputFile;

            if(path == "")
                outputFile = new File("resources/levels/level" + ((int)(Math.random() * 10000)) + ".png");
            else
                outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void setColor(int x, int y, int color) {
        pixels[x + y * width] = color;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
