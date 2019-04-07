package com.avekvist.pacman.core;

import com.avekvist.pacman.Game;
import com.avekvist.pacman.core.graphics.Animation;
import com.avekvist.pacman.core.graphics.Letter;
import com.avekvist.pacman.core.graphics.Sprite;
import com.avekvist.pacman.core.helper.Direction;
import com.avekvist.pacman.core.math.Vector2;
import com.avekvist.pacman.objects.PacMan;
import com.avekvist.pacman.core.graphics.PointText;
import com.avekvist.pacman.objects.ghosts.*;
import com.avekvist.pacman.objects.points.Pellet;
import com.avekvist.pacman.objects.points.PowerPellet;
import com.avekvist.pacman.objects.points.fruits.Fruit;
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
import static com.avekvist.pacman.core.graphics.SpriteSheet.graphics;

public class Level {
    private static boolean fruitIsTaken;
    private final Sprite pacmanSprite;
    private String path;
    private static int width, height, tileSize;
    private static ArrayList<GameObject> gameObjects;

    private static PacMan pacman;
    private static Vector2 pacmanSpawnPoint;
    private FruitSpawner fruitSpawner;
    private Pinky pinky;
    private Inky inky;
    private Blinky blinky;
    private Clyde clyde;
    private Vector2 textPosition;
    private static ArrayList<PointText> pointTexts;
    private ArrayList<Pellet> pellets;
    private Sprite fruitSprite;

