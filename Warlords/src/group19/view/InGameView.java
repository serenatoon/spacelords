package group19.view;

import java.text.DecimalFormat;

import com.sun.glass.ui.Window;

import group19.controller.GameStateController;
import group19.model.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
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
		Image bgImage = new Image("/res/images/space_lf.png");
		BackgroundImage bg = new BackgroundImage(bgImage, null, null, null, null);
		rootGameLayout.setBackground(new Background(bg));
		rootGameLayout.getChildren().addAll(drawBall(), drawPaddle(game.getPaddle1()),
				drawPaddle(game.getPaddle2()), drawPaddle(game.getPaddle3()),
				drawPaddle(game.getPaddle4()), drawBrick(),
				drawWarlord(game.getWarlord1(), new ImageView("/res/images/baseP1.png")),
				drawWarlord(game.getWarlord2(), new ImageView("/res/images/baseP2.png")),
				drawWarlord(game.getWarlord3(), new ImageView("/res/images/baseP3.png")), 
				drawWarlord(game.getWarlord4(), new ImageView("/res/images/baseP4.png")),
				drawGUI()); //add child nodes here 
		scene = new Scene(rootGameLayout, 1024, 768);
	}		

	public Scene getScene() {
		return scene;
	}
	
	//Below are all the grey-blocked implementations of the drawn objects. The constructors call model properties to set their dimensions
	//and position. The bind method helps model parameters translate to actual UI changes.
	public static Node drawBall() {
        Circle circle = new Circle(game.getBall().getXPos(), game.getBall().getYPos(), game.getBall().getRadius());
//        RadialGradient gradient = new RadialGradient(
//                0, 0, 0.5,0.5, 1.2, true, CycleMethod.NO_CYCLE,
//                new Stop(1, Color.GAINSBORO),
//                new Stop(0, Color.FIREBRICK)
//        );
        Glow glow = new Glow(1.0);
        circle.setFill(Color.RED);
        circle.setEffect(glow);
        
        circle.translateXProperty().bind(game.getBall().getXProperty());
        circle.translateYProperty().bind(game.getBall().getYProperty());
        return circle;
	}

	/*public static Node drawPaddle() {
		Rectangle rect = new Rectangle(game.getPaddle1().getWidth(), game.getPaddle1().getHeight());
        rect.translateXProperty().bind(game.getPaddle1().getXProperty());
        rect.translateYProperty().bind(game.getPaddle1().getYProperty());
        rect.setFill(Color.ANTIQUEWHITE);
		return rect;
	}*/
	
	public static Node drawPaddle(PaddleModel paddle) {
		//Rectangle rect = new Rectangle(paddle.getWidth(), paddle.getHeight());
		ImageView rect = new ImageView("/res/images/paddle_strip.png");
		rect.setFitWidth(paddle.getWidth());
		rect.setFitHeight(paddle.getHeight());
		rect.translateXProperty().bind(paddle.getXProperty());
		rect.translateYProperty().bind(paddle.getYProperty());
	//	rect.setFill(Color.ALICEBLUE);
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
		Rectangle leftPanel = new Rectangle(0,0, 128, 768); //rectangle at pos(0,0) 128px wide 768px high
		Rectangle rightPanel = new Rectangle(896,0,128,768);
		Text p1name = new Text(30, 50, "p1Name");
		p1name.setFont(Font.font(18));
		Text p1score = new Text(38,75, "<score>");
		Text p2name = new Text(30, 718, "p2Name");
		p2name.setFont(Font.font(18));
		Text p2score = new Text(40,693, "<score>");
		Text p3name = new Text(926, 50, "p3Name");
		p3name.setFont(Font.font(18));
		Text p3score = new Text(936,75, "<score>");
		Text p4name = new Text(926, 718, "p4Name");
		p4name.setFont(Font.font(18));
		Text p4score = new Text(935,693, "<score>");
		Text timerLeft = new Text(35,393, game.getTimeRemaining().toString()); 
		//StringProperty str = StringProperty.format("%02d:%02d", game.getTimeRemaining().intValue() / 60, game.getTimeRemaining().intValue() % 60);
		timerLeft.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -"));
		timerLeft.setFont(Font.font(18)); 
		Text timerRight = new Text(933, 382, "-timer-");
		timerRight.textProperty().bind(Bindings.concat( "- ", game.getTimeRemaining().asString("%.0f"), " -"));
		timerRight.setFont(Font.font(18)); 
		leftPanel.setFill(Color.ANTIQUEWHITE);
		rightPanel.setFill(Color.ANTIQUEWHITE);
		Text countdown = new Text(512, 384, "countdown");
		countdown.textProperty().bind(game.getCountdownTime().asString("%.0f"));
		
		// why does none of this work >:( 
		if (game.getCountdownTime().intValue() <= 2) {
			//countdown.textProperty().unbind();
			//countdown = new Text(512, 384, " "); 
			countdown.setFill(Color.TRANSPARENT); // lol
		}
		countdown.setFont(Font.font(48)); 
		countdown.setFill(Color.ALICEBLUE);
		Group GUI = 
		new Group(leftPanel, rightPanel, timerLeft, timerRight, p1name, p1score, p2name, p2score, p3name, p3score, p4name, p4score, countdown);
		return GUI;
		
	}
	
}
