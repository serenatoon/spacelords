package group19.model;
//import javax.swing.*;
//import java.awt.geom.*;
import java.awt.*;

public class BrickModel {
	private int x_pos; // horizontal position from left 
	private int y_pos; // vertical position from top 
	boolean is_destroyed; // boolean holding state of brick 

	// constructor 
	public BrickModel(int x, int y) {
		x_pos = x;
		y_pos = y;
		is_destroyed = false;
	}

	public int getHorizontalPosition() {
		return x_pos;
	}

	public int getVerticalPosition() {
		return y_pos;
	}

	
	// get state of brick, whether or not it is destroyed 
	public boolean getBrickState() {
		return is_destroyed;
	}

	// destroy brick
	public void destroyBrick() {
		is_destroyed = true;
	}

	public Rectangle brickAsRect() {
		Blocks blocks = new Blocks();
		Rectangle brick = new Rectangle(x_pos, y_pos, blocks.getBrickWidth(), blocks.getBrickHeight());
		return brick;
	}
}