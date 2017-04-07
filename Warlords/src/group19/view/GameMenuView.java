package group19.view;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import group19.model.GameModel;
import javafx.animation.FadeTransition; 
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

//This class is the entry point of the application, as seen by the extends Application, start, and main methods.
//The class renders a scene where the user can select options. Pressing SINGLE PLAYER is the only option which
//unloads the scene graph and loads in the InGameView.
//The menu is entirely operated by keyboard as per the brief specifications. 

public class GameMenuView extends Application {
    private static VBox menuBox; //a single vertical column, displaying all options
    private int currentItem = 0; 
    GameStateController gsc = new GameStateController(); //gsc controls volume, and will control game states later
    private static Scene gameMenu = new Scene(createScene()); 
    private static Stage window; //pass out top-level Stage container so other classes can call setScene to switch scenes

    GameModel models; 

    
    private static Parent createScene() { 
        Pane root = new Pane(); 
        root.setPrefSize(1024, 768); //dimensions set by specifications
        Rectangle bg = new Rectangle(1024, 768); 
        ContentFrame titleFrame = new ContentFrame(title()); 
        HBox title = new HBox(titleFrame); //child node of content frame, for title
        title.setTranslateX(235);
        title.setTranslateY(100);
        menuBox = new VBox(10,
                new MenuItem("SINGLE PLAYER"),
                new MenuItem("LOCAL MULTIPLAYER"),
                new MenuItem("STORY MODE"),
                new MenuItem("OPTIONS"),
                new MenuItem("EXIT")
        		);
        menuBox.setTranslateX(235); 
        menuBox.setTranslateY(358);
        getMenuItem(0).setActive(true); //highlight first item in menu
        root.getChildren().addAll(bg, title, menuBox); //place all created items as children of parent Pane
        return root;
    }

    private static Node title() {
        String title = "WARLORDS";
        HBox letters = new HBox(5); //for slight spacing
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + ""); //"" denotes the blank spacing
            letter.setFont(Font.font("Arial", FontWeight.LIGHT, 96));
            letter.setFill(Color.ANTIQUEWHITE);
            letters.getChildren().add(letter); //letter is child node of letters 
            FadeTransition ft = new FadeTransition(Duration.seconds(1), letter); //fade effect
            ft.setFromValue(1);
            ft.setToValue(0.2);
            ft.setAutoReverse(true);
            ft.setCycleCount(TranslateTransition.INDEFINITE);
            ft.play();
        }
        return letters;
    }

    private static MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index); //cast MenuBox to MenuItem (since it consists of them)
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) { //input parameter is createMiddleContent
            setAlignment(Pos.CENTER);
            getChildren().addAll(content);
        }
    }

    private static class MenuItem extends HBox {
        private Text text;

        public MenuItem(String name) {
            text = new Text(name);
            text.setFont(Font.font("Arial", FontWeight.LIGHT, 40));
            getChildren().addAll(text);
            setActive(false); //apart from first element, rest of options are not active
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.ANTIQUEWHITE : Color.GREY); //if b = true (active option) then fill white 
        }
    }
	@Override
    public void start(Stage window) throws Exception {
        //use getClassLoader() so getResource() searches relative to classpath, instead of .class file 
        //NOTE: .ogg files do not work with AudioClip, need to import MediaPlayer for that
        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());
        menuSelect.setVolume(gsc.getSFXVolume());  //set volume of SFX according to gsc attribute
        modeSelect.setVolume(gsc.getSFXVolume());
        window.setWidth(1024);
        window.setHeight(768);
        gameMenu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
            	
                if (currentItem > 0) { //swap active options, play sound
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true); 
                    menuSelect.play();
                    
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                    menuSelect.play();
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                modeSelect.play();
                switch(currentItem) {
                case 0: //single player
                	InGameViewController.gsc.setGameState(1); //1 = game_in_progress
                	InGameViewController.gsc.setSinglePlayer(true); //make it single player mode
                	// every time this is called, new instance of gamemodel is made
                	// for some reason the ball is really fking fast when a new game is made 
                	InGameViewController newGame = new InGameViewController(new GameModel()); //call a new instance of IGVC, gameLoop starts
                	//IGVC calls IGV in constructor and sets the scene up
                	window.setScene(InGameViewController.view.getScene()); //load IGV scene onto the existing Stage
                	System.out.println("single player mode");
                	break;
                case 1: //local multiplayer
                	InGameViewController.gsc.setSinglePlayer(false); //load multiplayer mode
                	System.out.println("multiplayer mode coming soon!");
                	break;
                case 2://story mode
                	System.out.println("story mode coming soon!");
                	break;
                case 3://options
                	OptionsView.displayOptionsView(); //popup the options view
                	System.out.println("options");
                	break;
                case 4://exit
                	System.exit(0); //macro to close application
                }
            }
        });
        window.setScene(gameMenu); //if setOnKeyPressed didn't trigger scene change, keep Stage on gameMenu
        window.setResizable(false); //disallow dimension changes
        window.sizeToScene(); //make window same size as scene (locked to 768 width no matter what OS)
        window.show();
        this.window = window; //pass window out so other classes can use it 
        }
    
    

    public static void main(String[] args) { //stick main here so game starts on this file
        launch(args);
    }

	public static Stage getWindow() {
		return window;
	}

	public static Scene getGameMenu() {
		return gameMenu;
	}
}