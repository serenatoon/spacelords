package group19.view;

import com.sun.glass.ui.Window;

import group19.controller.GameStateController;
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
//This class is called by its controller. It provides the actual UI in-game. The constructor sets up the scene,
//ready to be loaded. When IGV wants to be loaded, .setScene() will be called to load the scene onto the 
//top-level Stage container. As shown here, the scene is loaded with a Parent node of type Pane, which loads 
//all the game objects as children nodes. The draw functions are binded to the respective model properties so
//that the view can update when the model's attributes change. The function drawEverything() is called in the InGameLoop
//in IGVC to the graphics keep redrawing.

public class InGameView {
	
	static GameModel game;
	
//	private Stage window = new Stage();
	Pane rootGameLayout = new Pane();
	private Scene scene;
	
	public InGameView (double width, double height, GameModel model) { //upon inititalisation, switch focus to in game view
		this.game = model;
		
//		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
//		window.setTitle("Warlords");
//		window.setWidth(width);
//		window.setHeight(height);
		
		rootGameLayout.setPrefWidth(width); //set the root parent as an anchor pane, with same dimensions as stage
		rootGameLayout.setPrefHeight(height);
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord1(), drawWarlord2()); //add child nodes here 
		scene = new Scene(rootGameLayout);
	//	window.setScene(scene);
	//	this.scene = scene; //pass scene back out to a variable that controller can call upon
	//	window.show(); //wait for close before returning
	}		
	
//	public Stage getWindow() {
//		return window;
//	}
	public Scene getScene() {
		return scene;
	}
	
	
	public void drawEverything() { //this method is called on every tick in-game so that drawings update.
		rootGameLayout.getChildren().removeAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord1());
		drawBall();
		drawPaddle();
		drawBrick();
		drawWarlord1();
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord1());
	}
	
	
	//Below are all the greyblock implementations of the ball drawing.
	public static Node drawBall() {
        Circle circle = new Circle(game.getBall().getXPos(), game.getBall().getYPos(), game.getBall().getRadius());
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        //circle.setLayoutX(400); //remove these later once model is linked up to view, since all instances need to start at (0,0)
        //circle.setLayoutY(400);
        circle.translateXProperty().bind(game.getBall().getXProperty());
        circle.translateYProperty().bind(game.getBall().getYProperty());
        return circle;
	}

	public static Node drawPaddle() {
		Rectangle rect = new Rectangle(game.getPaddle().getXPos(), game.getPaddle().getYPos(), game.getPaddle().getWidth(), game.getPaddle().getHeight());
		//rect.setLayoutX(150);
		//rect.setLayoutY(150);
        rect.translateXProperty().bind(game.getPaddle().getXProperty());
        rect.translateYProperty().bind(game.getPaddle().getYProperty());
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
	
	public static Node drawWarlord1() {
		Rectangle rect = new Rectangle(game.getWarlord1().getXPos(), game.getWarlord1().getYPos(), game.getWarlord1().getWidth(), game.getWarlord1().getHeight());
		rect.setFill(Color.GREENYELLOW);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord1().getXProperty());
        rect.translateYProperty().bind(game.getWarlord1().getYProperty());
		return rect;
	}
	
	public static Node drawWarlord2() {
		Rectangle rect = new Rectangle(game.getWarlord2().getXPos(), game.getWarlord2().getYPos(), game.getWarlord2().getWidth(), game.getWarlord2().getHeight());
		rect.setFill(Color.BLUE);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord2().getXProperty());
        rect.translateYProperty().bind(game.getWarlord2().getYProperty());
		return rect;
	}


	public void addEventHandler(EventType<KeyEvent> keyPressed, Object object) {
		// TODO Auto-generated method stub
		
	}
}
