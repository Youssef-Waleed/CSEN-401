package model.effects;


public class Dodge extends Effect{

<<<<<<< HEAD
	public Dodge(String name, int duration, EffectType type) {
=======
	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
	}
	public Dodge(String name, int duration) {
>>>>>>> adf2ff60061b47e79006516f479c6638956e5cea
		super(name, duration, EffectType.BUFF);
	}

}
