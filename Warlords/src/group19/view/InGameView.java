package group19.view;

import com.sun.glass.ui.Window;

import group19.controller.GameStateController;
//import group19.controller.InGameViewController;
import group19.model.*;
import javafx.animation.FadeTransition; 
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
//This class basically works like a pop-up style window. Once a game mode is selected on the main menu, initModality blocks input
//events from occurring on mainMenu, and does not allow the user to switch out of this window (in the in-game window). showAndWait() is 
//also used instead of show() to support this functionality. This was done because InGameView needs to be pure .java instead of .fxml.

public class InGameView {
	/* private static BallModel ball = new BallModel(0, 0);
	private static BrickModel brick = new BrickModel(0, 0);
	private static PaddleModel paddle = new PaddleModel(0, 0);
	private static WarlordModel warlord = new WarlordModel(0, 0, 0); */
	
	static GameModel game;
	
	private Stage window;
	private Scene scene;
	
	public InGameView (double width, double height, GameModel model) { //upon inititalisation, switch focus to in game view
		this.game = model;
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setTitle("Warlords");
		window.setWidth(width);
		window.setHeight(height);
		this.window = window; //pass stage back out to variable that controller can call upon
		AnchorPane rootGameLayout = new AnchorPane(); 
		rootGameLayout.setPrefWidth(width); //set the root parent as an anchor pane, with same dimensions as stage
		rootGameLayout.setPrefHeight(height);
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord()); //add child nodes here 
		Scene scene = new Scene(rootGameLayout);
		window.setScene(scene);
		this.scene = scene; //pass scene back out to a variable that controller can call upon
		window.show(); //wait for close before returning
	}		
	
	public Stage getWindow() {
		return window;
	}
	public Scene getScene() {
		return scene;
	}
	
	
	public void drawEverything() { //this method is called on every tick in-game so that drawings update.
		drawBall();
		drawPaddle();
		drawBrick();
		drawWarlord();
	}
	
	
	//Below are all the greyblock implementations of the ball drawing.
	public static Node drawBall() {
        Circle circle = new Circle(game.getBall().getXPos(), game.getBall().getYPos(), game.getBall().getRadius());
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        circle.setLayoutX(400); //remove these later once model is linked up to view, since all instances need to start at (0,0)
        circle.setLayoutY(400);
        return circle;
	}

	public static Node drawPaddle() {
		Rectangle rect = new Rectangle(game.getPaddle().getXPos(), game.getPaddle().getYPos(), game.getPaddle().getWidth(), game.getPaddle().getHeight());
		//rect.setLayoutX(150);
		//rect.setLayoutY(150);
		return rect;
	}
	
	public static Node drawBrick() {
		Rectangle rect = new Rectangle(game.getBrick().getXPos(), game.getBrick().getYPos(), game.getBrick().getWidth(), game.getBrick().getHeight());
		rect.setFill(Color.CYAN);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(1.0);
		rect.setLayoutX(500);
		rect.setLayoutY(500);
		return rect;
	}
	
	public static Node drawWarlord() {
		Rectangle rect = new Rectangle(game.getWarlord().getXPos(), game.getWarlord().getYPos(), game.getWarlord().getWidth(), game.getWarlord().getHeight());
		rect.setFill(Color.GREENYELLOW);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.setLayoutX(800);
		rect.setLayoutY(500);
		return rect;
	}


	public void addEventHandler(EventType<KeyEvent> keyPressed, Object object) {
		// TODO Auto-generated method stub
		
	}
}
