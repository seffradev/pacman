package com.avekvist.pacman.objects.points.fruits;

import com.avekvist.pacman.Game;
import com.avekvist.pacman.core.Level;
import com.avekvist.pacman.core.helper.Timer;
import com.avekvist.pacman.core.math.Vector2;

public class FruitSpawner {
    private boolean fruitIsTaken;
    private int delay;
    private Timer timer;
    private Vector2 spawnPosition;

    public FruitSpawner(Vector2 spawnPosition, int delay) {
        this.delay = delay;
        this.spawnPosition = spawnPosition;

        timer = new Timer();
        timer.setDelay(delay);
        fruitIsTaken = false;
    }

    public void update(Game game) {
        if(!fruitIsTaken) {
            fruitIsTaken = Level.getFruitIsTaken();
            timer.update();

            if (timer.getDelay() <= 0) {
                Fruit fruit;
                switch (game.getLevelsBeaten()) {
                    case 0:
                        fruit = new Cherry();
                        break;
                    case 1:
                        fruit = new Strawberry();
                        break;
                    case 2:
                        fruit = new Orange();
                        break;
                    case 3:
                        fruit = new Apple();
                        break;
                    case 4:
                        fruit = new Melon();
                        break;
                    case 5:
                        fruit = new GalaxianBoss();
                        break;
                    case 6:
                        fruit = new Bell();
                        break;
                    case 7:
                        fruit = new Key();
                        break;
                    default:
                        fruit = null;
                        break;
                }

                if (fruit != null) {
                    fruit.setPosition(spawnPosition);
                    Level.add(fruit);

                    timer.setDelay(fruit.getDelay() / 60 + delay);
                } else
                    timer.setDelay(delay);
            }
        }
    }
}
