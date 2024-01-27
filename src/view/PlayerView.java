/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kachny.MainClass;
import kachny.Settings;
import model.Player;

/**
 *
 * @author Pocitac
 */
public class PlayerView extends ImageView {

    private Player player;
    private int imageIndex = 1; //pouziva se k urceni, ve ktere casti animace se sprite vyskytuje
    // krceni
    private int duckingImageIndex = 1; // pouziva se ke menemi obrazku pri krceni

    public PlayerView(Player player) {
        this.player = player;
        setImage(new Image("images/Duck" + imageIndex + ".png"));
    }

    private int countdown = 0;

    public void update() {
        setTranslateX(player.getX());
        setTranslateY(player.getY());
        // pro normalni chozeni
        if (!player.isInAir() && !player.isInDuck()) {
            if (++countdown == 3) {
                imageIndex = imageIndex == 1 ? 2 : 1;
                setImage(new Image("images/Duck" + imageIndex + ".png"));
                countdown = 0;
            }
        }
        // pro pokrcene chozeni
        if (player.isInDuck()) {
            changeDuckImage();
        }

    }
    
    private int duckingCountdown = 0;

    private void changeDuckImage() {
        if (duckingCountdown++ == 3) {
            duckingImageIndex = duckingImageIndex == 1 ? 2 : 1;
            setImage(new Image("images/Duck" + duckingImageIndex + "duck.png"));
            duckingCountdown = 0;
        }
    }
    
    //Provede se pri kazdym pokrceni, zabranuje chybe kdy misto skrcene kachny je kachna v normalnim obrazku, ale kachna se posune dolu
    //Zavolano ze tridy Player jakmile se stiskne klavesa DOWN
    public void setDuckImage() {
        duckingCountdown = 3; // aby pri zavolani metody update byla kachna hned v animaci skrceni
        update();
        
    }
    
    public void fly() {
        //Obrazek takovyhle zustane, zmeni se pouze az prestane platit player.isInAir == true;
        setImage(new Image("images/Duck_flying.png"));
    }
}
