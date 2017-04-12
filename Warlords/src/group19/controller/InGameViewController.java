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
	static ArrayList<PaddleModel> paddles;
	static ArrayList<BrickModel> bricks;
	static ArrayList<WarlordModel> warlords;
	public static InGameView view;
	public static GameStateController gsc = new GameStateController(); // to control whether the game is complete, at menu, etc.
	public final  Loop gameLoop = new Loop();
	public static WarlordModel attacker; // the last paddle/warlord which the ball bounced off.  used for determining whose score to increment
    private boolean gameStarted;
    boolean paddleCollision;
    private int ticksElapsed;

	// @param: GameModels which it is controlling 
	public InGameViewController(GameModel models) {
		super();
		view = new InGameView(1024,768, models);
		gameLoop.start();
		OptionsEventListener();
		game = models;
		paddles = game.getPaddleList();
		bricks = game.getBrickList();
		warlords = game.getWarlordList();
		ticksElapsed = 0;
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
						
                        if (!gameStarted) { // process things that only need to be called once at game start (after countdown) 
                            view.rootGameLayout.getChildren().get(0).setVisible(false); //first element of rootGameLayout must be countdown, makes countdown node invisible
                            KeyEventListener(); // keylistener only needs to be called once, at beginning of game                  		
                    		gameStarted = true;
                    		
                        }
						tick();
						// minus from remaining time 
						game.decrementTime(game.getTimeRemaining(), (float) (currentTime-lastTick)/1000000000); // /1000000000 to convert from ns to s
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
		
		// move AI once every 3 ticks 
		// AI should not move every single tick or else it will be moving too quickly 
		if (gsc.getSinglePlayer() == true) {
			if (ticksElapsed >= 3) {
				moveAI();
				ticksElapsed = 0;
			}
			else {
				ticksElapsed++;
			}
		}
		
		// check that ball doesn't hit edges of screen
		// if ball hits edge, it can't have hit a paddle 
		// don't check for brick collision until it is known that it has not collided with anything else (and is within the bounds of brick collision)  
		if (!checkBallBounds()) {  
			if (!checkPaddleCollision()) { // 
				if (!checkWarlordCollision()) {
					checkBricksThread();
				} 
			}
		}
		if (game.setWinner()) {
			gsc.setGameState(2); //game complete state
			WinnerView.showScene();
		} 
	}


	// perform brick collision checking on a new thread  
	// threading adapted from http://stackoverflow.com/questions/10658696/how-to-use-threads-for-collision-detection-simultaneously-for-different-pair-of
	public void checkBricksThread() {
		Thread collisionThread = new Thread() {
			public void run() {
				checkBrickCollision();
			}
		};
		collisionThread.start();
		try {
			collisionThread.join(); // main thread waits for this thread to finish 
		}
		catch(InterruptedException ie) {
			// do nothing 
		}
	}


	/*Listen for key input for paddle to move. if input is true, input is allowed to occur. if input is false, 
	(e.g. gameLoop.stop() was called in pause, then don't listen to key events) */
	public void KeyEventListener() {
		Thread keyListenerThread = new Thread() { // runs in thread 
			public void run() {
				if (gsc.getSinglePlayer() == true) {
				view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			      if (key.getCode() == KeyCode.LEFT) {
				    game.getPaddle1().subtractToAngle(1);
				  	game.getPaddle1().setXPos(128 - 255*Math.cos(game.getPaddle1().getAngle())); //orbit
					game.getPaddle1().setYPos(0 + 255*Math.sin(game.getPaddle1().getAngle()));
			    	
			      }
			      if (key.getCode() == KeyCode.RIGHT) {
			  		game.getPaddle1().addToAngle(1);
					game.getPaddle1().setXPos(128 - 255*Math.cos(game.getPaddle1().getAngle())); //orbit
			  		game.getPaddle1().setYPos(0 + 255*Math.sin(game.getPaddle1().getAngle()));
			    	
			      }
			      });
				}
				else { //multiplayer mode on 
					view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { 
						if (!(warlords.get(0).isDead())) {
						      if (key.getCode() == KeyCode.LEFT) { //p1
								    game.getPaddle1().subtractToAngle(1);
								  	game.getPaddle1().setXPos(128 - 255*Math.cos(game.getPaddle1().getAngle())); 
									game.getPaddle1().setYPos(0 + 255*Math.sin(game.getPaddle1().getAngle()));
						      }
						      if (key.getCode() == KeyCode.RIGHT) {
							  		game.getPaddle1().addToAngle(1);
									game.getPaddle1().setXPos(128 - 255*Math.cos(game.getPaddle1().getAngle())); 
							  		game.getPaddle1().setYPos(0 + 255*Math.sin(game.getPaddle1().getAngle()));
						      }
						}
						
						if (!(warlords.get(1).isDead())) {
						      if (key.getCode() == KeyCode.A) { //p2 
							  		game.getPaddle2().subtractToAngle(2);
								  	game.getPaddle2().setXPos(1024-128 - 275*Math.cos(game.getPaddle2().getAngle())); 
									game.getPaddle2().setYPos(0 + 275*Math.sin(game.getPaddle2().getAngle()));
						      }
						      if (key.getCode() == KeyCode.D) {
								    game.getPaddle2().addToAngle(2);
									game.getPaddle2().setXPos(1024-128 - 275*Math.cos(game.getPaddle2().getAngle())); 
							  		game.getPaddle2().setYPos(0 + 275*Math.sin(game.getPaddle2().getAngle()));
						      }
						}
						
						if (!(warlords.get(2).isDead())) {
						      if (key.getCode() == KeyCode.J) { //p3 
								    game.getPaddle3().addToAngle(3);
								  	game.getPaddle3().setXPos(128 - 280*Math.cos(game.getPaddle3().getAngle())); 
									game.getPaddle3().setYPos(768 + 280*Math.sin(game.getPaddle3().getAngle()));
						      }
						      if (key.getCode() == KeyCode.L) {
							  		game.getPaddle3().subtractToAngle(3);
									game.getPaddle3().setXPos(128 - 280*Math.cos(game.getPaddle3().getAngle())); 
							  		game.getPaddle3().setYPos(768 + 280*Math.sin(game.getPaddle3().getAngle()));
						      }
						}
						
						if (!(warlords.get(3).isDead())) {
						      if (key.getCode() == KeyCode.V) { //p4
								    game.getPaddle4().addToAngle(4);
								  	game.getPaddle4().setXPos(1024-128 - 290*Math.cos(game.getPaddle4().getAngle())); 
									game.getPaddle4().setYPos(768 + 290*Math.sin(game.getPaddle4().getAngle()));
						      }
						      if (key.getCode() == KeyCode.N) {
							  		game.getPaddle4().subtractToAngle(4);
									game.getPaddle4().setXPos(1024-128 - 290*Math.cos(game.getPaddle4().getAngle())); 
							  		game.getPaddle4().setYPos(768 + 290*Math.sin(game.getPaddle4().getAngle()));
						      }
						}
					     
		 			      });
				}
			}
		};
		keyListenerThread.start();
		try {
			keyListenerThread.join(); // main thread waits for this thread to finish 
		}
		catch(InterruptedException ie) {
				// do nothing  
		}
	}
	
	// Only needs to be called once to start listening for key events 
	// This is called in the constructor, so it is not recalled again and again.
	public void OptionsEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (keyR) -> { 
		      if (keyR.getCode() == KeyCode.ESCAPE) {
		          System.out.println("You pressed ESC, exiting and moving to main menu...");
		          gsc.setGameState(0); //back to menu state (game did not 'complete')
		          GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
		          gameLoop.stop();
		          return; //exit the function
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
		    	  game.skipToEnd(); //set seconds to 0 
		    	  WinnerView.showScene();
		      }
		      if (keyR.getCode() == KeyCode.T) { //FOR DEBUGGING PURPOSES ONLY
		    	  game.decrementTime(game.getTimeRemaining(), 5);
		      }
		});
	}

	public void moveAI() {
		//make other three paddles move (AI)    
		Thread thread = new Thread() { // run on thread 
			public void run() {
		
				int distanceFromPaddle2 = game.getPaddle2().getXPos() - (game.getBall().getXPos()+game.getBall().getXVelocity());
		        int distanceFromPaddle3 = game.getPaddle3().getXPos() - (game.getBall().getXPos()+2*game.getBall().getXVelocity());
		        int distanceFromPaddle4 = game.getPaddle4().getXPos() - (game.getBall().getXPos()+3*game.getBall().getXVelocity());
		        
		        if (!(warlords.get(1).isDead())) {
			        if (distanceFromPaddle2 < 0) { // move right
					    game.getPaddle2().addToAngle(2); 
						game.getPaddle2().setXPos(1024-128 - 275*Math.cos(game.getPaddle2().getAngle())); 
				  		game.getPaddle2().setYPos(0 + 275*Math.sin(game.getPaddle2().getAngle()));
			        }
			        else if (distanceFromPaddle2 > 0) {
				  		game.getPaddle2().subtractToAngle(2); //move left, as with multiplayer code
					  	game.getPaddle2().setXPos(1024-128 - 275*Math.cos(game.getPaddle2().getAngle())); 
						game.getPaddle2().setYPos(0 + 275*Math.sin(game.getPaddle2().getAngle()));
			        }
		        }
		        
		        if (!(warlords.get(2).isDead())) {
			        if (distanceFromPaddle3 < 0) { // move right
			        	game.getPaddle3().subtractToAngle(3);
						game.getPaddle3().setXPos(128 - 280*Math.cos(game.getPaddle3().getAngle())); 
				  		game.getPaddle3().setYPos(768 + 280*Math.sin(game.getPaddle3().getAngle()));
			        }
			        else if (distanceFromPaddle3 > 0) {
			        	 game.getPaddle3().addToAngle(3);
			        	 game.getPaddle3().setXPos(128 - 280*Math.cos(game.getPaddle3().getAngle())); 
			        	 game.getPaddle3().setYPos(768 + 280*Math.sin(game.getPaddle3().getAngle()));
			        }
		        }
		        
		        if (!(warlords.get(3).isDead())) {
			        if (distanceFromPaddle4 < 0) { // move right
				  		game.getPaddle4().subtractToAngle(4);
						game.getPaddle4().setXPos(1024-128 - 290*Math.cos(game.getPaddle4().getAngle())); 
				  		game.getPaddle4().setYPos(768 + 290*Math.sin(game.getPaddle4().getAngle()));
			        }
			        else if (distanceFromPaddle4 > 0) {
			        	game.getPaddle4().addToAngle(4);
					  	game.getPaddle4().setXPos(1024-128 - 290*Math.cos(game.getPaddle4().getAngle())); 
						game.getPaddle4().setYPos(768 + 290*Math.sin(game.getPaddle4().getAngle()));
			        }
		        }
			}
		};
		thread.start();
		try {
			thread.join();
		}
		catch(InterruptedException ie) {
			// do nothing
		}
	}
	
	// Makes sure ball stays within the bounds of the window 
	// Processes whether or not ball has collided with a paddle, brick, or warlord
	// Calls the appropriate methods in those cases (e.g. destroy brick if ball collided with brick) 
	
	// Keeps ball within the playing area, bounce against the window edges 
	public boolean checkBallBounds() {
		if (game.getBall().getXPos() < 128) { // left edge	
			game.getBall().setXPos(game.getBall().getRadius() + 128); // needs to be here or else the ball goes SKRRRT sometimes 
			game.getBall().bounceX();
			game.getBall().playWallSound();
			return true;
		}
		if (((game.getBall().getXPos()+game.getBall().getRadius()) > 896)) { // right edge 
			game.getBall().setXPos(896-game.getBall().getRadius());
			game.getBall().bounceX();
			game.getBall().playWallSound();
			return true;
		}
		if (((game.getBall().getYPos()+game.getBall().getRadius())) > 768) { // bottom edge 
			game.getBall().setYPos(768-game.getBall().getRadius());
			game.getBall().bounceY();
			game.getBall().playWallSound();
			return true;
		}
		
		if ((game.getBall().getYPos()-game.getBall().getRadius()) < 0) { // top edge 
			game.getBall().setYPos(game.getBall().getRadius());
			game.getBall().bounceY();
			game.getBall().playWallSound();
			return true;
		}
		
		return false;
	}
	
	// Iterate through all paddles, checking if the ball hits any of them
	// Ensure ball does not travel through paddle, changes direction of ball
	// Returns a bool of whether or not the ball hit a paddle 
	public boolean checkPaddleCollision() {
			for (int i = 0; i < 4; i++) { 
				if (InGameView.drawBall().intersects(InGameView.drawPaddle(paddles.get(i)).getBoundsInParent())) { 	
					// +20 because in one tick the ball might move PAST the actual edge of the paddle so +20 is midway point of the paddle 
					// instances of +40 is because the x,y co-ordinate is actually the TOP LEFT of the paddle
					// +10 to offset
					if (game.getBall().getYPos() < paddles.get(i).getYPos()+20 // if ball hits top edge of paddle 
							|| game.getBall().getYPos() > paddles.get(i).getYPos()+20) { // or bottom edge of paddle
						if (game.getBall().getYPos() <= paddles.get(i).getYPos()+20) { // if top edge 
							game.getBall().setYPos(paddles.get(i).getYPos()-10); // set Y pos to above paddle so the ball doesn't keep bouncing within paddle 
						}
						else { // if bottom edge 
							game.getBall().setYPos(paddles.get(i).getYPos() + 40 + 10); // set y pos to below 
						}
						game.getBall().bounceY(); // only bounce on Y axis 
					}
					else if (game.getBall().getXPos() < paddles.get(i).getXPos()+20 // if ball hits left edge of paddle 
							|| game.getBall().getXPos() > paddles.get(i).getXPos()+20) { // or right edge of paddle
						if (game.getBall().getXPos() < paddles.get(i).getXPos()+20) { // if left edge 
							game.getBall().setXPos(paddles.get(i).getXPos()-10);
						}
						else { // if right edge 
							game.getBall().setXPos(paddles.get(i).getXPos()+ 40 + 10);
						}
						game.getBall().bounceX(); // only bounce on X axis 
					}
					paddles.get(i).paddleHitSound();
					return true;
				}
			}
			return false;
	}


	// Check for collision with warlords
	// This kills the warlord
	public boolean checkWarlordCollision() {
		for (int i = 0; i < 4; i++) {
			if ((!warlords.get(i).isDead()) && InGameView.drawBall().intersects(warlords.get(i).getWarlordRect().getBoundsInParent())) {
				warlords.get(i).setDead();
				game.killWarlord();
				warlords.get(i).playWarlordDead();
				paddles.get(i).setDead();
				return true;
			}
		}
		return false;
	}
	
	// Loop through every brick which has not yet been destroyed, checking if the ball collides with any of them 
	// Returns a bool as to whether or not a collision was detected 
	public boolean checkBrickCollision() {
		/* | x |        | x |
		 * ----         ----
		 *     ////////
		 *     ////////  <---- within this area, no need to check for brick collisions 
		 *    ////////				         optimise performance 
		 * ----         ----
		 * | x |       | x |
		 */
		if (!(game.getBall().getXPos() > 128+120+70 
				&& game.getBall().getXPos() < 1024-120-70 
				&& game.getBall().getYPos() > 120+70 
				&& game.getBall().getYPos() < 768-120-70)) {
			int j = 0;
			for (BrickModel b : bricks) {
				if (InGameView.drawBall().intersects(bricks.get(j).getNode().getBoundsInParent())) { 
					game.getBall().bounceY(); // bounce on Y axis 
					game.getBall().bounceX(); // bounce on X axis 
			        bricks.get(j).destroy(); 
			        bricks.remove(j); // remove element from arraylist so there are fewer bricks to check -- optimisation  
			        return true;
				}
				j++;
			}
		}
		return false;
	}
}
