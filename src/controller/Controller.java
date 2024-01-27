/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import kachny.MainClass;
import model.Object;
import model.Player;
import kachny.Settings;
import model.SoundFactory;
import view.WorldView;

/**
 *
 * @author Pocitac
 */
public class Controller {

    private static Player player = null;
    private static ArrayList<Object> backgroundObjects;
    private static SoundFactory quackSound;
    private static int score = 0;
    private static int lives = 3;
    public static boolean quackingOn = true;

    public static void startGame() {
        player = new Player(Settings.DUCK_COORDINATION_X, Settings.STARTING_Y_COORDINATION);
        Object[] backgroundObjectsArray = {new Object(0, 0, 0, false, "background"),
            new Object(1100, 30, 0.01, false, "sun"), new Object(100, Settings.GROUND_LAYER - 543 /*Vyska stromu*/, 0.2, false, "tree1"),
            new Object(900, Settings.GROUND_LAYER - 359 /*Vyska stromu*/, 0.5, false, "tree2"),
            new Object(420, Settings.GROUND_LAYER - 183/*Vyska kere*/, 0.5, false, "tree3"),
            new Object(0, Settings.GROUND_LAYER, 1, false, "ground"),
            new Object(Settings.SCREEN_WIDTH, Settings.GROUND_LAYER, 1, false, "ground"),
            new Object(0, Settings.GROUND_LAYER - 96, 1, false, "grass"), new Object(Settings.SCREEN_WIDTH, Settings.GROUND_LAYER - 96, 1, false, "grass"),};
        backgroundObjects = new ArrayList<Object>(Arrays.asList(backgroundObjectsArray));
        //Music trida
        SoundFactory mf = new SoundFactory("Theme");
        mf.start();
        mf.setInfiniteLooping();

        quackSound = new SoundFactory("Quack1");
    }

    private static int spawnCounter = 0;
    private static int secondCounter = 0; //Pro kazdou vterinu...
    private static boolean controllingPause = false;
    private static SoundFactory deathSound = new SoundFactory("Death");

    //kazdy "interval" zavolano v mainu druhym vlaknem
    public static void timeChanged() {
        score++;
        //pohyb objektu okolnich
        try {
            for (Object object : backgroundObjects) {
                object.move();
                if (!controllingPause) {
                    if (object.getType().equals("enemy") && player.getY() + 200 > Settings.GROUND_LAYER - 100) {
                        if (object.chceckCollision()) {
                            controllingPause = true;
                            deathSound.start();
                            lives--;
                            WorldView.getInstance().loseLife();
                        }
                    }
                } else {
                    if (++secondCounter >= 4000 / Settings.TIMER_INTERVAL) {
                        secondCounter = 0;
                        controllingPause = false;
                    }
                }
            }
        } catch (java.util.ConcurrentModificationException ex) {

        }
        //Padani, jestli je kachna ve vzduchu
        if (player.isInAir()) {
            player.fall();
        }

        if (++spawnCounter >= 1200 / Settings.TIMER_INTERVAL) {
            spawnCounter = 0;
            WorldView.getInstance().createEnemy();
        }
    }

    //Na stridani QUAK zvuku
    private static int quackCounter = 1;

    public static void hitMe() {

    }

    public static void makeQuack() {
        if (quackingOn) {
        quackSound = new SoundFactory("Quack" + quackCounter);
        quackSound.start();
        quackCounter = quackCounter < 7 ? quackCounter + 1 : 1;
        }
    }

    public static ArrayList<Object> getBgObjects() {
        return backgroundObjects;
    }

    public static Player getPlayer() {
        return player;
    }
    public static int getScore() {
        return score;
    }
    public static int getLives() {
        return lives;
    }

    public static void upPressed() {
        //Vyletet
        if (!player.isInAir()) {
            player.jump();
            makeQuack();
        }
    }

    public static void downPressed() {
        player.duck();
        makeQuack();

    }

    public static void downReleased() {
        player.unduck();
    }

    public static void rightPressed() {
        makeQuack();
        player.speedUp();
    }

    public static void rightReleased() {
        player.speedDown();
    }
}
