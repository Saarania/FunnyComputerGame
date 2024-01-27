/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import kachny.Settings;
import view.PlayerView;

/**
 *
 * @author Pocitac
 */
public class Player {

    private double x;
    private double y;
    private int velocityX;
    private int velocityY;
    private PlayerView playerView;
    private boolean inAir;
    private boolean inDuck;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        inAir = false;
        velocityX = Settings.STARTING_DUCK_VELOCITY_X;

    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public boolean isInDuck() {
        return inDuck;
    }

    public void setInDuck(boolean inDuck) {
        this.inDuck = inDuck;
    }

    public void jump() {
        inAir = true;
        velocityY = Settings.DUCK_JUMPING_VELOCITY;
        playerView.fly();
    }

    public void duck() {
        inDuck = true;
        y += Settings.DUCKED_HEIGHT; // aby pokrcena kachna stala na zemi
        playerView.setDuckImage();
    }

    public void unduck() {
        y -= Settings.DUCKED_HEIGHT;
        inDuck = false;
    }

    //Zrychli pohyb kachny
    // vola se z konstruktoru pokud se stishne sipka doprava
    public void speedUp() {
        velocityX = Settings.SPEED_UP_DUCK_VELOCITY_X;
    }

    //Zmeni rychlost kachny na normalni rychlost
    // volano z konstrukotru kdyz klavesa doprava prestane byt stisknuta
    public void speedDown() {
        velocityX = Settings.STARTING_DUCK_VELOCITY_X;
    }

    //VOLA se pravidelne pri padani
    public void fall() {
        //Pri pokrceni rychleji padam
        if (inDuck) {
            y -= velocityY - 15;
        }else {
        y -= velocityY;
        }
        velocityY -= Settings.DUCK_FALLING_VELOCITY;
        //Urcovani spadnuti pro padajici kachnu
        if (inDuck) {
            if (y - Settings.DUCKED_HEIGHT >= Settings.STARTING_Y_COORDINATION) {
                land();
            }
        } else {
            if (y >= Settings.STARTING_Y_COORDINATION) {
                land();
            }
        }
    }
        //Pristat
    public  void land() {
        inAir = false;
    }

}
