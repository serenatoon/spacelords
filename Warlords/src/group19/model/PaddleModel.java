package group19.model;

import group19.testcases.IPaddle;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*; // listeners, controller 
//import java.awt.geom.*;


public class PaddleModel implements IPaddle {
	private int xPos; // left edge = 0
	private int yPos; // bottom = 0
	private int width;
	private int height;
	//private int moveDistance;
	
	public PaddleModel(int x, int y, int w, int h) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setXPos(int x) {
		if ((x - width/2) < 0) { // do not allow paddle to move beyond left wall 
			xPos = width/2; // re-position left edge of paddle to be touching left wall 
		}
		else if ((x + width/2) > 768) { // right wall, change 768 if frame size changes; is Board.getWidth() better practice? 
			xPos = 768 - width/2; // re-position right edge of paddle to be touching right wall 
		}
		else {
			xPos = x;
		}
		
		// TODO: paddle should not be allowed to move out of each player's boundaries 
	}
	
	public void setYPos(int y) {
		if ((y - width/2) < 0) { // do not allow paddle to move beyond left wall 
			yPos = width/2; // re-position left edge of paddle to be touching left wall 
		}
		else if ((y + width/2) > 768) { // right wall, change 768 if frame size changes; is Board.getWidth() better practice? 
			yPos = 768 - width/2; // re-position right edge of paddle to be touching right wall 
		}
		else {
			yPos = y;
		}
		
		// TODO: paddle should not be allowed to move out of each player's boundaries 
	}
	
	// put in view? 
	/*public Rectangle convertRectangle() {
		Rectangle rectanglePaddle = new Rectangle(xPos, yPos, width, height);
		
		return rectanglePaddle;
	}*/
	
	/*public void move() {
		//Blocks block = new Blocks(); // TODO: replace 'Blocks' with methods/variables/getters in their respective (model) classes 
		xPos =+ moveDistance;
		if (xPos <= 2) {
			xPos = 2; // if paddle hits left wall, stop it there 
		}
		if (xPos >= block.getFrameWidth() - width) {
			xPos = block.getFrameWidth() - width - 2; // if paddle hits right wall, stop it there 
		}
	}*/
	
}
