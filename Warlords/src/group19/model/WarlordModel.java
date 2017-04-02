package group19.model;

import group19.testcases.IWarlord;
//import javafx.beans.property.DoubleProperty;

//Because of the testcase restriction, isWinner and isDead has to be placed here so it can pass the tests
public class WarlordModel extends ObjectModel implements IWarlord { // extends ObjectModel to draw the warlord
	private int playerNo; //could be player 0, player 1, player 2 or player 3
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
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
	}
	
	@Override
	public void setYPos(int y) {
		super.setYPos(y);
	}
		
	public void setPlayer(int player) { //this is a new method - connects player to warlord
		playerNo = player;
	}
	
	/* | 1    2 |
	 * |        |
	 * |        |
	 * | 3    4 |  */ 
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
	
	@Override 
	public boolean hasWon() {
		return isWinner;
	}

	public void setWinner() {
		isWinner = true;
	}
	
	@Override
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
		System.out.println("score: " + score +  "");
	}
	
	public int getScore() {
		return score;
	}

}
