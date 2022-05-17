package model.effects;

import model.world.Champion;



public class Embrace extends Effect {
	

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}
	public void apply(Champion c){
		c.setCurrentHP(c.getCurrentHP()+ (int)(c.getMaxHP()*0.2) );
		c.setMana(c.getMana()+ (int)(c.getMana()*0.2));
		c.setSpeed(c.getSpeed()+ (int)(c.getSpeed()*0.2) );
		c.setAttackDamage(c.getAttackDamage()+ (int)(c.getAttackDamage()*0.2) );
		c.getAppliedEffects().add(new Embrace(this.getDuration()));
		}
	public void remove(Champion c){
		c.setSpeed( (int)(c.getSpeed()/1.2) );
		c.setAttackDamage((int)(c.getAttackDamage()/1.2) );
		c.getAppliedEffects().remove(this);
		}
	}

