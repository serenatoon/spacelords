import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Ball {
	private int radius;
	private int diameter;
	private int x_pos; // horizontal position ?
	private int y_pos; // vertical position from top?
	private int v_speed; // vertical speed
	private int h_speed; // horizontal speed 
	private boolean isGoingDown; 

	// constructor 
	public Ball(int x, int y, int r) {
		x_pos = x;
		y_pos = y;
		radius = r;
		isGoingDown = true;
		v_speed = 2; // CHANGE LATER
		h_speed = 0;
		diameter = 2*r; // diameter = 2 * radius
	}

	// move ball
	public void move() {

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