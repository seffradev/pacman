package com.avekvist.pacman;

import com.avekvist.pacman.core.GameObject;
import com.avekvist.pacman.core.graphics.Window;
import com.avekvist.pacman.objects.PacMan;
import com.avekvist.pacman.objects.Pinky;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Canvas implements Runnable {
    private ArrayList<GameObject> gameObjects;
    private boolean isRunning;
    private Window window;
    private Thread thread;
    private String title;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public Game(String title, int width, int height) {
        this.title = title;

        window = new Window(title, width, height);
        window.getFrame().add(this);

        gameObjects = new ArrayList<>();
        setRunning(false);

        PacMan pacman = new PacMan();
        add(pacman);
        addKeyListener(pacman);

        add(new Pinky());
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
        if(gameObjects != null) {
            Iterator<GameObject> gameObjectsIterator = gameObjects.iterator();

            for (Iterator<GameObject> it = gameObjectsIterator; it.hasNext(); ) {
                GameObject gameObject = it.next();
                if (!gameObject.isAlive()) {
                    gameObjectsIterator.remove();
                    continue;
                }

                if(gameObject != null)
                    gameObject.update();
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        window.render(g, gameObjects);

        g.dispose();
        bs.show();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void add(GameObject gameObject) {
        if(gameObject != null)
            gameObjects.add(gameObject);
    }

    public static void main(String[] args) {
        Game game = new Game("PAC-MAN", WIDTH, HEIGHT);

        game.start();
    }
}
