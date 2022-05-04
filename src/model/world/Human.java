package model.world;
import java.util.ArrayList;

public class Human {
	
private int maxHealth;
private int currentHealth;
private ArrayList<Gear> gears;



public Human(int maxHealth) {
	this.maxHealth = maxHealth;
	this.currentHealth= maxHealth;
	gears = new ArrayList<Gear>();
}


public int getMaxHealth() {
	return maxHealth;
}



public void setCurrentHealth(int currentHealth) {
	if (currentHealth < 0) {
		this.currentHealth = 0;
		
	} 
	else if (currentHealth > maxHealth)
		currentHealth = maxHealth;
	else
		currentHealth = currentHealth;
}


public int getCurrentHealth() {
	return currentHealth;
}
public ArrayList<Gear> getGears() {
	return gears;
}




}
