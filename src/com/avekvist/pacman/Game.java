package com.avekvist.pacman;

import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.graphics.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private boolean isRunning;
    private Window window;
    private Thread thread;
    private String title;
    private BufferedImage image;
    private int[] pixels;

    public static int WIDTH;
    public static int HEIGHT;

    Level level;

    public Game(String title) {
        this.title = title;

        setRunning(false);

        level = new Level("/level1.png", 12 * 3);
        addKeyListener(level.getPacMan());

        WIDTH = level.getWidth();
        HEIGHT = level.getHeight();

        window = new Window(title, WIDTH, HEIGHT);
        window.getFrame().add(this);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public synchronized void start() {
        setRunning(true);
        thread = new Thread(this, "Game");
        thread.start();
    }

    public synchronized void stop() {
        setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
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
                window.getFrame().setTitle(title + " | " + frames + " FPS, " + updates + " Updates");
                updates = 0;
                frames = 0;
            }
        }

        stop();
    }

    private void update() {
        level.update();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        level.render(g, pixels);

        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

        g.dispose();
        bs.show();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public static void main(String[] args) {
        Game game = new Game("PAC-MAN");

        game.start();
    }
}
