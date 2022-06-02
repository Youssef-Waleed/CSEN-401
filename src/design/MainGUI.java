//aint nothing but a heartache
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
import java.awt.Rectangle;
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
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;

@SuppressWarnings("serial")

public class MainGUI implements ActionListener, MouseInputListener, ListSelectionListener {
	private JFrame gameframe;
	private boolean castIsclicked=false;
	private Game G;
	private ArrayList<Damageable> targets;
	private ImageIcon icon, tmpico;
	private JLabel champ1, champ2, champ3, champ4, champ5, champ6;
	private JTextArea stats1,stats2, turnorderT;
	private JPanel info,main, container, current, actions,game,select, charc;
	private JButton up,down,right,left,attack,castability,useleaderab,startGame 
				/*,b11,b12,b13,b14,b15,
					b21,b22,b23,b24,b25,
					b31,b32,b33,b34,b35,
					b41,b42,b43,b44,b45,
					b51,b52,b53,b54,b55*/;
	private JButton[][] Gridbuttons;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JList list1 = new JList(); 
	private JList list2 = new JList();
	
	
	public static void main(String[] args) {
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
		Player p2 = new Player("mohamed el gamed awy aaa");
		Player p1 = new Player("CPU el fashel awy aaa");
		p1.getTeam().add(availableChampions.get(0));
		p1.setLeader(availableChampions.get(0));
		p1.getTeam().add(availableChampions.get(1));
		p1.getTeam().add(availableChampions.get(2));
		p2.getTeam().add(availableChampions.get(3));
		p2.setLeader(availableChampions.get(3));
		p2.getTeam().add(availableChampions.get(4));
		p2.getTeam().add(availableChampions.get(5));
		MainGUI bla = new MainGUI(new Game( p1 , p2 ));
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
		G=newGame;
		targets = new ArrayList<Damageable>();
		
	//-----------------------------------------game panel---------------------------------------------------------	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		icon = new ImageIcon(this.getClass().getResource("/resources/icons/Marvel_Logo.png"));
		ImageIcon upicon = new ImageIcon(this.getClass().getResource("/resources/icons/up-arrow.png"));
		ImageIcon downicon = new ImageIcon(this.getClass().getResource("/resources/icons/down-arrow.png"));
		ImageIcon righticon = new ImageIcon(this.getClass().getResource("/resources/icons/right-arrow.png"));
		ImageIcon lefticon = new ImageIcon(this.getClass().getResource("/resources/icons/left-arrow.png"));
		ImageIcon attackicon = new ImageIcon(this.getClass().getResource("/resources/icons/attack.png"));
		ImageIcon casticon = new ImageIcon(this.getClass().getResource("/resources/icons/ability.png"));
		ImageIcon leadicon = new ImageIcon(this.getClass().getResource("/resources/icons/wizard.png"));
		
		
		gameframe = new JFrame();
		//gameframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setLayout(new BorderLayout());
		gameframe.setSize(width+1, height);
		gameframe.setLocation(1920/2-width/2,0);
		//gameframe.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		//gameframe.setTitle("Marvel: Ultimate War");
		gameframe.setIconImage(icon.getImage());
		
//========================================================================================================================
		//											PANELS
		
		game = new JPanel();
		game.setBounds(0, 0, width, height);
		game.setLayout(new BorderLayout());
		game.setVisible(true);
		
		
		main = new JPanel();			//the new Panels
		main.setLayout(new GridLayout(5,5));
		main.setVisible(true);
		
		info = new JPanel();
		info.setBackground(new Color(0xBB381D));
		info.setPreferredSize(new Dimension(500, 600));
		info.setLayout(new BorderLayout());
		info.setVisible(true);
		
		container = new JPanel();
		container.setBackground(new Color(0xFFFFFF));
		container.setPreferredSize(new Dimension(600, 200));
		container.setLayout(new BorderLayout());
		container.setVisible(true);
		
		current = new JPanel();
		current.setBackground(new Color(0x353535));
		current.setPreferredSize(new Dimension(800, 300));
		current.setLayout(new FlowLayout(FlowLayout.LEADING, 50,10));
		current.setVisible(true);
		
		
		actions = new JPanel();
		actions.setBackground(new Color(0x555510));
		actions.setPreferredSize(new Dimension(500, 350));
		actions.setLayout(null);
		actions.setVisible(true);
		
		charc = new JPanel();
		charc.setBackground(new Color(0x9D2E18));
		charc.setPreferredSize(new Dimension(200, 600));
		charc.setLayout(null);
		charc.setVisible(true);
		info.add(charc,BorderLayout.CENTER);
		
		
		game.add(current, BorderLayout.SOUTH);
		game.add(main, BorderLayout.CENTER);
		game.add(container, BorderLayout.EAST);
		
		container.add(actions, BorderLayout.SOUTH);
		container.add(info, BorderLayout.CENTER);
		gameframe.add(game, BorderLayout.CENTER);
		
		
	//------------------------------------------------LABELS----------------------------------------------------------	
		champ1 = new JLabel();
		champ2 = new JLabel();
		champ3 = new JLabel();
		champ4 = new JLabel();
		champ5 = new JLabel();
		champ6 = new JLabel();
		
//		champ1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ5.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ6.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		
		champ1.setBounds(50, 60, 90,90);
		champ2.setBounds(40, 60+100, 110,110);  //Leader 1
		champ3.setBounds(50, 60+100+120, 90,90);
		champ4.setBounds(50+205, 60, 90,90);
		champ5.setBounds(40+205+5, 60+100,100,110);  // Leader 2
		champ6.setBounds(50+205, 60+100+120, 90,90);
		
				//sub-labels
				JLabel pONE = new JLabel(G.getFirstPlayer().getName(), SwingConstants.CENTER);
				JLabel pTWO = new JLabel(G.getSecondPlayer().getName(), SwingConstants.CENTER);
				pONE.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				pTWO.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				pONE.setBounds(10, 10, 180, 50);
				pTWO.setBounds(210, 10, 180, 50);
		
		charc.add(champ1);
		charc.add(champ2);
		charc.add(champ3);
		charc.add(champ4);
		charc.add(champ5);
		charc.add(champ6);
		charc.add(pONE);
		charc.add(pTWO);
		
		for(int i =0;i<3;i++){
			if(G.getFirstPlayer().getTeam().get(i) != G.getFirstPlayer().getLeader())
				if(champ1.getIcon()==null){
					champ1.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
					if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
						champ1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
				else{
					champ3.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
					if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
						champ3.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					}
			else{
				champ2.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH)));
				if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
					champ2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
			if(G.getSecondPlayer().getTeam().get(i) != G.getSecondPlayer().getLeader())
				if(champ4.getIcon()==null){
					champ4.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
					if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
						champ4.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
				else{
					champ6.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
					if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
						champ6.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
			else{
				champ5.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH)));
				if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
					champ5.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			}
		}
		
	//------------------------------------------------TEXTFIELDS----------------------------------------------------------	
		turnorderT = new JTextArea("The Turn Order:" + '\n' );
		turnorderT.setPreferredSize(new Dimension(200,400));
		turnorderT.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		turnorderT.setBackground(new Color(0xE7694F));
		turnOrderSetText();
		info.add(turnorderT, BorderLayout.EAST);
		
		
	//------------------------------------------------BUTTONS----------------------------------------------------------	
		
		up= new JButton( new ImageIcon(upicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		up.setHorizontalTextPosition(JButton.CENTER);
		up.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		up.setFocusable(false);
		up.setBounds(270,30,75,75);
		up.setActionCommand("UP");
		up.addMouseListener(this);
		
		right= new JButton( new ImageIcon(righticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		right.setHorizontalTextPosition(JButton.CENTER);
		right.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		right.setFocusable(false);
		right.setBounds(360,125,75,75);
		right.setActionCommand("RIGHT");
		right.addMouseListener(this);
		
		left= new JButton( new ImageIcon(lefticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		left.setHorizontalTextPosition(JButton.CENTER);
		left.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		left.setFocusable(false);
		left.setBounds(180,125,75,75);
		left.setActionCommand("LEFT");
		left.addMouseListener(this);
		
		down= new JButton( new ImageIcon(downicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		down.setHorizontalTextPosition(JButton.CENTER);
		down.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		down.setFocusable(false);
		down.setBounds(270,220,75, 75);
		down.setActionCommand("DOWN");
		down.addMouseListener(this);
		
		attack= new JButton(new ImageIcon(attackicon.getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH)));
		attack.setHorizontalTextPosition(JButton.CENTER);
		attack.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		attack.setFocusable(false);
		attack.setBounds(270,125,75,75);
		attack.setActionCommand("ATTACK");
		attack.addMouseListener(this);
		
		castability= new JButton("CAST ABILITY", new ImageIcon(casticon.getImage().getScaledInstance(120,80,Image.SCALE_SMOOTH)));
		castability.setVerticalTextPosition(JButton.BOTTOM);
		castability.setHorizontalTextPosition(JButton.CENTER);
		castability.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		castability.setFocusable(false);
		castability.setBounds(20,205,150,100);
		castability.setActionCommand("CAST ABILITY");
		castability.addMouseListener(this);
		
		useleaderab= new JButton("USE LEADER ABILITY", new ImageIcon(leadicon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
		useleaderab.setVerticalTextPosition(JButton.BOTTOM);
		useleaderab.setHorizontalTextPosition(JButton.CENTER);
		useleaderab.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		useleaderab.setFocusable(false);
		useleaderab.setBounds(450,205,150,100);
		useleaderab.setActionCommand("USE LEADER ABILITY");
		useleaderab.addMouseListener(this);
		
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
		
		
		
		
		Gridbuttons = new JButton[5][5];
		for(int i =4; i>=0; i--)
			for(int j =0; j<5; j++){
				Gridbuttons[i][j]= new JButton();
				Gridbuttons[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 16));
				Gridbuttons[i][j].setFocusable(false);
				Gridbuttons[i][j].addActionListener(this);
				//Gridbuttons[i][j].setBackground(new Color(0xA2CCE8));
				Object[][] board = G.getBoard();
				if(board[i][j] instanceof Champion)
					Gridbuttons[i][j].setIcon(	new ImageIcon(((((Champion)board[i][j])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));				//((Champion)board[i][j]).getIcon());
				else if(board[i][j] instanceof Cover)
					Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)board[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));			//((Cover)board[i][j]).getIcon());
				main.add(Gridbuttons[i][j]);
			}
		gameframe.setVisible(true);

		}


	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==up&&!castIsclicked)
		{
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.UP);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				no = true;
				e1.printStackTrace();
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x-1][G.getCurrentChampion().getLocation().y].setIcon(null);
			}
		}
		if(e.getSource()==down&&!castIsclicked)
		{
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.DOWN);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				no = true;
				e1.printStackTrace();
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x+1][G.getCurrentChampion().getLocation().y].setIcon(null);
			}
		}
		if(e.getSource()==right&&!castIsclicked)
		{
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.RIGHT);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				no = true;
				e1.printStackTrace();
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-1].setIcon(null);
			}
		}
		if(e.getSource()==left&&!castIsclicked)
		{
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.LEFT);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				no = true;
				e1.printStackTrace();
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+1].setIcon(null);
			}
		}
		
		//===============================================================================================================================================
		// CASTABILITY
		if(e.getSource()==castability){
			if(castIsclicked){
				try {
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2));
				} catch (NotEnoughResourcesException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (AbilityUseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i = 0;i<targets.size();i++)
				{
					castability.setBackground(Color.ORANGE);
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(null);
					//Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setIcon(I);
					System.out.println(Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].getBackground()== null);
				}
				castIsclicked=false;
			}
			else{
				castability.setBackground(Color.PINK);
				castIsclicked=true;
				System.out.println("works");
			
			
			try {
				targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2));
				for(int i = 0;i<targets.size();i++)
				{
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(new Color(251, 252, 136));
				}
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				G.castAbility(G.getCurrentChampion().getAbilities().get(2),4,1);
				targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0));
				for(int i = 0;i<targets.size();i++)
				{
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(new Color(251, 252, 136));
				}
			} catch (NotEnoughResourcesException e2) {
				// TODO Auto-generated catch block
				
			} catch (AbilityUseException e2) {
				// TODO Auto-generated catch block
				
			} catch (InvalidTargetException e2) {
				System.out.println("Enta bethazar?");
				
			} catch (CloneNotSupportedException e2) {
				// TODO Auto-generated catch block
				
			}
		    try {
				G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.UP);
				targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0));
				for(int i = 0;i<targets.size();i++)
				{
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(new Color(251, 252, 136));
				}
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
		

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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseEntered(MouseEvent e) {
		tmpico = new ImageIcon(((ImageIcon) ((JButton)e.getSource()).getIcon()).getImage());
		((JButton)e.getSource()).setIcon(null);
		((JButton)e.getSource()).setText(((JButton)e.getSource()).getActionCommand());
		
	}











	@Override
	public void mouseExited(MouseEvent e) {
		
			((JButton)e.getSource()).setIcon(tmpico);;
		if(e.getSource() != this.castability && e.getSource() != this.useleaderab )
			((JButton)e.getSource()).setText("");
		
	}











	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseMoved(MouseEvent e) {
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
	
	public void turnOrderSetText(){
		PriorityQueue turnz= G.getTurnOrder().clone();
		while(!turnz.isEmpty())
			turnorderT.setText(turnorderT.getText()+'\n' +((Champion)turnz.remove()).getName());
	}

}
