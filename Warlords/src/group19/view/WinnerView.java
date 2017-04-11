package group19.view;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import group19.model.GameModel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WinnerView extends PauseView {
	static Scene winnerScene;
	static String story;
	public static void showScene() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setWidth(1024);
		window.setHeight(768);
		window.initStyle(StageStyle.UNDECORATED);
		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(1024);
		layout.setPrefHeight(768);
		Text title = new Text(235, 100, "- game over -"); //settings for title - position, alignment, size/color
		title.setFont(new Font("Arial",70));
		title.setFill(Color.ANTIQUEWHITE);
		Text winner = new Text(235, 200, "Winner: " + GameModel.getWinner().getPlayerName());
		winner.setFont(new Font("Arial",30));
		winner.setFill(Color.ANTIQUEWHITE);
		GameStateController.totalGamesPlayed++;
		if (GameStateController.totalGamesPlayed == 1) {
			story = "Well done, you have talent in this intergalactic sport.\nPlay again to continue through the tournament.";
		}
		else if (GameStateController.totalGamesPlayed == 2) {
			story = "You have proven to be talented. Continue your journey to redemption.";
		}
		else {
			story = "Just one more round to go before you are free.";
		}
		Text storyText = new Text(235, 250, GameModel.getWinner().getPlayerName() + " - " + story);
		storyText.setFont(new Font(14));
		storyText.setFill(Color.ANTIQUEWHITE);
        menuBox = new VBox(10, //settings for menuBox (helper functions below) - spacing, position
                new MenuItem("back to main menu"),
                new MenuItem("instructions"),
                new MenuItem("credits"),
                new MenuItem("quit game")
        		);
        menuBox.setTranslateX(235); 
        menuBox.setTranslateY(458);

        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());

        getMenuItem(0).setActive(true); //highlight first item in menu
		layout.getChildren().addAll(title, winner, menuBox, storyText);
		//if attack mode, add score text too!!
		pauseScene = new Scene(layout);
		window.setResizable(false);
		window.setScene(pauseScene);
		window.sizeToScene();
		InGameViewController.gsc.playGameOver();
		window.show();
		//keypress functionality and menu navigation
        pauseScene.setOnKeyReleased(event -> {
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
                case 0:
                	InGameViewController.gsc.setGameState(0);
                	System.out.println("back to main menu");
                	window.close();
                	break;
                case 1: 
                	System.out.println("instructions");
                	InstructionsView.displayInstructions();
                	break;
                case 2:
                	CreditsView.displayCredits();
                	break;
                case 3:
                	ConfirmView.displayConfirmation();
                	break;

                }
            }
        });	
	}
}
