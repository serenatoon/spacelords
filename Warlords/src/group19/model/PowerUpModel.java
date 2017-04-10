package group19.model;

public class PowerUpModel extends ObjectModel {
	private int type;
	public static final int SPEEDUP_PADDLE = 1;
	public static final int EXTEND_PADDLE = 2;
	public static final int ENLARGE_BALL = 3;
	public static final int SPEEDUP_BALL = 4;
	public static final int EXTRA_LIFE = 5;
	public static final int EXTRA_BALL = 6;
	private int width;
	private int height;
	
	public PowerUpModel(int x, int y, int powerup_type) {
		super(x, y);
		type = powerup_type;
		width = 20;
		height = 20;
	}
	
	public int getPowerUpType() {
		return type;
	}
	
	public void consumePowerUp(PaddleModel paddle, BallModel ball) {
		if (type == SPEEDUP_PADDLE) {
			paddle.setXVelocity(40); // speed up paddle from 20px/frame
		}
		else if (type == EXTEND_PADDLE) {
			paddle.setSize(50, 50); // change paddle to 50x50.  might fk up collisions though :-(    i'm gonna have fun cleaning up these comments later 
		}
		else if (type == ENLARGE_BALL) {
			ball.setRadius(10); // this is probably gonna fk up collisions 
		}
		else if (type == SPEEDUP_BALL) {
			ball.setYVelocity((int) (ball.getYVelocity() * 1.5)); // might make these RNG 
			ball.setXVelocity((int) (ball.getXVelocity() * 1.5));
		}
		else if (type == EXTRA_LIFE) {
			paddle.getWarlord().addLives();
		}
		else if (type == EXTRA_BALL) {
			// TODO launch a new ball 
		}
	}
}
