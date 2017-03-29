package group19.model;
/*TODO: Deprecate this class
 * in favour of putting its functions in their respective classes
 * brick_width can go in brick class, paddle_width in paddle class 
 * Frame methods/variables in separate frame class?  
 * game status stored in another class(??)*/




/*i have removed all mentions of game state and placed in GameStateController*/
/*also not sure what this class does*/


public class Blocks {
	// placeholder numbers
	private int brick_width = 38; 
	private int brick_height = 7;
	private int paddle_width = 60;
	private int paddle_height = 8;
	private int frame_width = 1024; 
	private int frame_height = 768;

	public int getFrameHeight() {
		return frame_height;
	}

	public int getFrameWidth() {
		return frame_width;
	}

	public void setFrameHeight(int new_height) {
		frame_height = new_height;
	}

	public void setFrameWidth(int new_width) {
		frame_width = new_width;
	}

	public int getBrickWidth() {
		return brick_width;
	}

	public void setBrickWidth(int brick_width_) {
		brick_width = brick_width_;
	}

	public int getBrickHeight() {
		return brick_height;
	}

	public int getPaddleWidth() {
		return paddle_width;
	}

	public int getPaddleHeight() {
		return paddle_height;
	}

}
