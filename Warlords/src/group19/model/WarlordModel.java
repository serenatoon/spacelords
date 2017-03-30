package group19.model;

import group19.testcases.IWarlord;

//Because of the testcase restriction, isWinner and isDead has to be placed here so it can pass the tests
public class WarlordModel implements IWarlord {
	private int xPos;
	private int yPos;
	private int playerNo; //could be player 0, player 1, player 2 or player 3
	private boolean isWinner;
	private boolean isDead;
	//constructor 
	public WarlordModel(int x, int y) { 
		xPos = x;
		yPos = y;
	}
	

	// x and y setters probably not necessary? since x and y position should only be set once at beginning -- done so in constructor 
	
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
	
	
	public void setPlayer(int player) { //this is a new method - connects player to warlord
		playerNo = player;
	}
	
	public int getPlayer() {
		return playerNo;
	}


	public boolean hasWon() {
		return isWinner;
	}


	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}


	public boolean isDead() {
		return isDead;
	}


}
