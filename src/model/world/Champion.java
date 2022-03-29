package model.world;
import java.util.*;
import model.abilities.*;
import model.effects.*;
import java.awt.Point;

//Class
abstract public class Champion {
private String name;
private int maxHp;
private int currentHp;
private int mana;
private int maxActionPointsPerTurn;
private int currentActionPoints;
private int attackRange;
private int attackDamage;
private int speed;
private ArrayList<Ability> abilities;
private ArrayList<Effect> appliedEffects;
private Condition condition;
private Point location;
	
	public int getCurrentHp() {
	return currentHp;
}

public void setCurrentHp(int currentHp) {
	this.currentHp = currentHp;
}

public int getMana() {
	return mana;
}

public void setMana(int mana) {
	this.mana = mana;
}

public int getMaxActionPointsPerTurn() {
	return maxActionPointsPerTurn;
}

public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
	this.maxActionPointsPerTurn = maxActionPointsPerTurn;
}

public int getCurrentActionPoints() {
	return currentActionPoints;
}

public void setCurrentActionPoints(int currentActionPoints) {
	this.currentActionPoints = currentActionPoints;
}

public int getAttackDamage() {
	return attackDamage;
}

public void setAttackDamage(int attackDamage) {
	this.attackDamage = attackDamage;
}

public int getSpeed() {
	return speed;
}

public void setSpeed(int speed) {
	this.speed = speed;
}

public Condition getCondition() {
	return condition;
}

public void setCondition(Condition condition) {
	this.condition = condition;
}

public Point getLocation() {
	return location;
}

public void setLocation(Point location) {
	this.location = location;
}

public String getName() {
	return name;
}

public int getMaxHp() {
	return maxHp;
}

public int getAttackRange() {
	return attackRange;
}

public ArrayList<Ability> getAbilities() {
	return abilities;
}

public ArrayList<Effect> getAppliedEffects() {
	return appliedEffects;
}

	public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,
			int attackDamage) {
		this.name=name;
		this.maxHp= maxHP;
		this.mana=mana;
		this.maxActionPointsPerTurn=maxActions;
		this.speed= speed;
		this.attackRange=attackRange;
		this.attackDamage=attackDamage;
		abilities = new ArrayList<Ability>();
		appliedEffects = new ArrayList<Effect>();
		condition= Condition.ACTIVE;
		
	}

}
