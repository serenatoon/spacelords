package group19.view;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InstructionsView {
	static Scene instructionsScene;
	public static int currentItem;
	protected static HBox menuBox;
	
	public static void displayInstructions() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.initStyle(StageStyle.UNDECORATED);
		window.setWidth(1024);
		window.setHeight(768);
		window.setTitle("Instructions");
		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(1024);
		layout.setPrefHeight(768);
		Text title = new Text(88, 100, "Instructions"); //settings for title - position, alignment, size/color
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(48));
		title.setFill(Color.ANTIQUEWHITE);
		Text instructions = new Text();
		instructions.setX(88);
		instructions.setY(170);
		instructions.setFont(Font.font(16));
		instructions.setFill(Color.ANTIQUEWHITE);
		instructions.setText("The objective of the game is to destroy all other opponent's bases while defending your own.\n" +
				"If the timer runs out before this occurs (2 minute timer), the winner is decided based on the \n" +
				"player with the highest number of bricks remaining.\n\n" +
				"In single player mode, use the arrow keys to move the paddle around left and right.\n" +
				"You will control the top left player.\n\n" +
				"In local multiplayer mode, use the following keys to move the players around:\n" +
				"Player 1 - LEFT and RIGHT arrow keys \n" +
				"Player 2 - A and D \n" +
				"Player 3 - J and L \n" +
				"Player 4 - V and N \n\n" +
				"Story mode is a special single player mode where you play through the story.\n" +
				"As you progress through the rounds, the story is progressively unlocked. \n\n" +
				"Attack mode is a special multiplayer mode where a score is kept.\n" +
				"Scores are incremented as players destroy other player's bricks, encouraging players \n" +
				"to attack instead of defending. \n\n" +
				"General controls:\n" +
				"ESC - if in game, goes back to the main menu.\n" +
				"P - pauses the game and opens up the pause menu.\n" +
				"T (cheat) - decreases the time remaining in the round by 5 seconds. \n" +
				"PGDN (cheat) - reduces the time remaining to zero so the game ends immediately.\n");
        menuBox = new HBox(100, //settings for menuBox (helper functions below) - spacing, position
                new MenuItem("ok")
        		);
        menuBox.setTranslateX(480); 
        menuBox.setTranslateY(700);
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());
        getMenuItem(0).setActive(true); 
		layout.getChildren().addAll(title, menuBox, instructions);
		instructionsScene = new Scene(layout);
		window.sizeToScene();
		window.setScene(instructionsScene);
		window.show(); //wait for close before returning
        instructionsScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                modeSelect.play();
                window.close();
            }
        });
	}
    protected static MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index); //cast MenuBox to MenuItem (since it consists of them)
    }
    static class MenuItem extends HBox {
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
}
