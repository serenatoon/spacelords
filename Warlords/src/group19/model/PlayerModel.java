package group19.model;
//This is a new class which stores information about the four players on the board. I have moved some of the methods/variables from WarlordModel
//in here. I think it is a lot better if score/isAI/checkWinner stuff is separated from the physical Warlord, from a design POV.
//e.g. if the warlord instance is removed for some reason (maybe when it dies?) points can't be set anymore for the player. later on when
//we implement
//'ghosts' functionality, players might still get points even when their warlord is destroyed.


//There are four players in a game - player 0, 1, 2 and 3. So create four instances of PlayerModel on the InGameViewController
//Then create four instances of WarlordModel and call the new setPlayer() method to connect it up

//NOTE: COMMENTING WHOLE CLASS OUT COS IT DOESNT WORK WITH HOW SOME OF THESE METHODS ***HAVE*** TO BE IN WARLORD SO IWARLORD INTERFACE PASSES
//ANGRY REACTS ONLY

//public class PlayerModel {
//	private int playerNo;
//	private boolean isDead; 
//	private boolean isWinner;
//	private int score;
//	private boolean isAI;
//	
//	public PlayerModel (int player) { //PlayerModel needs to be declared with a player number
//		playerNo = player;
//		isDead = false;
//		isWinner = false;
//		score = 0;
//		isAI = false;
//	}
//	
//	public void setScore(int score_) {
//		score = score_;
//	}
//	
//	public void addScore() {
//		score += 10; 
//	}
//	
//	public int getScore() {
//		return score;
//	}
//	
//	public void setDead(boolean b) { 
//		isDead = b;
//	}
//	
//	public boolean checkIfDead() { 
//		return isDead; 
//	}
//	
//	public void setWinner(boolean b) {
//		isWinner = b;
//	}
//	
//	public boolean checkIfWinner() {
//		return isWinner;
//	}
//	
//	public void setAI(boolean b) {// returns whether or not player is AI (false if human-controllable player) 
//		isAI = b;
//	}
//}
