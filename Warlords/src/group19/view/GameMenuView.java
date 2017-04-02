package group19.view;
import group19.controller.GameStateController;
import group19.controller.InGameViewController;
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

public class GameMenuView extends Application {
    private static VBox menuBox; //single vertical column to display all game menu options
    private int currentItem = 0; //cycle counter for each option
    GameStateController gsc = new GameStateController(); //create instance of game state controller
    private static Scene gameMenu = new Scene(createScene());
    private static Stage window; //pass out top-level Stage container so other classes can call setScene to switch scenes
    
    private static Parent createScene() { //from javafx.scene, base class
        Pane root = new Pane(); 
        root.setPrefSize(1024, 768);
        Rectangle bg = new Rectangle(1024, 768); //set rectangle of 1024x768 size, default color black (change later)
        ContentFrame titleFrame = new ContentFrame(title()); 
        HBox title = new HBox(titleFrame); //objects stack horizontally (only 1 item)
        title.setTranslateX(235); //centered in middle of screen
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
        root.getChildren().addAll(bg, title, menuBox); //place all created items
        return root;
    }

    private static Node title() {
        String title = "WARLORDS";
        HBox letters = new HBox(5); //for aesthetic
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + ""); //"" denotes the blank space
            letter.setFont(Font.font("Helvetica", FontWeight.LIGHT, 96));
            letter.setFill(Color.ANTIQUEWHITE);
            letters.getChildren().add(letter);
            FadeTransition ft = new FadeTransition(Duration.seconds(1), letter); //fade effect
            ft.setFromValue(1);
            ft.setToValue(0.5);
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
            text.setFont(Font.font("Helvetica", FontWeight.LIGHT, 40));
            getChildren().addAll(text);
            setActive(false);
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.ANTIQUEWHITE : Color.GREY); //if b = true (active option) then fill white 
        }
    }
    @SuppressWarnings("static-access")
	@Override
    public void start(Stage window) throws Exception {
        //use getClassLoader() so getResource() searches relative to classpath, instead of .class file 
        //NOTE: .ogg files do not work with AudioClip, need to import MediaPlayer for that
        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.mp3").toString());
        menuSelect.setVolume(gsc.getSFXVolume()); 
        modeSelect.setVolume(gsc.getSFXVolume());
        window.setWidth(1024);
        window.setHeight(768);
        gameMenu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
            	
                if (currentItem > 0) {
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
                	gsc.setGameState(1); //1 = game_in_progress
                	InGameViewController newGame = new InGameViewController(); //call a new instance of IGVC
                	//IGVC calls IGV in constructor and sets the scene up
                	window.setScene(InGameViewController.view.getScene()); //load IGV scene onto the existing Stage
                	System.out.println("single player mode");
                	break;
                case 1: //local multiplayer
                	System.out.println("multiplayer mode");
                	break;
                case 2://story mode
                	System.out.println("story mode");
                	break;
                case 3://options
                	OptionsView.displayOptionsView(); //popup the game view
                	System.out.println("options");
                	break;
                case 4://exit
                	System.exit(0);
                }
            }
        });
        window.setScene(gameMenu);
        window.setResizable(false); //cant change dimensions
        window.sizeToScene();
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