package model.abilities;
import model.effects.*;
public class CrowdControlAbility extends Ability{
	private EffectType effect;
	public CrowdControlAbility(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,int required,EffectType effect){
		super(name,cost,baseCoolDown,castRange,area,required);
		this.effect=effect;
	}
	public EffectType getEffect() {
		return effect;
	}
	

}
