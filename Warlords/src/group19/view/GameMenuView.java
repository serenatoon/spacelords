package group19.view;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

    private static final Font FONT = Font.font("", FontWeight.BOLD, 24);

    private VBox menuBox; //single vertical column to display all game menu options
    private int currentItem = 0; //cycle counter for each option 

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1024, 768);

        Rectangle bg = new Rectangle(1024, 768);

        ContentFrame frame2 = new ContentFrame(createMiddleContent());

        HBox hbox = new HBox(frame2);
        hbox.setTranslateX(410); //centered in middle of screen
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("EXIT"); 
        itemExit.setOnActivate(() -> System.exit(0)); //call exit from java.lang.System.exit

        menuBox = new VBox(10,
                new MenuItem("SINGLE PLAYER"),
                new MenuItem("LOCAL MULTIPLAYER"),
                new MenuItem("HIGH SCORES"),
                new MenuItem("SET DIFFICULTY LEVEL"),
                new MenuItem("OPTIONS"),
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(375);
        menuBox.setTranslateY(358);

        Text about = new Text("COMPSYS202 \nGROUP19");
        about.setTranslateX(850);
        about.setTranslateY(728);
        about.setFill(Color.WHITE);
        about.setFont(FONT);
        about.setOpacity(0.3);
        getMenuItem(0).setActive(true);
        root.getChildren().addAll(bg, hbox, menuBox, about);
        return root;
    }

    private Node createMiddleContent() {
        String title = "WARLORDS";
        HBox letters = new HBox(5); //for aesthetic
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + ""); //"" denotes the blank space
            letter.setFont(FONT);
            letter.setFill(Color.CYAN);
            letters.getChildren().add(letter);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), letter); //from animation package
            tt.setDelay(Duration.millis(i * 70));
            tt.setToY(-35);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
            FadeTransition ft = new FadeTransition(Duration.seconds(1), letter);
            ft.setFromValue(1);
            ft.setToValue(0.3);
            ft.setAutoReverse(true);
            ft.setCycleCount(TranslateTransition.INDEFINITE);
            ft.play();
        }
        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index);
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) { //input parameter is createMiddleContent
            setAlignment(Pos.CENTER);

            Rectangle frame = new Rectangle(200, 200);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(Color.WHITE);

            getChildren().addAll(frame, content);
        }
    }

    private static class MenuItem extends HBox {
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            setAlignment(Pos.CENTER);
            text = new Text(name);
            text.setFont(FONT);
            getChildren().addAll(text);
            setActive(false);
            setOnActivate(() -> System.out.println(name + " activated")); //replace with switching view later
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.WHITE : Color.GREY); //if b = true (active option) then fill white 
        }

        public void setOnActivate(Runnable r) { //RESEARCH ON CLASS RUNNABLE, need setOnActivate to switch view
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        //use getClassLoader() so getResource() searches relative to classpath, instead of .class file 
        //NOTE: .ogg files do not work with AudioClip, need to import MediaPlayer for that
        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.mp3").toString());
        scene.setOnKeyPressed(event -> {
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
                getMenuItem(currentItem).activate();
                modeSelect.play();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}