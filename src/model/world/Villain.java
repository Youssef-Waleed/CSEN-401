package model.world;

import java.util.ArrayList;

import model.effects.*;



public class Villain extends Champion {

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException
	{
		for(int i=0;i<targets.size();i++)
		{
			if((targets.get(i).getCurrentHP()/targets.get(i).getMaxHP())<0.3)
				targets.get(i).setCondition(Condition.valueOf("KNOCKEDOUT"));
		}
	
	}
	
}
