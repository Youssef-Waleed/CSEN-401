package model.world;

import java.util.*;
import model.abilities.*;
import model.effects.*;
import java.awt.Point;

//Class
public class Champion {
	private String name;
	private int maxHP;
	private int currentHP;
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

	

	public Champion(String name, int maxHP, int mana, int maxActions,
			int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = (maxHP>=0?maxHP:0);
		this.currentHP=(maxHP>=0?maxHP:0);
		this.mana = mana;
		this.maxActionPointsPerTurn = maxActions;
		this.currentActionPoints= maxActions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		abilities = new ArrayList<>(3);
		appliedEffects = new ArrayList<>();
		condition = Condition.ACTIVE;

	}

	public int getCurrentHP() {
		return currentHP;
	}

public void setCurrentHP(int currentHp) {
	if (currentHp>maxHP)
		return;
	currentHP=(currentHp>=0?currentHp:0);
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

	public int getMaxHP() {
		return maxHP;
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

	public String getName() {
		return name;
	}
}
