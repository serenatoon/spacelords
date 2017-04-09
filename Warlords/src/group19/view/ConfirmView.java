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

public class ConfirmView {
	static Scene confirmScene;
	public static int currentItem;
	protected static HBox menuBox;
	
	public static void displayConfirmation() {
		currentItem = 0; //always start index at 'yes'
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setWidth(400);
		window.setHeight(150);
		window.setTitle("Are you sure?");
		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(600);
		layout.setPrefHeight(200);
		Text title = new Text(90, 50, "Are you sure you want to quit?"); //settings for title - position, alignment, size/color
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font("Arial",30));
		title.setFill(Color.ANTIQUEWHITE);
        menuBox = new HBox(100, //settings for menuBox (helper functions below) - spacing, position
                new MenuItem("yes"),
                new MenuItem("no")
        		);
        menuBox.setTranslateX(200); 
        menuBox.setTranslateY(100);
        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());
        getMenuItem(0).setActive(true); //highlight first item in menu
		layout.getChildren().addAll(title, menuBox);
		confirmScene = new Scene(layout);
		window.sizeToScene();
		window.setScene(confirmScene);
		window.show(); //wait for close before returning
		//keypress functionality and menu navigation
        confirmScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                if (currentItem > 0) { //swap active options, play sound
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true); 
                    menuSelect.play();
                }
            }

            if (event.getCode() == KeyCode.RIGHT) {
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
                	System.exit(0);
                case 1: 
                	window.close();
                	break;
                }
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
