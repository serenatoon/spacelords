package group19.view;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane; //StackPane is chosen as it places its children simply back-to-front (back starting with 0th child)
import javafx.util.Duration;

// This class 'holds' all the possible views that could be displayed, and implements methods that inject FXML files from the various Views
// into an instance of the ViewInterface. Which FXML file is injected is up to each View's respective controller.
// This class also has a method that sets the screen, but the initial call of this is naturally made in CurrentViewWithMain since 
// that is the entry point of the program.

public class ViewLoader extends StackPane {
	private HashMap<String, Node> views = new HashMap<>(); //stores all views to be displayed
	
	public ViewLoader() {
		super(); //calls parent constructor (StackPane's constructor). No arguments needed
	}
	
    public void addView(String name, Node view) {
        views.put(name, view); //put is in built method of hash map
    }
    
    public Node getView(String name) {
    	return views.get(name); //get is in built method of hash map
    }
    
    //Loads an FXML file, then adds the view to the collection in the 'views' hash map and
    //then sends the screen to be used by the controllers.
    
    public boolean loadView(String name, String resource) {
        try { //just in case loading fails 
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent loadView = (Parent) loader.load(); //class casting is OK with FXMLLoader and Parent nodes
            ViewInterface incomingView = ((ViewInterface) loader.getController()); //sends to controllers
            incomingView.setViewParent(this); //calls method from ViewInterface, is the 'connector' to controllers
            addView(name, loadView);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage()); //print out event message
            return false;
        }
    }
    
    //This method sets the view according to the given name, and is called in CurrentViewWithMain 
    //to start the scene adding. If a name is given, then if there are no children on the view Pane 
    //that means no scenes have been added yet, so call getChildren().add() to add a single view (this is the 
    //else part in the inner conditional), to the root. A Fade animation also plays for good measure.
    //If a view already exists on the pane (i.e. the if part of the inner conditional) then remove
    //the root child and add the new view on. There will never be a child of a child anyway so element 0 is accessed.
    
    public boolean setView(final String name) { //input parameter is uneditable, comes from hash map's addView()       
        if (views.get(name) != null) {   //if a valid input is given
            final DoubleProperty opacity = opacityProperty(); //for fade
            if (!getChildren().isEmpty()) {    //if there is already a view set
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    //remove the displayed view
                        getChildren().add(0, views.get(name));     //add the new view
                        Timeline fadeIn = new Timeline( //fade the new view in from opacity 0 to opacity 1
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else { //if there is not a view set (there are no children)
                setOpacity(0.0);
                getChildren().add(views.get(name));       //just show the specified view
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), //obligatory fade, 1sec
                        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("Invalid name to set view");
            return false;
        }
    }
}
