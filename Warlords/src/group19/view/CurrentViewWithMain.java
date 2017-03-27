package group19.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

//This class is in charge of the entry point of the application (contains the main method, and the javafx start method). 
//Has all your typical .setScene and .show calls in order to set the Stage, load the scene onto the stage, and load the correct FXML
//onto the scene. Our implementation uses ViewLoader to deal with Loading, so the input here is just the ID assigned from the
// hashmap in ViewLoader.

public class CurrentViewWithMain extends Application {

    public static String MainMenuViewID = "MainMenuView";
    public static String MainMenuViewFile = "MainMenuView.fxml";
    public static String InGameViewID = "InGameView";
    public static String InGameViewFile = "InGameView.fxml";
    public static String OptionsViewID = "OptionsView";
    public static String OptionsViewFile = "OptionsView.fxml";
	@Override
	public void start(Stage window) throws Exception {
        ViewLoader container = new ViewLoader();
        container.loadView(CurrentViewWithMain.MainMenuViewID, CurrentViewWithMain.MainMenuViewFile); //call loadView on all possible views 
        container.loadView(CurrentViewWithMain.InGameViewID, CurrentViewWithMain.InGameViewFile); //add more as more views are created in FXML
        container.loadView(CurrentViewWithMain.OptionsViewID, CurrentViewWithMain.OptionsViewFile);
        
        container.setView(CurrentViewWithMain.MainMenuViewID); //set initial view to main menu
      
        Group root = new Group(); //the typical code to set up the parent node -> scene -> stage progression
        root.getChildren().addAll(container); //add the children, which is given by the ViewLoader class to the parent
        Scene scene = new Scene(root); //set the parent node on the scene
        window.setScene(scene); //set the scene on the stage
        window.show(); //display the stage
	} 
	
	public static void main (String[] args) {
		launch(args);
	}

}
