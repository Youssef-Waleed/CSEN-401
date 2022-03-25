package model.effects;



public class Stun extends Effect{

	Stun(String name, int duration, EffectType type) {
		super(name, duration, EffectType.DEBUFF);
	}

}
