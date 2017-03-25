package group19.model;

//import java.awt.*;
//import java.awt.geom.*;
//import javax.swing.*;

public class BallModel {
	private int radius;
	private int diameter;
	private int xPos; // horizontal position, where left = 0
	private int yPos; // vertical position, where bottom = 0
	private int xVelocity; // vertical speed
	private int yVelocity; // horizontal speed 

	// constructor 
	// TODO: in initiation of a new game, ball should be initiated at (0,0) and 0 velocity
	public BallModel(int x, int y, int r) {
		xPos = x;
		yPos = y;
		radius = r;
		//isGoingDown = true;
		xVelocity = 0; // should be an argument? 
		yVelocity = 0;
		diameter = 2*r; // diameter = 2 * radius
	}

	// SEPARATING INTO ITS GETTERS AND SETTERS 
	// move ball
	/*public void move() {
		if (isGoingDown) {
			y_pos += v_speed; 
		}
		else {
			y_pos -= v_speed;
		} 

		yPos += xVelocity;
		xPos += yVelocity;

		//BoardModel board = new BoardModel(768, 768);
		//Blocks block = new Blocks();

		if (((xPos - radius) == 0) || (xPos + radius == board.getWidth())) {
			yVelocity = -(yVelocity); // change directions when ball hits left or right wall
		}

		if ((xPos + radius) > board.getWidth()) { // when ball x-pos exceeds right wall 
			xPos = board.getWidth() - radius; // reposition right edge of ball to be touching right wall 
			yVelocity = -(-yVelocity); // change directions 
		}

		if ((xPos - radius) < 0) { // when ball exceeds left wall 
			xPos = radius; // reposition left edge of ball to be touching left wall 
			yVelocity = -(yVelocity); // change directions 
		}

		if ((yPos - radius) <= 0) { // when bottom of ball hits bottom wall 
			isGoingDown = false; // change direction (bounce up)  
		}

		if ((yPos + radius) >= board.getHeight()) { // when top of ball hits top wall 
			isGoingDown = true; // change direction (bounce down)
		}
	}*/

	public void setXPos(int position) {
		if ((position - radius) < 0) {
			xPos = 0;
		}
		else if ((position + radius) > 768) {
			xPos = 768;
		}
		else { 
			xPos = position;
		}
	}

	public void setYPos(int position) {
		if ((position - radius) < 0) {
			yPos = 0;
		}
		else if ((position + radius) > 768) {
			yPos = 768;
		}
		else { 
			yPos = position;
		}
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}	
	
	public void setXVelocity(int velocity) {
		if (((xPos - radius) <= 0) || ((xPos + radius) >= 768)) { // 768 = frame width 
			xVelocity = -(velocity); // change directions when ball hits left or right wall 
		}
		else {
			xVelocity = velocity;
		}
	}

	public void setYVelocity(int velocity) {
		if (((yPos - radius) <= 0) || ((yPos + radius) >= 768)) { // 768 = frame height 
			yVelocity = -(velocity); // change directions when ball hits left or right wall 
		}
		else {
			yVelocity = velocity;
		}
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
		return diameter;
	}
}