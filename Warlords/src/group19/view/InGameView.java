package group19.view;

import group19.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.ArrayList;

//This class is called by its controller. It provides the actual UI in-game. The constructor sets up the scene,
//ready to be loaded. When IGV wants to be loaded, .setScene() will be called to load the scene onto the 
//top-level Stage container. As shown here, the scene is loaded with a Parent node of type Pane, which loads 
//all the game objects as children nodes. The draw functions are binded to the respective model properties so
//that the view can update when the model's attributes change. The function drawEverything() is called in the InGameLoop
//in IGVC to the graphics keep redrawing.

public class InGameView {
	
	static GameModel game;
	static ArrayList<BrickModel> brickList;
	
	public Pane rootGameLayout = new Pane(); //set the root parent node as a Pane
	private Scene scene;
	public static String p1Name = "Player 1"; //these variables can be edited in PlayerNameView
	public static String p2Name = "Player 2";
	public static String p3Name = "Player 3";
	public static String p4Name = "Player 4";

	public InGameView (double width, double height, GameModel model) { //upon initialisation, switch focus to in game view
		this.game = model; //pass input parameter out to local variable
        this.brickList = game.getBrickList(); 

		rootGameLayout.setPrefWidth(width);
		rootGameLayout.setPrefHeight(height);
		Image bgImage = new Image("/res/images/space_lf.png");
		BackgroundImage bg = new BackgroundImage(bgImage, null, null, null, null); //load space background image
		rootGameLayout.setBackground(new Background(bg));
		rootGameLayout.getChildren().addAll(drawCountdown(), drawBall(game.getBall()), drawBall(game.getExtraBall()), drawPaddle(game.getPaddle1()),
				drawPaddle(game.getPaddle2()), drawPaddle(game.getPaddle3()),
				drawPaddle(game.getPaddle4()),
				drawWarlord(game.getWarlord1(), new ImageView("/res/images/baseP1.png")),
				drawWarlord(game.getWarlord2(), new ImageView("/res/images/baseP2.png")),
				drawWarlord(game.getWarlord3(), new ImageView("/res/images/baseP3.png")), 
				drawWarlord(game.getWarlord4(), new ImageView("/res/images/baseP4.png")),
				game.getPowerUp().getNode(),
				drawGUI()); //add child nodes here (all GUI elements)
		
		int i = 0;
		for (BrickModel brick : brickList) {
			if (!brickList.get(i).isDestroyed()) {
				rootGameLayout.getChildren().add(game.getBrickList().get(i).getNode());
			}
			i++;
		}
		scene = new Scene(rootGameLayout, 1024, 768);
	}		

	public Scene getScene() {
		return scene;
	}
	
	//The code below draws the GUI elements of the in-game view. The constructors call model properties to set their dimensions
	//and position. The bind method helps model parameters translate to actual UI changes.
	public static Node drawBall(BallModel ball) {
        Circle circle = new Circle(ball.getRadius());
        Glow glow = new Glow(1.0);
        circle.setFill(Color.RED);
        circle.setEffect(glow);
        
        circle.centerXProperty().bind(ball.getXProperty());
        circle.centerYProperty().bind(ball.getYProperty());
        return circle;
	}

	
	public static Node drawPaddle(PaddleModel paddle) {
		ImageView rect = new ImageView("/res/images/paddle_strip.png");
		rect.setFitWidth(paddle.getWidth());
		rect.setFitHeight(paddle.getHeight());
		rect.translateXProperty().bind(paddle.getXProperty());
		rect.translateYProperty().bind(paddle.getYProperty());
		return rect;
	}
	
	public static Node drawWarlord(WarlordModel warlord, ImageView img) {
			img.setFitWidth(warlord.getWidth());
			img.setFitHeight(warlord.getHeight());
			Effect glow = new Glow(0.0); //slight glow effect to warlord, to signify importance
			img.setEffect(glow);
			Timeline timeline = new Timeline();
		    timeline.setCycleCount(Timeline.INDEFINITE);
		    timeline.setAutoReverse(true);
		    KeyValue kv = new KeyValue(((Glow) glow).levelProperty(), 0.5);
		    KeyFrame kf = new KeyFrame(Duration.millis(1500), kv);
		    timeline.getKeyFrames().add(kf);
		    timeline.play();  
			img.translateXProperty().bind(warlord.getXProperty());
			img.translateYProperty().bind(warlord.getYProperty());
			return img;
	}
	
	public static Group drawGUI() {
		//dimensions planned from scene builder view
		//set up 128px wide HUD nodes
		ImageView leftPanel = new ImageView("/res/images/sidepane.png");
		leftPanel.setLayoutX(0);
		leftPanel.setLayoutY(0);
		leftPanel.setFitWidth(128);
		leftPanel.setFitHeight(768);
		ImageView rightPanel = new ImageView("/res/images/sidepane.png");
		rightPanel.setLayoutX(896);
		rightPanel.setLayoutY(0);
		rightPanel.setFitWidth(128);
		rightPanel.setFitHeight(768);
		//set up text nodes for usernames
		Text p1name = new Text(30, 50, p1Name); 
		p1name.setFont(Font.font("Arial", 18));
		p1name.setFill(Color.ANTIQUEWHITE);
		p1name.setTextAlignment(TextAlignment.CENTER);
		Text p2name = new Text(926, 50, p2Name);
		p2name.setFont(Font.font("Arial", 18));
		p2name.setFill(Color.ANTIQUEWHITE);
		p2name.setTextAlignment(TextAlignment.CENTER);
		Text p3name = new Text(30, 718, p3Name);
		p3name.setFont(Font.font("Arial", 18));
		p3name.setFill(Color.ANTIQUEWHITE);
		p3name.setTextAlignment(TextAlignment.CENTER);
		Text p4name = new Text(926, 718, p4Name);
		p4name.setFont(Font.font("Arial", 18));
		p4name.setFill(Color.ANTIQUEWHITE);
		p4name.setTextAlignment(TextAlignment.CENTER);
		//set up timer nodes
		Text timerLeft = new Text(35,393, game.getTimeRemaining().toString()); 
		//text binding code, adapted from: http://stackoverflow.com/questions/34546433/javafx-binding-a-textproperty-eg-label-to-a-simple-integer
		timerLeft.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -")); 
		timerLeft.setFont(Font.font("Arial", 18));
		timerLeft.setFill(Color.ANTIQUEWHITE);
		Text timerRight = new Text(933, 382, "-timer-");
		timerRight.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -"));
		timerRight.setFont(Font.font("Arial", 18));
		timerRight.setFill(Color.ANTIQUEWHITE); 
		Group GUI = //add all elements to a group, which is added to the parent node of the stage
		new Group(leftPanel, rightPanel, timerLeft, timerRight, p1name, p2name, p3name, p4name);
		return GUI;
		
	}
	public Node drawCountdown() {
		Text countdown = new Text(480, 404, "countdown");
		countdown.textProperty().bind(game.getCountdownTime().asString("%.0f"));
		countdown.setFont(Font.font("/res/fonts/kenvector_future_thin.ttf", 98));
		countdown.setFill(Color.ANTIQUEWHITE);
		return countdown; 
	}
}
