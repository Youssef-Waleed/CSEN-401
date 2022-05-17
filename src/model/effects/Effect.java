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
	
	public abstract void apply(Champion c);

//public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount)
	public abstract void remove(Champion c);
	
}
