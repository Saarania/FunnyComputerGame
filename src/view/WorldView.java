package view;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import controller.Controller;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import kachny.FileManager;
import kachny.MainClass;
import kachny.Settings;
import model.Object;

/**
 *
 * @author Pocitac
 */
public class WorldView extends Group {

    private static final WorldView worldView = new WorldView();
    private static ArrayList<ObjectView> objectList;
    private MyText scoreText;
    private MyText livesText;
    
    private WorldView() {
        objectList = new ArrayList<>();
        createMainMenu();
    }

    public static WorldView getInstance() {
        return worldView;
    }

    public void createMainMenu() {
        ImageView mainMenuBackground = new ImageView("images/MainMenuBackground.png");
        ImageView quit = new ImageView("images/Quit.png"),start = new ImageView("images/Start.png");
        
        ImageView checkbox = new ImageView("images/Checkbox1.png");
        checkbox.setTranslateX(100);
        checkbox.setTranslateY(200);
        checkbox.setOnMouseClicked((MouseEvent event) -> {
            Controller.quackingOn = !Controller.quackingOn;
            if (Controller.quackingOn) {
                checkbox.setImage(new Image("images/Checkbox1.png"));
            }else {
                checkbox.setImage(new Image("images/Checkbox2.png"));
            }
        });
        
        getChildren().addAll(mainMenuBackground, quit, start,checkbox);
        quit.setTranslateX(1366 / 2 - 100);
        quit.setTranslateY(450);
        quit.setOnMouseClicked((MouseEvent event) -> {
            MainClass.primaryStage.close();
        });
        start.setTranslateX(1366 / 2 - 95);
        start.setTranslateY(200);
        start.setOnMouseClicked((MouseEvent event) -> {
            start.setVisible(false);
            getChildren().removeAll(mainMenuBackground, quit, start,checkbox);
            createGameSprites();
            MainClass.startTimer(); //spusti casovac
        });

    }

    public void createGameSprites() {
        Controller.startGame();

        //Objekty
        ArrayList<Object> backgroundObjects = Controller.getBgObjects();
        for (Object object : backgroundObjects) {
            if (object.getType().equals("background")) {
                objectList.add(new ObjectView(object, "Background"));
            }
            if (object.getType().equals("grass")) {
                objectList.add(new ObjectView(object, "Grass"));
            }
            if (object.getType().equals("ground")) {
                objectList.add(new ObjectView(object, "Ground"));
            }
            if (object.getType().equals("sun")) {
                objectList.add(new ObjectView(object, "Sun"));
            }
            if (object.getType().equals("tree1")) {
                objectList.add(new ObjectView(object, "Tree1"));
            }
            if (object.getType().equals("tree2")) {
                objectList.add(new ObjectView(object, "Tree2"));
            }
            if (object.getType().equals("tree3")) {
                objectList.add(new ObjectView(object, "Tree3"));
            }
            if (object.getType().equals("fourleaves")) {
                objectList.add(new ObjectView(object, "FourLeaves"));
            }
            object.setObjectView(objectList.get(objectList.size() - 1));//pridani view do objektu
        }
        //Vykresleni spritu ve hre
        for (ObjectView objectView : objectList) {
            getChildren().add(objectView);
            objectView.update();
        }
        //Hrac
        PlayerView playerView = new PlayerView(Controller.getPlayer());
        Controller.getPlayer().setPlayerView(playerView);
        getChildren().add(playerView);
        //Score
        scoreText = new MyText("Score = " + 0, 40,50,20);
        getChildren().add(scoreText);
        //Zdravi
        livesText = new MyText("Lives = " + 3,40,100,20);
        livesText.setFill(Color.RED);
        getChildren().add(livesText);
    }

    //vola se z casovace aby se obraz nastavil na souradnice hrace a vsechno posunulo
    public void update() {
        for (Node children : getChildren()) {
            if (children instanceof ObjectView) {
                ((ObjectView) children).update();
            }
            if (children instanceof PlayerView) {
                ((PlayerView) children).update();
            }
        }
        scoreText.setText("Score = "+Controller.getScore());
    }

    public void setImageAttributes(int x, int y, ImageView... imageViews) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setTranslateX(x);
            imageViews[i].setTranslateY(y);
            getChildren().add(imageViews[i]);
        }
    }
    
    private Random random = new Random();
    public void createEnemy() {
        int randomEnemy = random.nextInt(4); //1,2,3  => 3 typy nepratel, 0 => nic se nevytvori
        ObjectView enemyView = null;
        
        if (randomEnemy == 0)return;
        //Ify kvuli nastavovani souradnice y zavisle na vysce obrazku
        if (randomEnemy == 1) {
            enemyView = new ObjectView(new Object(Settings.SCREEN_WIDTH, Settings.GROUND_LAYER - 96 , random.nextDouble() + random.nextInt(3)+1, true, "enemy"), "Enemy1");
        }
        if (randomEnemy == 2) {
            enemyView = new ObjectView(new Object(Settings.SCREEN_WIDTH, Settings.GROUND_LAYER - 171, random.nextDouble() + random.nextInt(3)+1, true, "enemy"), "Enemy2");
        }
        if (randomEnemy == 3) {
            enemyView = new ObjectView(new Object(Settings.SCREEN_WIDTH, Settings.GROUND_LAYER - 171,  random.nextDouble() + random.nextInt(3)+1, true, "enemy"), "Enemy3");
        }
        enemyView.getObject().setObjectView(enemyView);
        Controller.getBgObjects().add(enemyView.getObject());
        getChildren().add(enemyView);
    }
    
    public void loseLife() {
        livesText.setText("Lives = "+ Controller.getLives());
        if (Controller.getLives() <= 0) { //Kdyz prohrajes
            MainClass.gameRunning = false;
            
            if (Controller.getScore() > Integer.parseInt(FileManager.getInstance().readFromFile())) {
                FileManager.getInstance().writeToFile(Integer.toString(Controller.getScore()));
            }
            
            MyText endText = new MyText("Game Over", Settings.SCREEN_WIDTH/2 - 200, Settings.SCREEN_HEIGHT /2 - 50,80);
            MyText finalScoreText = new MyText("Score = " + Controller.getScore(),Settings.SCREEN_WIDTH/2 - 100,Settings.SCREEN_HEIGHT/2+100, 45);
            MyText finalInfoText = new MyText("Click on Game Over to exit...",Settings.SCREEN_WIDTH/2 - 125,Settings.SCREEN_HEIGHT/2+130, 25);
            MyText highScoreText = new MyText("Highscore = "+FileManager.getInstance().readFromFile(),Settings.SCREEN_WIDTH/2 - 130,Settings.SCREEN_HEIGHT/2+300, 40);
            highScoreText.setFill(Color.RED);
            getChildren().addAll(endText, finalScoreText,finalInfoText,highScoreText);
            endText.setOnMouseClicked((MouseEvent event) -> {
                MainClass.primaryStage.close();
            });
        }
    }
}
