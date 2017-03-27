package group19.view;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class MainMenuViewController implements Initializable, ViewInterface { //ViewInterface passes stuff from ViewLoader into these controllers
	ViewLoader loader;
	
	@Override
	public void setViewParent(ViewLoader viewPage) { //taken from ViewInterface since it's a pure virtual class
		loader = viewPage; //just pass in whatever is given
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { //This is auto-generated and I can't delete it 
		// TODO Auto-generated method stub
		
	}
	
	//Below are methods that directly connect to fx:ids and calls in Scene Builder
	
	@FXML
	private VBox verticalButtons; //the Vbox which stores all the buttons are here

	@FXML
	private void switchToInGameView(ActionEvent event) { //defined in FXML as the 'single player' button (For now)
		loader.setView(CurrentViewWithMain.InGameViewID);
	}
	
	@FXML
	private void switchToOptionsView(ActionEvent event) { //defined in FXML as the 'options' button (For now)
		loader.setView(CurrentViewWithMain.OptionsViewID);
	}
}
