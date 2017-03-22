//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*; // listeners, controller 
//import java.awt.geom.*;


class Paddle {
	private int left_offset;
	private int width;
	private int height;
	private int move_distance;
	
	public Paddle(int offset, int h) {
		left_offset = offset;
		height = h;
		width = 60; // change later? 
	}
	
	public int getLeft() {
		return left_offset;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void move() {
		Blocks block = new Blocks(); // TODO: replace 'Blocks' with methods/variables/getters in their respective (model) classes 
		left_offset =+ move_distance;
		if (left_offset <= 2) {
			left_offset = 2; // if paddle hits left wall, stop it there 
		}
		if (left_offset >= block.getFrameWidth() - width) {
			left_offset = block.getFrameWidth() - width - 2; // if paddle hits right wall, stop it there 
		}
	}
}
