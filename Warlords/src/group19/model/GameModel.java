package group19.model;

public class GameModel {
	private BallModel ball; 
	private BrickModel brick; 
	private PaddleModel paddle; 
	private WarlordModel warlord1;
	private WarlordModel warlord2;
	
	public GameModel() {
		ball = new BallModel(0, 0);
		brick = new BrickModel(0, 0);
		paddle = new PaddleModel(0, 0);
		warlord1 = new WarlordModel(200, 200, 1);
		warlord2 = new WarlordModel(400, 400, 2);
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
	
	public WarlordModel getWarlord1() {
		return warlord1;
	}
	
	public WarlordModel getWarlord2() {
		return warlord2;
	}
}
