package group19.model;

import group19.testcases.IPaddle;

public class PaddleModel extends ObjectModel implements IPaddle {
	private int width;
	private int height;
	private int xVelocity;
	
	public PaddleModel(int x, int y) {
		super(x, y);
		width = 50;
		height = 10;
		xVelocity = 1;
	}
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
		System.out.println("xpos: " + x +  "");
	}
	
	@Override
	public void setYPos(int y) {
		super.setYPos(y);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	public int getXVelocity() {
		return xVelocity;
	}
	
	public void setXVelocity(int velocity) {
		xVelocity = velocity;
	}
}
