package group19.model;

import group19.testcases.IBall;

public class BallModel extends ObjectModel implements IBall {
	private int radius;
	private int xVelocity; // vertical speed
	private int yVelocity; // horizontal speed 

	// constructor: create ball as position (x,y) 
	// TODO: in initiation of a new game, ball should be initiated at (0,0) and 0 velocity
	public BallModel(int x, int y) {
		super(x, y);
		radius = 5; // change later to however big we want the ball to be 
		xVelocity = 20; // should be an argument? 
		yVelocity = 10; 
	}
	
	// in order to implement test interfaces...
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
	
	public void bounceX() { // to change directions when ball hits an object 
		xVelocity = -(xVelocity);
	}

	@Override
	public void setYVelocity(int velocity) {
		yVelocity = velocity;
	}
	
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
	
	public void moveBall() {
		super.setXPos(super.getXPos() + xVelocity);
		super.setYPos(super.getYPos() + yVelocity);
	}
}