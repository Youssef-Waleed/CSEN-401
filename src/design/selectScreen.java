package design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import com.sun.prism.paint.Color;

import engine.Game;
import engine.Player;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Disarm;
import model.effects.Dodge;
import model.effects.Effect;
import model.effects.Embrace;
import model.effects.PowerUp;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

 public class selectScreen implements ActionListener, MouseInputListener, ListSelectionListener  {
	private JTextArea stats1,stats2;
	private ImageIcon icon;
	private JTextField name1,name2;
	private JFrame selectframe;
	private JPanel select;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JList list1 = new JList(); 
	private JList list2 = new JList();
	private JButton startGame, add1, add2;
	private JLabel warning;
	private ArrayList<Champion> temp1,temp2;
	private String[] names1, names2;
	public selectScreen(){
		temp1 = new ArrayList<Champion>();
		temp2 = new ArrayList<Champion>();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		try {
			loadAbilities("Abilities.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			loadChampions("Champions.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icon = new ImageIcon("Marvel_Logo.png");
		selectframe = new JFrame();
		selectframe.setLayout(new BorderLayout());
		selectframe.setVisible(true);
		selectframe.setSize(width+1, height);
	    selectframe.setLocation(1920/2-width/2,0);
	    selectframe.setIconImage(icon.getImage());
		select = new JPanel();
		select.setBounds(0, 0, width, height);
		select.setLayout(null);
		String[] names = new String[20];
		for(int i = 0;i<availableChampions.size();i++)
			names[i]=availableChampions.get(i).getName();
		names1 = names;
		names2 = names;
		list1 = new JList(names); //data has type Object[]
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list1.setVisibleRowCount(-1);
		list1.addListSelectionListener(this);
		JScrollPane listScroller = new JScrollPane(list1);
		//listScroller.setPreferredSize(new Dimension(500, 300));
		listScroller.setBounds(10,120-50, 500, 300);
		list1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		list2 = new JList(names); //data has type Object[]
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list2.setVisibleRowCount(-1);
		list2.addListSelectionListener(this);
		JScrollPane listScroller2 = new JScrollPane(list2);
		//listScroller2.setPreferredSize(new Dimension(500, 300));
		listScroller2.setBounds(520,120-50, 500, 300);
		list2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		stats1 = new JTextArea();
		stats1.setBounds(520+510, 0, 450, 720-300);
		stats1.setText("Select Leader");
		stats1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		select.add(stats1);
		stats2 = new JTextArea();
		stats2.setBounds(520+510+400, 0, 450, 720-300);
		stats2.setText("Select Leader");
		stats2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		select.add(stats2);
		name1 = new JTextField();
		name2 = new JTextField();
		name1.setBounds(10, 10, 500, 50);
		name2.setBounds(520, 10, 500, 50);
		select.add(listScroller2);
		select.add(listScroller);
		startGame = new JButton("Start Game");
		//startGame.setActionCommand("Start game");
		startGame.addActionListener(this);
		startGame.setBounds(width-210, 720-110, 200, 100);
		add1 = new JButton("lock-in champion");
		add1.setBounds(260, 520, 100, 50);
		add1.addActionListener(this);
		add2 = new JButton("lock-in champion");
		add2.setBounds(770, 520, 100, 50);
		add2.addActionListener(this);
		warning = new JLabel();
		warning.setBounds(width-310, 720-220, 300, 100);
		warning.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//		
		//warning.setForeground(Color.green);
		select.add(startGame);
  	    select.add(name1);
		select.add(name2);
		select.add(add1);
		select.add(add2);
		select.add(warning);
		selectframe.add(select);
	}
	
	public static void main(String[]args){
		selectScreen test = new selectScreen();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("HAHAHAHA");
		if(e.getSource()==(startGame))
		{
			boolean alldone = false;
			if(name1.getText()!=null && name2.getText()!=null){
				Player p1 = new Player("name1.getText()");
				Player p2 = new Player("name2.getText()");
				if(alldone)
					System.out.println("tell me why");
					MainGUI m = new MainGUI(new Game(p1,p2));
					//audio.pause();
					selectframe.dispatchEvent(new WindowEvent(selectframe, WindowEvent.WINDOW_CLOSING));
			}
			else
				warning.setText("Please Type a name for each player ya roo7 mother");
				
		}
		
		if(e.getSource()==add1){
			Champion c = availableChampions.get(list1.getSelectedIndex());	
			if(temp1.contains(c))
			{
				temp1.add(c);
			}
			else
				warning.setText("You already have that champion");
		}
		
		if(e.getSource()==add2){
			Champion c = availableChampions.get(list2.getSelectedIndex());
			if(!temp2.contains(c))
			{
				temp2.add(c);
			}
			else
				warning.setText("You already have that champion");
		}
			

	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			if(list1.getSelectedIndex()!=-1){
				Champion c = availableChampions.get(list1.getSelectedIndex());
				//System.out.println(list1.getSelectedIndex());
				stats1.setText("Name: "+c.getName()+'\n'+"HP: "+c.getMaxHP()+'\n'+"Mana: "+c.getMana()+'\n'+"Speed: "+c.getSpeed()+'\n'+"Action Points "+c.getMaxActionPointsPerTurn());
			}
			if(list2.getSelectedIndex()!=-1){
				Champion c = availableChampions.get(list2.getSelectedIndex());
				//System.out.println(list1.getSelectedIndex());
				stats2.setText("Name: "+c.getName()+'\n'+"HP: "+c.getMaxHP()+'\n'+"Mana: "+c.getMana()+'\n'+"Speed: "+c.getSpeed()+'\n'+"Action Points "+c.getMaxActionPointsPerTurn());
			}
		}
		
		
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
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
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
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
