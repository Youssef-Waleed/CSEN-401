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
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
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


public class MainGUI implements ActionListener, MouseInputListener, ListSelectionListener, KeyListener, WindowListener  {
	private int width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),height= (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private JFrame gameframe, championstats= new JFrame();
	private boolean castIsclicked=false, isAttackMode= false, leaderClicked = false,alreadyclickedufuckingidiot = false,noneed;
	private Game G;
	private Color asfarika = new Color(251, 252, 136,150), a7marika = new Color(232, 173, 173,180), azra2ika= new Color(173, 197, 232, 180);
	private ArrayList<Damageable> targets;
	private ImageIcon icon, tmpico, leaderbordericon= new ImageIcon("icons/leadercrown.png")
								, defaultbordericon = new ImageIcon("icons/defaultborder.png")
	,bkgimg= new ImageIcon(new ImageIcon("icons/game-background.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)) ;
	private JLabel gamebackground, champ1, champ2, champ3, champ4, champ5, champ6,
								champ1border, champ2border, champ3border, champ4border,champ5border,champ6border, redteam, blueteam, leaderab1, leaderab2;
	private JTextArea turnorderT,applieff;
	private JTextPane ability1stats, stats1;
	private JPanel info,main, container, current, actions,charc;
	private JLayeredPane game,  bkg;
	private JButton up,down,right,left,attack,attack2,castability,useleaderab,endturn ,ab1 =new JButton() ,ab2=new JButton() ,ab3=new JButton(), confirmability, confirmleaderab
				/*,b11,b12,b13,b14,b15,
					b21,b22,b23,b24,b25,
					b31,b32,b33,b34,b35,
					b41,b42,b43,b44,b45,
					b51,b52,b53,b54,b55*/;
	private JLabel[][] Gridbuttons;
	private Toolkit toolkit;
	private Cursor c;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private ArrayList<Champion> leadertargs;
	private Media audio , record;
	private Timer timer1;
	private ActionListener al;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
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
		p1.setLeader(availableChampions.get(0));				//EXAMPLE players
		p1.getTeam().add(availableChampions.get(1));
		p1.getTeam().add(availableChampions.get(2));
		p1.getTeam().get(0).setCurrentHP(0);
		p1.getTeam().get(1).setCurrentHP(0);
		p1.getTeam().get(2).setCurrentHP(0);
		p1.getLeader().setCurrentHP(5000);			
		
		p2.getTeam().add(availableChampions.get(3));
		p2.setLeader(availableChampions.get(3));
		p2.getLeader().setCurrentHP(0);
		p2.getTeam().add(availableChampions.get(4));
		p2.getTeam().add(availableChampions.get(5));
		p2.getTeam().get(0).setCurrentHP(1);
		p2.getTeam().get(1).setCurrentHP(1);
		p2.getTeam().get(2).setCurrentHP(1);
		MainGUI bla = new MainGUI(new Game( p1 , p2 ));
		
		
//		JFrame test = new JFrame();
//		test.setSize(700,700);
//		test.setLayout(null);
//		JLabel lll= new JLabel(new ImageIcon(new ImageIcon("icons/Loki.png").getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH)));JLabel pic = new JLabel(new ImageIcon(new ImageIcon("icons/game-background.jpg").getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));
//		lll.setText("bla bla");
//		lll.setPreferredSize(new Dimension(700,400));
//		lll.setFont(new Font("", Font.BOLD, 39));
//		lll.setHorizontalAlignment(JLabel.CENTER);
//		lll.setForeground(Color.WHITE);
//		lll.setOpaque(true);
//		lll.setBackground(new Color(255,255,55,0));
//		lll.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
//		lll.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		
//		lll.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				
//				System.out.println("Hello");
//			}@Override public void mousePressed(MouseEvent arg0) {}@Override public void mouseExited(MouseEvent arg0) {}@Override public void mouseEntered(MouseEvent arg0) {}@Override public void mouseClicked(MouseEvent arg0) {}
//		});
//		JPanel ppp= new JPanel();
//		JPanel ppp2= new JPanel();
//		ppp.setBounds(0,0,700,700);
//		ppp2.setBackground(new Color(255,255,255,0));
//		ppp2.setBounds(0,150,700,700);
//		
//		test.add(ppp2);
//		test.add(ppp);
//		ppp.add(pic);
//		ppp2.add(lll);
//		test.setVisible(true);
//		test.validate();
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
		record = new Media("audios/transition.wav" ,false);
		G = newGame;
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
		
		audio = new Media("audios/maingame.wav" , true);
	    audio.play();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		icon = new ImageIcon("icons/Marvel_Logo.png");
		ImageIcon upicon = new ImageIcon("icons/up-arrow.png");
		ImageIcon downicon = new ImageIcon("icons/down-arrow.png");
		ImageIcon righticon = new ImageIcon("icons/right-arrow.png");
		ImageIcon lefticon = new ImageIcon("icons/left-arrow.png");
		ImageIcon attackicon = new ImageIcon("icons/attack.png");
		ImageIcon casticon = new ImageIcon("icons/ability.png");
		ImageIcon leadicon = new ImageIcon("icons/wizard.png");
		
		
		
		gameframe = new JFrame();
		gameframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		gameframe.setLayout(new BorderLayout()/*null*/ );
		gameframe.setSize(width+1, height);
		gameframe.setLocation(1920/2-width/2,0);
		//gameframe.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		gameframe.setTitle("Marvel: Ultimate War");
		gameframe.setIconImage(icon.getImage());
		gameframe.addMouseListener(this);
		gameframe.addKeyListener(this);
		gameframe.setFocusable(true);
		gameframe.addWindowListener(this);
		JLabel l = new JLabel("i work");
		l.setBounds(0,0,90,10);
		//gameframe.add(l);
		
		game = new JLayeredPane();
		game.setBounds(0, 0, width, height);
		game.setLayout(new BorderLayout());
		game.setOpaque(false);
		game.setBackground(new Color(0,0,255,0));
		game.setVisible(true);
		
		
		
//========================================================================================================================
		//											PANELS
		
		
		main = new JPanel();			//the new Panels
		GridLayout laylay = new GridLayout(5,5);
		laylay.setHgap(5);
		laylay.setVgap(5);
		main.setLayout(laylay);
		main.setVisible(true);
		main.setOpaque(false);
		main.setBackground(new Color(0,0,0,0));
		main.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		info = new JPanel();
		info.setBackground(new Color(53, 53, 53));
		info.setPreferredSize(new Dimension(500, 600));
		info.setLayout(new BorderLayout());
		info.setVisible(true);
		
		container = new JPanel();
		container.setBackground(new Color(255,255,255));
		container.setPreferredSize(new Dimension(600, 200));
		container.setLayout(new BorderLayout());
		container.setVisible(true);
		
		current = new JPanel();
		current.setBackground(new Color(53, 53, 53));
		current.setPreferredSize(new Dimension(800, 300));
		current.setLayout(null);
		current.setVisible(true);
		
		
		actions = new JPanel();
		actions.setBackground(new Color(53, 53, 53));
		actions.setPreferredSize(new Dimension(500, 325));
		actions.setLayout(null);
		actions.setVisible(true);
		
		charc = new JPanel();
		charc.setBackground(new Color(157, 46, 24 ));
		charc.setPreferredSize(new Dimension(200, 600));
		charc.setLayout(null);
		charc.setVisible(true);
		info.add(charc,BorderLayout.CENTER);
		
		
		bkg= new JLayeredPane();
		bkg.setLayout(null);
		bkg.setOpaque(false);
		bkg.setBounds(0, 0, width, height);
		
		
		game.add(current, BorderLayout.SOUTH);
		game.add(main, BorderLayout.CENTER);
		game.add(container, BorderLayout.EAST);
		
		container.add(actions, BorderLayout.SOUTH);
		container.add(info, BorderLayout.CENTER);
		
		game.setBounds(0, 0, width, height);
		bkg.setBounds(0, 0, width, height);
		//bkg.add(game, Integer.valueOf(99));
		
		
	//------------------------------------------------LABELS----------------------------------------------------------	
		champ1 = new JLabel();
		champ1.setOpaque(true);
		champ1.setBackground(Color.LIGHT_GRAY);
		champ2 = new JLabel();
		champ2.setOpaque(true);
		champ2.setBackground(Color.LIGHT_GRAY);
		champ3 = new JLabel();
		champ3.setOpaque(true);
		champ3.setBackground(Color.LIGHT_GRAY);
		champ4 = new JLabel();
		champ4.setOpaque(true);
		champ4.setBackground(Color.LIGHT_GRAY);
		champ5 = new JLabel();
		champ5.setOpaque(true);
		champ5.setBackground(Color.LIGHT_GRAY);
		champ6 = new JLabel();
		champ6.setOpaque(true);
		champ6.setBackground(Color.LIGHT_GRAY);
		
		champ1border = new JLabel();
		champ1border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ1border.setBounds(37, 27, 117,117);
		
		champ2border = new JLabel();		//Leader 1
		champ2border.setIcon(new ImageIcon(leaderbordericon.getImage().getScaledInstance(155,155,Image.SCALE_SMOOTH)));
		champ2border.setBounds(18, 18+100+10, 155,155);  

		champ3border = new JLabel();
		champ3border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ3border.setBounds(37, 27+100+120+20, 117,117);

		champ4border = new JLabel();
		champ4border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ4border.setBounds(35+205, 27, 117,117);

		champ5border = new JLabel();		// Leader 2
		champ5border.setIcon(new ImageIcon(leaderbordericon.getImage().getScaledInstance(155,155,Image.SCALE_SMOOTH)));
		champ5border.setBounds(16+205+5, 18+100+10,155,155);

		champ6border = new JLabel();
		champ6border.setIcon(new ImageIcon(defaultbordericon.getImage().getScaledInstance(117,117,Image.SCALE_SMOOTH)));
		champ6border.setBounds(35+205, 27+100+120+20, 117,117);
		
//		champ1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ2.setBorder(BorderFactory.createLineBorder(Color.green, 3));
//		champ3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
//		champ5.setBorder(BorderFactory.createLineBorder(Color.green, 3));
//		champ6.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		
		champ1.setBounds(60, 50, 70,70);
		champ2.setBounds(50, 46+100+15 ,90,90);  //Leader 1
		champ3.setBounds(60, 50+100+120+20, 70,70);
		champ4.setBounds(60+204, 50, 70,70);
		champ5.setBounds(50+204+5, 46+100+12, 90,90);  // Leader 2
		champ6.setBounds(60+204, 50+100+120+20, 70,70);
		
				//sub-labels
				JLabel pONE = new JLabel(G.getFirstPlayer().getName(), SwingConstants.CENTER);
				JLabel pTWO = new JLabel(G.getSecondPlayer().getName(), SwingConstants.CENTER);
				pONE.setFont(new Font("Agency FB", Font.BOLD, 20));
				pONE.setForeground(Color.WHITE);
				pTWO.setFont(new Font("Agency FB", Font.BOLD, 20));
				pTWO.setForeground(Color.WHITE);
				pONE.setBounds(10, 0, 180, 50);
				pTWO.setBounds(210, 0, 180, 50);
		
				
				
		redteam= new JLabel();
		redteam.setOpaque(true);
		redteam.setBackground(a7marika);
		redteam.setBounds(0, 0, 200, 600);
		
		blueteam= new JLabel();
		blueteam.setOpaque(true);
		blueteam.setBackground(azra2ika);
		blueteam.setBounds(200, 0, 200, 600);
		
		gamebackground = new JLabel(new ImageIcon(bkgimg.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
		gamebackground.setBounds(0,0,width,height);
		gameframe.getContentPane().add(gamebackground, BorderLayout.CENTER);
		gameframe.setContentPane(gamebackground);

		gameframe.getContentPane().add(game, BorderLayout.CENTER);
		
		leaderab1 = new JLabel("Leader Ability: Available");
		leaderab1.setBounds(30, 370, 150, 50);
		leaderab1.setFont(new Font("Agency FB", Font.BOLD, 20));
		leaderab1.setForeground(Color.WHITE);
		leaderab2 = new JLabel("Leader Ability: Available");
		leaderab2.setBounds(230, 370, 150, 50);
		leaderab2.setFont(new Font("Agency FB", Font.BOLD, 20));
		leaderab2.setForeground(Color.WHITE);
		
		
		charc.add(leaderab1);
		charc.add(leaderab2);
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
		charc.add(redteam);
		charc.add(blueteam);
		
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
		turnorderT.setForeground(Color.WHITE);
		turnorderT.setBackground(new Color(74, 74, 74));
		turnorderT.setEditable(false);
		turnOrderSetText();
		info.add(turnorderT, BorderLayout.EAST);
		
																//TEXTPANE
		ability1stats = new JTextPane();
		ability1stats.setText("Ability Details...");
		ability1stats.setBounds(width-550*width/1920,0,500,300);
		ability1stats.setFont(new Font("Agency FB", Font.BOLD, 25));
		ability1stats.setBackground(new Color(74, 74, 74));
		ability1stats.setForeground(Color.WHITE);
		StyledDocument doc = ability1stats.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		ability1stats.setEditable(false);
		//System.out.println(getappEffects(G.getCurrentChampion()));
		applieff = new JTextArea(getappEffects(G.getCurrentChampion()));
		applieff.setFont(new Font("Agency FB", Font.BOLD, 25));
		applieff.setBackground(new Color(64,64,64));
		applieff.setForeground(Color.WHITE);
		applieff.setBounds(305, 0, 300, 300);
		stats1 = new JTextPane();
		stats1.setText("Current Champion Stats:");
		stats1.setBounds(0,0,300,300);
		stats1.setFont(new Font("Agency FB", Font.BOLD, 24));
		stats1.setBackground(new Color(64,64,64));
		stats1.setForeground(Color.WHITE);
		StyledDocument doc1 = stats1.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center1, StyleConstants.ALIGN_LEFT);
		doc1.setParagraphAttributes(0, doc1.getLength(), center1, false);
		stats1.setEditable(false);
		
//		leaderab1 = new JLabel(G.getFirstPlayer().getName()+"'s Leader Ability: Available");
//		leaderab1.setBounds(610, 0, 500, 50);
//		leaderab1.setFont(new Font("Agency FB", Font.BOLD, 30));
//		leaderab1.setForeground(Color.WHITE);
//		leaderab2 = new JLabel(G.getSecondPlayer().getName()+"'s Leader Ability: Available");
//		leaderab2.setBounds(610, 55, 500, 50);
//		leaderab2.setFont(new Font("Agency FB", Font.BOLD, 30));
//		leaderab2.setForeground(Color.WHITE);
		
		current.add(stats1);
		current.add(ability1stats);
		current.add(applieff);
		//current.add(leaderab1);
		//current.add(leaderab2);
		
		
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
		ab1.setBackground(new Color(74, 74, 74));
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
		ab2.setBackground(new Color(74, 74, 74));
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
		ab3.setBackground(new Color(74, 74, 74));
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
		
		
		
		
		Gridbuttons = new JLabel[5][5];
		for(int i =4; i>=0; i--)
			for(int j =0; j<5; j++){
				Gridbuttons[i][j]= new JLabel();
				Gridbuttons[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 13));
				Gridbuttons[i][j].setForeground(Color.WHITE);
				Gridbuttons[i][j].setVerticalTextPosition(JLabel.BOTTOM);
				Gridbuttons[i][j].setHorizontalTextPosition(JLabel.CENTER);
				Gridbuttons[i][j].setHorizontalAlignment(JLabel.CENTER);
				Gridbuttons[i][j].setVerticalAlignment(JLabel.CENTER);
				Gridbuttons[i][j].setOpaque(true);
				Gridbuttons[i][j].setBackground(new Color(0,0,0,0));
				Gridbuttons[i][j].setFocusable(false);
				Gridbuttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
				Gridbuttons[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
				//Gridbuttons[i][j].addActionListener(this);
				Gridbuttons[i][j].addMouseListener(this);
				//Gridbuttons[i][j].setBackground(new Color(0xA2CCE8));
				Object[][] board = G.getBoard();
				if(board[i][j] instanceof Champion)
					Gridbuttons[i][j].setIcon(	new ImageIcon(((((Champion)board[i][j])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));				//((Champion)board[i][j]).getIcon());
				else if(board[i][j] instanceof Cover)
					Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)board[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));			//((Cover)board[i][j]).getIcon());
				main.add(Gridbuttons[i][j]);
			}
		

		main.setOpaque(true);
		main.setBackground(new Color(0,0,0,0));
		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion())){
			toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("icons/p1cursor.png");
			c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
			gameframe.setCursor (c);
		}
		else{
			toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("icons/p2cursor.png");
			c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
			gameframe.setCursor (c);
		}
			
		
		clearHighlight();

		device.setFullScreenWindow(gameframe);		//Fullscreen capability
		gameframe.setVisible(true);
		gameframe.revalidate();
		gameframe.setExtendedState(JFrame.ICONIFIED);
		gameframe.setExtendedState(JFrame.NORMAL);
		gameframe.setResizable(false);
		
		updateStats();
		

			al=new ActionListener() { public void actionPerformed(ActionEvent ae) {

				gameframe.revalidate();
				gamebackground.setIcon(null);
				gamebackground.setIcon(bkgimg);
		
			}
			};


		timer1 = new Timer(1, al);
		//timer.setInitialDelay(500);
		timer1.start();

	}


	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//============================================================================================================================================
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		noneed=false;
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
		}/*TRIAL*/else
		


