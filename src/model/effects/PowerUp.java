package model.effects;

import java.util.ArrayList;

import model.abilities.*;
import model.world.Champion;

public class PowerUp extends Effect {
	

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
		
	}
	public void apply(Champion c){
		ArrayList<Ability> arr= c.getAbilities();
		for(int i=0; i<arr.size();i++)
			if(arr.get(i) instanceof DamagingAbility)
				((DamagingAbility)(arr.get(i))).setDamageAmount( (int)(1.2*((DamagingAbility)(arr.get(i))).getDamageAmount()));
			else if(arr.get(i) instanceof HealingAbility)
				((HealingAbility)(arr.get(i))).setHealAmount((int)(1.2*((HealingAbility)(arr.get(i))).getHealAmount()));
	}
	public void remove(Champion c){				//idk should I change this?
		c.getAppliedEffects().remove(this);
		
		ArrayList<Ability> arr= c.getAbilities();
		for(int i=0; i<arr.size();i++)
			if(arr.get(i) instanceof DamagingAbility)
				((DamagingAbility)(arr.get(i))).setDamageAmount( (int)(((DamagingAbility)(arr.get(i))).getDamageAmount()/1.2));
			else if(arr.get(i) instanceof HealingAbility)
				((HealingAbility)(arr.get(i))).setHealAmount((int)(((HealingAbility)(arr.get(i))).getHealAmount()/1.2));
	}
}
