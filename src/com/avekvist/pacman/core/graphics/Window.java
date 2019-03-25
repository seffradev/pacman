package com.avekvist.pacman.core.graphics;

import com.avekvist.pacman.core.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Window {
    private String title;
    private int width;
    private int height;
    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        frame = new JFrame();

        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void render(Graphics g, ArrayList<GameObject> gameObjects) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixels[x + y * width] = 0x000000;
            }
        }

        if(gameObjects != null)
            for(GameObject gameObject : gameObjects) {
                if(gameObject != null)
                    gameObject.render(pixels);
            }

        g.drawImage(image, 0, 0, width, height, null);
    }

    public JFrame getFrame() {
        return frame;
    }
}
