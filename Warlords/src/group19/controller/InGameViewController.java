package group19.controller;

import group19.view.GameMenuView;
import group19.view.InGameView;
import group19.view.PauseView;
import group19.view.WinnerView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import group19.model.*;

import javafx.animation.AnimationTimer;


//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models (from 'GameModel') and the view are placed here.
public class InGameViewController {
	static GameModel game;
	public static InGameView view;
	public static GameStateController gsc = new GameStateController(); //to control whether the game is complete, at menu, etc.
	public final  Loop gameLoop = new Loop();
	public static WarlordModel attacker; // the last paddle/warlord which the ball bounced off.  used for determining whose score to increment 
	
	// @param: GameModels which it is controlling 
	public InGameViewController(GameModel models) {
		super();
		view = new InGameView(1024,768, models);
		gameLoop.start();
		OptionsEventListener();
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
				if (gsc.getCurrentGameState() == 1) { //if game in progress
					if (game.getCountdownTime().intValue() <= 0) {
						tick();
						view.rootGameLayout.getChildren().get(0).setVisible(false); //first element of rootGameLayout must be countdown, makes countdown node invisible
						game.decrementTime(game.getTimeRemaining(), (float) (currentTime-lastTick)/1000000000); // /1000000000 to convert from ns to s
						System.out.println("time remaining: " + game.getTimeRemaining() + "");
						KeyEventListener();	//if memory issues arise in future, move this out
					}
					else {
						game.decrementTime(game.getCountdownTime(), (float) (currentTime-lastTick)/1000000000);
						System.out.println(game.getCountdownTime());
					}
					
				}
				else if (gsc.getCurrentGameState() == 0) { //if state ever changes to main menu
					GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 	
				}
				
				lastTick = currentTime;
			}
		}
	}
	
	// Advances one frame 
	// Moves the ball according to its velocity 
	// Makes sure paddle and ball stays within its bounds 
	// Checks for collisions and win conditions 
	public void tick() {
		//System.out.println("ball vel:" + game.getBall().getXVelocity() + "");
		game.getBall().moveBall();
		checkBallCollision(); 
		checkPaddleBounds();
			if (isFinished()) {
				gsc.setGameState(2); //game complete state
				WinnerView.showScene();
			} 
		}

	// Check for win conditions 
	public boolean isFinished() {
		return ((game.getTimeRemaining().intValue() <= 0) || (game.getWarlord1().hasWon()) || game.getWarlord2().hasWon());
	}

	/*Listen for key input for paddle to move. if input is true, input is allowed to occur. if input is false, 
	(e.g. gameLoop.stop() was called in pause, then don't listen to key events) */
	public void KeyEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	      if (key.getCode() == KeyCode.LEFT) {
	  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() - game.getPaddle1().getXVelocity()); // move paddle left
	      }
	      else if (key.getCode() == KeyCode.RIGHT) {
	  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() + game.getPaddle1().getXVelocity()); // move paddle right
	      }
	      });
		
	}
	//We don't want this shit to open 60 times a second. Please have mercy on my RAM.
	//This is called in the constructor, so it is not recalled again and again.
	public void OptionsEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (keyR) -> { //we don't want to spam these key events
		      if (keyR.getCode() == KeyCode.ESCAPE) {
		          System.out.println("You pressed ESC, exiting and moving to main menu...");
		          gsc.setGameState(0); //back to menu state (game did not 'complete')
		          GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
		          return; //exit the function without being called til your RAM explodes
		      }
		      if (keyR.getCode() == KeyCode.P) {
		          System.out.println("You pressed pause, popping up pause menu");
		          gsc.setGameState(3); //paused state
		          PauseView.showScene();
		          return; 
		      }
		      if (keyR.getCode() == KeyCode.PAGE_DOWN) {
		    	  System.out.println("Fast forward to winner menu");
		    	  gsc.setGameState(2); //game complete state
		    	  WinnerView.showScene();
		      }
		      if (keyR.getCode() == KeyCode.T) { //FOR DEBUGGING PURPOSES ONLY
		    	  game.decrementTime(game.getTimeRemaining(), 5);
		      }
		});
	}

	
	// Makes sure ball stays within the bounds of the window 
	// Processes whether or not ball has collided with a paddle, brick, or warlord
	// Calls the appropriate methods in those cases (e.g. destroy brick if ball collided with brick) 
	public void checkBallCollision() {
		if (game.getBall().getXPos() < 128) { // left edge	
			game.getBall().setXPos(game.getBall().getRadius() + 128);
			game.getBall().bounceX();
			game.getBall().playWallSound();
		}
		
		if (((game.getBall().getXPos()+game.getBall().getRadius()) > 896)) { // right edge 
			game.getBall().setXPos(896-game.getBall().getRadius());
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
		if (InGameView.drawBall().intersects(InGameView.drawPaddle(game.getPaddle1()).getBoundsInParent())) { 
			if (game.getBall().getYPos() < game.getPaddle1().getYPos()) { // if ball is above paddle
				game.getBall().setYPos(game.getPaddle1().getYPos()-game.getBall().getRadius()-10); // I don't know why it's 10. but it works
			}
			else {
				game.getBall().setYPos(game.getPaddle1().getYPos()+game.getBall().getRadius()+10);
			}
			game.getBall().bounceY();
			game.getPaddle1().paddleHitSound();
		}
		
		// when a warlord dies, the warlord who killed him should be the winner (or have score incremented)
		// therefore we must note down the the last paddle (and thus warlord) that the ball bounced off
		// store in a variable? 
		
		// Check for collision with warlord
		// This kills the warlord
		// TODO: update drawWarlord, do this for all 4 warlords...
		/*
		if (InGameView.drawBall().intersects(InGameView.drawWarlord1().getBoundsInParent())) { 
			game.getWarlord1().setDead();
			game.getWarlord2().setWinner();
			game.getWarlord(1).playWarlordSound();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawWarlord2().getBoundsInParent())) { 
			game.getWarlord2().setDead();
			game.getWarlord1().setWinner();
		}*/
		
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
	// TODO: take in paddle as parameter. since we have 4 paddles now 
	public void checkPaddleBounds() {
		if ((game.getPaddle1().getXPos() < 128)) { // hit left wall
			game.getPaddle1().setXPos(128); // changed from the paddlewidth/2 nonsense since xpos is actually the left edge of the paddle
		}
		
		if ((game.getPaddle1().getXPos() > 896)) { // hit right wall
			game.getPaddle1().setXPos(896);
		}
		
		// top and bottom might be interesting since the paddle rotates 90 degrees.  the left edge might no longer be the left edge?
		if ((game.getPaddle1().getYPos() < 0)) { // hit top wall
			game.getPaddle1().setYPos(0);
		}
		
		if ((game.getPaddle1().getYPos() > 768)) { // hit bottom wall
			game.getPaddle1().setYPos(768);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds
		
		// really inefficient rn i just wanted to see if it works.  spoiler alert it doesnt 
		if (game.getPaddle1().getXPos() > game.getWarlord1().getUpperXBounds()) {
			InGameView.drawPaddle(game.getPaddle1()).getTransforms().add(new Rotate(90));
			//System.out.println("rotating paddle");
		}
	}
}
