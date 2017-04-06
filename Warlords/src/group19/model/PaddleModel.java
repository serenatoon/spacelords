package group19.model;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// Class for model of paddle.  Largely extends from Object model
public class PaddleModel extends ObjectModel {
	private int width;
	private int height;
	private int xVelocity;
	private WarlordModel warlord;
	AudioClip paddleHit = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/paddle_hit.wav").toString());
	
	public PaddleModel(int x, int y, WarlordModel warlord) {
		super(x, y);
		width = 100;
		height = 10;
		xVelocity = 20;
		// warlord should belong to a paddle
		// seems backwards but i'm thinking along the lines of how to increment score when you destroy a brick
		// pseudocode: when ball collides with paddle then destroys a brick, getwarlord().incrementScore()
		// was thinking of creating the warlord within the paddle / paddle within warlord constructor
		// but might not bode well with ghosts. so i will stick with this messy implementation for now 
		this.warlord = warlord; // warlord which this paddle belongs to
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
	
	// Get the warlord/player which this paddle belongs to 
	public WarlordModel getWarlord() {
		return warlord;
	}
}
