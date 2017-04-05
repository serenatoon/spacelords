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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
	
	public Pane rootGameLayout = new Pane(); //set the root parent node as a Pane
	private Scene scene;
	
	public InGameView (double width, double height, GameModel model) { //upon initialisation, switch focus to in game view
		this.game = model; //pass input parameter out to local variable
		rootGameLayout.setPrefWidth(width);
		rootGameLayout.setPrefHeight(height);
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		rootGameLayout.setBackground(new Background(bg));
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord1(), drawWarlord2(), drawWarlord3(), drawWarlord4(), drawGUI()); //add child nodes here 
		scene = new Scene(rootGameLayout, 1024, 768);
	}		

	public Scene getScene() {
		return scene;
	}
	
	//Below are all the grey-blocked implementations of the drawn objects. The constructors call model properties to set their dimensions
	//and position. The bind method helps model parameters translate to actual UI changes.
	public static Node drawBall() {
        Circle circle = new Circle(game.getBall().getXPos(), game.getBall().getYPos(), game.getBall().getRadius());
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        circle.translateXProperty().bind(game.getBall().getXProperty());
        circle.translateYProperty().bind(game.getBall().getYProperty());
        return circle;
	}

	public static Node drawPaddle() {
		Rectangle rect = new Rectangle(game.getPaddle1().getWidth(), game.getPaddle1().getHeight());
        rect.translateXProperty().bind(game.getPaddle1().getXProperty());
        rect.translateYProperty().bind(game.getPaddle1().getYProperty());
        rect.setFill(Color.ANTIQUEWHITE);
		return rect;
	}
	
	public static Node drawBrick() {
		Rectangle rect = new Rectangle(game.getBrick().getWidth(), game.getBrick().getHeight());
		rect.setFill(Color.CYAN);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(1.0);
		rect.translateXProperty().bind(game.getBrick().getXProperty());
        rect.translateYProperty().bind(game.getBrick().getYProperty());

		return rect;
	}
	
	public static Node drawWarlord1() {
		Rectangle rect = new Rectangle(game.getWarlord1().getWidth(), game.getWarlord1().getHeight());
		rect.setFill(Color.GREENYELLOW);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord1().getXProperty());
        rect.translateYProperty().bind(game.getWarlord1().getYProperty());
		return rect;
	}
	
	public static Node drawWarlord2() {
		Rectangle rect = new Rectangle(game.getWarlord2().getWidth(), game.getWarlord2().getHeight());
		rect.setFill(Color.BLUE);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord2().getXProperty());
        rect.translateYProperty().bind(game.getWarlord2().getYProperty());
		return rect;
	}
	
	public static Node drawWarlord3() {
		Rectangle rect = new Rectangle(game.getWarlord3().getWidth(), game.getWarlord3().getHeight());
		rect.setFill(Color.BLUE);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord3().getXProperty());
        rect.translateYProperty().bind(game.getWarlord3().getYProperty());
		return rect;
	}
	public static Node drawWarlord4() {
		Rectangle rect = new Rectangle(game.getWarlord4().getWidth(), game.getWarlord4().getHeight());
		rect.setFill(Color.BLUE);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.translateXProperty().bind(game.getWarlord4().getXProperty());
        rect.translateYProperty().bind(game.getWarlord4().getYProperty());
		return rect;
	}
	public static Group drawGUI() {
		Rectangle leftPanel = new Rectangle(0,0, 128, 768); //rectangle at pos(0,0) 128px wide 768px high
		Rectangle rightPanel = new Rectangle(896,0,128,768);
		
		Text timer = new Text(30,384,"timer: ");
		leftPanel.setFill(Color.ANTIQUEWHITE);
		rightPanel.setFill(Color.ANTIQUEWHITE);
		Group GUI = new Group(leftPanel, rightPanel, timer);
		return GUI;
		
	}

}
