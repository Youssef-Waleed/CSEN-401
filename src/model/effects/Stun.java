package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}
	public void apply(Champion c){
		c.setCondition(Condition.INACTIVE);
		c.getAppliedEffects().add(new Stun(this.getDuration()));
	}
	public void remove(Champion c){
		boolean cond = false;
		for(int i = 0; i < c.getAppliedEffects().size(); i++){
			if(c.getAppliedEffects().get(i) instanceof Root){
				cond = true;
				break;
			}
		}
		if(cond)
			c.setCondition(Condition.ROOTED);
		else
			c.setCondition(Condition.ACTIVE);
			c.getAppliedEffects().remove(this);
	}


}
