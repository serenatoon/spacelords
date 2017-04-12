package group19.controller;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

//This class is called on InGameViewController and contains methods that set the game state. This allows for the various different modes
//that the game has, such as single player/multiplayer, etc. It is also in charge of letting the gameLoop know when to pause.
public class GameStateController {
	private int currentState;
	public static final int MENU = 0;
	public static final int GAME_IN_PROGRESS = 1;
	public static final int GAME_COMPLETE = 2;
	public static final int GAME_PAUSED = 3;
	public static int totalGamesPlayed = 0;
	public boolean isSinglePlayer;
    AudioClip gameOver = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_over.wav").toString());
	
	public GameStateController() {
		setGameState(MENU); // initial state is MENU
	}

	public void setGameState(int gameState) { //setters and getters for it
		currentState = gameState;
	}
	public int getCurrentGameState() {
		return currentState;
	}
	public boolean isFinished() { 
		if (currentState == GAME_COMPLETE) {
			return true;
		}
		else return false;
	}
	
	public void setSinglePlayer(boolean b) { //method will be called setSinglePlayer(true) or vice versa
		if (b) {
			isSinglePlayer = true;
		}
		else isSinglePlayer = false;
	}
	public boolean getSinglePlayer() {
		return isSinglePlayer;
	}
	
	public void playGameOver() { //play the sound when the game is over
		gameOver.play();
	}
}
	
