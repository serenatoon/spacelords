package group19.model;

//import java.awt.*;
//import java.awt.geom.*;
//import javax.swing.*;

public class BallModel {
	private int radius;
	private int diameter;
	private int x_pos; // horizontal position, where left = 0
	private int y_pos; // vertical position, where bottom = 0
	private int v_speed; // vertical speed
	private int h_speed; // horizontal speed 
	private boolean isGoingDown; 

	// constructor 
	// TODO: in initiation of a new game, ball should be initiated at (0,0) and 0 velocity
	public BallModel(int x, int y, int r) {
		x_pos = x;
		y_pos = y;
		radius = r;
		//isGoingDown = true;
		v_speed = 0; // should be an argument? 
		h_speed = 0;
		diameter = 2*r; // diameter = 2 * radius
	}

	// move ball
	public void move() {
		/*if (isGoingDown) {
			y_pos += v_speed; 
		}
		else {
			y_pos -= v_speed;
		} */

		y_pos += v_speed;
		x_pos += h_speed;

		BoardModel board = new BoardModel(768, 768);
		//Blocks block = new Blocks();

		if (((x_pos - radius) == 0) || (x_pos + radius == board.getWidth())) {
			h_speed = -(h_speed); // change directions when ball hits left or right wall
		}

		if ((x_pos + radius) > board.getWidth()) { // when ball x-pos exceeds right wall 
			x_pos = board.getWidth() - radius; // reposition right edge of ball to be touching right wall 
			h_speed = -(-h_speed); // change directions 
		}

		if ((x_pos - radius) < 0) { // when ball exceeds left wall 
			x_pos = radius; // reposition left edge of ball to be touching left wall 
			h_speed = -(h_speed); // change directions 
		}

		if ((y_pos - radius) <= 0) { // when bottom of ball hits bottom wall 
			isGoingDown = false; // change direction (bounce up)  
		}

		if ((y_pos + radius) >= board.getHeight()) { // when top of ball hits top wall 
			isGoingDown = true; // change direction (bounce down)
		}
	}

	public void setXPos(int position) {
		x_pos = position;
	}

	public void setYPos(int position) {
		y_pos = position;
	}

	public void setVerticalSpeed(int speed) {
		v_speed = speed;
	}

	public void setHorizontalSpeed(int speed) {
		h_speed = speed;
	}

	public int getRadius() {
		return radius;
	}

	public int getXPos() {
		return x_pos;
	}

	public int getYPos() {
		return y_pos;
	}	
}