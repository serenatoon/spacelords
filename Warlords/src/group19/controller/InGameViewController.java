package group19.controller;

import group19.testcases.IGame;
import group19.view.GameMenuView;
import group19.view.InGameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import group19.model.*;

import javafx.animation.AnimationTimer;


//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models (from 'GameModel') and the view are placed here.
public class InGameViewController implements IGame {
	static GameModel game;
	public static InGameView view;
	public static GameStateController gsc = new GameStateController(); //to control whether the game is complete, at menu, etc.
	public final Loop gameLoop = new Loop();
	private int remainingTime;
	private boolean isFinished;
	
	// @param: GameModels which it is controlling 
	public InGameViewController(GameModel models) {
		super();
		view = new InGameView(1024,768, models);
		remainingTime = 120;
		gameLoop.start();
		game = models;
	}
	
    // Game loop which 'ticks' every 16ms
    // This gives a refresh rate of approximately 60fps 
    // Updates, redraws, polls for key press every 16ms
	public class Loop extends AnimationTimer {
		private long lastTick = 0;		
		@Override
		public void handle(long currentTime) {			
			if (lastTick == 0) { // first frame 
				lastTick = currentTime; 
				return;
			}			
						
			if (currentTime - lastTick >= 16000000) { // ~60fps 
				KeyEventListener();		
				tick();
				lastTick = currentTime;
			}
		}
	}
	
	// Advances one frame 
	// Moves the ball according to its velocity 
	// Makes sure paddle and ball stays within its bounds 
	// Checks for collisions and win conditions 
	@Override
	public void tick() {
		game.getBall().moveBall();
		checkBallCollision(); 
		checkPaddleBounds();
		
		if (isFinished()) {
			if (remainingTime <= 0) {
				game.getWarlord1().setWinner();
			}
			
			//gameLoop.stop(); // stop gameloop once the game has finished 
		} 
	}

	// Check for win conditions 
	@Override
	public boolean isFinished() {
		return ((remainingTime <= 0) || (game.getWarlord1().hasWon()) || game.getWarlord2().hasWon());
	}

	@Override
	public void setTimeRemaining(int seconds) {
		remainingTime = seconds;		
	}
	
	/*Added function for debugging: pressing tab closes the in game window*/
	/*Listen for key input for paddle to move.*/
	public void KeyEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	      if (key.getCode() == KeyCode.TAB) {
	          System.out.println("You pressed TAB, exiting and moving to main menu...");
	          gsc.setGameState(0); //back to menu state (game did not 'complete')
	          GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
	      }
	      else if (key.getCode() == KeyCode.LEFT) {
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() - game.getPaddle().getXVelocity()); // move paddle left
	      }
	      else if (key.getCode() == KeyCode.RIGHT) {
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() + game.getPaddle().getXVelocity()); // move paddle right
	      }
	      });	
	}
	
	// Makes sure ball stays within the bounds of the window 
	// Processes whether or not ball has collided with a paddle, brick, or warlord
	// Calls the appropriate methods in those cases (e.g. destroy brick if ball collided with brick) 
	public void checkBallCollision() {
		if (game.getBall().getXPos() < 0) { // left edge	
			game.getBall().setXPos(game.getBall().getRadius());
			game.getBall().bounceX();
			game.getBall().playWallSound();
		}
		
		if (((game.getBall().getXPos()+game.getBall().getRadius()) > 1024)) { // right edge 
			game.getBall().setXPos(1024-game.getBall().getRadius());
			game.getBall().bounceX();
			game.getBall().playWallSound();
		}
		
		if (((game.getBall().getYPos()+game.getBall().getRadius())) > 768) { // bottom edge 
			game.getBall().setYPos(768-game.getBall().getRadius());
			game.getBall().bounceY();
			game.getBall().playWallSound();
		}
		
		if ((game.getBall().getYPos()-game.getBall().getRadius()) < 0) { // top edge 
			game.getBall().setYPos(game.getBall().getRadius());
			game.getBall().bounceY();
			game.getBall().playWallSound();
		}
		
		// Check for collision with paddle
		// Ensure ball does not travel through paddle, changes direction of ball
		if (InGameView.drawBall().intersects(InGameView.drawPaddle().getBoundsInParent())) { 
			game.getBall().setYPos(game.getPaddle().getYPos());
			game.getBall().bounceY();
			//game.getPaddle().paddleHitSound();
		}
		
		// Check for collision with warlord
		// This kills the warlord
		if (InGameView.drawBall().intersects(InGameView.drawWarlord1().getBoundsInParent())) { 
			game.getWarlord1().setDead();
			game.getWarlord2().setWinner();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawWarlord2().getBoundsInParent())) { 
			game.getWarlord2().setDead();
			game.getWarlord1().setWinner();
		}
		
		// Check for collision with brick
		// Destroy brick
		if (InGameView.drawBall().intersects(InGameView.drawBrick().getBoundsInParent())) { 
			game.getBall().setYPos(game.getBrick().getYPos()-game.getBall().getRadius()-1);
			game.getBall().bounceY();
			game.getBrick().destroy(); // TODO: remove brick from view 
			game.getWarlord1().addScore();
		}
	}
	
	// Makes sure paddle stays within bounds of window 
	public void checkPaddleBounds() {
		if ((game.getPaddle().getXPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit left wall
			game.getPaddle().setXPos(((game.getPaddle().getWidth())/2));
		}
		
		if (((game.getPaddle().getXPos()+((game.getPaddle().getWidth())/2)) > 1024)) { // hit right wall
			game.getPaddle().setXPos((1024 - (game.getPaddle().getWidth())/2));
		}
		
		if ((game.getPaddle().getYPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit top wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		if (((game.getPaddle().getYPos()+((game.getPaddle().getWidth())/2)) > 768)) { // hit bottom wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds
	}
}
