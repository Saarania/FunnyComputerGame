/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import kachny.MainClass;

/**
 *
 * @author Pocitac
 */
public class Keyboard {

    private static final Keyboard keyboard = new Keyboard();

    private boolean firstDownPressed = false; //aktivuje prikazy, ktere se maji provest pouze jednou po stisknuti dolu
    private boolean firstRightPressed = false; //zabranuje volani metod pri drzeni sipky doprava vic nez jednou

    public static Keyboard getInstance() {
        return keyboard;
    }

    private Keyboard() {

    }

    public void sestupHandlers(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            updateState(true, event.getCode());
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            updateState(false, event.getCode());
        });
    }

    private void updateState(boolean pressed, KeyCode pressedKey) {
        //KEY PRESSED
        
        //vypnuti
        if (pressedKey == KeyCode.ESCAPE && pressed) {
            MainClass.primaryStage.close();
        }
        //litani
        if ((pressedKey == KeyCode.UP || pressedKey == KeyCode.W || pressedKey == KeyCode.SPACE) && pressed) {
            Controller.upPressed();
        }
        //krceni
        if ((pressedKey == KeyCode.S || pressedKey == KeyCode.DOWN) && pressed) {
            if (!firstDownPressed) {
                firstDownPressed = true;
                Controller.downPressed();
            }
        }
        //speeed
        if ((pressedKey == KeyCode.RIGHT || pressedKey == KeyCode.D) && pressed) {
            if (!firstRightPressed) {
            Controller.rightPressed();
            firstRightPressed = true;
            }
        }
        

        //KEY RELEASE
        
        //krceni
        if ((pressedKey == KeyCode.S || pressedKey == KeyCode.DOWN) && !pressed) {
            Controller.downReleased();
            firstDownPressed = false;
        }
        
        //speeed
        if ((pressedKey == KeyCode.RIGHT || pressedKey == KeyCode.D) && !pressed) {
            Controller.rightReleased();
            firstRightPressed = false;
        }

    }
}
