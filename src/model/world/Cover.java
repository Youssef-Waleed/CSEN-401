package model.world;
import java.awt.Point;

public class Cover {
private int currentHP;
private Point location;


	public int getCurrentHP() {
	return currentHP;
}


public void setCurrentHP(int currentHP) {
	this.currentHP = currentHP;
}


public Point getLocation() {
	return location;
}


	public Cover(int x, int y) {
		this.currentHP= (int)(100+(Math.random())*900);
		location = new Point(x,y);
	}
	
}
