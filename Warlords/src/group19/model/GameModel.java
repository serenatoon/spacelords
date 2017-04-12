package group19.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;


//This class consolidates all the models by calling them and setting their constructors. ball, brick(wall) and paddle
//are initialised with their x/y positions, and warlord1 and warlord2 are initialised with their constructors and 
//the player number.
public class GameModel {
	private BallModel ball; 
	private BallModel extraBall;
	private int ballCount;
    private ArrayList<BrickModel> brickList; 
	private WarlordModel warlord1;
	private WarlordModel warlord2;
	private WarlordModel warlord3;
	private WarlordModel warlord4;
	private int warlordsAlive;
	private static WarlordModel winner;
	private PaddleModel paddle1;
	private PaddleModel paddle2;
	private PaddleModel paddle3;
	private PaddleModel paddle4;
	private ArrayList<PaddleModel> paddleList = new ArrayList<PaddleModel>();
	private ArrayList<WarlordModel> warlordList = new ArrayList<WarlordModel>();
	private final DoubleProperty remainingTime = new SimpleDoubleProperty(0);
	private final DoubleProperty countdownTime = new SimpleDoubleProperty(3.5);
	private PowerUpModel powerup;

	
	public GameModel() {
		ball = new BallModel(300, 300);
		extraBall = new BallModel(1500, 1500); // create extra ball off-screen first 
		ballCount = 1; // start game with 1 ball
		
        brickList = new ArrayList<BrickModel>();

        
        // Create warlord, add them to arraylist 
		// Rectangle constructor creates the rectangle with the top-left corner at the x,y co-ordinates we give it 
		// thus the edges-120
		// add +/- 128 for HUD 
		// add +/- 10 so that warlord is not in the direct corner
        warlord1 = new WarlordModel(128+10, 0+10, 1);
        warlordList.add(warlord1);
        warlord2 = new WarlordModel(1024-120-128-10, 0+10, 2);
        warlordList.add(warlord2); 
        warlord3 = new WarlordModel(128+10,768-120-10, 3);
        warlordList.add(warlord3);
        warlord4 = new WarlordModel(1024-120-128-10, 768-120-10, 4);
        warlordList.add(warlord4);
        warlordsAlive = 4;
        
		ListIterator<WarlordModel> i = warlordList.listIterator();
		while (i.hasNext()) {
			initBricks(i.next());
		}
		
		// create paddle, add them to arraylist 
		paddle1 = new PaddleModel(128, 255, warlord1);
		paddleList.add(paddle1);
		paddle2 = new PaddleModel(1024-128-40, 275, warlord2); // x = window width - HUD - paddle length, y = radius of curved path
		paddleList.add(paddle2);
		paddle3 = new PaddleModel(128, 768-280, warlord3);
		paddleList.add(paddle3);
		paddle4 = new PaddleModel(1024-128-40, 768-290, warlord4);
		paddleList.add(paddle4);
		
		remainingTime.set(120); // each game should start with 120 seconds remaining on the clock 
		powerup = new PowerUpModel(1500, 1500, ThreadLocalRandom.current().nextInt(1,3)); // first create the power-up, off-screen 
	}
	
	public BallModel getBall() {
		return ball;
	}
	
