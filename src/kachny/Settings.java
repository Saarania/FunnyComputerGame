/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kachny;

import javafx.stage.Screen;

/**
 *
 * @author Pocitac
 */
public class Settings {
    private static javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    
    
    // x pocatecni pozice hrace ve hre, nemmena, nastavena podle sirky monitoru
    public static final int SCREEN_WIDTH = 1366;
    public static final int SCREEN_HEIGHT = 768;
    public static final int GROUND_LAYER = 768- 204; //odkdy zacina plocha
    public static final int TIMER_INTERVAL = 30;
    
    //DUCK
    public static final int DUCK_COORDINATION_X = 120;
    public static final int STARTING_Y_COORDINATION = Settings.GROUND_LAYER - 222/*vyska obrazku kachny*/; /*hit the layer*/
    public static final int STARTING_DUCK_VELOCITY_X = 10;
    public static final int SPEED_UP_DUCK_VELOCITY_X = STARTING_DUCK_VELOCITY_X * 2;
    public static final int DUCK_JUMPING_VELOCITY = 40;
    public static final int DUCK_FALLING_VELOCITY = 2;
    public static final int DUCKED_HEIGHT = 222-130; //O kolik musi kachna klesnout kdyz se zkrci

    
}
