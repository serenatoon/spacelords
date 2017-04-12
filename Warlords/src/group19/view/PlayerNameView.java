package group19.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

//This is the view which is shown to the user if they select 'select player names' from the GameMenuView. The window allows for the 
//submission of custom usernames, which utilise JavaFX's TextField methods which can capture the data in those fields and store them 
//in variables, for the InGameView to load.

public class PlayerNameView {
	static Scene nameScene;
	public static void displayPlayerNameView() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setWidth(300);
		window.setHeight(200);
		window.setTitle("Enter player usernames");
		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(300);
		layout.setPrefHeight(200);
		nameScene = new Scene(layout);
		window.sizeToScene();
		window.setScene(nameScene);
		window.show(); //wait for close before returning
		//code inspired from: http://docs.oracle.com/javafx/2/ui_controls/text-field.htm
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		final TextField player1 = new TextField();
		player1.setPromptText("Player 1 username...");
		player1.setPrefColumnCount(10);
		player1.getText();
		GridPane.setConstraints(player1, 0, 0);
		grid.getChildren().add(player1);
		final TextField player2 = new TextField();
		player2.setPromptText("Player 2 username...");
		GridPane.setConstraints(player2, 0, 1);
		grid.getChildren().add(player2);
		final TextField player3 = new TextField();
		player3.setPrefColumnCount(15);
		player3.setPromptText("Player 3 username...");
		GridPane.setConstraints(player3, 0, 2);
		grid.getChildren().add(player3);
		final TextField player4 = new TextField();
		player4.setPrefColumnCount(15);
		player4.setPromptText("Player 4 username...");
		GridPane.setConstraints(player4, 0, 3);
		grid.getChildren().add(player4);		
		//Defining the Submit button
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 3);
		grid.getChildren().add(submit);
		final Label label = new Label(); //initialise with no text, but show text upon submit (logic for this in handle method)
		label.setTextFill(Color.ANTIQUEWHITE);
		GridPane.setConstraints(label, 0, 4);
		GridPane.setColumnSpan(label, 2);
		grid.getChildren().add(label);
		layout.getChildren().addAll(grid);
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			    public void handle(ActionEvent e) {
			        if ((player1.getText().isEmpty() || player2.getText().isEmpty() || player3.getText().isEmpty() || player4.getText().isEmpty())) {
			            label.setText("Please enter usernames for all players."); //make sure that all fields are filled in
			        } else {
			            label.setText("Usernames have been updated!"); //prompt user 
			            InGameView.p1Name = player1.getText(); //pass out all fields to a String variable in InGameView
			            InGameView.p2Name = player2.getText();
			            InGameView.p3Name = player3.getText();
			            InGameView.p4Name = player4.getText();
			            
			        }
			     }
			 });
	}
	
}