//--------------------------------------------------- A T T A C K  1-----------------------------------------------------------------
					
					
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
		}/*TRIAL*/else
			
			
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
				showErrorMessage(e1.getMessage());
			} catch (UnallowedMovementException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x-1][G.getCurrentChampion().getLocation().y].setIcon(null);
			    noneed = true;
			}
			clearHighlight();
		}/*TRIAL*/else
		if(e.getSource()==down&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.DOWN);
			} catch (NotEnoughResourcesException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			} catch (UnallowedMovementException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x+1][G.getCurrentChampion().getLocation().y].setIcon(null);
			    noneed = true;
			}
			clearHighlight();
		}/*TRIAL*/else
		if(e.getSource()==right&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.RIGHT);
				System.out.println("hehehehehe");
			} catch (NotEnoughResourcesException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			} catch (UnallowedMovementException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-1].setIcon(null);
			    noneed = true;
			}
			clearHighlight();
		}/*TRIAL*/else
		if(e.getSource()==left&&!castIsclicked && !attack2.isVisible())
		{
			resetbuttons();
			boolean no = false;
			System.out.println(G.getCurrentChampion().getLocation().x +" "+ G.getCurrentChampion().getLocation().y);
			try {
				G.move(Direction.LEFT);
				System.out.println("hehehehehe");
			} catch (NotEnoughResourcesException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			} catch (UnallowedMovementException e1) {
				no = true;
				showErrorMessage(e1.getMessage());
			}
			if(!no){
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y].setIcon(new ImageIcon((G.getCurrentChampion().getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
			    Gridbuttons[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+1].setIcon(null);
			    noneed = true;
			}
			clearHighlight();
		}/*TRIAL*/else
		
		if(e.getSource()==up&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(0);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.UP);
				} else if(ab2.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(1);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.UP);
				}else if(ab3.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(2);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.UP);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
					{
						Object[][] board = G.getBoard();
						if(board[d.getLocation().x][d.getLocation().y] instanceof Cover){
							((Cover)board[d.getLocation().x][d.getLocation().y]).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
						}
					}
					cleanGrid(targets);
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					showErrorMessage("Please choose an ability");
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			}
		}/*TRIAL*/else
		if(e.getSource()==down&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(0);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.DOWN);
				} else if(ab2.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(1);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.DOWN);
				}else if(ab3.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(2);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.DOWN);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
					{
						Object[][] board = G.getBoard();
						if(board[d.getLocation().x][d.getLocation().y] instanceof Cover){
							((Cover)board[d.getLocation().x][d.getLocation().y]).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
						}
					}
					cleanGrid(targets);
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					showErrorMessage("Please choose an ability");
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			}
		}/*TRIAL*/else
		
		if(e.getSource()==right&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(0);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.RIGHT);
				} else if(ab2.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(1);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.RIGHT);
				}else if(ab3.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(2);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.RIGHT);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
					{
						Object[][] board = G.getBoard();
						if(board[d.getLocation().x][d.getLocation().y] instanceof Cover){
							((Cover)board[d.getLocation().x][d.getLocation().y]).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
						}
					}
					cleanGrid(targets);
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					showErrorMessage("Please choose an ability");
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			}
		}/*TRIAL*/else
		
		if(e.getSource()==left&&castIsclicked){
			try {
				Ability a = null;
				if(ab1.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(0);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(0),Direction.LEFT);
				} else if(ab2.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(1);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(1),Direction.LEFT);
				}else if(ab3.getBackground()== Color.GREEN){
					a=G.getCurrentChampion().getAbilities().get(2);
					targets = G.castAbility(G.getCurrentChampion().getAbilities().get(2),Direction.LEFT);
				}
				if(a!=null){
					G.FinishHim(targets, a);
					for(Damageable d: targets)
					{
						Object[][] board = G.getBoard();
						if(board[d.getLocation().x][d.getLocation().y] instanceof Cover){
							((Cover)board[d.getLocation().x][d.getLocation().y]).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
						}
					}
					cleanGrid(targets);
					resetbuttons();
					castIsclicked=false;
					clearHighlight();
					}
				else
					showErrorMessage("Please choose an ability");
			} catch (NotEnoughResourcesException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (AbilityUseException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				showErrorMessage(e1.getMessage());
			}
		}/*TRIAL*/else
		

		if(e.getSource()==up && attack2.isVisible()){
			ArrayList<Damageable> targets = null;
			try {
				targets = G.attack(Direction.UP);
				Object[][] board = G.getBoard();
				if(targets.get(0) instanceof Cover){
					((Cover)targets.get(0)).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
					Gridbuttons[targets.get(0).getLocation().x][targets.get(0).getLocation().y].setIcon( new ImageIcon((((Cover)targets.get(0)).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				}
				
				//targets.add((Damageable)board[G.getCurrentChampion().getLocation().x+1][G.getCurrentChampion().getLocation().y]);
				cleanGrid(targets);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				showErrorMessage(e1.getMessage());
			}catch(NullPointerException | ArrayIndexOutOfBoundsException npe){
				showErrorMessage("Target is invalid. Cannot attack outside the board.");
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}/*TRIAL*/else
		
		if(e.getSource()==down && attack2.isVisible()){
			ArrayList<Damageable> targets = null;
			try {
				targets = G.attack(Direction.DOWN);
				Object[][] board = G.getBoard();
				if(targets.get(0) instanceof Cover){
					((Cover)targets.get(0)).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
					Gridbuttons[targets.get(0).getLocation().x][targets.get(0).getLocation().y].setIcon( new ImageIcon((((Cover)targets.get(0)).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				}
				
				//targets.add((Damageable)board[G.getCurrentChampion().getLocation().x-1][G.getCurrentChampion().getLocation().y]);
				cleanGrid(targets);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				showErrorMessage(e1.getMessage());
			}catch(NullPointerException | ArrayIndexOutOfBoundsException npe){
				showErrorMessage("Target is invalid. Cannot attack outside the board.");
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}/*TRIAL*/else
		

		if(e.getSource()==right && attack2.isVisible()){
			ArrayList<Damageable> targets = null;
			try {
				targets = G.attack(Direction.RIGHT);
				Object[][] board = G.getBoard();
				if(targets.get(0) instanceof Cover){
					((Cover)targets.get(0)).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
					Gridbuttons[targets.get(0).getLocation().x][targets.get(0).getLocation().y].setIcon( new ImageIcon((((Cover)targets.get(0)).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				}
				
				//targets.add((Damageable)board[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y+1]);
				cleanGrid(targets);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				showErrorMessage(e1.getMessage());
			}catch(NullPointerException | ArrayIndexOutOfBoundsException npe){
				showErrorMessage("Target is invalid. Cannot attack outside the board.");
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}/*TRIAL*/else
		

		if(e.getSource()==left && attack2.isVisible()){
			ArrayList<Damageable> targets = null;
			try {
				targets = G.attack(Direction.LEFT);
				Object[][] board = G.getBoard();
				if(targets.get(0) instanceof Cover){
					((Cover)targets.get(0)).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
					Gridbuttons[targets.get(0).getLocation().x][targets.get(0).getLocation().y].setIcon( new ImageIcon((((Cover)targets.get(0)).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				}
				
				//targets.add((Damageable)board[G.getCurrentChampion().getLocation().x][G.getCurrentChampion().getLocation().y-1]);
				cleanGrid(targets);
			} catch (NotEnoughResourcesException | ChampionDisarmedException
					| InvalidTargetException e1) {
				showErrorMessage(e1.getMessage());
			}catch(NullPointerException | ArrayIndexOutOfBoundsException npe){
				showErrorMessage("Target is invalid. Cannot attack outside the board.");
			}
			clearHighlight();
			resetbuttons();
			updateStats();
		}/*TRIAL*/else
		
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
	}/*TRIAL*/else
		
		
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
		}/*TRIAL*/else
		
		
	//---------------------------------------------THE CONFIRM ABILITYY BUTTON----------------------------------------------------------------------
		
		
		
		if(e.getSource()== confirmability && ab1.getBackground() != Color.GREEN 
																&& ab2.getBackground() != Color.GREEN 
																&& ab3.getBackground() != Color.GREEN){
			showErrorMessage("Please choose an ability");
			return;
		}/*TRIAL*/else
		
		if(e.getSource()== confirmability){
			confirmability.setVisible(false);
			Ability a = null;
			if(ab1.getBackground()== Color.GREEN){
				a=G.getCurrentChampion().getAbilities().get(0);
			} else if(ab2.getBackground()== Color.GREEN){
				a=G.getCurrentChampion().getAbilities().get(1);
			}else if(ab3.getBackground()== Color.GREEN){
				a=G.getCurrentChampion().getAbilities().get(2);
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
				//clearHighlight();
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
				{
					Object[][] board = G.getBoard();
					if(board[d.getLocation().x][d.getLocation().y] instanceof Cover){
						((Cover)board[d.getLocation().x][d.getLocation().y]).setIcon(new ImageIcon("icons/ProfessionalCover.png"));
					}
				}
				cleanGrid(targets);
				resetbuttons();
				castIsclicked=false;
			} catch (NotEnoughResourcesException e1) {
				showErrorMessage(e1.getMessage());
				castIsclicked=false;
			} catch (AbilityUseException e1) {
				showErrorMessage(e1.getMessage());
				castIsclicked=false;
			} catch (CloneNotSupportedException e1) {
				showErrorMessage(e1.getMessage());
				castIsclicked=false;
			}
		
		}
			else if(a.getCastArea() == AreaOfEffect.SINGLETARGET){
				ability1stats.setText("Please choose a target on the board!");
				ab1.setVisible(false);
				ab2.setVisible(false);
				ab3.setVisible(false);
			}
		}/*TRIAL*/else
		
		if(e.getSource()==confirmleaderab && leaderClicked){
			try {
				G.useLeaderAbility();
				ability1stats.setText("Leader Ability Used");
				resetbuttons();
				clearHighlight();
				leadertargs = null;
				leaderClicked = false;
				if(G.getFirstPlayer().getLeader() == G.getCurrentChampion())
					leaderab1.setText("Leader Ability: Used");
				else
					leaderab2.setText("Leader Ability: Used");
			} catch (LeaderNotCurrentException e1) {
				
			} catch (LeaderAbilityAlreadyUsedException e1) {
				resetbuttons();
				clearHighlight();
				leadertargs = null;
				leaderClicked = false;
				ability1stats.setText("Ability Details...");
				showErrorMessage(e1.getMessage());
				
			}
		}/*TRIAL*/else
		
		
	

		
//--------------------------------------------------- A T T A C K  2-----------------------------------------------------------------
		
		
//		if(e.getSource()==attack){
//			castability.setVisible(false);
//			confirmability.setVisible(false);
//			useleaderab.setVisible(false);
//			endturn.setVisible(false);
//			ab1.setVisible(false);
//			ab2.setVisible(false);
//			ab3.setVisible(false);
//			up.setVisible(true);
//			down.setVisible(true);
//			right.setVisible(true);
//			left.setVisible(true);
//			isAttackMode=true;
//			attack.setVisible(false);
//			attack2.setVisible(true);
//		}/*TRIAL*/else
		if(e.getSource()==attack2){
			clearHighlight();
			resetbuttons();
			attack.setVisible(true);
			attack2.setVisible(false);
			
		}/*TRIAL*/else
		
		
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
//			Image image = toolkit.getImage("icons/p1cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
//		else{
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage("icons/p2cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
	

//		if(G.getFirstPlayer().getTeam().contains(G.getCurrentChampion())){
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage("icons/p1cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
//		else{
//			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			Image image = toolkit.getImage("icons/p2cursor.png"));
//			Cursor c = toolkit.createCustomCursor(image , new Point(gameframe.getX(), gameframe.getY()), "img");
//			gameframe.setCursor (c);
//		}
	}
	

	
	
//------------------------------------------Generic after every action -----------------------------------------------
	
	updateStats();
	//cleanGrid();
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
	if(G.checkGameOver()!=null)
	{
		
		showErrorMessage("Gameover, buddy. it's over go home");
	}
	

	gameframe.revalidate();
	gamebackground.setIcon(null);
	gamebackground.setIcon(bkgimg);
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
			//highlight(a, a.getCastArea());
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
			//highlight(a, a.getCastArea());
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
			//highlight(a, a.getCastArea());
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
		
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
			  if(e.getSource() == Gridbuttons[i][j])
				if (!(G.getBoard()[i][j] instanceof Cover) && G.getBoard()[i][j] != null && !alreadyclickedufuckingidiot) {
					championstats = new JFrame();
					championstats.setUndecorated(true);
					championstats.setOpacity(0.9f);
					championstats.setAlwaysOnTop(true);
					String s = getStats((Champion) G.getBoard()[i][j]);
					String eff = getappEffects((Champion) G.getBoard()[i][j]);
					TextArea stats = new TextArea(s);
					TextArea effects = new TextArea(
							getappEffects((Champion) G.getBoard()[i][j]));
					stats.setBounds(0, 0, 300, 250);
					stats.setBackground(new Color(0x404040));
					effects.setBounds(300, 0, 350, 250);
					effects.setBackground(new Color(0x404040));
					// championstats.setUndecorated(true);
					// int xloc=(MouseInfo.getPointerInfo().getLocation().x+210>width)?width-200:MouseInfo.getPointerInfo().getLocation().x+10;
					// int yloc= (MouseInfo.getPointerInfo().getLocation().y+105>height)?height-100:MouseInfo.getPointerInfo().getLocation().y+5;
					Point m = MouseInfo.getPointerInfo().getLocation();
					championstats.setLocation(m.x + 1, m.y + 1);
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
					// championstats.setFocusable(true);
					championstats.add(stats);
					championstats.add(effects);
					alreadyclickedufuckingidiot = true;
				}
			}
			
			
			
		if(e.getSource() == attack)
			attackHighlight();
	

		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
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
														if(ab1.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  /*clearHighlight()*/;}
			else if(e.getSource()== ab2){	/*THEN*/ ab2.setText(ab2.getActionCommand());	ability1stats.setText("Ability Details..."+'\n'+'\n'
														+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
														if(ab2.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  /*clearHighlight()*/;}
			else if(e.getSource()== ab3){	/*THEN*/ ab3.setText(ab3.getActionCommand());	ability1stats.setText("Ability Details..."+'\n'+'\n'
														+'\n'+"Get over one to see details."+'\n'+"Click to select"+'\n'+"& Confirm to execute");
														if(ab3.getBackground()!=Color.GREEN && ab2.getBackground()!=Color.GREEN && ab3.getBackground()!=Color.GREEN)  /*clearHighlight()*/;}
			//else if(e.getSource()== useleaderab /*&& leaderClicked*/) ability1stats.setText("Use your Leader Ability?");
		if(e.getSource()== useleaderab && !leaderClicked)	/*THEN*/  ability1stats.setText("Ability Details...");
		if(e.getSource() == attack && !isAttackMode)
			clearHighlight();
		
		for(int i =0 ; i<5; i++)
			for(int j=0; j<5; j++){
				if(e.getSource() == Gridbuttons[i][j]){
						championstats.dispatchEvent(new WindowEvent(championstats, WindowEvent.WINDOW_CLOSING));
						alreadyclickedufuckingidiot = false;
				}
			}
	

		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
	}







	@Override public void mouseClicked(MouseEvent e){gameframe.revalidate();gamebackground.setIcon(null);gamebackground.setIcon(bkgimg);}
	@Override public void mousePressed(MouseEvent e) {gameframe.revalidate();gamebackground.setIcon(null);gamebackground.setIcon(bkgimg);}
	@Override public void mouseDragged(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {gameframe.revalidate();gamebackground.setIcon(null);gamebackground.setIcon(bkgimg);}
	
	@Override public void mouseReleased(MouseEvent e) {
		
		
		// --------------------------------------------GRIDBUTTONS-------------------------------------------------------------
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				if (e.getSource() == Gridbuttons[i][j])
					if (castIsclicked) {
						Ability a = null;
						if (ab1.getBackground() == Color.GREEN) {
							a = G.getCurrentChampion().getAbilities().get(0);
						} else if (ab2.getBackground() == Color.GREEN) {
							a = G.getCurrentChampion().getAbilities().get(1);
						} else if (ab3.getBackground() == Color.GREEN) {
							a = G.getCurrentChampion().getAbilities().get(2);
						}
						if (a.getCastArea() == AreaOfEffect.SINGLETARGET)
							try {
								targets = G.castAbility(a, i, j);
								G.FinishHim(targets, a);
								for (Damageable d : targets) {
									Object[][] board = G.getBoard();
									if (board[d.getLocation().x][d
											.getLocation().y] instanceof Cover) {
										((Cover) board[d.getLocation().x][d
												.getLocation().y])
												.setIcon(new ImageIcon(
														"icons/ProfessionalCover.png"));
									}
								}
								cleanGrid(targets);
								resetbuttons();
								castIsclicked = false;
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
							} catch (CloneNotSupportedException
									| NotEnoughResourcesException
									| AbilityUseException
									| InvalidTargetException e1) {
								showErrorMessage(e1.getMessage());
							}
					} else if (!(G.getBoard()[i][j] instanceof Cover)
							&& G.getBoard()[i][j] != null
							&& !alreadyclickedufuckingidiot) {
						championstats = new JFrame();
						championstats.setUndecorated(true);
						String s = getStats((Champion) G.getBoard()[i][j]);
						String eff = getappEffects((Champion) G.getBoard()[i][j]);
						TextArea stats = new TextArea(s);
						TextArea effects = new TextArea(
								getappEffects((Champion) G.getBoard()[i][j]));
						stats.setBounds(0, 0, 300, 250);
						stats.setBackground(new Color(0x404040));
						effects.setBounds(300, 0, 350, 250);
						effects.setBackground(new Color(0x404040));
						// championstats.setUndecorated(true);
						// int xloc=
						// (MouseInfo.getPointerInfo().getLocation().x+210>width)?width-200:MouseInfo.getPointerInfo().getLocation().x+10
						// ;
						// int yloc=
						// (MouseInfo.getPointerInfo().getLocation().y+105>height)?height-100:MouseInfo.getPointerInfo().getLocation().y+5
						// ;
						Point m = MouseInfo.getPointerInfo().getLocation();
						championstats.setLocation(m.x + 1, m.y + 1);
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
						// championstats.setFocusable(true);
						championstats.add(stats);
						championstats.add(effects);
						alreadyclickedufuckingidiot = true;
					}

			}
//------------------------------------------Generic after every action -----------------------------------------------
		
		updateStats();
		//cleanGrid();
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
		if(G.checkGameOver()!=null)
		{
			
			showErrorMessage("Gameover, buddy. it's over go home");
		}
		
		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
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
			turnorderT.setText(turnorderT.getText()+'\n' +"  "+((Champion)turnz.remove()).getName());
		
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
			for(int j =0; j<5; j++){
				if(G.getBoard()[i][j]==null || G.getBoard()[i][j] instanceof Cover)
					Gridbuttons[i][j].setBackground(new Color(0,0,0,0));
				else if(G.getFirstPlayer().getTeam().contains( G.getBoard()[i][j]))
					Gridbuttons[i][j].setBackground(a7marika);
				else
					Gridbuttons[i][j].setBackground(azra2ika);
					
				
			}
		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
		
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
		

		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
	}
	
	private void highlight(Ability a, AreaOfEffect e){
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
				if(board[i][j] instanceof Champion){
					Gridbuttons[i][j].setIcon(	new ImageIcon(((((Champion)board[i][j])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));
					gameframe.revalidate();
					gamebackground.setIcon(null);
					gamebackground.setIcon(bkgimg);
					}
				else if(board[i][j] instanceof Cover){
					Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)board[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
					gameframe.revalidate();
					gamebackground.setIcon(null);
					gamebackground.setIcon(bkgimg);
					}
				else{
					Gridbuttons[i][j].setIcon(null);

					gameframe.revalidate();
					gamebackground.setIcon(null);
					gamebackground.setIcon(bkgimg);
					}
		}
		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
	}
	
	private void cleanGrid(ArrayList<Damageable> a){
		for(Damageable d: a){
				Object[][] board = G.getBoard();
				if(board[d.getLocation().x][d.getLocation().y] instanceof Champion)
					Gridbuttons[d.getLocation().x][d.getLocation().y].setIcon(	new ImageIcon(((((Champion)board[d.getLocation().x][d.getLocation().y])).getIcon().getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH))));
				else if(board[d.getLocation().x][d.getLocation().y] instanceof Cover)
					Gridbuttons[d.getLocation().x][d.getLocation().y].setIcon(new ImageIcon(((((Cover)board[d.getLocation().x][d.getLocation().y])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
				else
					Gridbuttons[d.getLocation().x][d.getLocation().y].setIcon(null);
		}

		gameframe.revalidate();
		gamebackground.setIcon(null);
		gamebackground.setIcon(bkgimg);
	}

	private void updateStats(){
		
	//stats TextArea Update
		Champion c = G.getCurrentChampion();
		stats1.setText(getStats(c));	
		
	//Grid Stats Update
		
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				if(G.getBoard()[i][j] != null)
					if(G.getBoard()[i][j] instanceof Cover){
//						String s = Gridbuttons[i][j].getText();
//						String rock = "";
//						for(int k = 4;k<s.length();k++)
//							rock+=s.charAt(k);
//						int hp = Integer.parseInt(rock);
//						System.out.println(hp);
//						if(((Cover)G.getBoard()[i][j]).getCurrentHP()!=hp){
//							((Cover)G.getBoard()[i][j]).setIcon(new ImageIcon("icons/Cover.png"));
//							Gridbuttons[i][j].setIcon(new ImageIcon(((((Cover)G.getBoard()[i][j])).getIcon()).getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
//						}
						Gridbuttons[i][j].setText("HP: "+ ((Cover)G.getBoard()[i][j]).getCurrentHP() );
					}
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
	
	private void showErrorMessage(String msg){
		JFrame err = new JFrame();
		err.setBounds(width/2 -250, height/2-100, 500, 200);
		err.setAlwaysOnTop(true);
		err.setUndecorated(true);
		err.getContentPane().setBackground(Color.DARK_GRAY);
		err.setLayout(null);
		err.setIconImage(icon.getImage());
		err.setFocusable(false);
		
		JTextPane mg = new JTextPane();
		mg.setText(msg);
		mg.setFont(new Font("Agency FB", Font.BOLD, 24));
		mg.setForeground(Color.WHITE);
		mg.setBackground(Color.DARK_GRAY);
		StyledDocument doc1 = mg.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center1, StyleConstants.ALIGN_CENTER);
		doc1.setParagraphAttributes(0, doc1.getLength(), center1, false);
		mg.setEditable(false);
		mg.setFocusable(false);
		mg.setBounds(0,20,500,100);
		
		JButton ok = new JButton("OKAY");
		ok.setFont(new Font("Agency FB", Font.BOLD, 20));
		ok.setBounds(250-85, 150, 170,40);
		ok.setBackground(new Color(93, 92, 79));
		ok.setForeground(Color.WHITE);
		ok.setFocusable(false);
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!msg.equals("Gameover, buddy. it's over go home"))
					err.dispatchEvent(new WindowEvent(err, WindowEvent.WINDOW_CLOSING));
				else{
					audio.pause();
					record.play();
					gameframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					gameframe.dispatchEvent(new WindowEvent(gameframe, WindowEvent.WINDOW_CLOSING));
					err.dispatchEvent(new WindowEvent(err, WindowEvent.WINDOW_CLOSING));
					new winnerwinnerChickenDinner(G.checkGameOver());
				}
				
			}
		});
		
		err.add(mg, BorderLayout.CENTER);
		err.add(ok, BorderLayout.SOUTH);
		
		err.setVisible(true);
		
		
		
	}
	
	
	private void confirmExitMessage(){
		JFrame conf = new JFrame();
		conf.setBounds(width/2 -250, height/2-100, 500, 200);
		conf.setAlwaysOnTop(true);
		conf.setUndecorated(true);
		conf.getContentPane().setBackground(Color.DARK_GRAY);
		conf.setIconImage(icon.getImage());
		
		JLabel mg = new JLabel("ARE YOU SURE YOU WANT TO EXIT?");
		mg.setFont(new Font("Agency FB", Font.BOLD, 24));
		mg.setHorizontalAlignment(JLabel.CENTER);
		mg.setForeground(Color.WHITE);
		
		JPanel p= new JPanel();
		p.setPreferredSize(new Dimension(700,50));
		p.setBackground(Color.DARK_GRAY);
		
		JButton yes = new JButton("Yes");
		yes.setFont(new Font("Agency FB", Font.BOLD, 20));
		yes.setPreferredSize(new Dimension(100,30));
		yes.setFocusable(false);
		yes.setBackground(new Color(93, 92, 79));
		yes.setForeground(Color.WHITE);
		
		JButton no = new JButton("No");
		no.setFont(new Font("Agency FB", Font.BOLD, 20));
		no.setPreferredSize(new Dimension(100,30));
		no.setFocusable(false);
		no.setBackground(new Color(93, 92, 79));
		no.setForeground(Color.WHITE);
		
		JButton minimize = new JButton("MINIMIZE");
		minimize.setFont(new Font("Agency FB", Font.BOLD, 20));
		minimize.setPreferredSize(new Dimension(150,30));
		minimize.setFocusable(false);
		minimize.setBackground(new Color(93, 92, 79));
		minimize.setForeground(Color.WHITE);
		
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameframe.dispatchEvent(new WindowEvent(gameframe, WindowEvent.WINDOW_CLOSING));
				
			}
		});
		
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				gameframe.transferFocus();
			}
		});
		
		minimize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameframe.setExtendedState(JFrame.ICONIFIED);
				conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
			}
		});
		conf.add(mg, BorderLayout.CENTER);
		conf.add(p, BorderLayout.SOUTH);
		p.add(yes);
		p.add(no);
		p.add(minimize);
		
		conf.setVisible(true);
		
		
		
	}
	
	
	

	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE)
			confirmExitMessage();
	}

	@Override public void keyReleased(KeyEvent arg0) { }
	@Override public void keyTyped(KeyEvent k) { }
	
	@Override public void windowActivated(WindowEvent arg0) { }
	@Override public void windowClosed(WindowEvent arg0) { }
	@Override public void windowDeactivated(WindowEvent arg0) { }
	@Override public void windowOpened(WindowEvent arg0) { }
	@Override public void windowDeiconified(WindowEvent arg0) { }
	@Override public void windowIconified(WindowEvent arg0) { }

	@Override public void windowClosing(WindowEvent e) {
		gameframe.setExtendedState(JFrame.NORMAL);
		confirmExitMessage();
	}
	
	
	
}
