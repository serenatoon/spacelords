package group19.model;

import group19.testcases.IPaddle;
import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// Class for model of paddle.  Largely extends from Object model
public class PaddleModel extends ObjectModel implements IPaddle {
	private int width;
	private int height;
	private int xVelocity;
	AudioClip paddleHit = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/paddle_hit.wav").toString());
	public PaddleModel(int x, int y) {
		super(x, y);
		width = 50;
		height = 10;
		xVelocity = 10;
	}
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
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
	public void paddleHitSound() {
		paddleHit.play();
	}
}
