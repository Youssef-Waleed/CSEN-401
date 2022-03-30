package model.abilities;

public class Ability {
    private String name;
    private int manaCost;
    private int baseCooldown;
    private int currentCooldown;
    private int castRange;
    private int requiredActionPoints;
    private AreaOfEffect castArea;
    public Ability(String name,int cost, int baseCoolDown, int castRange, AreaOfEffect area ,int required){
    	this.name=name;
    	manaCost=cost;
    	this.baseCooldown=baseCoolDown;
    	currentCooldown=0;
    	this.castRange=castRange;
    	requiredActionPoints=required;
    	castArea=area;
    }
	public int getCurrentCooldown() {
		return currentCooldown;
	}
	public void setCurrentCooldown(int currentCooldown) {
		this.currentCooldown = currentCooldown;
	}
	public String getName() {
		return name;
	}
	public int getManaCost() {
		return manaCost;
	}
	public int getBaseCooldown() {
		return baseCooldown;
	}
	public int getCastRange() {
		return castRange;
	}
	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}
	public AreaOfEffect getCastArea() {
		return castArea;
	}
	/*public String toString()
	{
		return "Name: "+name+"cost: "+manaCost+"Cooldown: "+baseCooldown+"range: "+castRange+"Area: "+castArea+"req: "+requiredActionPoints; 
	}*/
}
