package group19.model;

import java.util.concurrent.ThreadLocalRandom;

import group19.view.GameMenuView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

// Class for model of a ball.  Contains setters and getters for positions extended from ObjectModel
// Methods for ball characteristics, i.e. radius, diameter, movement 
public class BallModel extends ObjectModel {
	private int radius;
	private int xVelocity; // vertical speed
	private int yVelocity; // horizontal speed 
	public AudioClip ballToWall = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/wall_collision.wav").toString());
	public AudioClip ballToWarlord = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/sfx_lose.wav").toString());
	// Constructor: Create ball as position (x,y) 
	// of radius 10, vertical and horizontal velocities of 5px/frame 
	public BallModel(int x, int y) {
		super(x, y);
		radius = 10;  
		xVelocity = ThreadLocalRandom.current().nextInt(5, 11);
		yVelocity = ThreadLocalRandom.current().nextInt(5, 11); 
	}

	// Reverse ball's horizontal velocity when it hits an object 
	public void bounceX() {
		xVelocity = -(xVelocity);
	}

	public void setYVelocity(int velocity) {
		yVelocity = velocity;
	}
	
	// Reverse ball's velocity velocity when it hits an object 
	public void bounceY() {
		yVelocity = -(yVelocity);
	}

	public int getXVelocity() {
		return xVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}

	public int getRadius() {
		return radius;
	}
	
	public int getDiameter() {
		return 2*radius;
	}
	
	// Called in each game tick().  Moves ball according to its velocities 
	public void moveBall() {
		super.setXPos(super.getXPos() + xVelocity);
		super.setYPos(super.getYPos() + yVelocity);
	}
	
	public void playWallSound() {
		ballToWall.play();
	}
	public void playWarlordSound() {
		ballToWarlord.play();
	}
}