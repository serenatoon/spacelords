package group19.view;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

public class CreditsView {
	static Scene creditsScene;
	public static int currentItem;
	protected static HBox menuBox;
	
	public static void displayCredits() {	
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.initStyle(StageStyle.UNDECORATED);
		window.setWidth(1024);
		window.setHeight(768);
		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(1024);
		layout.setPrefHeight(768);
		Text title = new Text(315, 150, "- credits -"); //settings for title - position, alignment, size/color
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(80));
		Text creditsText = new Text();
		creditsText.setX(330);
		creditsText.setY(250);
		creditsText.setTextAlignment(TextAlignment.CENTER);
		creditsText.setFont(Font.font(24));
		creditsText.setFill(Color.ANTIQUEWHITE);
		creditsText.setText("Created by Group 19:\n\n\n\n\n\nCOMPSYS302\nUniversity of Auckland\n2017\n\n\n" + 
							"Assets sourced from:\n" +
							"freesound.org\n" +
							"incompetech.com\n" + 
							"kenney.nl assets\n" + 
							"ansimuz from opengameart.org");
		Text namesText = new Text();
		namesText.setX(432);
		namesText.setY(250);
		namesText.setTextAlignment(TextAlignment.CENTER);
		namesText.setFont(Font.font(24));
		namesText.setText("\n\n\n" + "Serena Toon" + "\n" + "Marcus Wong");
		namesText.setFill(Color.ANTIQUEWHITE);
		//rainbow code
	    FillTransition r = new FillTransition(Duration.millis(600), title);
	    r.setFromValue(Color.RED);
	    r.setToValue(Color.ORANGE);
	    FillTransition o = new FillTransition(Duration.millis(600), title);
	    o.setFromValue(Color.ORANGE);
	    o.setToValue(Color.YELLOW);
	    FillTransition y = new FillTransition(Duration.millis(600), title);
	    y.setFromValue(Color.YELLOW);
	    y.setToValue(Color.GREEN);
	    FillTransition g = new FillTransition(Duration.millis(600), title);
	    g.setFromValue(Color.GREEN);
	    g.setToValue(Color.BLUE);
	    FillTransition b = new FillTransition(Duration.millis(600), title);
	    b.setFromValue(Color.BLUE);
	    b.setToValue(Color.INDIGO);
	    FillTransition i = new FillTransition(Duration.millis(600), title);
	    i.setFromValue(Color.INDIGO);
	    i.setToValue(Color.VIOLET);
	    FillTransition v = new FillTransition(Duration.millis(600), title);
	    v.setFromValue(Color.VIOLET);
	    v.setToValue(Color.RED);
	    SequentialTransition rainbow = new SequentialTransition (r, o, y, g, b, i, v);
	    rainbow.setAutoReverse(true);
	    rainbow.setCycleCount(TranslateTransition.INDEFINITE);
	    rainbow.play();
	    FillTransition r2 = new FillTransition(Duration.millis(600), namesText);
	    r2.setFromValue(Color.RED);
	    r2.setToValue(Color.ORANGE);
	    FillTransition o2 = new FillTransition(Duration.millis(600), namesText);
	    o2.setFromValue(Color.ORANGE);
	    o2.setToValue(Color.YELLOW);
	    FillTransition y2 = new FillTransition(Duration.millis(600), namesText);
	    y2.setFromValue(Color.YELLOW);
	    y2.setToValue(Color.GREEN);
	    FillTransition g2 = new FillTransition(Duration.millis(600), namesText);
	    g2.setFromValue(Color.GREEN);
	    g2.setToValue(Color.BLUE);
	    FillTransition b2 = new FillTransition(Duration.millis(600), namesText);
	    b2.setFromValue(Color.BLUE);
	    b2.setToValue(Color.INDIGO);
	    FillTransition i2 = new FillTransition(Duration.millis(600), namesText);
	    i2.setFromValue(Color.INDIGO);
	    i2.setToValue(Color.VIOLET);
	    FillTransition v2 = new FillTransition(Duration.millis(600), namesText);
	    v2.setFromValue(Color.VIOLET);
	    v2.setToValue(Color.RED);
	    SequentialTransition rainbow2 = new SequentialTransition (r2, o2, y2, g2, b2, i2, v2);
	    rainbow2.setAutoReverse(true);
	    rainbow2.setCycleCount(TranslateTransition.INDEFINITE);
	    rainbow2.play();
        menuBox = new HBox(100, //settings for menuBox (helper functions below) - spacing, position
                new MenuItem("ok")
        		);
        menuBox.setTranslateX(500); 
        menuBox.setTranslateY(700);
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());
        getMenuItem(0).setActive(true); 
		layout.getChildren().addAll(title, namesText, menuBox, creditsText);
		creditsScene = new Scene(layout);
		window.sizeToScene();
		window.setScene(creditsScene);
		window.show(); //wait for close before returning
        creditsScene.setOnKeyReleased(event -> {
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
