package group19.model;

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
		width = 140;
		height = 140;
		setPaddleBounds();
		score = 0;
	}

	public void setPlayer(int player) { //this is a new method - connects player to warlord
		playerNo = player;
	}
	
	/* Player # positions are as follows: 
	 *				 | 1    2 |
	 * 				 |        |
	 * 			 	 |        |
	 * 			 	 | 3    4 |                         */	
	public void setPaddleBounds() {
		if (playerNo%2 == 1) { // left players, lower x bounds are 0
			lowerXBounds = 0;
			upperXBounds = super.getXPos() + 25; // paddle should not be able to move beyond each player's bounds
		}
		
		if (playerNo%2 == 0) { // right players, upper x bounds are 768
			upperXBounds = 768;
			lowerXBounds = super.getXPos() - 25;
		}
		
		if (playerNo >= 3) { // bottom players
			lowerYBounds = 0;
			upperYBounds = super.getYPos() + 25;
		}
		
		if (playerNo <= 2) { // top players
			upperYBounds = 768;
			lowerYBounds = super.getXPos() - 25;
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
	
	public int getPlayer() {
		return playerNo;
	}
	
	public boolean hasWon() {
		return isWinner;
	}

	public void setWinner() {
		isWinner = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDead() {
		isDead = true;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void addScore() {
		score =+ 10;
	}
	
	public int getScore() {
		return score;
	}

}
