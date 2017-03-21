
public class Blocks {
	// placeholder numbers
	private int brick_width = 38; 
	private int brick_height = 7;
	private int paddle_width = 60;
	private int paddle_height = 8;
	private int frame_width = 1024; // 768? 
	private int frame_height = 768;
	private boolean is_ingame = false; // boolean holding game status, true if ingame

	public int getFrameHeight() {
		return frame_height
	}

	public int getFrameWidth() {
		return frame_width
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

	public getGameStatus() {
		return is_ingame;
	}

	public void setInGame() {
		is_ingame = true;
	}
}
