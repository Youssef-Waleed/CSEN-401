package model.world;

import java.util.ArrayList;

import model.effects.*;



public class Hero extends Champion {

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException
	{Embrace E = new Embrace(2);
	for(int i=0;i<targets.size();i++)
	{
		int size=targets.get(i).getAppliedEffects().size();
		for(int j=0;j<size&& targets.get(i).getAppliedEffects().size()>1 ;j++)									//changed size to a static variable...we can't traverse a changing array list
		    if(targets.get(i).getAppliedEffects().get(j).getType()== EffectType.DEBUFF){
		    	targets.get(i).getAppliedEffects().get(j--).remove(targets.get(i));
		    	}
		if(!(targets.get(i).getAppliedEffects().isEmpty()) && targets.get(i).getAppliedEffects().get(0).getType()== EffectType.DEBUFF){
	    	targets.get(i).getAppliedEffects().get(0).remove(targets.get(i));
	    	}
		((Embrace)E.clone()).apply(targets.get(i));
		((Champion)(targets.get(i))).getAppliedEffects().add(E);		//added effect to array
	}
	}
	
}
