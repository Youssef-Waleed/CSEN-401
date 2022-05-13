package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.effects.Shield;
import model.world.*;

public class DamagingAbility extends Ability {

	private int damageAmount;

	public DamagingAbility(String name, int cost, int baseCoolDown,
			int castRadius, AreaOfEffect area, int required, int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.damageAmount = damageAmount;
	}

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}

	@Override
	public void execute(ArrayList<Damageable> targets) {
		boolean shield= false;
		Effect block = null;
		for (int i = 0; i < targets.size(); i++) {
			if(targets.get(i) instanceof Champion){
				for(int j =0; j<((Champion)targets.get(i)).getAppliedEffects().size(); j++){
					if(((Champion)targets.get(i)).getAppliedEffects().get(i) instanceof Shield ){
						shield=true;
						block = ((Champion)targets.get(i)).getAppliedEffects().get(i);
						break;
					}
				}
				if(shield)
					block.remove((Champion)targets.get(i));
				else
					((Champion)targets.get(i)).setCurrentHP(((Champion)targets.get(i)).getCurrentHP()-this.damageAmount);
			}
			else
				((Cover)targets.get(i)).setCurrentHP(((Cover)targets.get(i)).getCurrentHP()-this.damageAmount);
		}
	}
	

}
