/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kachny;

import controller.Controller;
import view.WorldView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.Keyboard;

/**
 *
 * @author Pocitac
 *

 * PRIDAT ZRYCHLENI KDYZ DRZIM SIPKU DOPREDU
 */
public class MainClass extends Application {

    public static Stage primaryStage;
    public static Scene scene;
    public static boolean gameRunning = true;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Group root = WorldView.getInstance(); //hlavni parrent
        scene = new Scene(root, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        Keyboard.getInstance().sestupHandlers(scene);
        FileManager.getInstance(); //Pro zavolani konstruktoru
        
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("Kachny!!!");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreenExitHint("");
    }

    //zavola se po zacatku hry kliknutim na start
    public static void startTimer() {
        Thread thread = new Thread(() -> {
            while (gameRunning) {
                Platform.runLater(() -> {
                    Controller.timeChanged();
                    WorldView.getInstance().update();
                });
                try {
                    Thread.sleep(Settings.TIMER_INTERVAL);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
