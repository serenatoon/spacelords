package group19.controller;

import group19.testcases.IGame;
import group19.view.InGameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import group19.model.*;

import javafx.animation.AnimationTimer;


//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models and the view are placed here.
public class InGameViewController implements IGame {
	/*BallModel game.getBall(); // how does controller know about the game.getBall()s n shit in IGV?? 
	BrickModel brick;
	PaddleModel paddle;
	WarlordModel warlord; // TODO: we need to make multiple warlords... */
	
	/*private static BallModel ball = new BallModel(0, 0);
	private static BrickModel brick = new BrickModel(0, 0);
	private static PaddleModel paddle = new PaddleModel(0, 0);
	private static WarlordModel warlord = new WarlordModel(0, 0, 0);*/
	
	static GameModel game = new GameModel();
	
	InGameView view;
	public static GameStateController gsc = new GameStateController(); //to control whether the game is complete, at menu, etc.
	public final Loop gameLoop = new Loop();
	// idk if these should go here. or if we need a gamemodel lol 
	//private int startTime; 
	private int remainingTime;
	private boolean isFinished;
	
	public InGameViewController() {
		super();
		view = new InGameView(1024,806, game);
		System.out.println("new game");	
		//startTime = 120; // gametime of 2 minutes 
		remainingTime = 120;
		//KeyEventListener();
		gameLoop.start();
	}
	
	
	// game loop doesn't start until I quit game back to main menu, then it starts ticking...
	// previous implementation (see loopy branch) worked as intended, ticks as soon as the game starts.
	// the "single player mode" thing doesn't get printed until you close the game window either... even with my changes commented out 
	// i dont know if it's something to do with getScene() or... 
	public class Loop extends AnimationTimer {
		private long lastTick = 0;
		
		@Override
		public void handle(long currentTime) {
			
			//private long lastTick = 0;
			
			if (lastTick == 0) { // first frame 
				lastTick = currentTime; 
				return;
			}			
			
			
			if (currentTime - lastTick >= 64000000) { // 64ms 
				KeyEventListener();		
				tick();
				lastTick = currentTime;
			}
			
			// TODO: timer actually counts down
			//remainingTime = startTime-(currentTime/1000000000);
			
			
		}
	}
	
	// I feel like we need a whole gamemodel class.  which gets passed into this controller
	// with the methods getBall() etc so i can pass them into the checkCollisions()


	@Override
	public void tick() {
		if (isFinished()) {
			gameLoop.stop();
		} 
		view.drawEverything();
		checkBallCollision(); 
		checkPaddleBounds();

		game.getBall().moveBall();
		//KeyEventListener
		System.out.println(view.getScene().getHeight());		
	}

	@Override
	public boolean isFinished() {
		return ((remainingTime <= 0) || (game.getWarlord().hasWon()));
	}

	// I don't know why you'd need a method for this...
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
	          gsc.setGameState(0);//back to menu state (game did not 'complete')
	          view.getWindow().close();
	      }
	      else if (key.getCode() == KeyCode.LEFT) {
	  		System.out.println("left pressed");
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() - game.getPaddle().getXVelocity());
	      }
	      else if (key.getCode() == KeyCode.RIGHT) {
	  		System.out.println("right pressed");
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() + game.getPaddle().getXVelocity());
	      }
	      });	
	}
	
	

	
	public void checkBallCollision() {
		if (((game.getBall().getXPos())-(game.getBall().getRadius()) < 0)) { // hit left wall
			//game.getBall().setXPos(game.getBall().getRadius());
			game.getBall().bounceX();
		}
		
		if (((game.getBall().getXPos()+game.getBall().getRadius()) > 1024)) { // right left wall
			//game.getBall().setXPos(1024-game.getBall().getRadius());
			game.getBall().bounceX();
		}
		
		if (((game.getBall().getYPos()+game.getBall().getRadius())) > 768) { // hit top wall 
			//game.getBall().setYPos(768-game.getBall().getRadius());
			game.getBall().bounceY();
		}
		
		if ((game.getBall().getYPos()-game.getBall().getRadius()) < 0) { // hit bottom wall 
			//game.getBall().setYPos(game.getBall().getRadius());
			game.getBall().bounceY();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawPaddle().getBoundsInLocal())) { // i feel like this isnt the right way but i need the nodes
			//game.getBall().bounceY();
		}
	}
	
	public void checkPaddleBounds() {
		if ((game.getPaddle().getXPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit left wall
			game.getPaddle().setXPos(((game.getPaddle().getWidth())/2));
		}
		
		if (((game.getPaddle().getXPos()+((game.getPaddle().getWidth())/2)) > 1024)) { // hit right wall
			game.getPaddle().setXPos((1024 - (game.getPaddle().getWidth())/2));
		}
		
		if ((game.getPaddle().getYPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit bottom wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		if (((game.getPaddle().getYPos()+((game.getPaddle().getWidth())/2)) > 768)) { // hit top wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds
	}
}
