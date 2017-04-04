package group19.view;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PauseView {
	static GameStateController gsc = new GameStateController();
	public static void show() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		Text text = new Text();
		text.setText("- game is paused -");
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> {
			window.close();
			gsc.setGameState(1); //back to in progress
			
		});
		Pane layout = new Pane();
		layout.setPrefWidth(1024);
		layout.setPrefHeight(768);
		layout.getChildren().addAll(text, closeButton);
		Scene scene = new Scene(layout);
		window.setResizable(false);
		window.setScene(scene);
		window.sizeToScene();
		window.show(); //wait for close before returning
	}
}