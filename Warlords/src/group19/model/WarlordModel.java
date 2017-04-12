package group19.model;

import java.util.ArrayList;

import group19.view.GameMenuView;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;

// Holds player information as well as properties to draw the warlord itself
public class WarlordModel extends ObjectModel { 
	private int playerNo; // player 1-4
	private boolean isWinner;
	private boolean isDead;
	private int width;
	private int height;
	private int lowerXBounds;
	private int upperXBounds;
	private int lowerYBounds;
	private int upperYBounds;
	private int score;
	private int bricksAlive;
	
	AudioClip warlordDead = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/sfx_lose.wav").toString());
	
	/* Player # positions are as follows: 
	 *				 | 1    2 |
	 * 				 |        |
	 * 			 	 |        |
	 * 			 	 | 3    4 |                         */	
	public WarlordModel(int x, int y, int playerNo) { 
		super(x, y);
		this.playerNo = playerNo; // need to know player number to set bounds of which the paddle can move for each player
		isWinner = false;
		isDead = false;
		width = 120;
		height = 120;
		setPaddleBounds();
		score = 0;
	}

	/* Player # positions are as follows: 
	 *				 | 1    2 |
	 * 				 |        |
	 * 			 	 |        |
	 * 			 	 | 3    4 |                         */	
	public void setPaddleBounds() {
		if (playerNo%2 == 1) { // left players, lower x bounds are 0
			lowerXBounds = 128;
			upperXBounds = super.getXPos() + 120+60; // xPos is top left, + 120 for width of warlord, + 60 for bricks 
		}
		
		if (playerNo%2 == 0) { // right players, upper x bounds are 768+128
			upperXBounds = 768+128;
			lowerXBounds = super.getXPos() - 60+9;
		}
		
		if (playerNo >= 3) { // bottom players
			lowerYBounds = super.getYPos()-60+9;
			upperYBounds = 768;
		}
		
		if (playerNo <= 2) { // top players
			upperYBounds = super.getYPos() + 120 + 60;
			lowerYBounds = 0;
		}
	}
	
	// 'Bounds' are the constraints of which each warlord can move their paddle
	
	public int getLowerXBounds() {
		return lowerXBounds;
	}
	
	public int getUpperXBounds() {
		return upperXBounds;
	}
	
	public int getLowerYBounds() {
		return lowerYBounds;
	}
	
	public int getUpperYBounds() {
		return upperYBounds;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	// Custom usernames 
	public String getPlayerName() {
		return String.format("Player %d", playerNo);
	}

	// Declare this player as the winner of the round 
	public void setWinner() {
		isWinner = true;
	}
	
	// Check whether or not this player is dead 
	public boolean isDead() {
		return isDead;
	}
	
	// Kill this player 
	// Removes the warlord (base) from the screen 
	public void setDead() {
		isDead = true;
		super.setXPos(1500);
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	// To detect collision with warlord 
	public Node getWarlordRect() {
		Rectangle rect = new Rectangle(super.getXPos(), super.getYPos(), width, height);
		return rect;
	}
	
	// Play SFX when warlord dies 
	public void playWarlordDead() {
		warlordDead.setRate(0.5);
		warlordDead.play(5.0);
	}
	
	// Get the number of bricks the warlord has remaininig 
	// Used for determining winner when game ends by time-out 
	public int getBricksAlive() {
		return bricksAlive;
	}
	
	// Remove a brick 
	public void destroyBrick() {
		bricksAlive -= 1;
	}
}
