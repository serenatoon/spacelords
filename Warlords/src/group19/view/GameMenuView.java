package group19.view;
import javafx.animation.FadeTransition; 
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private static final Font FONT = Font.font("Helvetica", FontWeight.LIGHT, 28); //static for class-wide scope, final so it can't be altered

    private VBox menuBox; //single vertical column to display all game menu options
    private int currentItem = 0; //cycle counter for each option
    public Scene gameMenu = new Scene(createContent());
    public static Stage currentWindow;
    private Parent createContent() { //from javafx.scene, base class
        Pane root = new Pane(); 
        root.setPrefSize(1024, 768);

        Rectangle bg = new Rectangle(1024, 768); //set rectangle of 1024x768 size, default color black
        //change this code to custom image later

        ContentFrame frame2 = new ContentFrame(title()); 

        HBox hbox = new HBox(frame2); //objects stack horizontally (only 1 item)
        hbox.setTranslateX(405); //centered in middle of screen
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("EXIT"); 
       itemExit.setOnActivate(() -> System.exit(0)); //call exit from java.lang.System.exit

        menuBox = new VBox(10,
                new MenuItem("SINGLE PLAYER"),
                new MenuItem("LOCAL MULTIPLAYER"),
                new MenuItem("HIGH SCORES"),
                new MenuItem("STORY"),
                new MenuItem("OPTIONS"),
                itemExit);
        menuBox.setTranslateX(363);
        menuBox.setTranslateY(358);

        Text about = new Text("COMPSYS302 \nGROUP19"); //move into options view in the future?
        about.setTranslateX(910);
        about.setTranslateY(738);
        about.setFill(Color.WHITE);
        Font smalltext = Font.font("Helvetica", FontWeight.LIGHT, 16);
        about.setFont(smalltext);
        about.setOpacity(0.3);
        getMenuItem(0).setActive(true);
        root.getChildren().addAll(bg, hbox, menuBox, about); //place all created items
        return root;
    }

    private Node title() {
        String title = "WARLORDS";
        HBox letters = new HBox(5); //for aesthetic
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + ""); //"" denotes the blank space
            letter.setFont(FONT);
            letter.setFill(Color.CYAN);
            letters.getChildren().add(letter);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), letter); //from animation package, wave effect
            tt.setDelay(Duration.millis(i * 70));
            tt.setToY(-35);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
            FadeTransition ft = new FadeTransition(Duration.seconds(1), letter); //fade effect
            ft.setFromValue(1);
            ft.setToValue(0.3);
            ft.setAutoReverse(true);
            ft.setCycleCount(TranslateTransition.INDEFINITE);
            ft.play();
        }
        return letters;
    }

    private MenuItem getMenuItem(int index) {
    	System.out.println(index);
        return (MenuItem)menuBox.getChildren().get(index); //cast MenuBox to MenuItem (since it consists of them)
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) { //input parameter is createMiddleContent
            setAlignment(Pos.CENTER);
// rectangle @ title screen surrounding warlords, choose to keep or remove later
            Rectangle frame = new Rectangle(200, 200);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(Color.WHITE);

            getChildren().addAll(frame, content);
        }
    }

    private static class MenuItem extends HBox {
        private Text text;
        private Runnable script; //attempt at multithreading between scene switch

        public MenuItem(String name) {
            setAlignment(Pos.CENTER); //comment this out and let me know if it looks better 
            text = new Text(name);
            text.setFont(FONT);
            getChildren().addAll(text);
            setActive(false);
            setOnActivate(() -> System.out.println(name + " activated")); //insert scene switch here
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.WHITE : Color.GREY); //if b = true (active option) then fill white 
        }

        public void setOnActivate(Runnable r) { //RESEARCH ON CLASS RUNNABLE, NEED SETONACTIVATE TO SWITCH VIEW
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }
    @Override
    public void start(Stage window) throws Exception {
        //use getClassLoader() so getResource() searches relative to classpath, instead of .class file 
        //NOTE: .ogg files do not work with AudioClip, need to import MediaPlayer for that
        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.mp3").toString());
         
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
            	if (currentItem == 1) {
          //  		window.setScene(inGameMenu);
            	}
                getMenuItem(currentItem).activate();
                modeSelect.play();
            }
        });
        window.setScene(gameMenu);
        window.setOnCloseRequest(event -> {
        	System.exit(0);
        });
        window.show();
        }

//    public static void main(String[] args) { //stick main here so game starts on this file
//        launch(args);
//    }
}