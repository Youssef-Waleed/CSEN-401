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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;


public class MainGUI implements ActionListener, MouseInputListener, ListSelectionListener {
	private JFrame gameframe, championstats= new JFrame();
	private boolean castIsclicked=false, isAttackMode= false, leaderClicked = false,alreadyclickedufuckingidiot = false;
	private Game G;
	private Color asfarika = new Color(251, 252, 136);
	private ArrayList<Damageable> targets;
	private ImageIcon icon, tmpico, leaderbordericon= new ImageIcon(this.getClass().getResource("/resources/icons/leadercrown.png"))
								, defaultbordericon = new ImageIcon(this.getClass().getResource("/resources/icons/defaultborder.png"));
	private JLabel champ1, champ2, champ3, champ4, champ5, champ6,
								champ1border, champ2border, champ3border, champ4border,champ5border,champ6border;
	private JTextArea turnorderT,applieff;
	private JTextPane ability1stats, stats1;
	private JPanel info,main, container, current, actions,game, charc, allcontentspane;
	private JButton up,down,right,left,attack,attack2,castability,useleaderab,endturn ,ab1 =new JButton() ,ab2=new JButton() ,ab3=new JButton(), confirmability, confirmleaderab
				/*,b11,b12,b13,b14,b15,
					b21,b22,b23,b24,b25,
					b31,b32,b33,b34,b35,
					b41,b42,b43,b44,b45,
					b51,b52,b53,b54,b55*/;
	private JButton[][] Gridbuttons;
	private Toolkit toolkit;
	private Cursor c;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private ArrayList<Champion> leadertargs;
	private int width,height;
	
	
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
		//p1.getLeader().setCurrentHP(0);
		p1.getTeam().add(availableChampions.get(1));
		p1.getTeam().add(availableChampions.get(2));
		
		p2.getTeam().add(availableChampions.get(3));
		p2.setLeader(availableChampions.get(3));
		//p2.getLeader().setCurrentHP(0);
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
		
//			||===============================================||
//			||		 ----------------------					 ||
//			||	-----Constructor  APPENDIX:----------		 ||
//			||		 ----------------------					 ||
//			||			*Game Panel							 ||
//			||			*other Panels						 ||
//			||			*Labels								 ||
//			||			*TextFields & TextPanes				 ||
//			||			*Buttons							 ||
//			||												 ||	
//			||===============================================||
		
		
		
	//-----------------------------------------GAME PANEL---------------------------------------------------------	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		icon = new ImageIcon(this.getClass().getResource("/resources/icons/Marvel_Logo.png"));
		ImageIcon upicon = new ImageIcon(this.getClass().getResource("/resources/icons/up-arrow.png"));
		ImageIcon downicon = new ImageIcon(this.getClass().getResource("/resources/icons/down-arrow.png"));
		ImageIcon righticon = new ImageIcon(this.getClass().getResource("/resources/icons/right-arrow.png"));
		ImageIcon lefticon = new ImageIcon(this.getClass().getResource("/resources/icons/left-arrow.png"));
		ImageIcon attackicon = new ImageIcon(this.getClass().getResource("/resources/icons/attack.png"));
		ImageIcon casticon = new ImageIcon(this.getClass().getResource("/resources/icons/ability.png"));
		ImageIcon leadicon = new ImageIcon(this.getClass().getResource("/resources/icons/wizard.png"));
		
		
		gameframe = new JFrame();
		gameframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setLayout(new BorderLayout());
		gameframe.setSize(width+1, height);
		gameframe.setLocation(1920/2-width/2,0);
		//gameframe.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		gameframe.setTitle("Marvel: Ultimate War");
		gameframe.setIconImage(icon.getImage());
		gameframe.addMouseListener(this);
		JLabel l = new JLabel("i work");
		l.setBounds(0,0,90,10);
		gameframe.add(l);
		
//========================================================================================================================
		//											PANELS
		
		game = new JPanel();
		game.setBounds(0, 0, width, height);
		game.setLayout(new BorderLayout());
		game.setVisible(true);
		
		
		main = new JPanel();			//the new Panels
		GridLayout laylay = new GridLayout(5,5);
		laylay.setHgap(5);
		laylay.setVgap(5);
		main.setLayout(laylay);
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
		current.setLayout(null);
		current.setVisible(true);
		
		
		actions = new JPanel();
		actions.setBackground(new Color(0x555510));
		actions.setPreferredSize(new Dimension(500, 325));
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
		
		champ1border = new JLabel();
		champ1border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ1border.setBounds(27, 27, 117,117);
		
		champ2border = new JLabel();		//Leader 1
		champ2border.setIcon(new ImageIcon(leaderbordericon.getImage().getScaledInstance(155,155,Image.SCALE_SMOOTH)));
		champ2border.setBounds(8, 22+100+10, 155,155);  

		champ3border = new JLabel();
		champ3border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ3border.setBounds(27, 27+100+120+20, 117,117);

		champ4border = new JLabel();
		champ4border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ4border.setBounds(32+205, 27, 117,117);

		champ5border = new JLabel();		// Leader 2
		champ5border.setIcon(new ImageIcon(leaderbordericon.getImage().getScaledInstance(155,155,Image.SCALE_SMOOTH)));
		champ5border.setBounds(8+205+5, 22+100+10,155,155);

		champ6border = new JLabel();
		champ6border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ6border.setBounds(32+205, 27+100+120+20, 117,117);
		
//		champ1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ2.setBorder(BorderFactory.createLineBorder(Color.green, 3));
//		champ3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ5.setBorder(BorderFactory.createLineBorder(Color.green, 3));
//		champ6.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		
		champ1.setBounds(50, 50, 70,70);
		champ2.setBounds(40, 50+100+15 ,90,90);  //Leader 1
		champ3.setBounds(50, 50+100+120+20, 70,70);
		champ4.setBounds(50+210, 50, 70,70);
		champ5.setBounds(40+205+5, 50+100+12, 90,90);  // Leader 2
		champ6.setBounds(50+210, 50+100+120+20, 70,70);
		
				//sub-labels
				JLabel pONE = new JLabel(G.getFirstPlayer().getName(), SwingConstants.CENTER);
				JLabel pTWO = new JLabel(G.getSecondPlayer().getName(), SwingConstants.CENTER);
				pONE.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				pTWO.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				pONE.setBounds(10, 2, 180, 50);
				pTWO.setBounds(210, 2, 180, 50);
		
		charc.add(champ1);
		charc.add(champ2);
		charc.add(champ3);
		charc.add(champ4);
		charc.add(champ5);
		charc.add(champ6);
		charc.add(pONE);
		charc.add(pTWO);
		charc.add(champ1border);
		charc.add(champ2border);
		charc.add(champ3border);
		charc.add(champ4border);
		charc.add(champ5border);
		charc.add(champ6border);
		
