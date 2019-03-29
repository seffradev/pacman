package com.avekvist.leveleditor;

import com.avekvist.pacman.core.graphics.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class LevelEditor extends Canvas implements Runnable, MouseWheelListener, MouseListener, MouseMotionListener, KeyListener {
    private int width;
    private int height;
    private int horizontalTiles;
    private int verticalTiles;
    private boolean isRunning;
    private Thread thread;
    private String title;
    private BufferedImage image;
    private int[] pixels;
    private JFrame frame;
    private int tileSize;
    private int rightMenuSize;
    private Map map;
    private ArrayList<Animation> images;
    private int index;

    public LevelEditor(int horizontalTiles, int verticalTiles, int tileSize) {
        map = new Map("resources/levels/level874.png");
        //map = new Map(horizontalTiles, verticalTiles);

        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;

        rightMenuSize = 128 * 3;

        width = horizontalTiles * tileSize + rightMenuSize;
        height = verticalTiles * tileSize;

        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.tileSize = tileSize;

        images = new ArrayList<>();

        images.add(null);                                           // Empty
        images.add(generateAnimation(0, 7)); // PacMan
        images.add(generateAnimation(0, 6)); // Blinky
        images.add(generateAnimation(0, 8)); // Pinky
        images.add(generateAnimation(8, 8)); // Inky
        images.add(generateAnimation(0, 9)); // Clyde
        images.add(new Animation(graphics, 16, 0, tileSize / 2, tileSize / 2, tileSize / 2, tileSize / 2)); // Pellet
        images.add(new Animation(graphics, 18, 0, tileSize / 2, tileSize / 2, tileSize / 2, tileSize / 2)); // Power Pellet
        images.add(generateAnimation(0, 4)); // Wall1
        images.add(generateAnimation(1, 4)); // Wall2
        images.add(generateAnimation(2, 4)); // Wall3
        images.add(generateAnimation(3, 4)); // Wall4
        images.add(generateAnimation(4, 4)); // Wall5
        images.add(generateAnimation(5, 4)); // Wall6

        for(Animation anim : images) {
            if(anim != null)
                anim.setWindowDimensions(width, height);
        }

        title = "Pac-Man Level Editor";

        frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.add(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public synchronized void start() {
        setRunning(true);
        thread = new Thread(this, "Level Editor");
        thread.start();
    }

    public synchronized void stop() {
        setRunning(false);
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1.0E09 / 60.0;
        double delta = 0;

        int updates = 0;
        int frames = 0;

        long now;

        while(isRunning()) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | " + frames + " FPS, " + updates + " Updates");
                updates = 0;
                frames = 0;
            }
        }

        stop();
    }

    private void update() {
        map.update();
    }

    private void render() {
        int xMargin;
        int yMargin;

        BufferStrategy bs = getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixels[x + y * width] = 0x000000;
            }
        }

        for(int y = 0; y < map.height * tileSize; y += tileSize) {
            for(int x = 0; x < map.width * tileSize; x++) {
                pixels[x + y * width] = 0x777777;
            }
        }

        for(int x = 0; x < map.width * tileSize; x += tileSize) {
            for(int y = 0; y < map.height * tileSize; y++) {
                pixels[x + y * width] = 0x777777;
            }
        }

        for(int y = 0; y < height; y++) {
            for(int x = width - rightMenuSize; x < width; x++) {
                pixels[x + y * width] = 0x585858;
            }
        }

        xMargin = width - rightMenuSize + tileSize;
        yMargin = tileSize;

        int currentColumn = 0;
        int maxColumns = 3;

        for(int i = 0; i < images.size(); i++) {
            Animation anim = images.get(i);

            if(i == index)
                for(int y = 0; y < tileSize; y++)
                    for(int x = 0; x < tileSize; x++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0x222222;

            for(int y = 0; y < tileSize + 1; y++)
                if(y % tileSize == 0)
                    for(int x = 0; x < tileSize; x++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0xFFFFFF;

            for(int x = 0; x < tileSize + 1; x++)
                if(x % tileSize == 0)
                    for(int y = 0; y < tileSize; y++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0xFFFFFF;

            if(anim != null)
                anim.render(pixels, 0, xMargin, yMargin);

            if(currentColumn >= maxColumns) {
                xMargin = width - rightMenuSize + tileSize;
                yMargin += tileSize + 5;
                currentColumn = 0;
            } else {
                xMargin += tileSize + 5;
            }

            currentColumn++;
        }

        for(int y = 0; y < map.height; y++) {
            for(int x = 0; x < map.width; x++) {
                int mapIndex = x + y * map.width;
                Animation anim = null;
                if(map.pixels[mapIndex] > 0 && map.pixels[mapIndex] < images.size())
                    anim = images.get(map.pixels[mapIndex]);

                if(anim != null)
                    anim.render(pixels, 0, x * tileSize, y * tileSize);
            }
        }

        g.drawImage(image, 0, 0, width, height, null);

        g.dispose();
        bs.show();
    }

    private boolean isRunning() {
        return isRunning;
    }

    private void setRunning(boolean b) {
        this.isRunning = b;
    }

    public Animation generateAnimation(int horizontalIndex, int verticalIndex) {
        return new Animation(graphics, horizontalIndex, verticalIndex, tileSize, tileSize, tileSize, tileSize);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();

        if(rotation > 0) {
            if(index < images.size() - 1)
                index += rotation;
            else
                index = 0;
        } else {
            if(index > 0)
                index += rotation;
            else
                index = images.size() - 1;
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(x > 0 && x < map.width * tileSize) {
            if(y > 0 && y < map.height * tileSize) {
                int mouseButton = e.getButton();

                int mapX;
                int mapY;

                if(mouseButton == MouseEvent.BUTTON1) {
                    mapX = x / tileSize;
                    mapY = y / tileSize;

                    map.setColor(mapX, mapY, index);
                } else if(mouseButton == MouseEvent.BUTTON3) {
                    mapX = x / tileSize;
                    mapY = y / tileSize;

                    map.setColor(mapX, mapY, 0);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            map.generateImage();
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        LevelEditor editor = new LevelEditor(19, 21, 36);

        editor.start();
    }
}