    public Level(String path, int tileSize) {
        this.path = path;
        this.tileSize = tileSize;
        gameObjects = new ArrayList<>();
        pointTexts = new ArrayList<>();
        pellets = new ArrayList<>();
        pacmanSprite = new Sprite();
        pacmanSprite.setAnimation(new Animation(graphics, 4, 3, 12 * 3, 12 * 3, 12 * 3, 12 * 3));
        pacmanSprite.setAnimationDirection(0);
        pacmanSprite.setAnimationDelay(0);

        fruitSprite = new Sprite();
        fruitSprite.setAnimation(new Animation(graphics, 0, 5, 12 * 3, 12 * 3, 12 * 3 * 8, 12 * 3));
        fruitSprite.setAnimationDirection(0);
        fruitSprite.setAnimationDelay(0);

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
                    switch(pixels[x + y * w] - 0xFF000000) {
                        case 1:
                            pacman = new PacMan();
                            pacmanSpawnPoint = new Vector2(x * tileSize, y * tileSize);
                            pacman.setPosition(pacmanSpawnPoint);
                            add(pacman);
                            break;
                        case 2:
                            blinky = new Blinky();
                            blinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            blinky.setDirection(Direction.UP);
                            blinky.setStartPosition(blinky.getPosition());
                            add(blinky);
                            break;
                        case 3:
                            pinky = new Pinky();
                            pinky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            pinky.setDirection(Direction.LEFT);
                            pinky.setStartPosition(pinky.getPosition());
                            add(pinky);
                            break;
                        case 4:
                            inky = new Inky();
                            inky.setPosition(new Vector2(x * tileSize, y * tileSize));
                            inky.setDirection(Direction.DOWN);
                            inky.setStartPosition(inky.getPosition());
                            add(inky);
                            break;
                        case 5:
                            clyde = new Clyde();
                            clyde.setPosition(new Vector2(x * tileSize, y * tileSize));
                            clyde.setDirection(Direction.RIGHT);
                            clyde.setStartPosition(clyde.getPosition());
                            add(clyde);
                            break;
                        case 6:
                            Pellet pellet = new Pellet();
                            pellet.setPosition(new Vector2(x * tileSize + tileSize / 2 - pellet.getWidth() / 2, y * tileSize + tileSize / 2 - pellet.getHeight() / 2));
                            add(pellet);
                            pellets.add(pellet);
                            break;
                        case 7:
                            PowerPellet powerPellet = new PowerPellet();
                            powerPellet.setPosition(new Vector2(x * tileSize + tileSize / 2 - powerPellet.getWidth() / 2, y * tileSize + tileSize / 2 - powerPellet.getHeight() / 2));
                            add(powerPellet);
                            break;
                        case 8:
                            Wall1 wall1 = new Wall1();
                            wall1.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall1);
                            break;
                        case 9:
                            Wall2 wall2 = new Wall2();
                            wall2.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall2);
                            break;
                        case 10:
                            Wall3 wall3 = new Wall3();
                            wall3.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall3);
                            break;
                        case 11:
                            Wall4 wall4 = new Wall4();
                            wall4.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall4);
                            break;
                        case 12:
                            Wall5 wall5 = new Wall5();
                            wall5.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall5);
                            break;
                        case 13:
                            Wall6 wall6 = new Wall6();
                            wall6.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall6);
                            break;
                        case 14:
                            Wall7 wall7 = new Wall7();
                            wall7.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall7);
                            break;
                        case 15:
                            Wall8 wall8 = new Wall8();
                            wall8.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall8);
                            break;
                        case 16:
                            Wall9 wall9 = new Wall9();
                            wall9.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall9);
                            break;
                        case 17:
                            Wall10 wall10 = new Wall10();
                            wall10.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall10);
                            break;
                        case 18:
                            Wall11 wall11 = new Wall11();
                            wall11.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall11);
                            break;
                        case 19:
                            Wall12 wall12 = new Wall12();
                            wall12.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall12);
                            break;
                        case 20:
                            Wall13 wall13 = new Wall13();
                            wall13.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall13);
                            break;
                        case 21:
                            Wall14 wall14 = new Wall14();
                            wall14.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall14);
                            break;
                        case 22:
                            Wall15 wall15 = new Wall15();
                            wall15.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall15);
                            break;
                        case 23:
                            Wall16 wall16 = new Wall16();
                            wall16.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall16);
                            break;
                        case 24:
                            Wall17 wall17 = new Wall17();
                            wall17.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall17);
                            break;
                        case 25:
                            Wall18 wall18 = new Wall18();
                            wall18.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall18);
                            break;
                        case 26:
                            Wall19 wall19 = new Wall19();
                            wall19.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall19);
                            break;
                        case 27:
                            Wall20 wall20 = new Wall20();
                            wall20.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall20);
                            break;
                        case 28:
                            Wall21 wall21 = new Wall21();
                            wall21.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall21);
                            break;
                        case 29:
                            Wall22 wall22 = new Wall22();
                            wall22.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall22);
                            break;
                        case 30:
                            Wall23 wall23 = new Wall23();
                            wall23.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall23);
                            break;
                        case 31:
                            Wall24 wall24 = new Wall24();
                            wall24.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall24);
                            break;
                        case 32:
                            Wall25 wall25 = new Wall25();
                            wall25.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall25);
                            break;
                        case 33:
                            Wall26 wall26 = new Wall26();
                            wall26.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall26);
                            break;
                        case 34:
                            Wall27 wall27 = new Wall27();
                            wall27.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall27);
                            break;
                        case 35:
                            Wall28 wall28 = new Wall28();
                            wall28.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall28);
                            break;
                        case 36:
                            Wall29 wall29 = new Wall29();
                            wall29.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall29);
                            break;
                        case 37:
                            Wall30 wall30 = new Wall30();
                            wall30.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall30);
                            break;
                        case 38:
                            Wall31 wall31 = new Wall31();
                            wall31.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall31);
                            break;
                        case 39:
                            Wall32 wall32 = new Wall32();
                            wall32.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall32);
                            break;
                        case 40:
                            Wall33 wall33 = new Wall33();
                            wall33.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall33);
                            break;
                        case 41:
                            Wall34 wall34 = new Wall34();
                            wall34.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall34);
                            break;
                        case 42:
                            Wall35 wall35 = new Wall35();
                            wall35.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall35);
                            break;
                        case 43:
                            Wall36 wall36 = new Wall36();
                            wall36.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall36);
                            break;
                        case 44:
                            Wall37 wall37 = new Wall37();
                            wall37.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall37);
                            break;
                        case 45:
                            Wall38 wall38 = new Wall38();
                            wall38.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall38);
                            break;
                        case 46:
                            Wall39 wall39 = new Wall39();
                            wall39.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall39);
                            break;
                        case 47:
                            Wall40 wall40 = new Wall40();
                            wall40.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall40);
                            break;
                        case 48:
                            Wall41 wall41 = new Wall41();
                            wall41.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall41);
                            break;
                        case 49:
                            Wall42 wall42 = new Wall42();
                            wall42.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall42);
                            break;
                        case 50:
                            Wall43 wall43 = new Wall43();
                            wall43.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall43);
                            break;
                        case 51:
                            Wall44 wall44 = new Wall44();
                            wall44.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall44);
                            break;
                        case 52:
                            Wall45 wall45 = new Wall45();
                            wall45.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall45);
                            break;
                        case 53:
                            Wall46 wall46 = new Wall46();
                            wall46.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall46);
                            break;
                        case 54:
                            Wall47 wall47 = new Wall47();
                            wall47.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall47);
                            break;
                        case 55:
                            Wall48 wall48 = new Wall48();
                            wall48.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall48);
                            break;
                        case 56:
                            Wall49 wall49 = new Wall49();
                            wall49.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall49);
                            break;
                        case 57:
                            Wall50 wall50 = new Wall50();
                            wall50.setPosition(new Vector2(x * tileSize, y * tileSize));
                            add(wall50);
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

        textPosition = new Vector2(16, height - 64);

        Letter s = new Letter("S");
        s.setPosition(textPosition);
        textPosition = textPosition.add(new Vector2(6 * 3, 0));

        Letter c = new Letter("C");
        c.setPosition(textPosition);
        textPosition = textPosition.add(new Vector2(6 * 3, 0));

        Letter o = new Letter("O");
        o.setPosition(textPosition);
        textPosition = textPosition.add(new Vector2(6 * 3, 0));

        Letter r = new Letter("R");
        r.setPosition(textPosition);
        textPosition = textPosition.add(new Vector2(6 * 3, 0));

        Letter e = new Letter("E");
        e.setPosition(textPosition);
        textPosition = textPosition.add(new Vector2(6 * 3 * 2, -3));

        add(s);
        add(c);
        add(o);
        add(r);
        add(e);
    }

    public static void setPacMan(PacMan pacman) {
        Level.pacman = pacman;
    }

    public static void setFruitIsTaken(boolean b) {
        fruitIsTaken = b;
    }

    public static boolean getFruitIsTaken() {
        return fruitIsTaken;
    }

    public void update(Game game) {
        if(pellets.size() <= 0)
            game.nextLevel();

        fruitSpawner.update(game);

        if(gameObjects != null) {
            Iterator<GameObject> gameObjectsIterator = gameObjects.iterator();

            for (Iterator<GameObject> it = gameObjectsIterator; it.hasNext(); ) {
                GameObject gameObject = it.next();

                if(gameObject != null) {
                    if(gameObject.getClass() == PointText.class && !gameObject.isAlive()) {
                        gameObjectsIterator.remove();
                        pointTexts.remove(gameObject);
                        continue;
                    }

                    if(gameObject.getClass() == Pellet.class && !gameObject.isAlive()) {
                        gameObjectsIterator.remove();
                        pellets.remove(gameObject);
                        continue;
                    }

                    if (!gameObject.isAlive() && (gameObject.getClass() != PacMan.class || game.getExtraLives() <= 0)) {
                        gameObjectsIterator.remove();
                        continue;
                    }

                    gameObject.update(game);
                    gameObject.setWindowDimensions(width, height);
                }
            }
        }

        PacMan pacman = getPacMan();

        if(!pacman.isAlive()) {
            if (game.getExtraLives() > 0) {
                game.setExtraLives(game.getExtraLives() - 1);
                getPacMan().setAlive(true);
                getPacMan().setPosition(pacmanSpawnPoint);
                getPacMan().setIsDying(false);

                for(GameObject object : gameObjects) {
                    if(object.getClass().getSuperclass() == Ghost.class)
                        object.setPosition(object.getStartPosition());
                    if(object.getClass().getSuperclass() == Fruit.class)
                        object.setAlive(false);
                }
            } else {
                game.restart();
            }
        } else {
            int score = game.getScore();
            String scoreText = Integer.toString(score);

            for(int i = 0; i < scoreText.length(); i++) {
                if(i > pointTexts.size() - 1) {
                    PointText pt = new PointText("0");
                    pt.setIndex(String.valueOf(scoreText.charAt(i)));
                    pt.setPosition(textPosition.add(new Vector2(i * 3 * 6, 0)));
                    pointTexts.add(pt);
                } else if(pointTexts.size() > scoreText.length()) {
                    pointTexts.get(i).setAlive(false);
                } else {
                    pointTexts.get(i).setIndex(String.valueOf(scoreText.charAt(i)));
                }
            }

            for(PointText pt : pointTexts) {
                if(!gameObjects.contains(pt))
                    add(pt);
            }
        }
    }

    public void render(Game game, Graphics g, int[] pixels) {
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                pixels[x + y * WIDTH] = 0x000000;
            }
        }

        for(GameObject gameObject : gameObjects) {
            if(gameObject != null)
                gameObject.render(pixels);
        }

        int extraLives = game.getExtraLives();
        pacmanSprite.setWindowDimensions(width, height);

        for(int i = 0; i < extraLives; i++) {
            pacmanSprite.render(pixels, width - (4 - i) * 12 * 3, height - (12 * 3) * 2);
        }

        int fruitsTaken = game.getFruitsTaken();
        fruitSprite.setWindowDimensions(width, height);

        for(int i = 0; i < fruitsTaken; i++) {
            fruitSprite.setAnimationIndex(i);
            fruitSprite.render(pixels, width - (5 + i) * 12 * 3, height - (12 * 3) * 2);
        }
    }

    public static PacMan getPacMan() {
        return pacman;
    }

    public static void add(GameObject gameObject) {
        if(gameObject != null)
            gameObjects.add(gameObject);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static boolean collidesAt(GameObject source, Vector2 position, String type, int margin) {
        for(GameObject gameObject : gameObjects) {
            if(gameObject == null)
                continue;
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

            boolean topLeft = myX >= otherX && myX + margin <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean topRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean bottomLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;
            boolean bottomRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;

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

            int myX = (int) position.getX();
            int myY = (int) position.getY();

            Vector2 otherPosition = gameObject.getPosition();
            int otherX = (int) otherPosition.getX();
            int otherY = (int) otherPosition.getY();

            int myWidth = source.getWidth();
            int myHeight = source.getHeight();
            int otherWidth = gameObject.getWidth();
            int otherHeight = gameObject.getHeight();

            boolean topLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean topRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + margin >= otherY + margin && myY + margin <= otherY + otherHeight;
            boolean bottomLeft = myX + margin >= otherX + margin && myX + margin <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;
            boolean bottomRight = myX + myWidth >= otherX + margin && myX + myWidth <= otherX + otherWidth && myY + myHeight >= otherY + margin && myY + myHeight <= otherY + otherHeight;

            if(topLeft || topRight || bottomLeft || bottomRight)
                return gameObject;
        }

        return null;
    }

    public static void setPointTexts(ArrayList<PointText> pointText) {
        Level.pointTexts = pointText;
    }
}
