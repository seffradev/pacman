package com.avekvist.pacman.core;

import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;
import com.avekvist.pacman.objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.avekvist.pacman.Game.HEIGHT;
import static com.avekvist.pacman.Game.WIDTH;

public class Level {
    private String path;
    private int width, height, tileSize;
    private ArrayList<GameObject> gameObjects;

    private PacMan pacman;
    private Pinky pinky;
    private Inky inky;
    private Blinky blinky;
    private Clyde clyde;

    public Level(String path, int tileSize) {
        this.path = path;
        this.tileSize = tileSize;
        gameObjects = new ArrayList<>();

        Wall wall1 = new Wall();
        wall1.setPosition(new Vector2(WIDTH / 2, HEIGHT / 2));
        add(wall1);

        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));

            int w = image.getWidth();
            int h = image.getHeight();

            width = w * tileSize;
            height = h * tileSize;

            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            for(int y = 0; y < h; y++) {
                for(int x = 0; x < w; x++) {
                    switch(pixels[x + y * w]) {
                        case 0xFFFFFF00:
                            pacman = new PacMan();
                            pacman.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(pacman);
                            break;
                        case 0xFF0000AA:
                            inky = new Inky();
                            inky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            inky.setDirection(Direction.DOWN);
                            add(inky);
                            break;
                        case 0xFFFF0000:
                            blinky = new Blinky();
                            blinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            blinky.setDirection(Direction.UP);
                            add(blinky);
                            break;
                        case 0xFFFF8800:
                            clyde = new Clyde();
                            clyde.setPosition(new Vector2(x * tileSize, y * tileSize));
                            clyde.setDirection(Direction.RIGHT);
                            add(clyde);
                            break;
                        case 0xFFFF00FF:
                            pinky = new Pinky();
                            pinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            pinky.setDirection(Direction.LEFT);
                            add(pinky);
                            break;
                        case 0xFF0000FF:
                            Wall wall = new Wall();
                            wall.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLevel() {

    }

    public void update() {
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

    public void render(Graphics g, int[] pixels) {
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                pixels[x + y * WIDTH] = 0x000000;
            }
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.render(pixels);
        }
    }

    public PacMan getPacMan() {
        return pacman;
    }

    public void add(GameObject gameObject) {
        if(gameObject != null)
            gameObjects.add(gameObject);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
