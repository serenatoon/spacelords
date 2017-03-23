import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


class PaddleModel {
	private int left_offset;
	private int width;
	private int height;
	private int to_move;
	
	public PaddleModel(int offset, int h) {
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
}
