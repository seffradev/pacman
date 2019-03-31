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
    private static int width;
    private static int height;
    private static int horizontalTiles;
    private static int verticalTiles;
    private boolean isRunning;
    private Thread thread;
    private String title;
    private static BufferedImage image;
    private static int[] pixels;
    private static JFrame frame;
    private static int tileSize;
    private static int rightMenuSize;
    private Map map;
    private ArrayList<Animation> images;
    private int index;

    public LevelEditor(int horizontalTiles, int verticalTiles, int tileSize) {
        frame = new JFrame();

        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.tileSize = tileSize;

        rightMenuSize = 128 * 3;

        width = horizontalTiles * tileSize + rightMenuSize;
        height = verticalTiles * tileSize;

        map = new Map(horizontalTiles, verticalTiles);

        images = new ArrayList<>();

        images.add(null);                                           // Empty
        images.add(generateAnimation(0, 7, tileSize * 2)); // PacMan
        images.add(generateAnimation(0, 6, tileSize * 2)); // Blinky
        images.add(generateAnimation(0, 8, tileSize * 2)); // Pinky
        images.add(generateAnimation(8, 8, tileSize * 2)); // Inky
        images.add(generateAnimation(0, 9, tileSize * 2)); // Clyde
        images.add(generateAnimation(16, 0)); // Pellet
        images.add(generateAnimation(18, 0)); // Power Pellet
        images.add(generateAnimation(0, 4, tileSize * 2)); // Wall1
        images.add(generateAnimation(1, 4, tileSize * 2)); // Wall2
        images.add(generateAnimation(2, 4, tileSize * 2)); // Wall3
        images.add(generateAnimation(3, 4, tileSize * 2)); // Wall4
        images.add(generateAnimation(4, 4, tileSize * 2)); // Wall5
        images.add(generateAnimation(5, 4, tileSize * 2)); // Wall6
        images.add(generateAnimation(16, 3)); // Wall7
        images.add(generateAnimation(17, 3)); // Wall8
        images.add(generateAnimation(18, 3)); // Wall9
        images.add(generateAnimation(19, 3)); // Wall10
        images.add(generateAnimation(20, 3)); // Wall11
        images.add(generateAnimation(21, 3)); // Wall12
        images.add(generateAnimation(22, 3)); // Wall13
        images.add(generateAnimation(23, 3)); // Wall14
        images.add(generateAnimation(24, 3)); // Wall15
        images.add(generateAnimation(25, 3)); // Wall16
        images.add(generateAnimation(26, 3)); // Wall17
        images.add(generateAnimation(27, 3)); // Wall18
        images.add(generateAnimation(28, 3)); // Wall19
        images.add(generateAnimation(29, 3)); // Wall20
        images.add(generateAnimation(30, 3)); // Wall21
        images.add(generateAnimation(31, 3)); // Wall22
        images.add(generateAnimation(16, 4)); // Wall23
        images.add(generateAnimation(17, 4)); // Wall24
        images.add(generateAnimation(18, 4)); // Wall25
        images.add(generateAnimation(19, 4)); // Wall26
        images.add(generateAnimation(20, 4)); // Wall27
        images.add(generateAnimation(21, 4)); // Wall28
        images.add(generateAnimation(22, 4)); // Wall29
        images.add(generateAnimation(23, 4)); // Wall30
        images.add(generateAnimation(24, 4)); // Wall31
        images.add(generateAnimation(25, 4)); // Wall32
        images.add(generateAnimation(26, 4)); // Wall33
        images.add(generateAnimation(27, 4)); // Wall34
        images.add(generateAnimation(28, 4)); // Wall35
        images.add(generateAnimation(29, 4)); // Wall36
        images.add(generateAnimation(30, 4)); // Wall37
        images.add(generateAnimation(31, 4)); // Wall38
        images.add(generateAnimation(16, 5)); // Wall39
        images.add(generateAnimation(17, 5)); // Wall40
        images.add(generateAnimation(18, 5)); // Wall41
        images.add(generateAnimation(19, 5)); // Wall42
        images.add(generateAnimation(20, 5)); // Wall43
        images.add(generateAnimation(21, 5)); // Wall44
        images.add(generateAnimation(22, 5)); // Wall45
        images.add(generateAnimation(23, 5)); // Wall46
        images.add(generateAnimation(24, 5)); // Wall47
        images.add(generateAnimation(25, 5)); // Wall48
        images.add(generateAnimation(26, 5)); // Wall49
        images.add(generateAnimation(27, 5)); // Wall50

        for(Animation anim : images) {
            if(anim != null)
                anim.setWindowDimensions(width, height);
        }

        title = "Pac-Man Level Editor";

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
        int maxColumns = 5;

        for(int i = 0; i < images.size(); i++) {
            Animation anim = images.get(i);

            int drawWidth = tileSize * 2;
            int drawHeight = tileSize * 2;

            if(i == index)
                for(int y = 0; y < drawHeight; y++)
                    for(int x = 0; x < drawWidth; x++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0x222222;

            for(int y = 0; y < drawHeight + 1; y++)
                if(y % tileSize == 0)
                    for(int x = 0; x < drawWidth; x++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0xFFFFFF;

            for(int x = 0; x < drawWidth + 1; x++)
                if(x % tileSize == 0)
                    for(int y = 0; y < drawHeight; y++)
                        pixels[xMargin + x + (yMargin + y) * width] = 0xFFFFFF;

            if(anim != null)
                anim.render(pixels, 0, xMargin, yMargin);

            if(currentColumn >= maxColumns) {
                xMargin = width - rightMenuSize + drawWidth;
                yMargin += drawHeight + 5;
                currentColumn = 0;
            } else {
                xMargin += drawWidth + 5;
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

    public Animation generateAnimation(int horizontalIndex, int verticalIndex, int tileSize) {
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
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_S) {
            map.generateImage();
        }

        if(keyCode == KeyEvent.VK_A) {
            String path = JOptionPane.showInputDialog("Enter filename");
            map.setPath("resources/levels/" + path);
            map.generateImage();
        }

        if(keyCode == KeyEvent.VK_O) {
            String path = JOptionPane.showInputDialog("Enter file to open");
            map = new Map("resources/levels/" + path);
        }

        if(keyCode == KeyEvent.VK_N) {
            map = new Map(horizontalTiles, verticalTiles);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public static void setHorizontalTiles(int horizontalTiles) {
        LevelEditor.horizontalTiles = horizontalTiles;
        width = horizontalTiles * tileSize + rightMenuSize;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        frame.setSize(width, height);
        System.out.println(width + ", " + height);
    }

    public static void setVerticalTiles(int verticalTiles) {
        LevelEditor.verticalTiles = verticalTiles;
        height = verticalTiles * tileSize;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        frame.setSize(width, height);
        System.out.println(width + ", " + height);
    }

    public static void main(String[] args) {
        LevelEditor editor = new LevelEditor(19 * 2, 21 * 2, 18);

        editor.start();
    }
}
