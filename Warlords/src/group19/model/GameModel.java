package group19.model;

//This class consolidates all the models by calling them and setting their constructors. ball, brick(wall) and paddle
//are initialised with their x/y positions, and warlord1 and warlord2 are initialised with their constructors and 
//the player number.
public class GameModel {
	private BallModel ball; 
	private BrickModel brick; 
	private PaddleModel paddle; 
	private WarlordModel warlord1;
	private WarlordModel warlord2;
	
	public GameModel() {
		ball = new BallModel(100, 0);
		brick = new BrickModel(200, 200);
		paddle = new PaddleModel(0, 0);
		warlord1 = new WarlordModel(300, 300, 1);
		warlord2 = new WarlordModel(300, 300, 2);
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
