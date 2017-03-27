package group19.model;

public class WarlordModel {
	private int xPos;
	private int yPos;
	private boolean isDead; // might rename, currently same name as method 
	private boolean isWinner;
	private int score;
	private boolean isAI;
	
	//constructor 
	public WarlordModel(int x, int y) {
		xPos = x;
		yPos = y;
		isDead = false;
		isWinner = false;
	}
	
	public boolean isAI() { // returns whether or not player is AI (false if human-controllable player) 
		return isAI;
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
	
	public void setScore(int score_) {
		score = score_;
	}
	
	public void addScore() {
		score += 10; // how many points should you get for destroying 1 brick? 
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isDead() {
		return isDead; 
	}
	
	public void setDead() {
		isDead = true;
	}
	
	public boolean hasWon() {
		return isWinner;
	}
}
