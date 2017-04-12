package group19.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Class for a model of a power-up
// Has many things in common with a regular game object, thus inherits from the ObjectModel class
// Had many planned types of power-ups which were eventually scrapped, means it will be easier to implement power-ups in the future however
public class PowerUpModel extends ObjectModel {
	private int type;
	public static final int SPEEDUP_BALL = 1;
	public static final int EXTRA_BALL = 2;
	private int radius;
	private Circle circle; 
	private int time; 
	
	public PowerUpModel(int x, int y, int powerup_type) {
		super(x, y);
		type = powerup_type;
		radius = 50;
		time = 10;
		
        // create node to be drawn in view
		circle = new Circle(radius);
        if (type == SPEEDUP_BALL) {
        	circle.setFill(Color.ROYALBLUE);
        }
        else if (type == EXTRA_BALL) {
        	circle.setFill(Color.CRIMSON);
        }
        circle.setOpacity(30);
        circle.translateXProperty().bind(super.getXProperty());
        circle.translateYProperty().bind(super.getYProperty());
	}
	
	public int getType() {
		return type;
	}
	
	public void consumePowerUp(BallModel ball) {
		if (type == SPEEDUP_BALL) {
			ball.setYVelocity((int) (ball.getYVelocity() * 1.1)); 
			ball.setXVelocity((int) (ball.getXVelocity() * 1.1));
		}
		super.setXPos(1500); // once the power-up has been consumed, take it out of the field 
	}
	
	public void consumePowerUp(GameModel game) {
		game.addBall();
		super.setXPos(1500); // once the power-up has been consumed, take it out of the field
	}
	
	public int getTimeRemaining() {
		return time;
	}
	
	public void decrementTime() {
		time--;
	}
	
	// Used to determine collisions and also draw the object 
	public Node getNode() {
		return circle;
	}
	
	// Change powerup type along with its colour 
	public void setType(int type) {
		this.type = type;
		
		if (type == SPEEDUP_BALL) {
        	circle.setFill(Color.ROYALBLUE);
        }
        else if (type == EXTRA_BALL) {
        	circle.setFill(Color.CRIMSON);
        }
	}
}
