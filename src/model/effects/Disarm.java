package model.effects;

import model.effects.EffectType;

public class Disarm extends Effect {


	public Disarm(String name, int duration) {
		super(name, duration, EffectType.DEBUFF);
	}

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
	}

}
