package group19.model;

import group19.testcases.IWall;

// One "brick" in a wall, implements the interface IWall
public class BrickModel extends ObjectModel implements IWall {
	boolean isDestroyed; // boolean holding state of brick 
	private int height;
	private int width;
	
	// Constructor: Create ball at position (x,y) 
	public BrickModel(int x, int y) {
		super(x, y);
		isDestroyed = false; // initiate brick as not destroyed 
		width = 100;
		height = 50;
	}
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
	}
	
	@Override 
	public void setYPos(int y) {
		super.setYPos(y);
	}
	
	// get state of brick, i.e. whether or not it is destroyed
	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	// destroy brick
	public void destroy() {
		isDestroyed = true;
	}
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}