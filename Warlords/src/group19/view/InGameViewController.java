package group19.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class InGameViewController implements Initializable, ViewInterface {
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

}
