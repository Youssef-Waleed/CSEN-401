package model.effects;

import java.util.ArrayList;

import model.abilities.*;
import model.world.*;


public abstract class Effect implements Cloneable{
	private String name;
	private EffectType type;
	private int duration;

	public Effect(String name, int duration, EffectType type) {
		this.name = name;
		this.type = type;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public EffectType getType() {
		return type;
	}

	@Override
    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
	
	public void apply(Champion c)
	{
		if( this instanceof Disarm){
			c.getAppliedEffects().add(new Disarm(this.duration));
			c.getAbilities().add( new DamagingAbility("Punch",0, 1,1,AreaOfEffect.SINGLETARGET, 1,50));
		}
		else if(this instanceof Dodge){
			c.getAppliedEffects().add(new Dodge(this.duration));
			c.setSpeed(c.getSpeed()+ (int)(c.getSpeed()*0.05) );
		}
				
		else if(this instanceof Embrace){
			c.setCurrentHP(c.getCurrentHP()+ (int)(c.getMaxHP()*0.2) );
			c.setMana(c.getMana()+ (int)(c.getMana()*0.2));
			c.setSpeed(c.getSpeed()+ (int)(c.getSpeed()*0.2) );
			c.setAttackDamage(c.getAttackDamage()+ (int)(c.getAttackDamage()*0.2) );
			c.getAppliedEffects().add(new Embrace(this.duration));
			}	
		else if(this instanceof PowerUp ){
			c.getAppliedEffects().add(new PowerUp(this.duration));
			
			ArrayList<Ability> arr= c.getAbilities();
			for(int i=0; i<arr.size();i++)
				if(arr.get(i) instanceof DamagingAbility)
					((DamagingAbility)(arr.get(i))).setDamageAmount( (int)(1.2*((DamagingAbility)(arr.get(i))).getDamageAmount()));
				else if(arr.get(i) instanceof HealingAbility)
					((HealingAbility)(arr.get(i))).setHealAmount((int)(1.2*((HealingAbility)(arr.get(i))).getHealAmount()));
		}
		else if(this instanceof Root)
		{
		if(c.getCondition() != Condition.INACTIVE)
			c.setCondition(Condition.ROOTED);
			c.getAppliedEffects().add(new Root(this.duration));
		}
		else if(this instanceof Shield){
			c.setSpeed(c.getSpeed()+ (int)(c.getSpeed()*0.02) );
			c.getAppliedEffects().add(new Shield(this.duration));
		}
		else if(this instanceof Shock){
			c.setSpeed(c.getSpeed()- (int)(c.getSpeed()*0.1) );
			c.setAttackDamage(c.getAttackDamage()- (int)(c.getAttackDamage()*0.1));
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
			c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
			c.getAppliedEffects().add(new Shock(this.duration));
		}
			
		else if(this instanceof Silence){
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+2);
			c.setCurrentActionPoints(c.getCurrentActionPoints()+2);
			c.getAppliedEffects().add(new Silence(this.duration));
		}
		else if(this instanceof SpeedUp){
			c.setSpeed(c.getSpeed()- (int)(c.getSpeed()*0.15) );
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
			c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
			c.getAppliedEffects().add(new SpeedUp(this.duration));
		}
		else if(this instanceof Stun){
			
			c.setCondition(Condition.INACTIVE);
			c.getAppliedEffects().add(new Stun(this.duration));
		}
			
		
	}
//public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount)
	public void remove(Champion c){
		if( this instanceof Disarm){
			c.getAppliedEffects().remove(this);
			ArrayList<Ability> arr= c.getAbilities();
			for(int i=0; i<arr.size();i++)
				if(arr.get(i).getName().equals("Punch"))
					arr.remove(i);
		}
		else if(this instanceof Dodge){
			c.getAppliedEffects().remove(this);
			c.setSpeed((int)(c.getSpeed()/1.05) );
		}
				
		else if(this instanceof Embrace){
			c.setSpeed( (int)(c.getSpeed()/1.2) );
			c.setAttackDamage((int)(c.getAttackDamage()/1.2) );
			c.getAppliedEffects().remove(this);
			}	
		else if(this instanceof PowerUp ){
			c.getAppliedEffects().remove(this);
			
			ArrayList<Ability> arr= c.getAbilities();
			for(int i=0; i<arr.size();i++)
				if(arr.get(i) instanceof DamagingAbility)
					((DamagingAbility)(arr.get(i))).setDamageAmount( (int)(((DamagingAbility)(arr.get(i))).getDamageAmount()/1.2));
				else if(arr.get(i) instanceof HealingAbility)
					((HealingAbility)(arr.get(i))).setHealAmount((int)(((HealingAbility)(arr.get(i))).getHealAmount()/1.2));
		}
		else if(this instanceof Root)
		{
		if(c.getCondition() != Condition.INACTIVE)
			c.setCondition(Condition.ACTIVE);
			c.getAppliedEffects().remove(this);
		}
		else if(this instanceof Shield){
			c.setSpeed((int)(c.getSpeed()/1.02) );
			c.getAppliedEffects().remove(this);
		}
		else if(this instanceof Shock){
			c.setSpeed((int)(c.getSpeed()/1.1) );
			c.setAttackDamage((int)(c.getAttackDamage()/1.1));
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()+1);
			c.setCurrentActionPoints(c.getCurrentActionPoints()+1);
			c.getAppliedEffects().remove(this);
		}
			
		else if(this instanceof Silence){
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-2);
			c.setCurrentActionPoints(c.getCurrentActionPoints()-2);
			c.getAppliedEffects().remove(this);
		}
		else if(this instanceof SpeedUp){
			c.setSpeed((int)(c.getSpeed()/1.15) );
			c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn()-1);
			c.setCurrentActionPoints(c.getCurrentActionPoints()-1);
			c.getAppliedEffects().remove(this);
		}
		else if(this instanceof Stun){
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
	
}
