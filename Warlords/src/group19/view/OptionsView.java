package group19.view;

import group19.controller.GameStateController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OptionsView {
	static GameStateController gsc = new GameStateController();
	public static void displayOptionsView() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setTitle("Warlords");
		window.setWidth(1024);
		window.setHeight(768);
		Text text = new Text();
		text.setText("OPTIONS VIEW");
		Button closeButton = new Button("Exit");
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
