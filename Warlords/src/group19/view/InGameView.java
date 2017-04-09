package group19.view;

import group19.model.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
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
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
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
import java.util.ArrayList;
import java.util.ListIterator;

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
	
	public InGameView (double width, double height, GameModel model) { //upon initialisation, switch focus to in game view
		this.game = model; //pass input parameter out to local variable
        // BRICKS CURRENTLY BEING MADE IN GAMEMODEL, MIGHT MOVE TO WARLORD/PADDLE LATER
        this.brickList = game.getBrickList(); 

		rootGameLayout.setPrefWidth(width);
		rootGameLayout.setPrefHeight(height);
		Image bgImage = new Image("/res/images/space_lf.png");
		BackgroundImage bg = new BackgroundImage(bgImage, null, null, null, null);
		rootGameLayout.setBackground(new Background(bg));
		rootGameLayout.getChildren().addAll(drawCountdown(), drawBall(), drawPaddle(game.getPaddle1()),
				drawPaddle(game.getPaddle2()), drawPaddle(game.getPaddle3()),
				drawPaddle(game.getPaddle4()),
				//drawBrick(),
				drawWarlord(game.getWarlord1(), new ImageView("/res/images/baseP1.png")),
				drawWarlord(game.getWarlord2(), new ImageView("/res/images/baseP2.png")),
				drawWarlord(game.getWarlord3(), new ImageView("/res/images/baseP3.png")), 
				drawWarlord(game.getWarlord4(), new ImageView("/res/images/baseP4.png")),
				drawGUI()); //add child nodes here 
		
		int i = 0;
		for (BrickModel brick : brickList) {
			if (!brickList.get(i).isDestroyed()) {
				rootGameLayout.getChildren().add(drawBrick(brickList.get(i)));
			}
			i++;
		}
		scene = new Scene(rootGameLayout, 1024, 768);
	}		

	public Scene getScene() {
		return scene;
	}
	
	//Below are all the grey-blocked implementations of the drawn objects. The constructors call model properties to set their dimensions
	//and position. The bind method helps model parameters translate to actual UI changes.
	public static Node drawBall() {
        Circle circle = new Circle(game.getBall().getRadius());
        Glow glow = new Glow(1.0);
        circle.setFill(Color.RED);
        circle.setEffect(glow);
        
        circle.centerXProperty().bind(game.getBall().getXProperty());
        circle.centerYProperty().bind(game.getBall().getYProperty());
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
	
    // takes a single brick in as param
    public static Node drawBrick(BrickModel brick) {
        Rectangle rect = new Rectangle(brick.getWidth(), brick.getHeight());
        if (brick.getOwner().getPlayerNo() == 1) {	
        	rect.setFill(Color.CYAN);
        }
        else if (brick.getOwner().getPlayerNo() == 2) {
        	rect.setFill(Color.CHARTREUSE);
        }
        else if (brick.getOwner().getPlayerNo() == 3) {
        	rect.setFill(Color.CRIMSON);
        }
        else if (brick.getOwner().getPlayerNo() == 4) {
        	rect.setFill(Color.GOLD);
        }
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1.0);
        rect.translateXProperty().bind(brick.getXProperty());
        rect.translateYProperty().bind(brick.getYProperty());
        return rect;
    }

	
	public static Node drawWarlord(WarlordModel warlord, ImageView img) {
			img.setFitWidth(warlord.getWidth());
			img.setFitHeight(warlord.getHeight());
			Effect glow = new Glow(0.0);
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
		Text p1name = new Text(30, 50, "p1Name");
		p1name.setFont(Font.font("Arial", 18));
		p1name.setFill(Color.ANTIQUEWHITE);
		Text p1score = new Text(38,75, "<score>");
		p1score.setFont(Font.font("Arial", 13));
		p1score.setFill(Color.ANTIQUEWHITE);
		Text p2name = new Text(926, 50, "p2Name");
		p2name.setFont(Font.font("Arial", 18));
		p2name.setFill(Color.ANTIQUEWHITE);
		Text p2score = new Text(936, 75, "<score>");
		p2score.setFont(Font.font("Arial", 13));
		p2score.setFill(Color.ANTIQUEWHITE);
		Text p3name = new Text(30, 718, "p3Name");
		p3name.setFont(Font.font("Arial", 18));
		p3name.setFill(Color.ANTIQUEWHITE);
		Text p3score = new Text(40, 693, "<score>");
		p3score.setFont(Font.font("Arial", 13));
		p3score.setFill(Color.ANTIQUEWHITE);
		Text p4name = new Text(926, 718, "p4Name");
		p4name.setFont(Font.font("Arial", 18));
		p4name.setFill(Color.ANTIQUEWHITE);
		Text p4score = new Text(935,693, "<score>");
		p4score.setFont(Font.font("Arial", 13));
		p4score.setFill(Color.ANTIQUEWHITE);
		Text timerLeft = new Text(35,393, game.getTimeRemaining().toString()); 
		timerLeft.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -"));
		timerLeft.setFont(Font.font("Arial", 18));
		timerLeft.setFill(Color.ANTIQUEWHITE);
		Text timerRight = new Text(933, 382, "-timer-");
		timerRight.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -"));
		timerRight.setFont(Font.font("Arial", 18));
		timerRight.setFill(Color.ANTIQUEWHITE); 
		Group GUI = 
		new Group(leftPanel, rightPanel, timerLeft, timerRight, p1name, p1score, p2name, p2score, p3name, p3score, p4name, p4score);
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
