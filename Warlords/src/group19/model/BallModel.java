package group19.model;

import group19.testcases.IBall;
import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// Class for model of a ball.  Contains setters and getters for positions extended from ObjectModel
// Methods for ball characteristics, i.e. radius, diameter, movement 
public class BallModel extends ObjectModel implements IBall {
	private int radius;
	private int xVelocity; // vertical speed
	private int yVelocity; // horizontal speed 
	AudioClip ballToWall = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/wall_collision.wav").toString());
	// Constructor: Create ball as position (x,y) 
	// of radius 10, vertical and horizontal velocities of 5px/frame 
	public BallModel(int x, int y) {
		super(x, y);
		radius = 10;  
		xVelocity = 5; 
		yVelocity = 5; 
	}
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
	}
	
	@Override
	public void setYPos(int y) {
		super.setYPos(y);
	}
	
	@Override
	public int getXPos() {
		return super.getXPos();
	}
	
	@Override
	public int getYPos() {
		return super.getYPos();
	}
	
	@Override
	public void setXVelocity(int velocity) {
		xVelocity = velocity;
	}
	
	// Reverse ball's horizontal velocity when it hits an object 
	public void bounceX() {
		xVelocity = -(xVelocity);
	}

	@Override
	public void setYVelocity(int velocity) {
		yVelocity = velocity;
	}
	
	// Reverse ball's velocity velocity when it hits an object 
	public void bounceY() {
		yVelocity = -(yVelocity);
	}

	@Override
	public int getXVelocity() {
		return xVelocity;
	}
	
	@Override
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
}