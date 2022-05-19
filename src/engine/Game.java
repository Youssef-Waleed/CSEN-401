package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class Game {
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first, Player second) {
		firstPlayer = first;

		secondPlayer = second;
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
		}

	public static void loadAbilities(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1],
						Integer.parseInt(content[2]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar,
						Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1],
						Integer.parseInt(content[2]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar,
						Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1],
						Integer.parseInt(content[2]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar,
						Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]),
						Integer.parseInt(content[3]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[5]),
						Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]),
						Integer.parseInt(content[3]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[5]),
						Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]),
						Integer.parseInt(content[3]),
						Integer.parseInt(content[4]),
						Integer.parseInt(content[5]),
						Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}

	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}
	public Champion getCurrentChampion(){
		return (Champion) turnOrder.peekMin();
	}
	public Player checkGameOver(){
		boolean cond1 = true;
		for(int i = 0; i < firstPlayer.getTeam().size(); i++){
			if(firstPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT){
				cond1 = false;
				break;
			}
		}
		boolean cond2 = true;
		for(int i = 0; i < secondPlayer.getTeam().size(); i++){
			if(secondPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT){
				cond2 = false;
				break;
			}
		}
		if(cond1)
			return secondPlayer;
		if(cond2)
			return firstPlayer;
		return null;
	}
	public void move(Direction d) throws UnallowedMovementException, NotEnoughResourcesException{
		if(this.getCurrentChampion().getCondition() == Condition.ROOTED)
			throw new UnallowedMovementException("The champion is rooted.");
		
		if(this.getCurrentChampion().getCurrentActionPoints() < 1)
			throw new NotEnoughResourcesException("Not enough action points.");
		Point l = this.getCurrentChampion().getLocation();
		switch(d){
		
		case RIGHT: 
			if(this.getCurrentChampion().getLocation().y < BOARDWIDTH-1){
				if(board[l.x][l.y+1] == null){
					board[l.x][l.y+1] = this.getCurrentChampion();
					board[l.x][l.y] = null;
					this.getCurrentChampion().setLocation(new Point(l.x, l.y+1));
					}
				else
					throw new UnallowedMovementException("This is an invalid movement");
				}
			else
				throw new UnallowedMovementException("This is an invalid movement");
			break;
		
		case LEFT: 
			if(this.getCurrentChampion().getLocation().y > 0){
				if(board[l.x][l.y-1] == null){
					board[l.x][l.y-1] = this.getCurrentChampion();
					board[l.x][l.y] = null;
					this.getCurrentChampion().setLocation(new Point(l.x, l.y-1));
					}
				else
					throw new UnallowedMovementException("This is an invalid movement");
				}
			else
				throw new UnallowedMovementException("This is an invalid movement");
			break;
		
		case DOWN: 
			if(l.x > 0){
				if(board[l.x-1][l.y] == null){
					board[l.x-1][l.y] = this.getCurrentChampion();
					board[l.x][l.y] = null;
					this.getCurrentChampion().setLocation(new Point(l.x-1, l.y));
					}
				else
					throw new UnallowedMovementException("This is an invalid movement");
				}
			else
				throw new UnallowedMovementException("This is an invalid movement");
			break;
		
		case UP: 
			if(l.x < BOARDHEIGHT-1){
					if(board[l.x+1][l.y] == null){
						board[l.x+1][l.y] = this.getCurrentChampion();
						board[l.x][l.y] = null;
						this.getCurrentChampion().setLocation(new Point(l.x+1, l.y));
						}
					else
						throw new UnallowedMovementException("This is an invalid movement");
			}
			else
				throw new UnallowedMovementException("This is an invalid movement");
				
			break;
			}
	this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-1);
	}
	
	public void attack(Direction d) throws ChampionDisarmedException, NotEnoughResourcesException{
		boolean cond = false;
		boolean first = false;
		for(int i = 0; i < this.getCurrentChampion().getAppliedEffects().size(); i++){
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Disarm){
				cond = true;
				break;
			}
		}
		if(cond)
			throw new ChampionDisarmedException("The champion is disarmed.");
		if(this.getCurrentChampion().getCurrentActionPoints() < 2)
			throw new NotEnoughResourcesException("Not enough action points.");
		if(firstPlayer.getTeam().contains(this.getCurrentChampion()))
			first = true;
		Point l = null;
		switch(d){
		case RIGHT:
			for(int i = 1; i <= this.getCurrentChampion().getAttackRange() && this.getCurrentChampion().getLocation().y+i < BOARDWIDTH; i++){
				if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i] != null){
					if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i] instanceof Cover){
						l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y+i);
						break;
					}
					else{
						if(first && secondPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i])){
							l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y+i);
							break;
						}
						else if(!first && firstPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i])){
							l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y+i);
							break;
						}
					}
				}
			}
			break;
		case LEFT:
			for(int i = 1; i <= this.getCurrentChampion().getAttackRange() && this.getCurrentChampion().getLocation().y-i >= 0; i++){
				if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i] != null){
					if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i] instanceof Cover){
						l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y-i);
						break;
					}
					else{
						if(first && secondPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i])){
							l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y-i);
							break;
						}
						else if(!first && firstPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i])){
							l = new Point(this.getCurrentChampion().getLocation().x, this.getCurrentChampion().getLocation().y-i);
							break;
						}
					}
				}
			}
			break;
		case DOWN:
			for(int i = 1; i <= this.getCurrentChampion().getAttackRange() && this.getCurrentChampion().getLocation().x-i >= 0; i++){
				if(board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y] != null){
					if(board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y] instanceof Cover){
						l = new Point(this.getCurrentChampion().getLocation().x-i, this.getCurrentChampion().getLocation().y);
						break;
					}
					else{
						if(first && secondPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y])){
							l = new Point(this.getCurrentChampion().getLocation().x-i, this.getCurrentChampion().getLocation().y);
							break;
						}
						else if(!first && firstPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y])){
							l = new Point(this.getCurrentChampion().getLocation().x-i, this.getCurrentChampion().getLocation().y);
							break;
						}
					}
				}
			}
			break;
		case UP:
			for(int i = 1; i <= this.getCurrentChampion().getAttackRange() && this.getCurrentChampion().getLocation().x+i < BOARDHEIGHT; i++){
				if(board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y] != null){
					if(board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y] instanceof Cover){
						l = new Point(this.getCurrentChampion().getLocation().x+i, this.getCurrentChampion().getLocation().y);
						break;
					}
					else{
						if(first && secondPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y])){
							l = new Point(this.getCurrentChampion().getLocation().x+i, this.getCurrentChampion().getLocation().y);
							break;
						}
						else if(!first && firstPlayer.getTeam().contains(board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y])){
							l = new Point(this.getCurrentChampion().getLocation().x+i, this.getCurrentChampion().getLocation().y);
							break;
						}
					}
				}
			}
			break;
		}
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-2);
		if(l != null){
			Damageable target = null;
			if(board[l.x][l.y] instanceof Cover){
				target = (Cover) board[l.x][l.y];
				target.setCurrentHP((target.getCurrentHP() - this.getCurrentChampion().getAttackDamage()));
				if(target.getCurrentHP() == 0)
					board[l.x][l.y] = null;
				
			}
			else{
				target = (Champion) board[l.x][l.y];
				boolean dodge = false;
				boolean shield = false;
				Effect block = null;
				for(int i = 0; i < ((Champion)target).getAppliedEffects().size(); i++){
					if(((Champion)target).getAppliedEffects().get(i) instanceof Dodge)
						dodge = true;
					else if(((Champion)target).getAppliedEffects().get(i) instanceof Shield){
						shield = true;
						if(block == null)
							block = ((Champion)target).getAppliedEffects().get(i);
					}
				}
				if(dodge)
					if(Math.random() < 0.5){
						return;
					}
				if(shield){
					block.remove(((Champion)target));
					return;
				}
				if(this.getCurrentChampion() instanceof Hero){
					if(target instanceof Hero)
						target.setCurrentHP((target.getCurrentHP() - this.getCurrentChampion().getAttackDamage()));
					else
						target.setCurrentHP( (target.getCurrentHP() - (int)(this.getCurrentChampion().getAttackDamage()*1.5)));
				}
				else if(this.getCurrentChampion() instanceof Villain){
					if(target instanceof Villain)
						target.setCurrentHP((target.getCurrentHP() - this.getCurrentChampion().getAttackDamage()));
					else
						target.setCurrentHP( (target.getCurrentHP() - (int)(this.getCurrentChampion().getAttackDamage()*1.5)));
				}
				else{
					if(target instanceof AntiHero)
						target.setCurrentHP((target.getCurrentHP() - this.getCurrentChampion().getAttackDamage()));
					else
						target.setCurrentHP( (target.getCurrentHP() - (int)(this.getCurrentChampion().getAttackDamage()*1.5)));
				}
			}
			/*if(target.getCurrentHP() == 0){
				board[l.x][l.y] = null;
				((Champion)target).setLocation(null);
				((Champion)target).setCondition(Condition.KNOCKEDOUT);
				
			}*/
		}
		this.checkDeath();
	}
	public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException, CloneNotSupportedException{
		boolean firstLeader = false;
		if(this.getCurrentChampion() != firstPlayer.getLeader() && this.getCurrentChampion() != secondPlayer.getLeader() )
			throw new LeaderNotCurrentException("It is not the leader's turn.");
		else if(this.getCurrentChampion() == firstPlayer.getLeader())
			firstLeader = true;
		ArrayList<Champion> targets = new ArrayList<>();
		Player current = null;
		Player opponent = null;
		boolean abilityUsed = false;
		if(firstLeader){
			current = firstPlayer;
			opponent = secondPlayer;
			abilityUsed = firstLeaderAbilityUsed;
		}
		else{
			current = secondPlayer;
			opponent = firstPlayer;
			abilityUsed = secondLeaderAbilityUsed;
		}
		if(abilityUsed)
			throw new LeaderAbilityAlreadyUsedException("Leader ability already used.");
		else{
			if(this.getCurrentChampion() instanceof Hero){
				for(int i = 0; i < 3; i++){
					if(current.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT)
						targets.add(current.getTeam().get(i));
				}
			}
			else if(this.getCurrentChampion() instanceof Villain){
				for(int i = 0; i < 3; i++){
					if(opponent.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT)
						targets.add(opponent.getTeam().get(i));
				}
			}
			else{
				for(int i = 0; i < firstPlayer.getTeam().size(); i++)
						targets.add(firstPlayer.getTeam().get(i));
				
				for(int i = 0; i < secondPlayer.getTeam().size(); i++)
						targets.add(secondPlayer.getTeam().get(i));
			}
		}
		if(targets != null){
			this.getCurrentChampion().useLeaderAbility(targets);
			if(firstLeader)							//added this if conditional
				firstLeaderAbilityUsed= true;
			else
				secondLeaderAbilityUsed = true;
			}
		this.checkDeath();
	}
	
	public void castAbility(Ability a, Direction d) throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException{
		if(a.getCastArea() != AreaOfEffect.DIRECTIONAL)
			return;
		for(int i=0; i<this.getCurrentChampion().getAppliedEffects().size(); i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Champion is silenced. You can't use the Ability.");
		
		if(this.getCurrentChampion().getMana() < a.getManaCost())
			throw new NotEnoughResourcesException("Not enough mana for casting this Ability.");
		if(this.getCurrentChampion().getCurrentActionPoints() < a.getRequiredActionPoints())
			throw new NotEnoughResourcesException("Not enough Action Points for casting this Ability.");
		if(a.getCurrentCooldown() != 0){
			throw new NotEnoughResourcesException("This ability is on cooldown.");
		}
		
		ArrayList<Damageable> targets = new ArrayList<>();
		
		switch(d){
		
		case RIGHT:
			for(int i = 1; i <= a.getCastRange() && this.getCurrentChampion().getLocation().y+i < BOARDHEIGHT; i++){
				if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i] != null){
					targets.add((Damageable) board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y+i]);
					}
				}
			break;
		
			case LEFT:
				for(int i = 1; i <= a.getCastRange() && this.getCurrentChampion().getLocation().y-i >= 0; i++){
					if(board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i] != null){
						targets.add((Damageable) board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y-i]);
					}
				}
				break;
				
			case DOWN:
				for(int i = 1; i <=  a.getCastRange() && this.getCurrentChampion().getLocation().x-i >= 0; i++){
					if(board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y] != null){
						targets.add((Damageable) board[this.getCurrentChampion().getLocation().x-i][this.getCurrentChampion().getLocation().y]);
					}
				}
				break;
				
			case UP:
				for(int i = 1; i <=  a.getCastRange() && this.getCurrentChampion().getLocation().x+i < BOARDWIDTH; i++){
					if(board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y] != null){
						targets.add((Damageable) board[this.getCurrentChampion().getLocation().x+i][this.getCurrentChampion().getLocation().y]);
					}	
				}
				break;
		}
		Player current = null;
		if(firstPlayer.getTeam().contains(this.getCurrentChampion())){
			current = firstPlayer;
		}
		else{
			current = secondPlayer;
		}
		
		ArrayList<Damageable> allies = new ArrayList<>();
		ArrayList<Damageable> enemies = new ArrayList<>();
		ArrayList<Damageable> covers = new ArrayList<>();
		for(int i= 0; i<targets.size(); i++){
			if(targets.get(i) instanceof Cover)
				covers.add(targets.get(i));	
			else{
				if(current.getTeam().contains(targets.get(i)))
					allies.add(targets.get(i));
				else
					enemies.add(targets.get(i));
			}
		}
		if (targets.size() != 0) {
			if (a instanceof CrowdControlAbility)
				if (((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)
					a.execute(enemies);
				else
					a.execute(allies);

			if (a instanceof DamagingAbility) {
				a.execute(enemies);
				a.execute(covers);
			} else if (a instanceof HealingAbility)
				a.execute(allies);
			for(int i= 0; i<targets.size(); i++){
				if(targets.get(i).getCurrentHP()==0)
				{
					board[targets.get(i).getLocation().x][targets.get(i).getLocation().y] = null;
				    if(targets.get(i) instanceof Champion)
				    {
				    	((Champion)targets.get(i)).setLocation(null);
				    	((Champion)targets.get(i)).setCondition(Condition.KNOCKEDOUT);
				    }
				}
			
			}
		}
		
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana() - a.getManaCost());
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()- a.getRequiredActionPoints());
		a.setCurrentCooldown(a.getBaseCooldown());
		this.checkDeath();
	}
	
	public void castAbility(Ability a, int x, int y) throws AbilityUseException, InvalidTargetException, NotEnoughResourcesException, CloneNotSupportedException{
		if(a.getCastArea() != AreaOfEffect.SINGLETARGET)
			return;
		for(int i=0; i<this.getCurrentChampion().getAppliedEffects().size(); i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Champion is silenced. You can't use the Ability.");
		if(board[x][y]==null)
			throw new InvalidTargetException();
		if((this.getCurrentChampion().getMana()<a.getManaCost()) )
			throw new NotEnoughResourcesException("There is no enough Mana for casting the ability");
		if (this.getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints())
			throw new NotEnoughResourcesException("There is no enough Action Points for casting the ability");
		if(a.getCurrentCooldown() != 0){
			throw new AbilityUseException("This ability is on cooldown.");
		}
		Damageable target = (Damageable)board[x][y];
		boolean first = false;
		if(firstPlayer.getTeam().contains(this.getCurrentChampion()))
			first = true ;
		String targetType;
		if(target instanceof Cover )
			targetType="Cover";
		if(firstPlayer.getTeam().contains(target)&&first || secondPlayer.getTeam().contains(target)&&!first)
			targetType="Ally";
		else
			targetType="Enemy";
		switch(targetType){
		case "Ally":
			if(a instanceof DamagingAbility || (a instanceof CrowdControlAbility && ((CrowdControlAbility)a).getEffect().getType()==EffectType.DEBUFF))
				throw new InvalidTargetException();
			break;
		case "Enemy":
			if(a instanceof HealingAbility || (a instanceof CrowdControlAbility && ((CrowdControlAbility)a).getEffect().getType()==EffectType.BUFF))
				throw new InvalidTargetException();
			break;
		case "Cover":
			if(a instanceof CrowdControlAbility || a instanceof HealingAbility)
				throw new InvalidTargetException();
		}
		int Distance=0;
		Point l = this.getCurrentChampion().getLocation();
		Distance = Math.abs(l.x-x) + Math.abs(l.y-y);
		if(Distance>a.getCastRange())
			throw new AbilityUseException("Target out of range.");
		ArrayList <Damageable>targets = new ArrayList<>();
		targets.add(target);
		a.execute(targets);
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana()-a.getManaCost());
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
		a.setCurrentCooldown(a.getBaseCooldown());
		/*if(target.getCurrentHP() == 0){
			board[l.x][l.y] = null;
			((Champion)target).setLocation(null);
			((Champion)target).setCondition(Condition.KNOCKEDOUT);
		}*/
		this.checkDeath();
	}
	
	public void checkDeath()
	{
		int size = turnOrder.size();
		PriorityQueue temp = new PriorityQueue(size);
		while(!turnOrder.isEmpty())
		{
			if(this.getCurrentChampion().getCurrentHP() == 0 )
			{
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y] = null;
				this.getCurrentChampion().setLocation(null);
				this.getCurrentChampion().setCondition(Condition.KNOCKEDOUT);
				turnOrder.remove();
			}
			else
			{
				temp.insert(turnOrder.remove());
			}
		}
		while(!temp.isEmpty())
			turnOrder.insert(temp.remove());
	}
	
	public void endTurn(){
		do{
			this.getCurrentChampion().updateTimers();
			turnOrder.remove();
			if(turnOrder.isEmpty())
				prepareChampionTurns();
		}while(this.getCurrentChampion().getCondition() == Condition.INACTIVE);
		this.getCurrentChampion().updateTimers();
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getMaxActionPointsPerTurn());
	}
	
	private void prepareChampionTurns(){
		
		for (int i = 0; i < firstPlayer.getTeam().size() && i < secondPlayer.getTeam().size(); i++) {
			if(((Champion)(firstPlayer.getTeam().get(i))).getCondition() != Condition.KNOCKEDOUT)
				turnOrder.insert(firstPlayer.getTeam().get(i));
			if(((Champion)(secondPlayer.getTeam().get(i))).getCondition() != Condition.KNOCKEDOUT)
				turnOrder.insert(secondPlayer.getTeam().get(i));
		}
		
		
	}
	public void castAbility(Ability a) throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException{
		for(int i=0; i<this.getCurrentChampion().getAppliedEffects().size(); i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Champion is silenced. You can't use the Ability.");
		
		if(this.getCurrentChampion().getMana() < a.getManaCost())
			throw new NotEnoughResourcesException("Not enough mana for casting this Ability.");
		if(this.getCurrentChampion().getCurrentActionPoints() < a.getRequiredActionPoints())
			throw new NotEnoughResourcesException("Not enough mana for casting this Ability.");
		if(a.getCurrentCooldown() != 0){
			throw new NotEnoughResourcesException("This ability is on cooldown.");
		}
		ArrayList<Damageable> targets = new ArrayList<>();
		switch(a.getCastArea()){
		case SELFTARGET: 
			targets.add(this.getCurrentChampion());
			break;
		case TEAMTARGET:
			for(Champion c: firstPlayer.getTeam()){
				if(this.getCurrentChampion().manhattaninator(c) <= a.getCastRange())
					targets.add(c);
			}
			for(Champion c: secondPlayer.getTeam()){
				if(this.getCurrentChampion().manhattaninator(c) <= a.getCastRange())
					targets.add(c);
			}
			break;
		case SURROUND:
			int x = this.getCurrentChampion().getLocation().x;
			int y = this.getCurrentChampion().getLocation().y;
			boolean canUp = false;
			boolean canDown = false;
			boolean canLeft = false;
			boolean canRight = false;
			int right = y+1;
			int left = y-1;			//changed those instead of x++
			int down = x-1;
			int up = x+1;
			if(up < BOARDHEIGHT && up >= 0)
				canUp = true;
			if(down < BOARDHEIGHT && down >= 0)
				canDown = true;
			if(right < BOARDWIDTH && right >= 0)
				canRight = true;
			if(left < BOARDWIDTH && left >= 0)
				canLeft = true;
			if(canUp && board[up][y] != null)			//I changed the x with y in IFs and gave relatively less failures
				targets.add((Damageable) board[up][y]);
			if(canDown && board[down][y] != null)
				targets.add((Damageable) board[down][y]);
			if(canLeft && board[x][left] != null)
				targets.add((Damageable) board[x][left]);
			if(canRight && board[x][right] != null)
				targets.add((Damageable) board[x][right]);
			if(canUp && canRight && board[up][right] != null)
				targets.add((Damageable) board[up][right]);
			if(canUp && canLeft && board[up][left] != null)
				targets.add((Damageable) board[up][left]);
			if(canDown && canLeft && board[down][left] != null)
				targets.add((Damageable) board[down][left]);
			if(canDown && canRight && board[down][right] != null)
				targets.add((Damageable) board[down][right]);
			break;
		default: return;
		}
		Player current = null;
		if(firstPlayer.getTeam().contains(this.getCurrentChampion())){
			current = firstPlayer;
		}
		else{
			current = secondPlayer;
		}
		
		ArrayList<Damageable> allies = new ArrayList<>();
		ArrayList<Damageable> enemies = new ArrayList<>();
		ArrayList<Damageable> covers = new ArrayList<>();
		for(int i= 0; i<targets.size(); i++){
			if(targets.get(i) instanceof Cover)
				covers.add(targets.get(i));	
			else{
				if(current.getTeam().contains(targets.get(i)))
					allies.add(targets.get(i));
				else
					enemies.add(targets.get(i));
			}
		}
		if (targets.size() != 0) {
			if (a instanceof CrowdControlAbility)
				if (((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)
					a.execute(enemies);
				else
					a.execute(allies);

			if (a instanceof DamagingAbility) {
				a.execute(enemies);
			} else if (a instanceof HealingAbility)
				a.execute(allies);
		}
		
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana() - a.getManaCost());
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()- a.getRequiredActionPoints());
		a.setCurrentCooldown(a.getBaseCooldown());
		this.checkDeath();
	}
	
	
	
}
