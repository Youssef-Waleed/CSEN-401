package model.effects;

import model.world.Champion;

public class Shock extends Effect {

	public Shock(int duration) {
		super("Shock", duration, EffectType.DEBUFF);
		
	}
	public void apply(Champion c){
		c.setSpeed(c.getSpeed()- (int)(c.getSpeed()*0.1) );
		c.setAttackDamage(c.getAttackDamage()- (int)(c.getAttackDamage()*0.1));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
		c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
		c.getAppliedEffects().add(new Shock(this.getDuration()));
	}
	public void remove(Champion c){
		c.setSpeed((int)(c.getSpeed()/1.1) );
		c.setAttackDamage((int)(c.getAttackDamage()/1.1));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
		c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
		c.getAppliedEffects().remove(this);
	}

}
