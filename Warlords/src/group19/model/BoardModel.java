package group19.model;
/* Methods and variables of the gameplay board (768x768 square) 
 * not sure if the whole 1024x768 frame should be here? for HUD/GUI purposes 
 * idk if we need this at this point i feel like it's easier if we put all the paddle/ball/bricks in one model class??? */ 

/*no we must separate models. every model is public so they can all be called as an instance on the ingameview*/
/*also I feel this class is quite redundant considering InGameView's constructor will set the size anyway*/
public class BoardModel {
	int width; // 1024 if including HUD? 
	int height;

	//constructor
	public BoardModel(int width_, int height_) {
		width = width_;
		height = height_;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
}
