package group19.controller;

import group19.testcases.IGame;
import group19.view.InGameView;
import group19.model.*;

public class InGameViewController implements IGame {
	BallModel ball;
	BrickModel brick;
	PaddleModel paddle;
	WarlordModel warlord;
	InGameView view;
	
	public InGameViewController() {
		super();
	}

	@Override
	public void tick() {
		//checkBallCollision();
		//checkPaddleBounds();
		//drawEverything();
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
	//passed in from keylistener in view. 
	// these methods should updates PaddleModel's x position with x position - velocity. These methods are called in tick() 
	public void handlePaddleLeft() {
		System.out.println("left pressed");
	//	paddle.setXPos(paddle.getXPos() - paddle.getXVelocity());
	}
	
	public void handlePaddleRight() {
		System.out.println("right pressed");
	//  paddle.setXPos(paddle.getXPos() + paddle.getXVelocity());
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
