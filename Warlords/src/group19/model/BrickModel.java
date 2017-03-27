// One "brick" of a wall, implements the interface IWall 

package group19.model;
//import javax.swing.*;
//import java.awt.geom.*;
//import java.awt.*;

public class BrickModel {
	private int xPos; // horizontal position from left 
	private int yPos; // vertical position from top 
	boolean isDestroyed; // boolean holding state of brick 

	// constructor 
	public BrickModel(int x, int y) {
		xPos = x;
		yPos = y;
		isDestroyed = false; // initiate wall as not destroyed 
	}
	
	public void setXPos(int x) {
		xPos = x;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
	
	// get state of brick, i.e. whether or not it is destroyed 
	public boolean isDestroyed() {
		return isDestroyed;
	}

	// destroy brick
	public void destroy() {
		isDestroyed = true;
	}
}