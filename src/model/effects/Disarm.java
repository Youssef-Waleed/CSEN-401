package model.effects;
import model.effects.EffectType;


public class Disarm extends Effect{

	public Disarm(String name, int duration, EffectType type) {
		super(name, duration, EffectType.DEBUFF);
	}

}
