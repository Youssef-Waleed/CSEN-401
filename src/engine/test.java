package engine;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.effects.*;
import model.effects.*;
import model.abilities.*;
import model.effects.EffectType;

public class test {
	public static void main(String[]args) throws IOException
	{
		//System.out.println((int)(Math.random()*5));
		BufferedReader br= new BufferedReader(new FileReader("Abilities.csv"));
		String[][] DataBase=new String[45][8];
		String Line=br.readLine();
		for(int i = 0;Line!=null;i++){
		     DataBase[i]=Line.split(",");
		     Line=br.readLine();
		}
		ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
		for(int i=0;i<DataBase.length;i++)
		{
			Effect effect=null;
			switch(DataBase[i][7]){
			case"Shield":effect= new Shield(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break; 
			case"PowerUp":effect= new PowerUp(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"Shock":effect= new Shock(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"Silence":effect= new Silence(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"SpeedUp":effect= new SpeedUp(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"Stun":effect= new Stun(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"Embrace":effect= new Embrace(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			case"Root":effect= new Root(DataBase[i][7],Integer.parseInt(DataBase[i][8]));break;
			}
			switch(DataBase[i][0]){
			case "CC":availableAbilities.add(new CrowdControlAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),effect));break;
			case "HEL":availableAbilities.add(new HealingAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			case "DMG":availableAbilities.add(new DamagingAbility(DataBase[i][1],Integer.parseInt(DataBase[i][2]),Integer.parseInt(DataBase[i][4]),Integer.parseInt(DataBase[i][3]),AreaOfEffect.valueOf(DataBase[i][5]),Integer.parseInt(DataBase[i][6]),Integer.parseInt(DataBase[i][7])));break;
			}
		}
		for(int i=0;i<availableAbilities.size();i++)
		{
			System.out.println(availableAbilities.get(i));
		}
		
	}

}
