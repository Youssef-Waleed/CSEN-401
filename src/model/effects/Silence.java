package model.effects;



public class Silence extends Effect{

	Silence(String name, int duration, EffectType type) {
		super(name, duration, EffectType.DEBUFF);
	}

}
