package group19.controller;

import group19.testcases.IGame;
import group19.view.InGameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import group19.model.*;
//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models and the view are placed here.
public class InGameViewController implements IGame {
	BallModel ball;
	BrickModel brick;
	PaddleModel paddle;
	WarlordModel warlord;
	InGameView view;
	public static GameStateController gsc = new GameStateController(); //to control whether the game is complete, at menu, etc.
	
	public InGameViewController() {
		super();
		view = new InGameView(1024,768);
		KeyEventListener();
	}

	@Override
	public void tick() {
		//checkBallCollision();
		//checkPaddleBounds();
		//view.drawEverything();
		//KeyEventListener
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTimeRemaining(int seconds) {
		// TODO Auto-generated method stub
		
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
	  		//	paddle.setXPos(paddle.getXPos() - paddle.getXVelocity());
	      }
	      else if (key.getCode() == KeyCode.RIGHT) {
	  		System.out.println("right pressed");
	  		//  paddle.setXPos(paddle.getXPos() + paddle.getXVelocity());
	      }
	      });	
		}

	
	public void checkBallCollision(BallModel ball) {
		if ((ball.getXPos()-ball.getRadius() <= 0)) { // hit left wall
			ball.setXPos(ball.getRadius());
			ball.bounceX();
		}
		
		if ((ball.getXPos()+ball.getRadius() >= 768)) { // right left wall
			ball.setXPos(768-ball.getRadius());
			ball.bounceX();
		}
		
		if ((ball.getYPos()+ball.getRadius()) >= 768) { // hit top wall 
			ball.setYPos(768-ball.getRadius());
			ball.bounceY();
		}
		
		if ((ball.getYPos()-ball.getRadius()) <= 0) { // hit bottom wall 
			ball.setYPos(ball.getRadius());
			ball.bounceY();
		}
		
		// TODO: when ball intersects() with objects (paddle, bricks) 
		// perhaps put ball collision detection with bricks in another method which when it returns true, destroyBrick() will be called 
	}
	
	public void checkPaddleBounds(PaddleModel paddle) {
		if ((paddle.getXPos()-((paddle.getWidth())/2) <= 0)) { // hit left wall
			paddle.setXPos((paddle.getWidth())/2);
		}
		
		if ((paddle.getXPos()+((paddle.getWidth())/2) >= 768)) { // hit right wall
			paddle.setXPos((paddle.getWidth())/2);
		}
		
		if ((paddle.getYPos()-((paddle.getWidth())/2) <= 0)) { // hit bottom wall
			paddle.setYPos((paddle.getWidth())/2);
		}
		
		if ((paddle.getYPos()+((paddle.getWidth())/2) >= 768)) { // hit top wall
			paddle.setYPos((paddle.getWidth())/2);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds.. which are different for each player :-/ ??? 
	}
}
