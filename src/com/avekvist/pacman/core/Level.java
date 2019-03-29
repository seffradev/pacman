package com.avekvist.pacman.core;

import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;
import com.avekvist.pacman.objects.PacMan;
import com.avekvist.pacman.objects.ghosts.Blinky;
import com.avekvist.pacman.objects.ghosts.Clyde;
import com.avekvist.pacman.objects.ghosts.Inky;
import com.avekvist.pacman.objects.ghosts.Pinky;
import com.avekvist.pacman.objects.points.Pellet;
import com.avekvist.pacman.objects.points.PowerPellet;
import com.avekvist.pacman.objects.points.fruits.FruitSpawner;
import com.avekvist.pacman.objects.walls.*;

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
    private static ArrayList<GameObject> gameObjects;

    private static PacMan pacman;
    private static Vector2 pacmanSpawnPoint;
    private FruitSpawner fruitSpawner;
    private Pinky pinky;
    private Inky inky;
    private Blinky blinky;
    private Clyde clyde;
    private static int fruitTaken;

    public Level(String path, int tileSize) {
        this.path = path;
        this.tileSize = tileSize;
        gameObjects = new ArrayList<>();

        fruitTaken = 0;

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
                        case 0xFF000001:
                            pacman = new PacMan();
                            pacmanSpawnPoint = new Vector2(x * tileSize, y * tileSize);
                            pacman.setPosition(pacmanSpawnPoint);
                            add(pacman);
                            break;
                        case 0xFF000002:
                            blinky = new Blinky();
                            blinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            blinky.setDirection(Direction.UP);
                            add(blinky);
                            break;
                        case 0xFF000003:
                            pinky = new Pinky();
                            pinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            pinky.setDirection(Direction.LEFT);
                            add(pinky);
                            break;
                        case 0xFF000004:
                            inky = new Inky();
                            inky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            inky.setDirection(Direction.DOWN);
                            add(inky);
                            break;
                        case 0xFF000005:
                            clyde = new Clyde();
                            clyde.setPosition(new Vector2(x * tileSize, y * tileSize));
                            clyde.setDirection(Direction.RIGHT);
                            add(clyde);
                            break;
                        case 0xFF000006:
                            Pellet pellet = new Pellet();
                            pellet.setPosition(new Vector2(x * tileSize + tileSize / 2 - pellet.getWidth() / 2, y * tileSize + tileSize / 2 - pellet.getHeight() / 2));
                            add(pellet);
                            break;
                        case 0xFF000007:
                            PowerPellet powerPellet = new PowerPellet();
                            powerPellet.setPosition(new Vector2(x * tileSize + tileSize / 2 - powerPellet.getWidth() / 2, y * tileSize + tileSize / 2 - powerPellet.getHeight() / 2));
                            add(powerPellet);
                            break;
                        case 0xFF000008:
                            Wall1 wall1 = new Wall1();
                            wall1.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall1);
                            break;
                        case 0xFF000009:
                            Wall2 wall2 = new Wall2();
                            wall2.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall2);
                            break;
                        case 0xFF00000A:
                            Wall3 wall3 = new Wall3();
                            wall3.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall3);
                            break;
                        case 0xFF00000B:
                            Wall4 wall4 = new Wall4();
                            wall4.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall4);
                            break;
                        case 0xFF00000C:
                            Wall5 wall5 = new Wall5();
                            wall5.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall5);
                            break;
                        case 0xFF00000D:
                            Wall6 wall6 = new Wall6();
                            wall6.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall6);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fruitSpawner = new FruitSpawner(pacmanSpawnPoint, 1);
    }

    public void update() {
        fruitSpawner.update();

        if(gameObjects != null) {
            Iterator<GameObject> gameObjectsIterator = gameObjects.iterator();

            for (Iterator<GameObject> it = gameObjectsIterator; it.hasNext(); ) {
                GameObject gameObject = it.next();
                if (!gameObject.isAlive()) {
                    gameObjectsIterator.remove();
                    continue;
                }

                if(gameObject != null) {
                    gameObject.update();
                    gameObject.setWindowDimensions(width, height);
                }
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

    public static PacMan getPacMan() {
        return pacman;
    }

    public static void add(GameObject gameObject) {
        if(gameObject != null)
            gameObjects.add(gameObject);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int getFruitTaken() {
        return fruitTaken;
    }

    public static void setFruitTaken(int ft) {
        fruitTaken = ft;
    }

    public static void addFruitTaken(int fruitTaken) {
        setFruitTaken(getFruitTaken() + fruitTaken);
    }

    public static boolean collidesAt(GameObject source, Vector2 position, String type, int margin) {
        for(GameObject gameObject : gameObjects) {
            if(gameObject == source)
                continue;
            if(gameObject.getType() != type)
                continue;

            int myX = (int) position.getX();
            int myY = (int) position.getY();

            Vector2 otherPosition = gameObject.getPosition();
            int otherX = (int) otherPosition.getX();
            int otherY = (int) otherPosition.getY();

            int myWidth = source.getWidth();
            int myHeight = source.getHeight();
            int otherWidth = gameObject.getWidth();
            int otherHeight = gameObject.getHeight();

//        System.out.println("My { " + myX + ", " + myY + ", " + myWidth + ", " + myHeight + ", " + (myX + myWidth) + ", " + (myY + myHeight) + " }, Other { " + otherX + ", " + otherY + ", " + otherWidth + ", " + otherHeight + ", " + (otherX + otherWidth) + ", " + (otherY + otherHeight) + " }");

            boolean topLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean topRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean bottomLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;
            boolean bottomRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;

//            System.out.println("top left: " + topLeft + ", top right: " + topRight + ", bottom left: " + bottomLeft + ", bottom right: " + bottomRight);

            if(topLeft || topRight || bottomLeft || bottomRight)
                return true;
        }

        return false;
    }

    public static GameObject collidesAtGameObject(GameObject source, Vector2 position, String type, int margin) {
        for(GameObject gameObject : gameObjects) {
            if(gameObject == source)
                continue;
            if(gameObject.getType() != type)
                continue;

            //System.out.println(gameObject);
            int myX = (int) position.getX();
            int myY = (int) position.getY();

            Vector2 otherPosition = gameObject.getPosition();
            int otherX = (int) otherPosition.getX();
            int otherY = (int) otherPosition.getY();

            int myWidth = source.getWidth();
            int myHeight = source.getHeight();
            int otherWidth = gameObject.getWidth();
            int otherHeight = gameObject.getHeight();

//        System.out.println("My { " + myX + ", " + myY + ", " + myWidth + ", " + myHeight + ", " + (myX + myWidth) + ", " + (myY + myHeight) + " }, Other { " + otherX + ", " + otherY + ", " + otherWidth + ", " + otherHeight + ", " + (otherX + otherWidth) + ", " + (otherY + otherHeight) + " }");

//            int margin = 1;

            boolean topLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean topRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean bottomLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;
            boolean bottomRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;

//            System.out.println("top left: " + topLeft + ", top right: " + topRight + ", bottom left: " + bottomLeft + ", bottom right: " + bottomRight);

            if(topLeft || topRight || bottomLeft || bottomRight)
                return gameObject;
        }

        return null;
    }
}
