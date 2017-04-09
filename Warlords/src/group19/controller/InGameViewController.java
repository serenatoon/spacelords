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
	static ArrayList<WarlordModel> warlords;
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
		warlords = game.getWarlordList();
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
		if (game.setWinner()) {
			gsc.setGameState(2); //game complete state
			WinnerView.showScene();
		} 
	}

	/*Listen for key input for paddle to move. if input is true, input is allowed to occur. if input is false, 
	(e.g. gameLoop.stop() was called in pause, then don't listen to key events) */
	public void KeyEventListener() {
//		ArrayList<Integer> xValues = new ArrayList<Integer>();
//		xValues.add(255); //starting point 
//		for(int i = 1; i < 33; i++){ //specified 32 ticks
//				xValues.add(xValues.get(i-1) + 4);
//		}
//		ArrayList<Integer> yValues = new ArrayList<Integer>();
//		yValues.add(128); //starting point 
//		for(int i = 1; i < 33; i++){ //specified 32 ticks
//				yValues.add(yValues.get(i-1) - 4);
//		}

		if (gsc.getSinglePlayer() == true) {
		view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	      if (key.getCode() == KeyCode.LEFT) {
		    game.getPaddle1().subtractToAngle(1);
		    //System.out.println(game.getPaddle1().getAngle());
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
			      if (key.getCode() == KeyCode.A) { //p2 
				  		game.getPaddle2().subtractToAngle(2);
					  	game.getPaddle2().setXPos(1024-128 - 255*Math.cos(game.getPaddle2().getAngle())); 
						game.getPaddle2().setYPos(0 + 255*Math.sin(game.getPaddle2().getAngle()));
			      }
			      if (key.getCode() == KeyCode.D) {
					    game.getPaddle2().addToAngle(2);
						game.getPaddle2().setXPos(1024-128 - 255*Math.cos(game.getPaddle2().getAngle())); 
				  		game.getPaddle2().setYPos(0 + 255*Math.sin(game.getPaddle2().getAngle()));
			      }
			      if (key.getCode() == KeyCode.J) { //p3 
					    game.getPaddle3().addToAngle(3);
					    System.out.println(game.getPaddle3().getAngle());
					  	game.getPaddle3().setXPos(128 - 255*Math.cos(game.getPaddle3().getAngle())); 
						game.getPaddle3().setYPos(768 + 255*Math.sin(game.getPaddle3().getAngle()));
			      }
			      if (key.getCode() == KeyCode.L) {
				  		game.getPaddle3().subtractToAngle(3);
					    System.out.println(game.getPaddle3().getAngle());
						game.getPaddle3().setXPos(128 - 255*Math.cos(game.getPaddle3().getAngle())); 
				  		game.getPaddle3().setYPos(768 + 255*Math.sin(game.getPaddle3().getAngle()));
			      }
			      if (key.getCode() == KeyCode.V) { //p4
					    game.getPaddle4().addToAngle(4);
					    System.out.println(game.getPaddle4().getAngle());
					  	game.getPaddle4().setXPos(1024-128 - 255*Math.cos(game.getPaddle4().getAngle())); 
						game.getPaddle4().setYPos(768 + 255*Math.sin(game.getPaddle4().getAngle()));
			      }
			      if (key.getCode() == KeyCode.N) {
				  		game.getPaddle4().subtractToAngle(4);
					    System.out.println(game.getPaddle4().getAngle());
						game.getPaddle4().setXPos(1024-128 - 255*Math.cos(game.getPaddle4().getAngle())); 
				  		game.getPaddle4().setYPos(768 + 255*Math.sin(game.getPaddle4().getAngle()));
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
		
		// Check for collision with warlords
		// This kills the warlord	
		int i = 0;
		for (WarlordModel wl : warlords) {
			if ((!warlords.get(i).isDead()) && InGameView.drawBall().intersects(warlords.get(i).getWarlordRect().getBoundsInParent())) {
				warlords.get(i).setDead();
				game.killWarlord();
				// TODO: should ball travel through or bounce off? 
				warlords.get(i).playWarlordDead(); // TODO: sound on warlord dying 
			}
			i++;
		}
		
		// Check for collision with brick
		// Destroy brick
		int j = 0;
		for (BrickModel b : bricks) {
			if (InGameView.drawBall().intersects(InGameView.drawBrick(bricks.get(j)).getBoundsInParent())) { 
				if (game.getBall().getYPos() < bricks.get(j).getYPos()) { // if ball is above brick
					game.getBall().setYPos(bricks.get(j).getYPos()-game.getBall().getRadius()-30); // I don't know why it's 10. but it works
				}
				else {
					game.getBall().setYPos(bricks.get(j).getYPos()+game.getBall().getRadius()+30);
				}
		        game.getBall().bounceY();
		        bricks.get(j).destroy(); 		        
			}
			j++;
		}
	}
}
