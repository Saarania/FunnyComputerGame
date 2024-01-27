/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;
import kachny.Settings;
import view.ObjectView;
import view.WorldView;

/**
 *
 * @author Pocitac
 *
 * Tato trida znazornuje objekt na pozadi pohubujici se proti smeru kachny v
 * ruznych rychlostech podle toho jak jsou daleko. Z duvodu ze kachna jako
 * takova se nehybe a vsechno ostatni se hybe proti ni. Dulezity boolean
 * parametr rozhoduje, zda objekt je prekazka(auto) a muze kachne ublizit a nebo
 * je jen objekt v dalce slouzici jako pohyblive pozadi
 *
 */
public class Object {

    private double x;
    private double y; //Neni final protoze pri nastaveni jineho obrazku se musi y souradnice nastavit aby strom stal na zemi
    // kazdy objekt se pohybuje jinak rychle, napr. slunce nejpomaleji, Normalne 1, coz znamena stejne rychle jako hrac, ale slunce treba 0.01 aby bylo fakt pomale
    private double weight;
    private final boolean dangerous; //if true muze hraci ublizit(auto) else je to jen dekorace
    // sun, tree, ground, background... muzu doplnovat ale musi to byt napsane bez chyby, urceno pro rozdeleni ve view
    private final String type;
    private ObjectView objectView;

    public Object(double x, double y, double velocityX, boolean dangerous, String typ) {
        this.x = x;
        this.y = y;
        this.weight = velocityX;
        this.dangerous = dangerous;
        this.type = typ;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public String getType() {
        return type;
    }

    public ObjectView getObjectView() {
        return objectView;
    }

    public void setObjectView(ObjectView objectView) {
        this.objectView = objectView;
    }

    public void setY(double y) {
        this.y = y;
    }

    //Pro generovani nahodne hmotnosti (urcuje jak rychle se bude strom posunovat)
    private static Random randomWeight = new Random();
    public void move() {
        x -= weight * controller.Controller.getPlayer().getVelocityX();
        //NASTAVOVANI ZNOVU UKAZANI OBJEKTU
        if (x <= 0) {
            //Kdyz se jedna o podlahu nebo travu ktera je potreba posunout znovu dopredu
            if ((type.equals("ground") || type.equals("grass")) && x <= -Settings.SCREEN_WIDTH) {
                x += Settings.SCREEN_WIDTH*2;
            }
            
            //Kdyz se jedna o stromy, rozhodne se typ stromu(a jeslti pujde videt) a posune se
            if (type.substring(0, 4).equals("tree") && x <= - 500) {
                objectView.resetTree();
                weight = randomWeight.nextDouble() + randomWeight.nextInt(2);
                x = Settings.SCREEN_WIDTH;
                y = Settings.GROUND_LAYER - objectView.getImage().getHeight();
            }
            
            if (type.equals("enemy") && x <= -300) {
                WorldView.getInstance().getChildren().remove(objectView);
                objectView = null;
                controller.Controller.getBgObjects().remove(this);
            } 
        }
    }

    public boolean chceckCollision() {
        if (x > Settings.DUCK_COORDINATION_X && x < Settings.DUCK_COORDINATION_X + 250) {
            return true;
        }
        return false;
    }

}
