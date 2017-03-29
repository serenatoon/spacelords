package group19.controller;

public class GameStateController {
	private boolean isPaused;
	private int currentState;
	private int previousState;
	
	public static final int MENU = 0;
	public static final int GAME_IN_PROGRESS = 1;
	public static final int GAME_COMPLETE = 2;
	public static final int GAME_PAUSED = 3;
	
	public GameStateController() {
		isPaused = false;
		setGameState(MENU); // initial state is MENU
	}

	public void setPaused(boolean b) {
		isPaused = b;
	}
	public void setGameState(int gameState) {
		previousState = currentState;
		currentState = gameState;
	}
	public int getCurrentGameState() {
		return currentState;
	}
	public int getPreviousGameState() {
		return previousState;
	}
	public boolean isFinished() { //test cases require this 
		if (currentState == GAME_COMPLETE) {
			return true;
		}
		else return false;
	}
	
}
	
