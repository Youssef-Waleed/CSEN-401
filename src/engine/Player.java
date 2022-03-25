package engine;
import java.util.ArrayList;

public class Player {
	private String name;
	private Champion leader;
	private ArrayList<Champion> team;

	Player(String name) {
		this.name = name;
		this.team = new ArrayList<Champion>();
	}

	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}

}
