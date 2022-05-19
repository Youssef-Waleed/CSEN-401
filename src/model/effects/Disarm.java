package model.effects;

import java.util.ArrayList;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {
	

	public Disarm( int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
		
	}
	public void apply(Champion c){
		c.getAbilities().add( new DamagingAbility("Punch",0, 1,1,AreaOfEffect.SINGLETARGET, 1,50));
	}
	public void remove(Champion c){				//idk should I change this?
		c.getAppliedEffects().remove(this);
		ArrayList<Ability> arr= c.getAbilities();
		for(int i=0; i<arr.size();i++)
			if(arr.get(i).getName().equals("Punch"))
				arr.remove(i);
	}
	
}
