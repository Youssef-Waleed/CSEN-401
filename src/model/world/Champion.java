package model.world;

import java.awt.Point;
import java.util.ArrayList;

import model.world.*;
import model.abilities.Ability;
import model.effects.*;

public abstract class Champion implements Damageable, Comparable{
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
	

	public Champion(String name, int maxHP, int mana, int actions, int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.currentHP = this.maxHP;
		this.maxActionPointsPerTurn = actions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.abilities = new ArrayList<Ability>();
		this.appliedEffects = new ArrayList<Effect>();
		this.currentActionPoints=maxActionPointsPerTurn;
	}
	
	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException
	{
		if(this instanceof Hero){
			Embrace E = new Embrace(2);
			for(int i=0;i<targets.size();i++)
			{
				for(int j=0;j<targets.size();j++)
				    if(targets.get(i).appliedEffects.get(j).getType().equals("DEBUFF")) 
				    	targets.get(i).appliedEffects.remove(targets.get(i));
				
				((Embrace)E.clone()).apply(targets.get(i));
			}
			}
		else if(this instanceof Villain)
			for(int i=0;i<targets.size();i++)
			{
				if((targets.get(i).currentHP/targets.get(i).maxHP)<0.3)
					targets.get(i).setCondition(Condition.valueOf("KNOCKEDOUT"));
			}
		else{
			Stun s = new Stun(2);
			for(int i=0;i<targets.size();i++){
				((Stun)s.clone()).apply(targets.get(i));
			}
		}
	}
	
	public int compareTo(Object other)
	{
		Champion Other = (Champion)other;
		if(speed - Other.speed == 0)
			return name.compareTo(Other.name);
		else
			return Other.speed - speed;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public String getName() {
		return name;
	}

	public void setCurrentHP(int hp) {

		if (hp < 0) {
			currentHP = 0;
			
		} 
		else if (hp > maxHP)
			currentHP = maxHP;
		else
			currentHP = hp;

	}

	
	public int getCurrentHP() {

		return currentHP;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
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

	public void setSpeed(int currentSpeed) {
		if (currentSpeed < 0)
			this.speed = 0;
		else
			this.speed = currentSpeed;
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

	public void setLocation(Point currentLocation) {
		this.location = currentLocation;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if(currentActionPoints>maxActionPointsPerTurn)
			currentActionPoints=maxActionPointsPerTurn;
		else 
			if(currentActionPoints<0)
			currentActionPoints=0;
		this.currentActionPoints = currentActionPoints;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}
	public void updateTimers(){
		for(int i = 0; i < abilities.size(); i++)
			if(abilities.get(i).getCurrentCooldown() != 0)
				abilities.get(i).setCurrentCooldown(abilities.get(i).getCurrentCooldown()-1);
		for(int i = 0; i < appliedEffects.size(); i++){
			appliedEffects.get(i).setDuration(appliedEffects.get(i).getDuration()-1);
			if(appliedEffects.get(i).getDuration() == 0)
				appliedEffects.get(i).remove(this);
		}
		
	}
	
	

}
