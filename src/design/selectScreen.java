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
	private int count = 0;
	private String t1,t2 = "";
	private JTextArea team1,team2,ability1stats,ability2stats = new JTextArea("");
	private ImageIcon icon;
	private JTextField name1,name2;
	private JFrame selectframe;
	private JPanel select;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JList champlist1,champlist2;
	private JButton startGame, add1, add2,ab1,ab2,ab3,ab4,ab5,ab6;
	private JLabel warning;
	private ArrayList<Champion> temp1,temp2;
	private String[] names1, names2;
	
	public static void main(String[]args){
		selectScreen test = new selectScreen();
	}
	
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
		icon = new ImageIcon(this.getClass().getResource("/resources/icons/Marvel_Logo.png"));
		selectframe = new JFrame();
		selectframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectframe.setLayout(new BorderLayout());
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
		
		champlist1 = new JList(names); //data has type Object[]
		champlist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		champlist1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		champlist1.setVisibleRowCount(-1);
		champlist1.addListSelectionListener(this);
		
		JScrollPane listScroller = new JScrollPane(champlist1);
		//listScroller.setPreferredSize(new Dimension(500, 300));
		listScroller.setBounds(10,120-50, 500, 300);
		champlist1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		champlist2 = new JList(names); //data has type Object[]
		champlist2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		champlist2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		champlist2.setVisibleRowCount(-1);
		champlist2.addListSelectionListener(this);
		
		
		JScrollPane listScroller2 = new JScrollPane(champlist2);
		//listScroller2.setPreferredSize(new Dimension(500, 300));
		listScroller2.setBounds(520,120-50, 500, 300);
		champlist2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		
		
		stats1 = new JTextArea();
		stats1.setBounds(520+510, 10, 400, 720-460);
		stats1.setText("Select Leader");
		stats1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		select.add(stats1);
		
		
		stats2 = new JTextArea();
		stats2.setBounds(520+510+400, 10, 400, 720-460);
		stats2.setText("Select Leader");
		stats2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		select.add(stats2);
		
		
		name1 = new JTextField();
		name2 = new JTextField();
		name1.setBounds(10, 10, 500, 50);
		name1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		
		name2.setBounds(520, 10, 500, 50);
		name2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		select.add(listScroller2);
		select.add(listScroller);
		
		
		startGame = new JButton("Start Game");
		//startGame.setActionCommand("Start game");
		startGame.addActionListener(this);
		startGame.setBounds(205, 690, 200, 100);
		startGame.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
		
		
		add1 = new JButton("lock-in champion");
		add1.setBounds(205-50, 530, 200, 50);
		add1.addActionListener(this);
		add1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		add2 = new JButton("lock-in champion");
		add2.setBounds(715-50, 530, 200, 50);
		add2.addActionListener(this);
		add2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		warning = new JLabel();
		warning.setBounds(0, 590, 900, 100);
		warning.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		warning.setText("");
		
		t1="";
		t2="";
		team1 = new JTextArea("");
		team2 = new JTextArea("");
		team1.setBounds(10, 370, 500, 150);
		team1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		team2.setBounds(520, 370, 500, 150);
		team2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		ab1 = new JButton("Ablity 1");
		ab2 = new JButton("Ablity 2");
		ab3 = new JButton("Ablity 3");
		ab4 = new JButton("Ablity 1");
		ab5 = new JButton("Ablity 2");
		ab6 = new JButton("Ablity 3");
		ab1.setBounds(520+560, 280, 110, 20);
		ab2.setBounds(630+560+5, 280, 110, 20);
		ab3.setBounds(740+560+10, 280, 110, 20);
		ab4.setBounds(850+560+55, 280, 110, 20);
		ab5.setBounds(960+560+60, 280, 110, 20);
		ab6.setBounds(1070+560+65, 280, 110, 20);
		ability1stats = new JTextArea();
		ability2stats = new JTextArea();
		ability1stats.setBounds(520+510, 310, 420, 720-460);
		ability2stats.setBounds(520+540+400, 310, 420, 720-460);
		ability1stats.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		ability2stats.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		ab1.addActionListener(this);
		ab2.addActionListener(this);
		ab3.addActionListener(this);
		ab4.addActionListener(this);
		ab5.addActionListener(this);
		ab6.addActionListener(this);
		ab1.addMouseListener(this);
		ab2.addMouseListener(this);
		ab3.addMouseListener(this);
		ab4.addMouseListener(this);
		ab5.addMouseListener(this);
		ab6.addMouseListener(this);
		
		
		
