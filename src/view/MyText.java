/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Pocitac
 */
public class MyText extends Text {
    public MyText(String string, int x, int y, double size) {
        super(string);
        setTranslateX(x);
        setTranslateY(y);
        setFont(Font.font(size));
    }
    
}
