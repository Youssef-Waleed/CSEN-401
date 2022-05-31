package design;
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
import model.world.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import engine.Game;

@SuppressWarnings("serial")

public class MainGUI implements ActionListener, MouseInputListener, ListSelectionListener {
	private JFrame gameframe;
	private ImageIcon icon;
	private JTextArea stats1,stats2;
	private JPanel info,main, container, current, actions,game,select;
	private JButton up,down,right,left,attack,castability,useleaderab,startGame 
				/*,b11,b12,b13,b14,b15,
					b21,b22,b23,b24,b25,
					b31,b32,b33,b34,b35,
					b41,b42,b43,b44,b45,
					b51,b52,b53,b54,b55*/;
	private JButton[][] button;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JList list1 = new JList(); 
	private JList list2 = new JList();
	public static void main(String[] args) {
		//MainGUI bla = new MainGUI();

	}
	
	public MainGUI(Game newGame){
//		availableChampions = new ArrayList<Champion>();
//		availableAbilities = new ArrayList<Ability>();
//		try {
//			loadAbilities("Abilities.csv");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			loadChampions("Champions.csv");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	//-----------------------------------------game panel---------------------------------------------------------	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		game = new JPanel();
		game.setBounds(0, 0, width, height);
		game.setLayout(new BorderLayout());
		icon = new ImageIcon("Marvel_Logo.png");
		ImageIcon upicon = new ImageIcon("up-arrow.png");
		ImageIcon downicon = new ImageIcon("down-arrow.png");
		ImageIcon righticon = new ImageIcon("right-arrow.png");
		ImageIcon lefticon = new ImageIcon("left-arrow.png");
		ImageIcon attackicon = new ImageIcon("attack.png");
		ImageIcon casticon = new ImageIcon("ability.png");
		ImageIcon leadicon = new ImageIcon("wizard.png");
		
		gameframe = new JFrame();
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setSize(width+1, height);
		gameframe.setLocation(1920/2-width/2,0);
		gameframe.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		gameframe.setTitle("Marvel: Ultimate War");
		gameframe.setLayout(null);
		gameframe.setIconImage(icon.getImage());
		main = new JPanel();			//the new Panels
		main.setLayout(new GridLayout(5,5));
		info = new JPanel();
		info.setBackground(new Color(0xBB381D));
		info.setPreferredSize(new Dimension(500, 600));
		info.setLayout(new FlowLayout(FlowLayout.LEADING, 50,10));
		container = new JPanel();
		container.setBackground(new Color(0xFFFFFF));
		container.setPreferredSize(new Dimension(600, 200));
		container.setLayout(new BorderLayout());
		current = new JPanel();
		current.setBackground(new Color(0x353535));
		current.setPreferredSize(new Dimension(800, 200));
		current.setLayout(new FlowLayout(FlowLayout.LEADING, 50,10));
		actions = new JPanel();
		actions.setBackground(new Color(0x555510));
		actions.setPreferredSize(new Dimension(500, 350));
		actions.setLayout(null);
		
		game.add(current, BorderLayout.SOUTH);
		game.add(main, BorderLayout.CENTER);
		game.add(container, BorderLayout.EAST);
		container.add(actions, BorderLayout.SOUTH);
		container.add(info, BorderLayout.CENTER);
		gameframe.add(game);
		gameframe.setVisible(true);
	//------------------------------------------------BUTTONS----------------------------------------------------------	
		
		up= new JButton( new ImageIcon(upicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		up.setHorizontalTextPosition(JButton.CENTER);
		up.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		up.setBounds(270,30,75,75);
		
		right= new JButton( new ImageIcon(righticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		right.setHorizontalTextPosition(JButton.CENTER);
		right.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		right.setBounds(360,125,75,75);
		
		left= new JButton( new ImageIcon(lefticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		left.setHorizontalTextPosition(JButton.CENTER);
		left.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		left.setBounds(180,125,75,75);
		
		down= new JButton( new ImageIcon(downicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		down.setHorizontalTextPosition(JButton.CENTER);
		down.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		down.setBounds(270,220,75, 75);
		
		attack= new JButton("ATTACK",new ImageIcon(attackicon.getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH)));
		attack.setHorizontalTextPosition(JButton.CENTER);
		attack.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		attack.setBounds(270,125,75,75);
		
		castability= new JButton("CAST ABILITY", new ImageIcon(casticon.getImage().getScaledInstance(120,80,Image.SCALE_SMOOTH)));
		castability.setVerticalTextPosition(JButton.BOTTOM);
		castability.setHorizontalTextPosition(JButton.CENTER);
		castability.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		castability.setBounds(20,205,150,100);
		
		useleaderab= new JButton("USE LEADER ABILITY", new ImageIcon(leadicon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
		useleaderab.setVerticalTextPosition(JButton.BOTTOM);
		useleaderab.setHorizontalTextPosition(JButton.CENTER);
		useleaderab.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		useleaderab.setBounds(450,205,150,100);
		
		actions.add(up);
		actions.add(down);
		actions.add(right);
		actions.add(left);
		actions.add(castability);
		actions.add(attack);
		actions.add(useleaderab);

		
		up.addActionListener(this);
		down.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		attack.addActionListener(this);
		castability.addActionListener(this);
		useleaderab.addActionListener(this);
		
		
		
		
		button = new JButton[5][5];
		for(int i =0; i<5; i++)
			for(int j =0; j<5; j++)
				button[i][j]= new JButton();			//the new Buttons						


		for(int i =0; i<5; i++)								//button font
			for(int j =0; j<5; j++)
				button[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 16));

//		for(int i =0; i<5; i++)
//			for(int j =0; j<5; j++)
//				button[i][j].setBorder(BorderFactory.);
//		for(int i =0; i<5; i++)
//			for(int j =0; j<5; j++)
//				button[i][j].setBackground(new Color(0xF9F3D3));
		
		for(int i =0; i<5; i++)								//_______________
			for(int j =0; j<5; j++)
				button[i][j].setFocusable(false);

		for(int i =0; i<5; i++)								//_______________
			for(int j =0; j<5; j++)
				button[i][j].addActionListener(this);
		
		for(int i =0; i<5; i++)
			for(int j =0; j<5; j++)
				main.add(button[i][j]);
		
		//---------------------------------------------Character select------------------------
//		select = new JPanel();
//		select.setBounds(0, 0, width, height);
//		select.setLayout(null);
//		String[] names = new String[20];
//		for(int i = 0;i<availableChampions.size();i++)
//			names[i]=availableChampions.get(i).getName(); 
//		list1 = new JList(names); //data has type Object[]
//		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//		list1.setVisibleRowCount(-1);
//		list1.addListSelectionListener(this);
//		JScrollPane listScroller = new JScrollPane(list1);
//		listScroller.setPreferredSize(new Dimension(500, 300));
//		listScroller.setBounds(10,10, 500, 300);
//		list1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//		list2 = new JList(names); //data has type Object[]
//		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		list2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//		list2.setVisibleRowCount(-1);
//		list2.addListSelectionListener(this);
//		JScrollPane listScroller2 = new JScrollPane(list2);
//		listScroller2.setPreferredSize(new Dimension(500, 300));
//		listScroller2.setBounds(520,10, 500, 300);
//		list2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//		stats1 = new JTextArea();
//		stats1.setBounds(520+510, 0, 400, 720-300);
//		stats1.setText("Select Leader");
//		stats1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//		select.add(stats1);
//		stats2 = new JTextArea();
//		stats2.setBounds(520+510+400, 0, 400, 720-300);
//		stats2.setText("Select Leader");
//		stats2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//		select.add(stats2);
//		
//		select.add(listScroller2);
//		select.add(listScroller);
//		startGame = new JButton("Start Game");
//		startGame.setActionCommand("Start game");
//		startGame.addActionListener(this);
//		startGame.setBounds(500, 500, 200, 100);
//		select.add(startGame);
//
//		
//
//		gameframe.setVisible(true);
//		gameframe.add(select);
//		//gameframe.validate(); 3ayzeen net check law di sa7
//		//game=new Game( firstPlayer,  secondPlayer);	
//	
		}


	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("HAHAHAHA");
//		if(e.getSource()==(startGame))
//		{
//			//select.setVisible(false);
//			game.setVisible(true);
//			//System.out.println("el condition mish el moshkela ");
//		}
			

	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
//		if(e.getValueIsAdjusting()==false){
//			if(list1.getSelectedIndex()!=-1){
//				Champion c = availableChampions.get(list1.getSelectedIndex());
//				//System.out.println(list1.getSelectedIndex());
//				stats1.setText("Name: "+c.getName()+'\n'+"HP: "+c.getMaxHP()+'\n'+"Mana: "+c.getMana()+'\n'+"Speed: "+c.getSpeed()+'\n'+"Action Points "+c.getMaxActionPointsPerTurn());
//			}
//			if(list2.getSelectedIndex()!=-1){
//				Champion c = availableChampions.get(list2.getSelectedIndex());
//				//System.out.println(list1.getSelectedIndex());
//				stats2.setText("Name: "+c.getName()+'\n'+"HP: "+c.getMaxHP()+'\n'+"Mana: "+c.getMana()+'\n'+"Speed: "+c.getSpeed()+'\n'+"Action Points "+c.getMaxActionPointsPerTurn());
//			}
//		}
		
		
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

}