//		
		//warning.setForeground(Color.green);
		select.add(ab1);
		select.add(ab2);
		select.add(ab3);
		select.add(ab4);
		select.add(ab5);
		select.add(ab6);
		select.add(startGame);
  	    select.add(name1);
		select.add(name2);
		select.add(add1);
		select.add(add2);
		select.add(warning);
		select.add(team1);
		select.add(team2);
		select.add(ability1stats);
		select.add(ability2stats);
		selectframe.add(select);
		selectframe.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("HAHAHAHA");
		if(e.getSource()==(startGame))
		{
			boolean alldone = false;
			if(!name1.getText().equals("") && !name2.getText().equals("")){
				Player p1 = new Player("name1.getText()");
				Player p2 = new Player("name2.getText()");
				if(temp1.size()<3||temp2.size()<3)
					warning.setText("Select 3 Champions for each team");
				else{
					for(int i=0;i<3;i++){
					    p1.getTeam().add(temp1.get(i));
					    if(i == 0)
					    	p1.setLeader(p1.getTeam().get(i));
					}
					for(int i=0;i<3;i++){
					    p2.getTeam().add(temp2.get(i));
					    if(i == 0)
					    	p2.setLeader(p2.getTeam().get(i));
					}
					MainGUI newgame = new MainGUI(new Game(p1,p2));
					//selectframe.dispatchEvent(new WindowEvent(selectframe, WindowEvent.WINDOW_CLOSING));
					selectframe.setVisible(false);
					}
			}
			else
				switch(count){
				case 0:
				warning.setText("Please Type a name for each player ya roo7 mother");
				count++;
				break;
				case 1:
				warning.setText("    enta fakerni haseebak?");
				count--;
				break;
				}
				
		}
		
		if(e.getSource()==add1){
			Champion c = null;
			if(champlist1.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist1.getSelectedIndex());
			else
				warning.setText("maybe select smth first?");
			if(!temp1.contains(c))
				if(temp1.size()<3)
				{
				    temp1.add(c);
				    System.out.println(c.getName());
				    warning.setText("");
				    if(temp1.size() == 1)
				    	t1 = t1 + c.getName()+ " (Leader)" +'\n';
				    else
				    	t1 = t1 + c.getName()+'\n';
				    team1.setText(t1);
				}
				else
					warning.setText("only 3 per team plz. coroona b2a w keda ");
			else
				warning.setText("You already have that champion");
		}
		
		if(e.getSource()==add2){
			Champion c = null;
			if(champlist2.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist2.getSelectedIndex());
			else
				warning.setText("maybe select smth first?");
			if(!temp2.contains(c))
				if(temp2.size()<3)
				{
				    temp2.add(c);
				    System.out.println(c.getName());
				    warning.setText("");
				    if(temp2.size() == 1)
				    	t2 = t2 + c.getName()+ " (Leader)" +'\n';
				    else
				    	t2 = t2 + c.getName()+'\n';
				    team2.setText(t2);
				}
				else
					warning.setText("only 3 per team plz. coroona b2a w keda ");
			else
				warning.setText("You already have that champion");
		}
		if(e.getSource()==ab1&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(0);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab2&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(1);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab3&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(2);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab4&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(0);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
		if(e.getSource()==ab5&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(1);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
		if(e.getSource()==ab6&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(2);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
			
		
			

	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			if(champlist1.getSelectedIndex()!=-1){
				Champion c = availableChampions.get(champlist1.getSelectedIndex());
				String type ="";
				if(c instanceof Hero)
					type = "Hero";
				else if(c instanceof Villain)
					type = "Villain";
				else
					type = "Antihero";
				stats1.setText("Name: "+c.getName()+'\n'
						+"HP: "+c.getMaxHP()+'\n'
						+"Mana: "+c.getMana()+'\n'
						+"Speed: "+c.getSpeed()+'\n'
						+"Action Points "+c.getMaxActionPointsPerTurn()+'\n'
						+"Type: "+type);		
			}
			if(champlist2.getSelectedIndex()!=-1){
				Champion c = availableChampions.get(champlist2.getSelectedIndex());
				String type ="";
				if(c instanceof Hero)
					type = "Hero";
				else if(c instanceof Villain)
					type = "Villain";
				else
					type = "Antihero";
				stats2.setText("Name: "+c.getName()+'\n'
						+"HP: "+c.getMaxHP()+'\n'+"Mana: "+c.getMana()+'\n'
						+"Speed: "+c.getSpeed()+'\n'
						+"Action Points "+c.getMaxActionPointsPerTurn()+'\n'
						+"Type: "+type);
			}
		}
		
		
	}
	
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==(startGame))
		{
			boolean alldone = false;
			if(!name1.getText().equals("") && !name2.getText().equals("")){
				Player p1 = new Player("name1.getText()");
				Player p2 = new Player("name2.getText()");
				if(temp1.size()<3||temp2.size()<3)
					warning.setText("Select 3 Champions for each team");
				else{
					for(int i=0;i<3;i++)
					    p1.getTeam().add(temp1.get(i));
					for(int i=0;i<3;i++)
					    p2.getTeam().add(temp2.get(i));
					//MainGUI newgame = new MainGUI(p1,p2);
					selectframe.dispatchEvent(new WindowEvent(selectframe, WindowEvent.WINDOW_CLOSING));
					}
			}
			else
				switch(count){
				case 0:
				warning.setText("Please Type a name for each player ya roo7 mother");
				count++;
				break;
				case 1:
				warning.setText("    enta fakerni haseebak?");
				count--;
				break;
				}
				
		}
		
		if(e.getSource()==add1){
			Champion c = null;
			if(champlist1.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist1.getSelectedIndex());
			else
				warning.setText("maybe select smth first?");
			if(!temp1.contains(c))
				if(temp1.size()<3)
				{
				    temp1.add(c);
				    System.out.println(c.getName());
				    warning.setText("");
				    t1 = t1 + c.getName()+'\n';
				    team1.setText(t1);
				}
				else
					warning.setText("only 3 per team plz. coroona b2a w keda ");
			else
				warning.setText("You already have that champion");
		}
		
		if(e.getSource()==add2){
			Champion c = null;
			if(champlist2.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist2.getSelectedIndex());
			else
				warning.setText("maybe select smth first?");
			if(!temp2.contains(c))
				if(temp2.size()<3)
				{
				    temp2.add(c);
				    System.out.println(c.getName());
				    warning.setText("");
				    t2 = t2 + c.getName()+'\n';
				    team2.setText(t2);
				}
				else
					warning.setText("only 3 per team plz. coroona b2a w keda ");
			else
				warning.setText("You already have that champion");
		}
		if(e.getSource()==ab1&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(0);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab2&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(1);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab3&& champlist1.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist1.getSelectedIndex()).getAbilities().get(2);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
		}
		if(e.getSource()==ab4&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(0);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
		if(e.getSource()==ab5&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(1);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
		if(e.getSource()==ab6&& champlist2.getSelectedIndex()!=-1){
			Ability a = availableChampions.get(champlist2.getSelectedIndex()).getAbilities().get(2);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+
					"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability2stats.setText(temp);
		}
			
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==ab1 || e.getSource()==ab2 || e.getSource()==ab3)
			ability1stats.setText("");
		else if(e.getSource()==ab4 || e.getSource()==ab5 || e.getSource()==ab6)
		ability2stats.setText("");
		
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
