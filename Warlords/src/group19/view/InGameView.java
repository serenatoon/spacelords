package group19.view;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import group19.model.BallModel;
import group19.model.BrickModel;
import group19.model.PaddleModel;
import group19.model.WarlordModel;
import javafx.animation.FadeTransition; 
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
//This class basically works like a pop-up style window. Once a game mode is selected on the main menu, initModality blocks input
//events from occurring on mainMenu, and does not allow the user to switch out of this window (in the in-game window). showAndWait() is 
//also used instead of show() to support this functionality. This was done because InGameView needs to be pure .java instead of .fxml
//so objects can pass the bind() test properly, also handling any sort of object instances/logic on Scene Builder is difficult.

public class InGameView {
	private BallModel ball;
	private BrickModel brick;
	private PaddleModel paddle;
	private WarlordModel warlord;
	//instances of the respective controllers are declared below
	public static InGameViewController controller = new InGameViewController(); 
	public static GameStateController gsc = new GameStateController();
	
	public InGameView (BallModel ballModel, BrickModel brickModel, PaddleModel paddleModel, WarlordModel warlordModel) {
		ball = ballModel;
		brick = brickModel;
		paddle = paddleModel;
		warlord = warlordModel;
	}
	
	public static void displayInGameView() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setTitle("Warlords");
		window.setWidth(1024);
		window.setHeight(768);
		Text text = new Text();
		text.setText("IN GAME VIEW");
		Button closeButton = new Button("Exit Current Game");
		closeButton.setOnAction(e -> {
			window.close();
			gsc.setGameState(0); //back to menu state (game did not 'complete')
		});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(text, closeButton);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); //wait for close before returning
	}
}