		for(int i =0;i<3;i++){
			if(G.getFirstPlayer().getTeam().get(i) != G.getFirstPlayer().getLeader())
				if(champ1.getIcon()==null){
					champ1.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH)));
					champ1.setText(G.getFirstPlayer().getTeam().get(i).getName());
					champ1.setFont(new Font("", Font.PLAIN, 0));
					if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
						champ1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
				else{
					champ3.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH)));
					champ3.setText(G.getFirstPlayer().getTeam().get(i).getName());
					champ3.setFont(new Font("", Font.PLAIN, 0));
					if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
						champ3.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					}
			else{
				champ2.setIcon(new ImageIcon(((ImageIcon)G.getFirstPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
				champ2.setText(G.getFirstPlayer().getTeam().get(i).getName());
				champ2.setFont(new Font("", Font.PLAIN, 0));
				if(G.getTurnOrder().peekMin() == G.getFirstPlayer().getTeam().get(i))
					champ2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
			if(G.getSecondPlayer().getTeam().get(i) != G.getSecondPlayer().getLeader())
				if(champ4.getIcon()==null){
					champ4.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH)));
					champ4.setText(G.getSecondPlayer().getTeam().get(i).getName());
					champ4.setFont(new Font("", Font.PLAIN, 0));
					if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
						champ4.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
				else{
					champ6.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH)));
					champ6.setText(G.getSecondPlayer().getTeam().get(i).getName());
					champ6.setFont(new Font("", Font.PLAIN, 0));
					if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
						champ6.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
				}
			else{
				champ5.setIcon(new ImageIcon(((ImageIcon)G.getSecondPlayer().getTeam().get(i).getIcon()).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
				champ5.setText(G.getSecondPlayer().getTeam().get(i).getName());
				champ5.setFont(new Font("", Font.PLAIN, 0));
				if(G.getTurnOrder().peekMin() == G.getSecondPlayer().getTeam().get(i))
					champ5.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			}
		}
		
	//------------------------------------------------TEXTFIELDS----------------------------------------------------------
		
		
		turnorderT = new JTextArea("The Turn Order:" + '\n' );
		turnorderT.setPreferredSize(new Dimension(200,400));
		turnorderT.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		turnorderT.setBackground(new Color(0xE7694F));
		turnorderT.setEditable(false);
		turnOrderSetText();
		info.add(turnorderT, BorderLayout.EAST);
		
																//TEXTPANE
		ability1stats = new JTextPane();
		ability1stats.setText("Ability Details...");
		ability1stats.setBounds(width-550*width/1920,0,500,300);
		ability1stats.setFont(new Font("Agency FB", Font.BOLD, 25));
		ability1stats.setBackground(new Color(0x4A4A4A));
		ability1stats.setForeground(Color.WHITE);
		StyledDocument doc = ability1stats.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		ability1stats.setEditable(false);
		//System.out.println(getappEffects(G.getCurrentChampion()));
		applieff = new JTextArea(getappEffects(G.getCurrentChampion()));
		applieff.setFont(new Font("Agency FB", Font.BOLD, 25));
		applieff.setBackground(new Color(0x404040));
		applieff.setForeground(Color.WHITE);
		applieff.setBounds(305, 0, 300, 300);
		stats1 = new JTextPane();
		stats1.setText("Current Champion Stats:");
		stats1.setBounds(0,0,300,300);
		stats1.setFont(new Font("Agency FB", Font.BOLD, 24));
		stats1.setBackground(new Color(0x404040));
		stats1.setForeground(Color.WHITE);
		StyledDocument doc1 = stats1.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center1, StyleConstants.ALIGN_LEFT);
		doc1.setParagraphAttributes(0, doc1.getLength(), center1, false);
		stats1.setEditable(false);
		
		current.add(stats1);
		current.add(ability1stats);
		current.add(applieff);
		
		
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
		attack.addActionListener(this);
		
		attack2= new JButton(new ImageIcon(attackicon.getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH)));
		attack2.setHorizontalTextPosition(JButton.CENTER);
		attack2.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		attack2.setFocusable(false);
		attack2.setBounds(270,125,75,75);
		attack2.setActionCommand("attack");
		attack2.setVisible(false);
		attack2.setBackground(Color.PINK);
		attack2.addMouseListener(this);
		attack2.addActionListener(this);
		
		castability= new JButton( new ImageIcon(casticon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
		castability.setVerticalTextPosition(JButton.BOTTOM);
		castability.setHorizontalTextPosition(JButton.CENTER);
		castability.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		castability.setFocusable(false);
		castability.setBounds(40,40,120,100);
		castability.setBorder(BorderFactory.createSoftBevelBorder(3));
		castability.setBackground(null);
		castability.setForeground(Color./*zeft*/WHITE);
		castability.setActionCommand("<html>" + "CAST" + "<br>" + "ABILITY" + "</html>");
		castability.addMouseListener(this);
		
		useleaderab= new JButton( new ImageIcon(leadicon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
		useleaderab.setVerticalTextPosition(JButton.BOTTOM);
		useleaderab.setHorizontalTextPosition(JButton.CENTER);
		useleaderab.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		useleaderab.setFocusable(false);
		useleaderab.setBounds(450,40,120,100);
		useleaderab.setBorder(BorderFactory.createSoftBevelBorder(3));
		useleaderab.setBackground(null);
		useleaderab.setForeground(Color./*zeft*/WHITE);
		useleaderab.setActionCommand("<html>" + "USE" + "<br>" + "LEADER" + "<br>" + "ABILITY"+ "</html>");
		useleaderab.addMouseListener(this);
		if(G.getCurrentChampion() == G.getFirstPlayer().getLeader() || G.getCurrentChampion() == G.getSecondPlayer().getLeader())
			useleaderab.setEnabled(true);
		else
			useleaderab.setEnabled(false);
		
		confirmability = new JButton("Confirm Ability");
		confirmability.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		confirmability.setBounds(430, 270, 150, 45);
		confirmability.setFocusable(false);
		confirmability.setVisible(false);
		confirmability.addActionListener(this);
		
		confirmleaderab = new JButton("Confirm");
		confirmleaderab.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		confirmleaderab.setBounds(430, 270, 150, 45);
		confirmleaderab.setFocusable(false);
		confirmleaderab.setVisible(false);
		confirmleaderab.addActionListener(this);

		ab1 =new JButton(G.getCurrentChampion().getAbilities().get(0).getName());
		ab1.setActionCommand(G.getCurrentChampion().getAbilities().get(0).getName());
		ab1.setBounds(170, 70, 250, 50);
		ab1.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		ab1.setBackground(Color.DARK_GRAY);
		ab1.setForeground(Color.white);
		ab1.setFocusable(false);
		ab1.setEnabled(true);
		ab1.setVisible(false);
		ab1.addMouseListener(this);
		ab1.addActionListener(this);
		
		ab2 =new JButton(G.getCurrentChampion().getAbilities().get(1).getName());
		ab2.setActionCommand(G.getCurrentChampion().getAbilities().get(1).getName());
		ab2.setBounds(170, 70+50+25, 250, 50);
		ab2.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		ab2.setBackground(Color.DARK_GRAY);
		ab2.setForeground(Color.white);
		ab2.setFocusable(false);
		ab2.setEnabled(true);
		ab2.setVisible(false);
		ab2.addMouseListener(this);
		ab2.addActionListener(this);
		
		ab3 =new JButton(G.getCurrentChampion().getAbilities().get(2).getName());
		ab3.setActionCommand(G.getCurrentChampion().getAbilities().get(2).getName());
		ab3.setBounds(170, 70+50+50+50, 250, 50);
		ab3.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		ab3.setBackground(Color.DARK_GRAY);
		ab3.setForeground(Color.white);
		ab3.setFocusable(false);
		ab3.setEnabled(true);
		ab3.setVisible(false);
		ab3.addMouseListener(this);
		ab3.addActionListener(this);
		
		endturn = new JButton("EndTurn");
		endturn.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
		endturn.setBounds(445, 270, 120, 45);
		endturn.setBackground(new Color(0xAF2929));
		endturn.setForeground(Color.WHITE);
		endturn.setFocusable(false);
		endturn.addActionListener(this);
		
		actions.add(endturn);
		actions.add(confirmability);
		actions.add(confirmleaderab);
		actions.add(up);
		actions.add(down);
		actions.add(right);
		actions.add(left);
		actions.add(castability);
		actions.add(attack);
		actions.add(attack2);
		actions.add(useleaderab);
		actions.add(ab1);
		actions.add(ab2);
		actions.add(ab3);

		
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
				Gridbuttons[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 13));
				Gridbuttons[i][j].setVerticalTextPosition(JLabel.BOTTOM);
				Gridbuttons[i][j].setHorizontalTextPosition(JLabel.CENTER);
				Gridbuttons[i][j].setFocusable(false);
				Gridbuttons[i][j].addActionListener(this);
				Gridbuttons[i][j].addMouseListener(this);
				//Gridbuttons[i][j].setBackground(new Color(0xA2CCE8));
				Object[][] board = G.getBoard();
				if(board[i][j] instanceof Champion)
					Gridbuttons[i][j].setIcon(	new ImageIcon(((((Champion)board[i][j])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));				//((Champion)board[i][j]).getIcon());
				else if(board[i][j] instanceof Cover)
					Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)board[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));			//((Cover)board[i][j]).getIcon());
				main.add(Gridbuttons[i][j]);
			}
		
		
		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion())){
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p1cursor.png"));
			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
			gameframe.setCursor (c);
		}
		else{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p2cursor.png"));
			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
			gameframe.setCursor (c);
		}
			
		
		
		
		gameframe.setVisible(true);
		
		updateStats();

		}


	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	//---------------------------------------       LEADER  ABILITY      ---------------------------------------------------------------------
		if(e.getSource()==useleaderab){
			leadertargs = new ArrayList<>();
			if(!leaderClicked){
				endturn.setVisible(false);
				castability.setVisible(false);
				confirmleaderab.setVisible(true);
				up.setVisible(false);
				down.setVisible(false);
				right.setVisible(false);
				left.setVisible(false);
				attack.setVisible(false);
				boolean first = false;
				if(G.getCurrentChampion() == G.getFirstPlayer().getLeader())
					first = true;
				if(G.getCurrentChampion() instanceof Hero){
					if(first)
						leadertargs = G.getFirstPlayer().getTeam();
					else
						leadertargs = G.getSecondPlayer().getTeam();
				}
				else if(G.getCurrentChampion() instanceof Villain){
					if(first)
						for(Champion c: G.getSecondPlayer().getTeam()){
							if (c.getCurrentHP() < (0.3 * c.getMaxHP()))
								leadertargs.add(c);
						}
						
					else
						for(Champion c: G.getFirstPlayer().getTeam()){
							if (c.getCurrentHP() < (0.3 * c.getMaxHP()))
								leadertargs.add(c);
						}
				}
				else{
					for(Champion c: G.getFirstPlayer().getTeam())
						leadertargs.add(c);
					for(Champion c: G.getSecondPlayer().getTeam())
						leadertargs.add(c);
					leadertargs.remove(G.getFirstPlayer().getLeader());
					leadertargs.remove(G.getSecondPlayer().getLeader());
				}
				for(int i = 0;i<leadertargs.size();i++)
					Gridbuttons[leadertargs.get(i).getLocation().x][leadertargs.get(i).getLocation().y].setBackground(asfarika);
				leaderClicked = true;
				ability1stats.setText("Use your Leader Ability?");
				if(G.getCurrentChampion() instanceof Hero)
					ability1stats.setText(ability1stats.getText()+'\n'+'\n'
						+"Confirming will Remove all negative effects from the player’s entire team and adds an Embrace effect to them which lasts for 2 turns.");
				else if(G.getCurrentChampion() instanceof Villain)
					ability1stats.setText(ability1stats.getText()+'\n'+'\n'+ "Confirming will Immediately eliminates (knocks out) all enemy champions with less than 30% healthpoints");
				else if(G.getCurrentChampion() instanceof AntiHero)
					ability1stats.setText(ability1stats.getText()+'\n'+'\n'+ "Confirming will stun ALL Champions on the board for 2 turns EXCEPT for the Leaders of each team.");
			
			}
			else{
				ability1stats.setText("Ability Details...");
				resetbuttons();
				clearHighlight();
				leadertargs = null;
				leaderClicked = false;
			}
		}
		
	//-----------------------------------------UP 	DOWN 	LEFT 	RIGHT--------------------------------------------------------
		
		if(e.getSource()==up&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.UP);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (UnallowedMovementException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x-1][G.getCurrentChampion().getLocation().y].setIcon(null);
			}
		}
		if(e.getSource()==down&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.DOWN);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (UnallowedMovementException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x+1][G.getCurrentChampion().getLocation().y].setIcon(null);
			}
		}
		if(e.getSource()==right&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.RIGHT);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (UnallowedMovementException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-1].setIcon(null);
			}
		}
		if(e.getSource()==left&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.LEFT);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (UnallowedMovementException e1) {
				no = true;
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+1].setIcon(null);
			}
		}
		
		if(e.getSource()==up&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=findAbilityByName(ab1.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.UP);
				} else if(ab2.getBackground()== Color.GREEN){
					a=findAbilityByName(ab2.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.UP);
				}else if(ab3.getBackground()== Color.GREEN){
					a=findAbilityByName(ab3.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.UP);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
						System.out.println(d.getCurrentHP());
					cleanGrid();
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					JOptionPane.showMessageDialog(null,"Please choose an ability","Marvel", JOptionPane.ERROR_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
		}
		if(e.getSource()==down&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=findAbilityByName(ab1.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.DOWN);
				} else if(ab2.getBackground()== Color.GREEN){
					a=findAbilityByName(ab2.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.DOWN);
				}else if(ab3.getBackground()== Color.GREEN){
					a=findAbilityByName(ab3.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.DOWN);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
						System.out.println(d.getCurrentHP());
					cleanGrid();
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					JOptionPane.showMessageDialog(null,"Please choose an ability","Marvel", JOptionPane.ERROR_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(e.getSource()==right&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=findAbilityByName(ab1.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.RIGHT);
				} else if(ab2.getBackground()== Color.GREEN){
					a=findAbilityByName(ab2.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.RIGHT);
				}else if(ab3.getBackground()== Color.GREEN){
					a=findAbilityByName(ab3.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.RIGHT);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
						System.out.println(d.getCurrentHP());
					cleanGrid();
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					JOptionPane.showMessageDialog(null,"Please choose an ability","Marvel", JOptionPane.ERROR_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(e.getSource()==left&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=findAbilityByName(ab1.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.LEFT);
				} else if(ab2.getBackground()== Color.GREEN){
					a=findAbilityByName(ab2.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.LEFT);
				}else if(ab3.getBackground()== Color.GREEN){
					a=findAbilityByName(ab3.getText());
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.LEFT);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
						System.out.println(d.getCurrentHP());
					cleanGrid();
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					JOptionPane.showMessageDialog(null,"Please choose an ability","Marvel", JOptionPane.ERROR_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
		}
		

		if(e.getSource()==up && attack2.isVisible()){
			try {
				G.attack(Direction.UP);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}
		
		if(e.getSource()==down && attack2.isVisible()){
			try {
				G.attack(Direction.DOWN);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}
		

		if(e.getSource()==right && attack2.isVisible()){
			try {
				G.attack(Direction.RIGHT);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}
		

		if(e.getSource()==left && attack2.isVisible()){
			try {
				G.attack(Direction.LEFT);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}
		
	//===============================================================================================================================================
														// CASTABILITY
		
		
		if(e.getSource()==castability){
			if(castIsclicked){
				endturn.setVisible(true);
				confirmability.setVisible(false);
				ab1.setVisible(false);
				ab2.setVisible(false);
				ab3.setVisible(false);
				up.setVisible(true);
				down.setVisible(true);
				right.setVisible(true);
				left.setVisible(true);
				attack.setVisible(true);
				useleaderab.setVisible(true);
				ability1stats.setText("Ability Details...");
				ab1.setBackground(Color.DARK_GRAY);
				ab2.setBackground(Color.DARK_GRAY);
				ab3.setBackground(Color.DARK_GRAY);
				castability.setBackground(null);
				clearHighlight();
					
				castIsclicked=false;
			}
			else{
				endturn.setVisible(false);
				confirmability.setVisible(true);
				ab1.setVisible(true);
				ab2.setVisible(true);
				ab3.setVisible(true);
				up.setVisible(false);
				down.setVisible(false);
				right.setVisible(false);
				left.setVisible(false);
				attack.setVisible(false);
				useleaderab.setVisible(false);
				ability1stats.setText("Ability Details..."+'\n'+'\n'
						+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
				castability.setBackground(new Color(0xCF4F3D));
				castIsclicked=true;
				System.out.println("works");
			
			
		    
		}
	}
		
		
	//---------------------------------------------THE THREE ABILITIESSSS BUTTON----------------------------------------------------------------------
		
		
		if( (e.getSource()== ab1 || e.getSource()== ab2 || e.getSource()== ab3) && ((JButton)e.getSource()).getBackground() == Color.DARK_GRAY ){ 
			if(e.getSource()== ab1){
				ab1.setBackground(Color.green);
				ab2.setBackground(Color.DARK_GRAY);
				ab3.setBackground(Color.DARK_GRAY);
				clearHighlight();
				System.out.println("wtf");
				highlight(G.getCurrentChampion().getAbilities().get(0), G.getCurrentChampion().getAbilities().get(0).getCastArea());
			} else if(e.getSource()== ab2){
				ab1.setBackground(Color.DARK_GRAY);
				ab2.setBackground(Color.GREEN);
				ab3.setBackground(Color.DARK_GRAY);
				clearHighlight();
				highlight(G.getCurrentChampion().getAbilities().get(1), G.getCurrentChampion().getAbilities().get(1).getCastArea());
			}else if(e.getSource()== ab3){
				ab1.setBackground(Color.DARK_GRAY);
				ab2.setBackground(Color.DARK_GRAY);
				ab3.setBackground(Color.GREEN);
				clearHighlight();
				highlight(G.getCurrentChampion().getAbilities().get(2), G.getCurrentChampion().getAbilities().get(2).getCastArea());
			}
		}
		else if((e.getSource()== ab1 || e.getSource()== ab2 || e.getSource()== ab3)){
			((JButton)e.getSource()).setBackground(Color.DARK_GRAY);
			clearHighlight();
			}
	//---------------------------------------------THE CONFIRM ABILITYY BUTTON----------------------------------------------------------------------
		
		
		
		if(e.getSource()== confirmability && ab1.getBackground() != Color.GREEN 
																&& ab2.getBackground() != Color.GREEN 
																&& ab3.getBackground() != Color.GREEN){
			JOptionPane.showMessageDialog(null,"Please Choose an Ability","Marvel", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(e.getSource()== confirmability){
			confirmability.setVisible(false);
			Ability a = null;
			if(ab1.getBackground()== Color.GREEN){
				a=findAbilityByName(ab1.getText());
			} else if(ab2.getBackground()== Color.GREEN){
				a=findAbilityByName(ab2.getText());
			}else if(ab3.getBackground()== Color.GREEN){
				a=findAbilityByName(ab3.getText());
			}
			
			if(a.getCastArea() == AreaOfEffect.DIRECTIONAL){
				ability1stats.setText("Choose DIRECTION from buttons" + '\n'+ "Warning: once you click the direction the ability will be casted!");
				ab1.setVisible(false);
				ab2.setVisible(false);
				ab3.setVisible(false);
				up.setVisible(true);
				down.setVisible(true);
				right.setVisible(true);
				left.setVisible(true);
				clearHighlight();
				//highlight(a, AreaOfEffect.DIRECTIONAL);	wtf is this?
			}
			else if(a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.TEAMTARGET || a.getCastArea() == AreaOfEffect.SURROUND){
				ability1stats.setText("Ability applied...");
				ab1.setVisible(false);
				ab2.setVisible(false);
				ab3.setVisible(false);
				up.setVisible(true);
				down.setVisible(true);
				right.setVisible(true);
				left.setVisible(true);
				attack.setVisible(true);
				useleaderab.setVisible(true);
				endturn.setVisible(true);
				castability.setBackground(null);
			try {
				clearHighlight();
				targets = G.castAbility(a);
				G.FinishHim(targets, a);
				for(Damageable d: targets)
					System.out.println(d.getCurrentHP());
				cleanGrid();
				resetbuttons();
				castIsclicked=false;
			} catch (NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
				castIsclicked=false;
			} catch (AbilityUseException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
				castIsclicked=false;
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
				castIsclicked=false;
			}
		
		}
			else if(a.getCastArea() == AreaOfEffect.SINGLETARGET){
				ability1stats.setText("Please choose a target on the board!");
				ab1.setVisible(false);
				ab2.setVisible(false);
				ab3.setVisible(false);
			}
		}
		
		if(e.getSource()==confirmleaderab && leaderClicked){
			try {
				G.useLeaderAbility();
				ability1stats.setText("Leader Ability Used");
				resetbuttons();
				clearHighlight();
				leadertargs = null;
				leaderClicked = false;
			} catch (LeaderNotCurrentException e1) {
				
			} catch (LeaderAbilityAlreadyUsedException e1) {
				resetbuttons();
				clearHighlight();
				leadertargs = null;
				leaderClicked = false;
				ability1stats.setText("Ability Details...");
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
				
			}
		}
		
		
	//--------------------------------------------GRIDBUTTONS-------------------------------------------------------------
		for(int i =0 ; i<5; i++)
			for(int j=0; j<5; j++){
				if(e.getSource() == Gridbuttons[i][j])
					if(castIsclicked){
						Ability a = null;
						if(ab1.getBackground()== Color.GREEN){
							a=findAbilityByName(ab1.getText());
						} else if(ab2.getBackground()== Color.GREEN){
							a=findAbilityByName(ab2.getText());
						}else if(ab3.getBackground()== Color.GREEN){
							a=findAbilityByName(ab3.getText());
						}
						if(a.getCastArea() == AreaOfEffect.SINGLETARGET)
							try {
								targets=G.castAbility(a, i, j);
								G.FinishHim(targets, a);
								for(Damageable d: targets)
									System.out.println(d.getCurrentHP());
								cleanGrid();
								resetbuttons();
								castIsclicked=false;
								ability1stats.setText("Ability applied...");
								ab1.setVisible(false);
								ab2.setVisible(false);
								ab3.setVisible(false);
								up.setVisible(true);
								down.setVisible(true);
								right.setVisible(true);
								left.setVisible(true);
								attack.setVisible(true);
								useleaderab.setVisible(true);
								endturn.setVisible(true);
								castability.setBackground(null);
								clearHighlight();
							} catch (CloneNotSupportedException | NotEnoughResourcesException 
									| AbilityUseException | InvalidTargetException e1) {
								JOptionPane.showMessageDialog(null,e1.getMessage(),"Marvel", JOptionPane.WARNING_MESSAGE);
							}
					}
					else if(!(G.getBoard()[i][j] instanceof Cover)&& G.getBoard()[i][j] !=null && !alreadyclickedufuckingidiot){
							championstats=new JFrame();
							championstats.setUndecorated(true);
				            String s = getStats((Champion)G.getBoard()[i][j]);
				            String eff = getappEffects((Champion)G.getBoard()[i][j]);
				            TextArea stats = new TextArea(s);
				            TextArea effects = new TextArea(getappEffects((Champion) G.getBoard()[i][j]));
							stats.setBounds(0,0,300,250);
							stats.setBackground(new Color(0x404040));
							effects.setBounds(300,0,350,250);
							effects.setBackground(new Color(0x404040));
							//championstats.setUndecorated(true);
							//int xloc= (MouseInfo.getPointerInfo().getLocation().x+210>width)?width-200:MouseInfo.getPointerInfo().getLocation().x+10 ;
							//int yloc= (MouseInfo.getPointerInfo().getLocation().y+105>height)?height-100:MouseInfo.getPointerInfo().getLocation().y+5 ;
							Point m = MouseInfo.getPointerInfo().getLocation();
							championstats.setLocation(m.x+1, m.y+1);
							championstats.setSize(650, 250);
							championstats.setLayout(null);
							stats.setFont(new Font("Agency FB", Font.BOLD, 18));
							stats.setBackground(new Color(0x404040));
							stats.setForeground(Color.WHITE);
							stats.setEditable(false);
							effects.setFont(new Font("Agency FB", Font.BOLD, 18));
							effects.setBackground(new Color(0x404040));
							effects.setForeground(Color.WHITE);
							effects.setEditable(false);
							championstats.setVisible(true);
							championstats.setAlwaysOnTop(true);
							//championstats.setFocusable(true);
							championstats.add(stats);
							championstats.add(effects);
							alreadyclickedufuckingidiot = true;
						}
				
			}
		
//--------------------------------------------------- A T T A C K-----------------------------------------------------------------
		
		
		if(e.getSource()==attack){
			castability.setVisible(false);
			confirmability.setVisible(false);
			useleaderab.setVisible(false);
			endturn.setVisible(false);
			ab1.setVisible(false);
			ab2.setVisible(false);
			ab3.setVisible(false);
			up.setVisible(true);
			down.setVisible(true);
			right.setVisible(true);
			left.setVisible(true);
			isAttackMode=true;
			attack.setVisible(false);
			attack2.setVisible(true);
		}
		if(e.getSource()==attack2){
			clearHighlight();
			resetbuttons();
			attack.setVisible(true);
			attack2.setVisible(false);
			
		}
		
		
//------------------------------------------------E N D I N G   T U R N------------------------------------------------------------------
	
	if(e.getSource() == endturn){
		G.endTurn();
		turnOrderSetText();
		if(G.getCurrentChampion() == G.getFirstPlayer().getLeader() || G.getCurrentChampion() == G.getSecondPlayer().getLeader())
			useleaderab.setEnabled(true);
		else
			useleaderab.setEnabled(false);
		

//		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion())){
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p1cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
//		else{
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p2cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
	

//		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion())){
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p1cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
//		else{
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage(this.getClass().getResource("/resources/icons/p2cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
	}
	
	
//------------------------------------------Generic after every action -----------------------------------------------
	
	updateStats();
	cleanGrid();
	applieff.setText(getappEffects(G.getCurrentChampion()));
	System.out.println("it should work");
	
	if(G.getCurrentChampion().getAbilities().get(0).getCurrentCooldown()!=0)
		ab1.setEnabled(false);
	else	
		ab1.setEnabled(true);
	
	if(G.getCurrentChampion().getAbilities().get(1).getCurrentCooldown()!=0)
		ab2.setEnabled(false);
	else	
		ab2.setEnabled(true);
	
	if(G.getCurrentChampion().getAbilities().get(2).getCurrentCooldown()!=0)
		ab3.setEnabled(false);
	else	
		ab3.setEnabled(true);
	
	}
	
	
	



	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == attack || e.getSource() == attack2 || e.getSource() == up || e.getSource() == up
				|| e.getSource() == down || e.getSource() == right || e.getSource() == left ||
				e.getSource() == castability || e.getSource() == useleaderab){
			tmpico = new ImageIcon(((ImageIcon) ((JButton)e.getSource()).getIcon()).getImage());
			((JButton)e.getSource()).setIcon(null);
			((JButton)e.getSource()).setText(((JButton)e.getSource()).getActionCommand());
			}
		if(e.getSource()== ab1){
			ab1.setText("Ability 1");
			Ability a = G.getCurrentChampion().getAbilities().get(0);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+	"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
			highlight(a, a.getCastArea());
		}
		if(e.getSource()== ab2){	
			ab2.setText("Ability 2");
			Ability a = G.getCurrentChampion().getAbilities().get(1);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+	"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
			highlight(a, a.getCastArea());
		}	
		if(e.getSource()== ab3){	
			ab3.setText("Ability 3");
			Ability a = G.getCurrentChampion().getAbilities().get(2);
			String temp = "Name: " + a.getName()+'\n'+
					"Mana Cost: "+a.getManaCost()+'\n'+
					"Base Cooldown: "+a.getBaseCooldown()+'\n'+
					"Cast Range: "+a.getCastRange()+'\n'+
					"Cast Area: "+a.getCastArea()+'\n'+	"Required Action Points: "+a.getRequiredActionPoints()+'\n';
			if(a instanceof CrowdControlAbility)
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName();
			else if (a instanceof DamagingAbility)
				temp += "Damage Amount: "+((DamagingAbility)a).getDamageAmount();
			else
				temp += "Healing Amount: "+((HealingAbility)a).getHealAmount();
			ability1stats.setText(temp);
			highlight(a, a.getCastArea());
		}	
		if(e.getSource()== useleaderab && useleaderab.isEnabled()){
			String temp=(leaderClicked)?"Use your Leader Ability?":"Ability Details...";
			if(G.getCurrentChampion() instanceof Hero)
				ability1stats.setText(temp+'\n'+'\n'
					+"Confirming will Remove all negative effects from the player’s entire team and adds an Embrace effect to them which lasts for 2 turns.");
			else if(G.getCurrentChampion() instanceof Villain)
				ability1stats.setText(temp+'\n'+'\n'+ "Confirming will Immediately eliminates (knocks out) all enemy champions with less than 30% healthpoints");
			else if(G.getCurrentChampion() instanceof AntiHero)
				ability1stats.setText(temp+'\n'+'\n'+ "Confirming will stun ALL Champions on the board for 2 turns EXCEPT for the Leaders of each team.");
		}	
		
			
//		for(int i =0 ; i<5; i++)
//			for(int j=0; j<5; j++){
//				if(e.getSource() == Gridbuttons[i][j] && !(G.getBoard()[i][j] instanceof Cover)&& G.getBoard()[i][j] !=null){
//						championstats=new JFrame();
//						String s = getStats((Champion)G.getBoard()[i][j]);
//						TextArea l = new TextArea(s);
//						l.setBounds(0,0,400,200);
//						//championstats.setUndecorated(true);
//						//int xloc= (MouseInfo.getPointerInfo().getLocation().x+210>width)?width-200:MouseInfo.getPointerInfo().getLocation().x+10 ;
//						//int yloc= (MouseInfo.getPointerInfo().getLocation().y+105>height)?height-100:MouseInfo.getPointerInfo().getLocation().y+5 ;
//						Point m = MouseInfo.getPointerInfo().getLocation();
//						championstats.setLocation(m.x, m.y);
//						championstats.setSize( 400, 200);
//						championstats.setVisible(true);
//						championstats.setAlwaysOnTop(true);
//						championstats.setFocusable(false);
//						//championstats.setUndecorated(true);
//						championstats.add(l);
					
//					JPanel pp=new JPanel();
//					pp.setLayout(new FlowLayout(0,1,FlowLayout.LEADING));
//					
//					championstats.add(pp, BorderLayout.CENTER);
//					pp.setBackground(Color.DARK_GRAY);
//					pp.setOpaque(true);
//					championstats.setOpacity(0.7f);
//								
//					JTextPane chStats = new JTextPane();
//		
//					chStats.setText("Stats:");
//					chStats.setPreferredSize(new Dimension(500,300));
//					chStats.setFont(new Font("Agency FB", Font.BOLD, 23));
//					chStats.setBackground(new Color(0x404040));
//					chStats.setForeground(Color.WHITE);
//					StyledDocument doc1 = chStats.getStyledDocument();				//Don't mind these here
//					SimpleAttributeSet center1 = new SimpleAttributeSet();
//					StyleConstants.setAlignment(center1, StyleConstants.ALIGN_CENTER);
//					doc1.setParagraphAttributes(0, doc1.getLength(), center1, false);
//					chStats.setEditable(false);
//					
//					
//					chStats.setText("Stats"+ '\n'+getStats((Champion)G.getBoard()[i][j]));
//					
//					
//					JTextPane appAB = new JTextPane();
//					appAB.setText("Effects:");
//					appAB.setPreferredSize(new Dimension(500,300));
//					appAB.setFont(new Font("Agency FB", Font.BOLD, 23));
//					appAB.setBackground(new Color(0x404040));
//					appAB.setForeground(Color.WHITE);
//					StyledDocument doc = appAB.getStyledDocument();				//Don't mind these here
//					SimpleAttributeSet center = new SimpleAttributeSet();
//					StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
//					doc.setParagraphAttributes(0, doc.getLength(), center, false);
//					appAB.setEditable(false);
//					appAB.setText("Effects: "+'\n'+ getappEffects((Champion)G.getBoard()[i][j]));
//					
//					pp.add(chStats);
//					pp.add(appAB);		
//					
					//championstats.setVisible(true);
					//championstats.setAlwaysOnTop(true);
					
				//}
			//}
			
			
			
		if(e.getSource() == attack)
			attackHighlight();
	}









	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == attack || e.getSource() == attack2 || e.getSource() == up || e.getSource() == up
				|| e.getSource() == down || e.getSource() == right || e.getSource() == left ||
				e.getSource() == castability || e.getSource() == useleaderab){
			((JButton)e.getSource()).setIcon(tmpico);;
			((JButton)e.getSource()).setText("");
		}
		else
			if(e.getSource()== ab1){	/*THEN*/ ab1.setText(ab1.getActionCommand());	ability1stats.setText("Ability Details..."+'\n'+'\n'
														+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
														if(ab1.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  clearHighlight();}
			else if(e.getSource()== ab2){	/*THEN*/ ab2.setText(ab2.getActionCommand());	ability1stats.setText("Ability Details..."+'\n'+'\n'
														+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
														if(ab2.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  clearHighlight();}
			else if(e.getSource()== ab3){	/*THEN*/ ab3.setText(ab3.getActionCommand());	ability1stats.setText("Ability Details..."+'\n'+'\n'
														+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
														if(ab3.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  clearHighlight();}
			//else if(e.getSource()== useleaderab /*&& leaderClicked*/) ability1stats.setText("Use your Leader Ability?");
		if(e.getSource()== useleaderab && !leaderClicked) ability1stats.setText("Ability Details...");
		if(e.getSource() == attack && !isAttackMode)
			clearHighlight();
		
		for(int i =0 ; i<5; i++)
			for(int j=0; j<5; j++){
				if(e.getSource() == Gridbuttons[i][j]){
						championstats.dispatchEvent(new WindowEvent(championstats, WindowEvent.WINDOW_CLOSING));
						alreadyclickedufuckingidiot = false;
				}
			}
	}







	@Override public void mouseClicked(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
	@Override public void mouseDragged(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
	

	
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
		turnorderT.setText("The Turn order:" +'\n' );
		while(!turnz.isEmpty())
			turnorderT.setText(turnorderT.getText()+'\n' +((Champion)turnz.remove()).getName());
		
		//Updating Abilities Button
		

		ab1.setActionCommand(G.getCurrentChampion().getAbilities().get(0).getName());
		ab1.setText(ab1.getActionCommand());
		if(G.getCurrentChampion().getAbilities().get(0).getCurrentCooldown()!=0)
			ab1.setEnabled(false);
		else	
			ab1.setEnabled(true);
		
		ab2.setActionCommand(G.getCurrentChampion().getAbilities().get(1).getName());
		ab2.setText(ab2.getActionCommand());
		if(G.getCurrentChampion().getAbilities().get(1).getCurrentCooldown()!=0)
			ab2.setEnabled(false);
		else	
			ab2.setEnabled(true);
		
		ab3.setActionCommand(G.getCurrentChampion().getAbilities().get(2).getName());
		ab3.setText(ab3.getActionCommand());
		if(G.getCurrentChampion().getAbilities().get(2).getCurrentCooldown()!=0)
			ab3.setEnabled(false);
		else	
			ab3.setEnabled(true);
		
		
		//Updating highlighting Champ Border:
		
		if(G.getCurrentChampion().getName().equals( champ1.getText() )){
			champ1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ2.setBorder(null);
			champ3.setBorder(null);
			champ4.setBorder(null);
			champ5.setBorder(null);
			champ6.setBorder(null);
		
		}
		else if(G.getCurrentChampion().getName().equals( champ3.getText() )){
			champ3.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ2.setBorder(null);
			champ1.setBorder(null);
			champ4.setBorder(null);
			champ5.setBorder(null);
			champ6.setBorder(null);
				
		}
		else if(G.getCurrentChampion().getName().equals( champ2.getText())){
			champ2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ1.setBorder(null);
			champ3.setBorder(null);
			champ4.setBorder(null);
			champ5.setBorder(null);
			champ6.setBorder(null);
		}			
					
			
		if (G.getCurrentChampion().getName().equals( champ4.getText()) ) {
			champ4.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ5.setBorder(null);
			champ6.setBorder(null);
			champ1.setBorder(null);
			champ2.setBorder(null);
			champ3.setBorder(null);
		} else if (G.getCurrentChampion().getName().equals( champ6.getText())) {
			champ6.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ4.setBorder(null);
			champ5.setBorder(null);
			champ1.setBorder(null);
			champ2.setBorder(null);
			champ3.setBorder(null);
		} else if (G.getCurrentChampion().getName().equals( champ5.getText())) {
			champ5.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			champ4.setBorder(null);
			champ6.setBorder(null);
			champ1.setBorder(null);
			champ2.setBorder(null);
			champ3.setBorder(null);
		}
		
	}
	
	public void resetbuttons(){
		if(ab1.getBackground()==Color.GREEN || ab2.getBackground()==Color.GREEN || ab3.getBackground()==Color.GREEN)
			ability1stats.setText("Ability applied...");
		castIsclicked=false;
		isAttackMode = false;
		leaderClicked= false;
		ab1.setVisible(false);
		ab2.setVisible(false);
		ab3.setVisible(false);
		up.setVisible(true);
		down.setVisible(true);
		right.setVisible(true);
		left.setVisible(true);
		attack.setVisible(true);
		attack2.setVisible(false);
		useleaderab.setVisible(true);
		endturn.setVisible(true);
		castability.setBackground(null);
		castability.setVisible(true);
		confirmability.setVisible(false);
		confirmleaderab.setVisible(false);
		ab1.setBackground(Color.DARK_GRAY);
		ab2.setBackground(Color.DARK_GRAY);
		ab3.setBackground(Color.DARK_GRAY);
		castability.setBackground(null);
	}
	
	private void clearHighlight(){
		for(int i =4; i>=0; i--)
			for(int j =0; j<5; j++)
				Gridbuttons[i][j].setBackground(null);
	}
	
	private void attackHighlight(){
		boolean first = false;
		
		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion()))
			first = true;
		Point []ls = new Point[4];
		//RIGHT:
			for(int i = 1; i <= G.getCurrentChampion().getAttackRange() && G.getCurrentChampion().getLocation().y+i < 5; i++){
				if(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+i] != null){
					if(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+i] instanceof Cover){
						Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y+i);
						ls[0]= l1;
						break;
					}
					else{
						if(first && G.getSecondPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+i])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y+i);
							ls[0]= l1;
							break;
						}
						else if(!first && G.getFirstPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+i])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y+i);
							ls[0]= l1;
							break;
						}
					}
				}
			}
		//LEFT:
			for(int i = 1; i <= G.getCurrentChampion().getAttackRange() && G.getCurrentChampion().getLocation().y-i >= 0; i++){
				if(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-i] != null){
					if(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-i] instanceof Cover){
						Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y-i);
						ls[1]= l1;
						break;
					}
					else{
						if(first && G.getSecondPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-i])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y-i);
							ls[1]= l1;
							break;
						}
						else if(!first && G.getFirstPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-i])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x, G.getCurrentChampion().getLocation().y-i);
							ls[1]= l1;
							break;
						}
					}
				}
			}
		//DOWN:
			for(int i = 1; i <= G.getCurrentChampion().getAttackRange() && G.getCurrentChampion().getLocation().x-i >= 0; i++){
				if(G.getBoard()[G.getCurrentChampion().getLocation().x-i][G.getCurrentChampion().getLocation().y] != null){
					if(G.getBoard()[G.getCurrentChampion().getLocation().x-i][G.getCurrentChampion().getLocation().y] instanceof Cover){
						Point l1 = new Point(G.getCurrentChampion().getLocation().x-i, G.getCurrentChampion().getLocation().y);
						ls[2]= l1;
						break;
					}
					else{
						if(first && G.getSecondPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x-i][G.getCurrentChampion().getLocation().y])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x-i, G.getCurrentChampion().getLocation().y);
							ls[2]= l1;
							break;
						}
						else if(!first && G.getFirstPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x-i][G.getCurrentChampion().getLocation().y])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x-i, G.getCurrentChampion().getLocation().y);
							ls[2]= l1;
							break;
						}
					}
				}
			}
			
		//UP:
			for(int i = 1; i <= G.getCurrentChampion().getAttackRange() && G.getCurrentChampion().getLocation().x+i < 5; i++){
				if(G.getBoard()[G.getCurrentChampion().getLocation().x+i][G.getCurrentChampion().getLocation().y] != null){
					if(G.getBoard()[G.getCurrentChampion().getLocation().x+i][G.getCurrentChampion().getLocation().y] instanceof Cover){
						Point l1 = new Point(G.getCurrentChampion().getLocation().x+i, G.getCurrentChampion().getLocation().y);
						ls[3]= l1;
						break;
					}
					else{
						if(first && G.getSecondPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x+i][G.getCurrentChampion().getLocation().y])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x+i, G.getCurrentChampion().getLocation().y);
							ls[3]= l1;
							break;
						}
						else if(!first && G.getFirstPlayer().getTeam().contains(G.getBoard()[G.getCurrentChampion().getLocation().x+i][G.getCurrentChampion().getLocation().y])){
							Point l1 = new Point(G.getCurrentChampion().getLocation().x+i, G.getCurrentChampion().getLocation().y);
							ls[3]= l1;
							break;
						}
					}
				}
			}
		for(int i =0; i<4;i++)
			if(ls[i]!=null)
				Gridbuttons[ls[i].x][ls[i].y].setBackground(asfarika);
	}
	
	private void highlight(Ability a, AreaOfEffect e){
		System.out.println("high");
		System.out.println("highlight");
		int oldaction = G.getCurrentChampion().getCurrentActionPoints();
		int oldmana = G.getCurrentChampion().getMana();
		G.getCurrentChampion().setCurrentActionPoints(999);
		G.getCurrentChampion().setMana(999);
		
		switch(e)
		{
		case TEAMTARGET :
		case SURROUND :	
		case SELFTARGET :
			try {
				targets = G.castAbility(a);
			} catch (NotEnoughResourcesException | AbilityUseException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
			}
			break;
		case DIRECTIONAL :
			System.out.println("directional");
			try {
				targets = G.castAbility(a, Direction.UP);
				for(int i = 0;i<targets.size();i++)
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(asfarika);
			} catch (NotEnoughResourcesException | AbilityUseException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
			}
			try {
				targets = G.castAbility(a, Direction.DOWN);
				for(int i = 0;i<targets.size();i++)
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(asfarika);
			} catch (NotEnoughResourcesException | AbilityUseException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
			}
			try {
				targets = G.castAbility(a, Direction.RIGHT);
				for(int i = 0;i<targets.size();i++)
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(asfarika);
			} catch (NotEnoughResourcesException | AbilityUseException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
			}
			try {
				targets = G.castAbility(a, Direction.LEFT);
				for(int i = 0;i<targets.size();i++)
					Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(asfarika);
			} catch (NotEnoughResourcesException | AbilityUseException
					| CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
			}
			break;
		case SINGLETARGET:
			Object[][] board = G.getBoard();
			targets = new ArrayList<Damageable>();
			for(int i = 4;i>=0;i--)
				for(int j = 0;j<5;j++)
				{
					ArrayList<Champion> enemy = new ArrayList<Champion>(); 
					Point l = G.getCurrentChampion().getLocation();
					int range = Math.abs(l.x-i) + Math.abs(l.y-j);
					if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion()))
					   enemy = G.getSecondPlayer().getTeam();
					else
						enemy = G.getFirstPlayer().getTeam();
					if(range<=a.getCastRange() && board[i][j] instanceof Champion && enemy.contains((Champion)board[i][j]))
						targets.add((Damageable)board[i][j]);
				}
			break;
		}
		for(int i = 0;i<targets.size();i++)
			Gridbuttons[targets.get(i).getLocation().x][targets.get(i).getLocation().y].setBackground(asfarika);
		
		G.getCurrentChampion().setCurrentActionPoints(oldaction);
		G.getCurrentChampion().setMana(oldmana);
	}
	
	private void cleanGrid(){
		for(int i =4; i>=0; i--)
			for(int j =0; j<5; j++){
				Object[][] board = G.getBoard();
				if(board[i][j] instanceof Champion)
					Gridbuttons[i][j].setIcon(	new ImageIcon(((((Champion)board[i][j])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));
				else if(board[i][j] instanceof Cover)
					Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)board[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				else
					Gridbuttons[i][j].setIcon(null);
		}
	}

	private void updateStats(){
		
	//stats TextArea Update
		Champion c = G.getCurrentChampion();
		stats1.setText(getStats(c));	
		
	//Grid Stats Update
		
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				if(G.getBoard()[i][j] != null)
					if(G.getBoard()[i][j] instanceof Cover)
						Gridbuttons[i][j].setText("HP: "+ ((Cover)G.getBoard()[i][j]).getCurrentHP() );
					else
						Gridbuttons[i][j].setText("<html>" + ((Champion)G.getBoard()[i][j]).getName() + "<br>" + "HP:"+ ((Champion)G.getBoard()[i][j]).getCurrentHP() + "</html>" );
				else
					Gridbuttons[i][j].setText("");
	
	}
	
	private String getappEffects(Champion c){
		String ret="";
		for(Effect e : c.getAppliedEffects()){
			ret+= "[] Effect: "+e.getName()+", "+e.getDuration()+" Turn(s), "+e.getType()+'\n';
		}
		
		return "Applied Effects: \n"+ret;
	}
	
	private String getStats(Champion c){
		String ret="";
		String type ="";
		if(c instanceof Hero)
			type = "Hero";
		else if(c instanceof Villain)
			type = "Villain";
		else
			type = "Antihero";
		ret= ("Name: "+c.getName()+'\n'
				+"HP: "+c.getCurrentHP()+'\n'
				+"Mana: "+c.getMana()+'\n'
				+"Speed: "+c.getSpeed()+'\n'
				+"Action Points: "+c.getCurrentActionPoints()+'\n'
				+"Type: "+type+'\n'
				+"Normal Attack Damage: "+ c.getAttackDamage()+'\n'
				+"Maximum Range: " + c.getAttackRange()+'\n'
				+"Current State: "+ c.getCondition());	
		
		if(c == G.getCurrentChampion())
			return "Current Champion Stats: \n"+ret;
		else
			return ret;
	}
	
	
}
