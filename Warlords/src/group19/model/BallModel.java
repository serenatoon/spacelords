package group19.model;

import java.util.concurrent.ThreadLocalRandom;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// Class for model of a ball.  Contains setters and getters for positions extended from ObjectModel
// Methods for ball characteristics, i.e. radius, diameter, movement 
public class BallModel extends ObjectModel {
	private int radius;
	private double xVelocity; // vertical speed
	private double yVelocity; // horizontal speed 
	public AudioClip ballToWall = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/wall_collision.wav").toString());
	public AudioClip ballToWarlord = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/sfx_lose.wav").toString());
	
	// Constructor: Create ball as position (x,y) 
	// of radius 10, vertical and horizontal velocities of 5px/frame 
	public BallModel(int x, int y) {
		super(x, y);
		radius = 8;  
		xVelocity = ThreadLocalRandom.current().nextInt(5, 11); // a new ball is created with random velocity between 5 and 10 px/frame 
		yVelocity = ThreadLocalRandom.current().nextInt(5, 11); 
	}

	// Reverse ball's horizontal velocity when it hits an object 
	public void bounceX() {
		xVelocity = -(xVelocity);
	}

	public void setYVelocity(int velocity) {
		yVelocity = velocity;
	}
	
	public void setXVelocity(int velocity) {
		xVelocity = velocity;
	}
	
	// Reverse ball's velocity velocity when it hits an object 
	public void bounceY() {
		yVelocity = -(yVelocity);
	}

	public double getXVelocity() {
		return xVelocity;
	}
	
	public double getYVelocity() {
		return yVelocity;
	}
	
	// Method which is called every 10 seconds of gametime
	// Speeds up the ball to make the game more interesting 
	// increments are very small because the gametime is divisible by 10 for more than one gametick 
	// so the ball is actually speed up many times than we'd like it to be in each time interval
	public void incrementVelocity() {
		if (xVelocity > 0) {
			xVelocity += 0.025;
		}
		else {
			xVelocity -= 0.025;
		}
		
		if (yVelocity > 0) {
			yVelocity += 0.025;
		}
		else {
			yVelocity -= 0.025;
		}
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
	
	public void playWallSound() { // sound to be played when ball collides with the edges of the screen 
		ballToWall.play();
	}
	
	public void playWarlordSound() { // sound to be played when the ball destroys a warlord 
		ballToWarlord.play();
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
}