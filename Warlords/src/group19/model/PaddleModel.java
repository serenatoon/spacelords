package group19.model;

import group19.testcases.IPaddle;

public class PaddleModel extends ObjectModel implements IPaddle {
	private int width;
	private int height;
	
	public PaddleModel(int x, int y, int w, int h) {
		super(x, y);
		width = w;
		height = h;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
