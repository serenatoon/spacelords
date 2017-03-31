package group19.view;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import group19.model.BallModel;
import group19.model.BrickModel;
import group19.model.PaddleModel;
import group19.model.WarlordModel;
import javafx.animation.FadeTransition; 
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
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
//also used instead of show() to support this functionality. This was done because InGameView needs to be pure .java instead of .fxml
//so objects can pass the bind() test properly, also handling any sort of object instances/logic on Scene Builder is difficult.

public class InGameView {
	private static BallModel ball = new BallModel(0, 0);
	private static BrickModel brick = new BrickModel(0, 0);
	private static PaddleModel paddle = new PaddleModel(0, 0);
	private static WarlordModel warlord = new WarlordModel(0, 0, 0);
	//instances of the respective controllers are declared below
	public static InGameViewController controller = new InGameViewController(); 
	public static GameStateController gsc = new GameStateController();
	
	public InGameView (BallModel ballModel, BrickModel brickModel, PaddleModel paddleModel, WarlordModel warlordModel) {
		ball = ballModel;
		brick = brickModel;
		paddle = paddleModel;
		warlord = warlordModel;
	}
	
//pseudocode for incorporating ticks into inGameView
//	while (!gsc.isFinished()) {
//		if (!gsc.checkIfPaused()) {
//			controller.tick(); //igvc has one tick
//		}
//	}
	
	public static void displayInGameView() { //this method is called by the main menu to start a game
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 
		window.setTitle("Warlords");
		window.setWidth(1024);
		window.setHeight(768);
		AnchorPane rootGameLayout = new AnchorPane(); 
		rootGameLayout.setPrefWidth(1024); //set the root parent as an anchor pane, with same dimensions as stage
		rootGameLayout.setPrefHeight(768);
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(), drawBrick(), drawWarlord()); //add child nodes here 
		Scene scene = new Scene(rootGameLayout);
		
		/*Added function for debugging: pressing tab closes the in game window*/
		/*Listen for key input for paddle to move, but pass logic to controller*/
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		      if(key.getCode() == KeyCode.TAB) {
		          System.out.println("You pressed TAB, exiting and moving to main menu...");
		          gsc.setGameState(0);//back to menu state (game did not 'complete')
		          window.close();
		      }
		      else if (key.getCode() == KeyCode.LEFT) {
		    	  controller.handlePaddleLeft();
		      }
		      else if (key.getCode() == KeyCode.RIGHT) {
		    	  controller.handlePaddleRight();
		      }
		});
		

		window.setScene(scene);
		window.showAndWait(); //wait for close before returning
	}
	
	public void drawEverything() { //this method is called on every tick in-game so that drawings update.
		drawBall();
		drawPaddle();
		drawBrick();
		drawWarlord();
	}
	
	
	//Below are all the greyblock implementations of the ball drawing.
	public static Node drawBall() {
        Circle circle = new Circle(ball.getXPos(), ball.getYPos(), ball.getRadius());
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        circle.setLayoutX(400); //remove these later once model is linked up to view, since all instances need to start at (0,0)
        circle.setLayoutY(400);
        return circle;
	}

	public static Node drawPaddle() {
		Rectangle rect = new Rectangle(paddle.getXPos(), paddle.getYPos(), paddle.getWidth(), paddle.getHeight());
		rect.setLayoutX(150);
		rect.setLayoutY(150);
		return rect;
	}
	
	public static Node drawBrick() {
		Rectangle rect = new Rectangle(brick.getXPos(), brick.getYPos(), brick.getWidth(), brick.getHeight());
		rect.setFill(Color.CYAN);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(1.0);
		rect.setLayoutX(500);
		rect.setLayoutY(500);
		return rect;
	}
	
	public static Node drawWarlord() {
		Rectangle rect = new Rectangle(warlord.getXPos(), warlord.getYPos(), warlord.getWidth(), warlord.getHeight());
		rect.setFill(Color.GREENYELLOW);
		rect.setStroke(Color.HOTPINK);
		rect.setStrokeWidth(3.0);
		rect.setLayoutX(800);
		rect.setLayoutY(500);
		return rect;
	}
}
