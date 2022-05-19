package model.world;

import java.util.ArrayList;

import model.effects.*;

public class AntiHero extends Champion {

	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}
	
	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException
	{
		
		Stun s = new Stun(2);
		for(int i=0;i<targets.size();i++){
			((Stun)s.clone()).apply(targets.get(i));
			((Champion)(targets.get(i))).getAppliedEffects().add(s);		//added effect to array
		}
	}

	
}
