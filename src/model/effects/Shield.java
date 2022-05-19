package model.effects;

import model.world.Champion;

public class Shield extends Effect {

	public Shield( int duration) {
		super("Shield", duration, EffectType.BUFF);
		
	}
	public void apply(Champion c){
		c.setSpeed(c.getSpeed()+ (int)(c.getSpeed()*0.02) );
	}
	public void remove(Champion c){				//idk should I change this?
		c.setSpeed((int)(c.getSpeed()/1.02) );
		c.getAppliedEffects().remove(this);
	}
}
