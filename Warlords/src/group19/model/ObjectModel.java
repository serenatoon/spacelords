// Class containing properties for the positions of game objects
// Stored as DoubleProperty to draw the objects
// Getters and setters for positions which are used by all game objects 

package group19.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class ObjectModel {
	
	// DoubleProperty values used to bind with the view 
	private final DoubleProperty xPos = new SimpleDoubleProperty(0);
	private final DoubleProperty yPos = new SimpleDoubleProperty(0);
	
	public ObjectModel(double x, double y) {
		this.xPos.set(x);
		this.yPos.set(y);
	}
	
	public int getXPos() {
		return xPos.intValue();
	}
	
	public void setXPos(double x) { 
		xPos.set(x);
	}
	
	public void setXPos(int x) {  
		xPos.setValue(x);
	}
	
	public DoubleProperty getXProperty() {
		return xPos;
	}
		
	public int getYPos() {
		return yPos.intValue();
	}
	
	public void setYPos(double y) {
		yPos.set(y);
	}
	
	public void setYPos(int y) { 
		yPos.setValue(y);
	}
	
	public DoubleProperty getYProperty() {
		return yPos;
	}
}
