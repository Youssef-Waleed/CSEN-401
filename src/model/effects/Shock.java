package model.effects;



public class Shock extends Effect{

	Shock(String name, int duration, EffectType type) {
		super(name, duration, EffectType.DEBUFF);
	}

}
