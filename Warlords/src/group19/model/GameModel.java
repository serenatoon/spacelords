package group19.model;

public class GameModel {
	private BallModel ball; 
	private BrickModel brick; 
	private PaddleModel paddle; 
	private WarlordModel warlord; 
	
	public GameModel() {
		ball = new BallModel(0, 0);
		brick = new BrickModel(0, 0);
		paddle = new PaddleModel(0, 0);
		warlord = new WarlordModel(0, 0, 0);
	}
	
	public BallModel getBall() {
		return ball;
	}
	
	public BrickModel getBrick() {
		return brick;
	}
	
	public PaddleModel getPaddle() {
		return paddle;
	}
	
	public WarlordModel getWarlord() {
		return warlord;
	}
}
