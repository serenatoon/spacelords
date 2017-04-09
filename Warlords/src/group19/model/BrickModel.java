package group19.model;

import group19.view.GameMenuView;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// One "brick" in a wall, implements the interface IWall
public class BrickModel extends ObjectModel {
	boolean isDestroyed; // boolean holding state of brick 
	private int height;
	private int width;
	private WarlordModel owner;
	private Rectangle rectangle;
	AudioClip brickBreak = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/brick_break.wav").toString());
	// Constructor: Create ball at position (x,y) 
	public BrickModel(int x, int y, WarlordModel owner) {
		super(x, y);
		isDestroyed = false; // initiate brick as not destroyed 
		width = 20;
		height = 20;
		
        // create node to be drawn in view 
        rectangle = new Rectangle(width, height);
        if (owner.getPlayerNo() == 1) {	
        	rectangle.setFill(Color.CYAN);
        }
        else if (owner.getPlayerNo() == 2) {
        	rectangle.setFill(Color.CHARTREUSE);
        }
        else if (owner.getPlayerNo() == 3) {
        	rectangle.setFill(Color.CRIMSON);
        }
        else if (owner.getPlayerNo() == 4) {
        	rectangle.setFill(Color.GOLD);
        }
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1.0);
        rectangle.translateXProperty().bind(super.getXProperty());
        rectangle.translateYProperty().bind(super.getYProperty());
        
		this.owner = owner; // owner of this brick 
	}
	
	// get state of brick, i.e. whether or not it is destroyed
	public boolean isDestroyed() {
		return isDestroyed;
	}

	// destroy brick
	public void destroy() {
		isDestroyed = true;
		brickBreak.play();
		setXPos(1500);
		owner.destroyBrick();
	}
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public WarlordModel getOwner() {
		return owner;
	}
	
    // to detect collision with brick 
    public Node getNode() {
        return rectangle;
    }
}