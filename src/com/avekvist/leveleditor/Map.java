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
        LevelEditor.setIsLoading(true);

        this.path = path;
        pixels = null;

        try {
            BufferedImage image = ImageIO.read(new File(this.path + ".png"));

            width = image.getWidth();
            height = image.getHeight();

            pixels = new int[width * height];

            image.getRGB(0, 0, width, height, pixels, 0, width);
            for(int i = 0; i < width * height; i++)
                pixels[i] = pixels[i] - 0xFF000000;

            LevelEditor.setHorizontalTiles(width);
            LevelEditor.setVerticalTiles(height);

            LevelEditor.setIsLoading(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map(int width, int height) {
        LevelEditor.setIsLoading(true);
        pixels = null;

        this.width = width;
        this.height = height;

        pixels = new int[width * height];

        LevelEditor.setHorizontalTiles(width);
        LevelEditor.setVerticalTiles(height);

        LevelEditor.setIsLoading(false);
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
                outputFile = new File(path + ".png");
            ImageIO.write(image, "png", outputFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setColor(int x, int y, int color) {
        pixels[x + y * width] = color;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
