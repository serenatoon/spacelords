package group19.model;

public class WarlordModel {
	private int xPos;
	private int yPos;
	private boolean isDead; // might rename, currently same name as method 
	private boolean isWinner;
	
	//constructor 
	public WarlordModel(int x, int y) {
		xPos = x;
		yPos = y;
		isDead = false;
		isWinner = false;
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
	
	public boolean isDead() {
		return isDead; 
	}
	
	public boolean hasWon() {
		return isWinner;
	}
}
