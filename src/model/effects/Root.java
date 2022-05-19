package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {

	public Root( int duration) {
		super("Root", duration, EffectType.DEBUFF);
		
	}
	public void apply(Champion c){
		if(c.getCondition() != Condition.INACTIVE)
			c.setCondition(Condition.ROOTED);
	}
	public void remove(Champion c){				//idk should I change this?
		if(c.getCondition() != Condition.INACTIVE)
			for(Effect e : c.getAppliedEffects())
				if(e instanceof Root){
					c.setCondition(Condition.ROOTED);
					break;
					}
				else
					c.setCondition(Condition.ACTIVE);
		c.getAppliedEffects().remove(this);
	}
}
