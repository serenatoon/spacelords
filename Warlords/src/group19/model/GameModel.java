package group19.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.util.ArrayList;


//This class consolidates all the models by calling them and setting their constructors. ball, brick(wall) and paddle
//are initialised with their x/y positions, and warlord1 and warlord2 are initialised with their constructors and 
//the player number.
public class GameModel {
	private BallModel ball; 
	//private BrickModel brick; 
    private ArrayList<BrickModel> brickList; 
	private WarlordModel warlord1;
	private WarlordModel warlord2;
	private WarlordModel warlord3;
	private WarlordModel warlord4;
	private PaddleModel paddle1;
	private PaddleModel paddle2;
	private PaddleModel paddle3;
	private PaddleModel paddle4;
	private ArrayList<PaddleModel> paddleList = new ArrayList<PaddleModel>();
	private final DoubleProperty remainingTime = new SimpleDoubleProperty(0);
	private final DoubleProperty countdownTime = new SimpleDoubleProperty(3.5);
	
	public GameModel() {
		ball = new BallModel(0, 0);
		//brick = new BrickModel(300, 500);
        brickList = new ArrayList<BrickModel>();
        initBricks();

		// Rectangle constructor creates the rectangle with the top-left corner at the x,y co-ordinates we give it 
		// thus the edges-120
		// add +/- 128 for HUD 
		//add +/- 10 so that warlord is not in the direct corner
		warlord1 = new WarlordModel(128+10, 0+10, 1);
		warlord2 = new WarlordModel(1024-120-128-10, 0+10, 2); 
		warlord3 = new WarlordModel(128+10,768-120-10, 3);
		warlord4 = new WarlordModel(1024-120-128-10, 768-120-10, 4);
		
		// might move creation of paddles into the arraylist creation instead? 
		paddle1 = new PaddleModel(40, 150, warlord1);
		paddleList.add(paddle1);
		paddle2 = new PaddleModel(700, 150, warlord2);
		paddleList.add(paddle2);
		paddle3 = new PaddleModel(180, 768-150, warlord3);
		paddleList.add(paddle3);
		paddle4 = new PaddleModel(700, 768-150, warlord4);
		paddleList.add(paddle4);
		
		remainingTime.set(120);
	}
	
	public BallModel getBall() {
		return ball;
	}
	
//	public BrickModel getBrick() {
//		return brick;
//	}
	
    public void initBricks() {
        // place bricks at periodic x,y co-ordinates
        // note that x,y co-ordinates refer to the top-left corner of the brick 
        for (int y = 128; y <= 120+60; y += 20) { // 20 = width of a brick
            for (int x = 128; x <= 120+60+120; x += 20) {
                brickList.add(new BrickModel(x, y));
            }
        }
    }
    
    public ArrayList<BrickModel> getBrickList() {
        return brickList;
    }
	
	public PaddleModel getPaddle1() {
		return paddle1;
	}
	
	public PaddleModel getPaddle2() {
		return paddle2;
	}

	public PaddleModel getPaddle3() {
		return paddle3;
	}

	public PaddleModel getPaddle4() {
		return paddle4;
	}
	
	public ArrayList<PaddleModel> getPaddleList() {
		return paddleList;
	}
	
	// don't know if need all the warlord getters since they are associated with a paddle 
	public WarlordModel getWarlord1() { 
		return warlord1;
	}
	
	public WarlordModel getWarlord2() {
		return warlord2;
	}
	
	public WarlordModel getWarlord3() {
		return warlord3;
	}
	
	public WarlordModel getWarlord4() {
		return warlord4;
	}
	
	public DoubleProperty getTimeRemaining() {
		return remainingTime;
	}
	
	// used to skip to end of time 
	public void skipToEnd() {
		remainingTime.set(0);
	}
	
	public void decrementTime(DoubleProperty time, float seconds) {
		float currentTime = time.floatValue();
		time.set(currentTime - seconds);
	}
	
	public DoubleProperty getCountdownTime() {
		return countdownTime;
	}
}
