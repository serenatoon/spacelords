package group19.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUpModel extends ObjectModel {
	private int type;
	public static final int SPEEDUP_PADDLE = 1;
	public static final int EXTEND_PADDLE = 2;
	public static final int ENLARGE_BALL = 3;
	public static final int SPEEDUP_BALL = 4;
	public static final int EXTRA_LIFE = 5;
	public static final int EXTRA_BALL = 6;
	private int width;
	private int height;
	private Rectangle rectangle; 
	private int time; // how much time is remainng for the powerup effect (how long until it wears off) 
	
	public PowerUpModel(int x, int y, int powerup_type) {
		super(x, y);
		type = powerup_type;
		width = 20;
		height = 20;
		time = 10;

        // create node to be drawn in view 
        rectangle = new Rectangle(width, height);
        if (type == SPEEDUP_PADDLE) {	
        	rectangle.setFill(Color.MOCCASIN);
        }
        else if (type == EXTEND_PADDLE) {
        	rectangle.setFill(Color.GOLDENROD);
        }
        else if (type == ENLARGE_BALL) {
        	rectangle.setFill(Color.BLUE);
        }
        else if (type == SPEEDUP_BALL) {
        	rectangle.setFill(Color.CORNFLOWERBLUE);
        }
        else if (type == EXTRA_LIFE) {
        	rectangle.setFill(Color.AQUAMARINE);
        }
        else if (type == EXTRA_BALL) {
        	rectangle.setFill(Color.DARKORCHID);
        }
//        rectangle.setStroke(Color.BLACK);
//        rectangle.setStrokeWidth(1.0);
        rectangle.translateXProperty().bind(super.getXProperty());
        rectangle.translateYProperty().bind(super.getYProperty());
	}
	
	public int getType() {
		return type;
	}
	
	// might move to different methods with different params  to be overloaded 
	public void consumePowerUp(PaddleModel paddle, BallModel ball) {
		if (type == SPEEDUP_PADDLE) {
			paddle.setXVelocity(40); // speed up paddle from 20px/frame
		}
		else if (type == EXTEND_PADDLE) {
			paddle.setSize(50, 50); // change paddle to 50x50.  might fk up collisions though :-(    i'm gonna have fun cleaning up these comments later 
		}
		else if (type == ENLARGE_BALL) {
			ball.setRadius(10); // this is probably gonna fk up collisions 
		}
		else if (type == SPEEDUP_BALL) {
			ball.setYVelocity((int) (ball.getYVelocity() * 1.5)); // might make these RNG 
			ball.setXVelocity((int) (ball.getXVelocity() * 1.5));
		}
		else if (type == EXTRA_LIFE) {
			paddle.getWarlord().addLives();
		}
		else if (type == EXTRA_BALL) {
			// TODO launch a new ball 
		}
	}
	
	public int getTimeRemaining() {
		return time;
	}
	
	public void decrementTime() {
		time--;
	}
	
	// might move to different methods with different params 
	// Remove effects of powerup 
	public void revert(PaddleModel paddle) {
		if (type == SPEEDUP_PADDLE) {
			paddle.setXVelocity(20); // revert to regular velocity of 20px/frame
		}
		else if (type == EXTEND_PADDLE) {
			paddle.setSize(40, 40); // revert to regular size of 40x40 px 
		}
	}
	
	public void revert(BallModel ball) {
		if (type == ENLARGE_BALL) {
			ball.setRadius(8); // revert to regular size of 8px radius
		}
		else if (type == SPEEDUP_BALL) {
			ball.setYVelocity((int) (ball.getYVelocity() / 1.5)); // is this how u math.. before they were *1.5
			ball.setXVelocity((int) (ball.getXVelocity() / 1.5));
		}
	}
}