	public BallModel getExtraBall() {
		return extraBall;
	}
	
    
	// Initialise bricks for all players 
    public void initBricks(WarlordModel warlord) {
    	// INIT THE COLUMNS OF BRICKS (next to the warlord) 
    	for (int y = warlord.getLowerYBounds(); y < warlord.getUpperYBounds()-20; y += 20) {
    		if (warlord.getPlayerNo() % 2 != 0) { // players on the left 
	    		for (int x = warlord.getLowerXBounds()+120; x < warlord.getUpperXBounds()-20; x += 20) {
	    			brickList.add(new BrickModel(x, y, warlord)); 
	    		}
    		}
    		else { // players on the right 
    			for (int x = warlord.getLowerXBounds(); x < warlord.getUpperXBounds()-120-20; x += 20) {
	    			brickList.add(new BrickModel(x, y, warlord));  
	    		}
    		}
    	}	
    	
    	// INIT BRICKS BELOW/ABOVE WARLORD 
    	if (warlord.getPlayerNo() <= 2) { // players on the top 
	    	for (int y = warlord.getLowerYBounds()+120; y < warlord.getUpperYBounds()-20; y += 20) {
	    		if (warlord.getPlayerNo() == 1) {
		    		for (int x = warlord.getLowerXBounds(); x < warlord.getUpperXBounds()-20-60; x += 20) {
		    			brickList.add(new BrickModel(x, y, warlord)); 
		    		}
		    	}
	    		else {
	    			for (int x = warlord.getLowerXBounds()+60; x < warlord.getUpperXBounds()-20; x += 20) {
		    			brickList.add(new BrickModel(x, y, warlord)); 
		    		}
	    		}
	    	}
    	}
    	else { // players on the bottom 
    		for (int y = warlord.getLowerYBounds(); y < warlord.getUpperYBounds()-120-20; y += 20) {
    			if (warlord.getPlayerNo() == 3) { 
	    			for (int x = warlord.getLowerXBounds(); x < warlord.getUpperXBounds()-20-60; x += 20) {
		    			brickList.add(new BrickModel(x, y, warlord)); 
		    		}
    			}
    			else {
    				for (int x = warlord.getLowerXBounds()+60; x < warlord.getUpperXBounds()-20; x += 20) {
		    			brickList.add(new BrickModel(x, y, warlord)); 
		    		}
    			}
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
	
	public ArrayList<WarlordModel> getWarlordList() {
		return warlordList;
	}
	
	public int getWarlordsAlive() {
		return warlordsAlive;
	}
	
	// method is called when a warlord is killed 
	public void killWarlord() {
		warlordsAlive -= 1;
	}
	
	// called in each tick of gameloop, returns whether or not the game has a winner and subsequently sets winner of game  
	public boolean setWinner() {
		if (warlordsAlive == 1) { // if there is only one remaining warlord 
			for (int i = 0; i < 4; i++) {
				if (!warlordList.get(i).isDead()) { // iterate until we find a warlord that isn't dead 
					warlordList.get(i).setWinner(); // set that warlord as winner 
					winner = warlordList.get(i);
					return true; // return true that there is a winner 
				}
			}
		}  
		else if (remainingTime.intValue() <= 0) { // when game finishes by time-out, winner is determined by whomever has the most bricks left  
			WarlordModel tempWinner = null;
			int i = 0;
			for (i = 0; i < 4; i++) {
				if (!warlordList.get(i).isDead()) { // traverse list, find first alive warlord
					tempWinner = warlordList.get(i);
					break; // leave loop once we have found the first alive warlord 
				}
			}
			for (int j = i; j < 4; j++) {
				if (!warlordList.get(j).isDead()) { // traverse list finding the next alive warlord 
					if (warlordList.get(j).getBricksAlive() > tempWinner.getBricksAlive()) { // compare number of bricks 
						tempWinner = warlordList.get(j); // if this warlord has more bricks, set this as the winner 
					}
				}
			}
			
			// once we have traversed the list and compared all the warlords' number of bricks
			// we can now declare the winner 
			tempWinner.setWinner();
			winner = tempWinner;
			return true;
		}	
		return false; 
	}
	
	// Return winner of game 
	public static WarlordModel getWinner() {
		return winner;
	}
	
	// Return amount of time remaining in the game.  
	// DoubleProperty so it can be bound to the timer displayed in the HUD 
	public DoubleProperty getTimeRemaining() {
		return remainingTime;
	}
	
	// used to skip to end of time 
	public void skipToEnd() {
		remainingTime.set(0);
		setWinner();
	}
	
	// Timer
	public void decrementTime(DoubleProperty time, float seconds) {
		float currentTime = time.floatValue();
		time.set(currentTime - seconds);
	}
	
	// Return current time of countdown timer (3-2-1 countdown at beginning) 
	public DoubleProperty getCountdownTime() {
		return countdownTime;
	}
	
	// Return current power-up which is in queue 
	public PowerUpModel getPowerUp() {
		return powerup;
	}
	
	// Create a new power-up
	// We are actually reusing the same object
	// Only changing the type 
	public void newPowerUp() {
		if (ballCount == 1) {
			powerup.setType(ThreadLocalRandom.current().nextInt(1,3));
		}
		else {
			powerup.setType(1);
		}
	}
	
	// Spawn a new ball with random velocity in the centre of the screen
	public void addBall() {
		extraBall.setXPos(512);
		extraBall.setYPos(384);
		ballCount++;
	}
	
	// Get number of balls in the playing field 
	public int getBallCount() {
		return ballCount;
	}
}
