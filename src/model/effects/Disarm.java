package model.effects;
import model.effects.EffectType;


public class Disarm extends Effect{

	Disarm(String name, int duration, EffectType type) {
		super(name, duration, EffectType.DEBUFF);
	}

}
