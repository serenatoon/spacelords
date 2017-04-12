package group19.view;


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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

//This class is the entry point of the application, as seen by the extends Application, start, and main methods.
//The class renders a scene where the user can select options. Pressing SINGLE PLAYER is the only option which
//unloads the scene graph and loads in the InGameView.
//The menu is entirely operated by keyboard, and the highlighting/de-highlighting effect is adapted from code attributed
//here: https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/tutorial26/MKXMenuApp.java

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
        title.setTranslateX(185);
        title.setTranslateY(100);
        menuBox = new VBox(20, //all options
                new MenuItem("single player"),
                new MenuItem("local multiplayer"),
                new MenuItem("select player names"),
                new MenuItem("credits"),
                new MenuItem("instructions"),
                new MenuItem("quit game")
        		);
        menuBox.setTranslateX(185); 
        menuBox.setTranslateY(298);
        getMenuItem(0).setActive(true); //highlight first item in menu
        root.getChildren().addAll(bg, title, menuBox); //place all created items as children of parent Pane
        return root;
    }

    private static Node title() {
        String title = "SPACELORDS";
        HBox letters = new HBox(5); //for slight spacing
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + ""); //"" denotes the blank spacing
            letter.setFont(Font.font("Arial", FontWeight.LIGHT, 90));
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
        window.setWidth(1024);
        window.setHeight(768);
        gameMenu.setOnKeyReleased(event -> {
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
                	InGameViewController singlePlayerGame = new InGameViewController(new GameModel()); //call a new instance of IGVC, gameLoop starts
                	//IGVC calls IGV in constructor and sets the scene up
                	window.setScene(InGameViewController.view.getScene()); //load IGV scene onto the existing Stage
                	break;
                case 1: //local multiplayer
                	InGameViewController.gsc.setGameState(1); //1 = game_in_progress
                	InGameViewController.gsc.setSinglePlayer(false); //load multiplayer mode
                	InGameViewController multiplayerGame = new InGameViewController(new GameModel()); 
                	window.setScene(InGameViewController.view.getScene());
                	break;
                case 2://enter player names
                	PlayerNameView.displayPlayerNameView();
                	break;
                case 3://credits
                	CreditsView.displayCredits();
                	break;
                case 4://instructions
                	InstructionsView.displayInstructions(); //popup the instructions view
                	break;
                case 5://exit
                	ConfirmView.displayConfirmation();
                }
            }
        });
        window.setScene(gameMenu); //if setOnKeyPressed didn't trigger scene change, keep Stage on gameMenu
        window.setResizable(false); //disallow dimension changes
        window.sizeToScene(); //make window same size as scene (locked to 768 width no matter what OS)
		window.initStyle(StageStyle.UNDECORATED);
        window.show();
        this.window = window; //pass window out so other classes can use it 
        }
    
    

    public static void main(String[] args) { //stick main here so game starts on this file, as per javafx specifications
        launch(args);
    }

	public static Stage getWindow() {
		return window;
	}

	public static Scene getGameMenu() {
		return gameMenu;
	}
}