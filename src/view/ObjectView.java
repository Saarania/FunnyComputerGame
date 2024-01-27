/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kachny.Settings;
import model.Object;

/**
 *
 * @author Pocitac
 */
public class ObjectView extends ImageView {

    private Object object;

    public ObjectView(Object object, String imageName) {
        this.object = object;
        setImage(new Image("images/" + imageName + ".png"));
    }

    public void update() {
        setTranslateX(object.getX());
        setTranslateY(object.getY());

        
    }

    private static Random random = new Random();

    //Vola se z objektu ktery se posune tak ze nejde videt
    public void resetTree() {
        int randomInt = random.nextInt(4);
        setVisible(true);
        if (randomInt == 0) {
            setVisible(false);
        } else {
            setImage(new Image("images/Tree" + randomInt + ".png"));
        }
        update();
    }

    public Object getObject() {
        return object;
    }
    
}
