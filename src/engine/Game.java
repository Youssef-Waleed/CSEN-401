package engine;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.awt.Point;
import model.world.*;
import model.abilities.*;
import model.effects.Effect;
import model.effects.Embrace;
import model.effects.PowerUp;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;

public class Game {
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private final static int BOARDHEIGHT=5;
	private final static int BOARDWIDTH=5;
	public Game(Player first, Player second) throws Exception
	{
		firstPlayer=first;
		secondPlayer=second;
		availableChampions= new ArrayList<Champion>();
		availableAbilities= new ArrayList<Ability>();
		board = new Object[BOARDHEIGHT][BOARDWIDTH];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		
	}
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	public Player getSecondPlayer() {
		return secondPlayer;
	}
	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}
	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}
	public Object[][] getBoard() {
		return board;
	}
	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}
	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}
	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}
	public int getBoardheight() {
		return BOARDHEIGHT;
	}
	public int getBoardwidth() {
		return BOARDWIDTH;
	}
	private void placeChampions()
	{
		ArrayList<Champion> firstTeam = firstPlayer.getTeam();
		ArrayList<Champion> secondTeam = secondPlayer.getTeam();
		for(int i=0;i<firstTeam.size();i++)
		{
			
				firstTeam.get(i).setLocation(new Point(0,i+1));
				board[0][i+1]=firstTeam.get(i);
			
		}
		for(int i=0;i<secondTeam.size();i++)
		{
			
			secondTeam.get(i).setLocation(new Point(BOARDHEIGHT-1,i+1));
			board[BOARDHEIGHT-1][i+1]=secondTeam.get(i);
		}
	}
	private void placeCovers()
	{
		int x = 0;
		int y = 0;
		for(int i=0;i<5;i++)
		{
			while(x==0&&y==0||x==BOARDWIDTH-1&&y==BOARDHEIGHT-1||x==0&&y==BOARDHEIGHT-1||x==BOARDWIDTH-1||y==0||board[y][x]!=null)
			{
				x=(int)(Math.random()*4);
				y=(int)(Math.random()*4);
			}
			board[y][x]=new Cover(x,y);
		}
	}
	public static void loadAbilities(String filePath) throws Exception 
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String[][] DataBase=new String[45][9];
		String Line=br.readLine();
		for(int i = 0;Line!=null;i++){
		     DataBase[i]=Line.split(",");
		     Line=br.readLine();
		}
		for(int i=0;i<DataBase.length;i++)
		{
			if(DataBase[i][0]==null||DataBase[i][1]==null||DataBase[i][2]==null||DataBase[i][3]==null||DataBase[i][4]==null||DataBase[i][5]==null||DataBase[i][6]==null||DataBase[i][0]=="CC"&&DataBase[i][7]==null)
				break;
			Effect effect=null;
			switch(DataBase[i][7]){
			case"Shield":effect= new Shield(Integer.parseInt(DataBase[i][8]));break; 
			case"PowerUp":effect= new PowerUp(Integer.parseInt(DataBase[i][8]));break;
			case"Shock":effect= new Shock(Integer.parseInt(DataBase[i][8]));break;
			case"Silence":effect= new Silence(Integer.parseInt(DataBase[i][8]));break;
			case"SpeedUp":effect= new SpeedUp(Integer.parseInt(DataBase[i][8]));break;
			case"Stun":effect= new Stun(Integer.parseInt(DataBase[i][8]));break;
			case"Embrace":effect= new Embrace(Integer.parseInt(DataBase[i][8]));break;
			case"Root":effect= new Root(Integer.parseInt(DataBase[i][8]));break;
			}
			switch(DataBase[i][0]){
			case "CC":availableAbilities.add(new CrowdControlAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),effect));break;
			case "HEL":availableAbilities.add(new HealingAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			case "DMG":availableAbilities.add(new DamagingAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			}
		}
	}
	public static void loadChampions(String filePath) throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String[][] DataBase=new String[15][11];
		String Line=br.readLine();
		for(int i = 0;Line!=null;i++){
		     DataBase[i]=Line.split(",");
		     Line=br.readLine();
		}
		for(int i=0;i<DataBase.length;i++)
		{
			if(DataBase[i][0]==null||DataBase[i][1]==null||DataBase[i][2]==null||DataBase[i][3]==null||DataBase[i][4]==null||DataBase[i][5]==null||DataBase[i][6]==null||DataBase[i][7]==null||DataBase[i][8]==null||DataBase[i][9]==null||DataBase[i][10]==null)
				break;
			switch(DataBase[i][0])
			{
			case "A": availableChampions.add(new AntiHero(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][3]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			case "H": availableChampions.add(new Hero(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][3]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			case "V": availableChampions.add(new Villain(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][3]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			}
			int A1 = -1;
			int A2 = -1;
			int A3 = -1;
			for(int j=0;j<availableAbilities.size();j++)
			{
				if(DataBase[i][8].equals(availableAbilities.get(j).getName()))
					A1=j;
				if(DataBase[i][9].equals(availableAbilities.get(j).getName()))
					A2=j;
				if(DataBase[i][10].equals(availableAbilities.get(j).getName()))
					A3=j;
			}
			if(A1 != -1)
				availableChampions.get(i).getAbilities().add(availableAbilities.get(A1));
			if(A2 != -1)
				availableChampions.get(i).getAbilities().add(availableAbilities.get(A2));
			if(A3 != -1)
				availableChampions.get(i).getAbilities().add(availableAbilities.get(A3));
		}
		
	}
	

}