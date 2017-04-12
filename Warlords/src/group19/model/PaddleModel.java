package group19.model;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// Class for model of paddle.  Largely extends from Object model
public class PaddleModel extends ObjectModel {
	private int width;
	private int height;
	private int xVelocity;
	private WarlordModel warlord;
	private double angle;
	AudioClip paddleHit = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/paddle_hit.wav").toString());
	
	public PaddleModel(int x, int y, WarlordModel warlord) {
		super(x, y);
		width = 40;
		height = 40;
		xVelocity = 20;
		if (warlord.getPlayerNo() == 1 || warlord.getPlayerNo() == 2) {
			angle = 1.5; //for p1/p2
		}
		else if (warlord.getPlayerNo() == 3 || warlord.getPlayerNo() == 4) {
			angle = 4.84; //for p3/p4			
		}

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
	
	// Used for movement 
	public double getAngle() {
		return angle;
	}
	
	public void addToAngle(int player) { //need to specify player, which allows for different angle constraints
		if (player == 1) {
			if (angle < 3.15) { //only allow movement between 1.55 to 3.15... values chosen because angle is 90 degrees w/ 60 ticks (1 sec) req for total movement
				angle += 0.1;
			}
			else angle = 3.15;
		}
		if (player == 2) {
			if (angle < 1.42) {
				angle += 0.1;
			}
			else angle = 1.42; //constraints to a 90 degree arc 
		}
		if (player == 3) {
			if (angle < 4.73) {
				angle += 0.1;
			}
			else angle = 4.73;
		}
		if (player == 4) {
			if (angle < 6.12) {
				angle += 0.1;
			}
			else angle = 6.12;
		}
	}
	public void subtractToAngle(int player) {
		if (player == 1) {
			if (angle > 1.55) {
				angle -= 0.1;
			}
			else angle = 1.55;
		}
		if (player == 2) {
			if (angle > 0) {
				angle -= 0.1;
			}
			else angle = 0;
		}
		if (player == 3) {
			if (angle > 3.3) {
				angle -= 0.1;
			}
			else angle = 3.3;
		}
		if (player == 4) {
			if (angle > 4.84) {
				angle -= 0.1;
			}
			else angle = 4.84;
		}
	}
	
	// Once player has died, the paddle should also go with it.
	// KeyListener will no longer be listening (done in controller) 
	public void setDead() {
		super.setXPos(1500); // move paddle off screen
	}
}
