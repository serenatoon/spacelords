//import javax.swing.*;
//import java.awt.geom.*;
import java.awt.*;

public class Brick {
	private int x_pos; // horizontal position from left 
	private int y_pos; // vertical position from top 
	boolean isDestroyed; // boolean holding state of brick 

	// constructor 
	public Brick(int x, int y) {
		x_pos = x;
		y_pos = y;
		isDestroyed = false;
	}

	public int getHorizontalPosition() {
		return x_pos;
	}

	public int getVerticalPosition() {
		return y_pos;
	}

	// get state of brick, whether or not it is destroyed 
	public boolean getBrickState() {
		return isDestroyed;
	}

	// destroy brick
	public void destroyBrick() {
		isDestroyed = true;
	}

	public Rectangle brickAsRect() {
		Blocks blocks = new Blocks();
		Rectangle brick = new Rectangle(x_pos, y_pos, blocks.getBrickWidth(), blocks.getBrickHeight());
		return brick;
	}
}