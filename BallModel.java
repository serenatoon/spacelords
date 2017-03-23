import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class BallModel {
	private int radius;
	private int diameter;
	private int x_pos; // horizontal position ?
	private int y_pos; // vertical position from top?
	private int v_speed; // vertical speed
	private int h_speed; // horizontal speed 
	private boolean isGoingDown; 

	// constructor 
	public BallModel(int x, int y, int r) {
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
		if (isGoingDown) {
			y_pos += v_speed; 
		}
		else {
			y_pos -= v_speed;
		} 

		x_pos += h_speed;

		Blocks block = new Blocks();

		if (((x_pos - radius) == 0) || (x + radius == block.getWidth())) {
			h_speed = -(h_speed); // change directions when ball hits walls
		}

		if ((x_pos + radius) > block.getWidth()) {
			x_pos = block.getWidth() - radius; // 
		}

		if ((x_pos - radius) < 0) { // when ball hits left wall 
			x_pos = radius;
			h_speed = -(h_speed); // change directions 
		}

		if (y_pos <= 0) { // when hits top wall 
			isGoingDown = true; // change direction (bounce down) 
		}

		if (y_pos + (2*radius) >= block.getHeight()) { // hit bottom? 
			isGoingDown = false;
		}

		
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