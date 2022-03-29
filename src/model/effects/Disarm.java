package model.effects;

import model.effects.EffectType;

public class Disarm extends Effect {

<<<<<<< HEAD
public class Disarm extends Effect{

	public Disarm(String name, int duration, EffectType type) {
=======
	public Disarm(String name, int duration) {
>>>>>>> adf2ff60061b47e79006516f479c6638956e5cea
		super(name, duration, EffectType.DEBUFF);
	}

	public Disarm(int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
	}

}
