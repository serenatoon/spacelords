package group19.controller;

import group19.view.GameMenuView;
import group19.view.InGameView;
import group19.view.PauseView;
import group19.view.WinnerView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.ListIterator;

import group19.model.*;

import javafx.animation.AnimationTimer;


//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models (from 'GameModel') and the view are placed here.
public class InGameViewController {
	static GameModel game;
	// in the process of refactoring all mentions of game.getPaddle() etc to just paddle so code is cleaner 
	static ArrayList<PaddleModel> paddles;
	static ArrayList<BrickModel> bricks;
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
		paddles = game.getPaddleList();
		bricks = game.getBrickList();
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
						KeyEventListener();	//if memory issues arise in future, move this out
					}
					else {
						game.decrementTime(game.getCountdownTime(), (float) (currentTime-lastTick)/1000000000); //do 3 2 1 counter
					}
					
				}
				else if (gsc.getCurrentGameState() == 0) { //if state ever changes to main menu
					GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
					gameLoop.stop();
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
				
		game.getBall().moveBall();
		checkBallCollision(); 

		// keep paddle within bounds for all 4 paddles 
		ListIterator<PaddleModel> iterator = paddles.listIterator();
		while (iterator.hasNext()) {
			checkPaddleBounds(iterator.next());
		}
		
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
		
		if (gsc.getSinglePlayer() == true) {
		view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	      if (key.getCode() == KeyCode.LEFT) {
	  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() - game.getPaddle1().getXVelocity()); // move paddle left
	      }
	      if (key.getCode() == KeyCode.RIGHT) {
	  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() + game.getPaddle1().getXVelocity()); // move paddle right
	      }
	      });
		}
		else { //multiplayer mode on 
			view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { 
			      if (key.getCode() == KeyCode.LEFT) { //p1
			  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() - game.getPaddle1().getXVelocity()); // move paddle left
			      }
			      if (key.getCode() == KeyCode.RIGHT) {
			  		game.getPaddle1().setXPos(game.getPaddle1().getXPos() + game.getPaddle1().getXVelocity()); // move paddle right
			      }
			      if (key.getCode() == KeyCode.A) { //p2 
				  	game.getPaddle2().setXPos(game.getPaddle2().getXPos() - game.getPaddle2().getXVelocity()); // move paddle left
			      }
			      if (key.getCode() == KeyCode.D) {
			    	game.getPaddle2().setXPos(game.getPaddle2().getXPos() + game.getPaddle2().getXVelocity()); // move paddle right
			      }
			      if (key.getCode() == KeyCode.J) { //p3 
				  	game.getPaddle3().setXPos(game.getPaddle3().getXPos() - game.getPaddle3().getXVelocity()); 
			      }
			      if (key.getCode() == KeyCode.L) {
			    	game.getPaddle3().setXPos(game.getPaddle3().getXPos() + game.getPaddle3().getXVelocity()); 
			      }
			      if (key.getCode() == KeyCode.V) { //p4
				  	game.getPaddle4().setXPos(game.getPaddle4().getXPos() - game.getPaddle4().getXVelocity()); 
			      }
			      if (key.getCode() == KeyCode.N) {
			    	game.getPaddle4().setXPos(game.getPaddle4().getXPos() + game.getPaddle4().getXVelocity()); 
			      }
			     
 			      });
		}
	}
	//We don't want this shit to open 60 times a second. Please have mercy on my RAM.
	//This is called in the constructor, so it is not recalled again and again.
	public void OptionsEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (keyR) -> { //we don't want to spam these key events
		      if (keyR.getCode() == KeyCode.ESCAPE) {
		          System.out.println("You pressed ESC, exiting and moving to main menu...");
		          gsc.setGameState(0); //back to menu state (game did not 'complete')
		          GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
		          gameLoop.stop();
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
		    	  //change later to just tick time remaining to 0 
		    	  game.skipToEnd(); //set seconds to 0 
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
		
		// Iterate through all paddles, checking if the ball hits any of them
		// Ensure ball does not travel through paddle, changes direction of ball
		for (int i = 0; i < 4; i++) { 
			if (InGameView.drawBall().intersects(InGameView.drawPaddle(paddles.get(i)).getBoundsInParent())) { 
				if (game.getBall().getYPos() < paddles.get(i).getYPos()) { // if ball is above paddle
					//game.getBall().setYPos(paddles.get(i).getYPos()-game.getBall().getRadius()-10); // I don't know why it's 10. but it works
				}
				else {
					//game.getBall().setYPos(paddles.get(i).getYPos()+game.getBall().getRadius()+10);
				}
				game.getBall().bounceY();
				paddles.get(i).paddleHitSound();
			}
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
		int i = 0;
		for (BrickModel b : bricks) {
			if (InGameView.drawBall().intersects(InGameView.drawBrick(bricks.get(i)).getBoundsInParent())) { 
				if (game.getBall().getYPos() < bricks.get(i).getYPos()) { // if ball is above brick
					game.getBall().setYPos(bricks.get(i).getYPos()-game.getBall().getRadius()-30); // I don't know why it's 10. but it works
				}
				else {
					game.getBall().setYPos(bricks.get(i).getYPos()+game.getBall().getRadius()+30);
				}
		        game.getBall().bounceY();
		        bricks.get(i).destroy(); 
		        game.getWarlord1().addScore();
		        
			}
			i++;
		}
	}
	
	// Makes sure paddle stays within bounds of window 
	// TODO: take in paddle as parameter. since we have 4 paddles now 
	public void checkPaddleBounds(PaddleModel paddle) {
		if ((paddle.getXPos() < 128)) { // hit left wall
			paddle.setXPos(128); // changed from the paddlewidth/2 nonsense since xpos is actually the left edge of the paddle
		}
		
		// hit right wall    
		if ((paddle.getXPos() > 768+128-100)) { // CHANGE '100' ACCORDING TO WIDTH OF PADDLE  
			paddle.setXPos(768+128-100);
		}
		
		// top and bottom might be interesting since the paddle rotates 90 degrees.  the left edge might no longer be the left edge?
		if ((paddle.getYPos() < 0)) { // hit top wall
			paddle.setYPos(0+100);
		}
		
		if ((paddle.getYPos() > 768)) { // hit bottom wall
			paddle.setYPos(768-100);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds
	}
}
