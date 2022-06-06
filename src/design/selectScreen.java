package design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
	private ImageIcon icon, selbkground, leaderbordericon= new ImageIcon(this.getClass().getResource("/resources/icons/leadercrown.png"))
	, defaultbordericon = new ImageIcon(this.getClass().getResource("/resources/icons/defaultborder.png"));
	private JTextField name1,name2;
	private JFrame selectframe;
	private JPanel select, loading =new JPanel();;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JList champlist1,champlist2;
	private JButton startGame, add1, add2,ab1,ab2,ab3,ab4,ab5,ab6;
	private ArrayList<Champion> temp1,temp2;
	private String[] names1, names2;
	private JLabel strips, ld1b, ld2b, ch1b, ch2b, ch3b, ch4b, ld1, ld2, ch1, ch2, ch3, ch4,
							loadingframe, loadingbar, marvelLogo;
	private Media theme;
	private Timer timer;
	private ActionListener al;
	private Player p1,p2;
	
	
	public static void main(String[]args){
		selectScreen test = new selectScreen();
	}
	
	public selectScreen(){
		
		temp1 = new ArrayList<Champion>();
		temp2 = new ArrayList<Champion>();

		
		icon = new ImageIcon(this.getClass().getResource("/resources/icons/Marvel_Logo.png"));
		theme = new Media(this.getClass().getResource("/resources/audios/selectpage-theme.wav") , true);
		theme.play();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		strips = new JLabel();
		strips.setBounds(0,0,width,height);
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
		
		

		loading = new JPanel();
		loading.setOpaque(true);
		loading.setBackground(Color.BLACK);
		loading.setLayout(null);
		loading.setBounds(0, 0,width, height);
		loading.setVisible(false);
		
		loadingframe = new JLabel();
		loadingframe.setBounds(width/2 - 270, height/2- 40, 520, 100);
		loadingframe.setBorder(BorderFactory.createLineBorder(Color.CYAN,4 ));
		loading.add(loadingframe);
		
		loadingbar = new JLabel();
		loadingbar.setBounds(width/2 - 250, height/2- 20, 5, 60);	//final = 500,60 (size)
		loadingbar.setText(""+(int)(loadingbar.getSize().width/5)+"%");
		loadingbar.setHorizontalTextPosition(JLabel.RIGHT);
		loadingbar.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		loadingbar.setOpaque(true);
		loadingbar.setBackground(Color.CYAN);
		loading.add(loadingbar);
		
//		marvelLogo= new JLabel();
//		marvelLogo.setBounds(width/2- 125, height/4-150, (int)(((450)*width)/1920), (int)(((100)*width)/1080));
//		marvelLogo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(marvelLogo.getSize().width, marvelLogo.getSize().height,Image.SCALE_SMOOTH)));
//		loading.add(marvelLogo);
		
		JLabel ldtext= new JLabel("Loading...");
		ldtext.setFont(new Font("Agency FB", Font.BOLD, (int)(((40)*width)/1920)));
		ldtext.setForeground(Color.CYAN);
		ldtext.setBounds( width/2 - 310, height/2- 130,250,70);
		loading.add(ldtext);
		
		al=new ActionListener() { public void actionPerformed(ActionEvent ae) {
									loadingbar.setBounds((int)(loadingbar.getLocation().getX()),
											(int)(loadingbar.getLocation().getY()) ,
											loadingbar.getSize().width+5 , 60);
									loadingbar.setText(""+(int)(loadingbar.getSize().width/5)+"%");
									if(loadingbar.getSize().width== 500){
										MainGUI newgame = new MainGUI(new Game(p1,p2));
										theme.pause();
										selectframe.setVisible(false);
										timer.stop();
									}
								 }
    							};
    
	
	timer = new Timer(100, al);
    //timer.setInitialDelay(500);
   // timer.start();

		
		
		
		selbkground = new ImageIcon(this.getClass().getResource("/resources/icons/SelectBackground.jpg"));
		selectframe = new JFrame();
		selectframe.setTitle("Character Select");
		selectframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectframe.setLayout(new BorderLayout());
		selectframe.setSize(width+1, height);
	    selectframe.setLocationRelativeTo(null);
	    selectframe.setIconImage(icon.getImage());
	    strips.setIcon(new ImageIcon(selbkground.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH)));
	    
		select = new JPanel();
		//select.setVisible(false);
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
		champlist1.setBackground(new Color(255, 79, 75));
		
		JScrollPane listScroller = new JScrollPane(champlist1);
		//listScroller.setPreferredSize(new Dimension(500, 300));
		listScroller.setBounds((int)(((10)*width)/1920),(int)(((120-50)*height)/1080), (int)(((500)*width)/1920), (int)(((300)*height)/1080));
		champlist1.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		champlist2 = new JList(names); //data has type Object[]
		champlist2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		champlist2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		champlist2.setVisibleRowCount(-1);
		champlist2.addListSelectionListener(this);
		champlist2.setBackground(new Color(82, 89, 169));
		
		
		JScrollPane listScroller2 = new JScrollPane(champlist2);
		//listScroller2.setPreferredSize(new Dimension(500, 300));
		listScroller2.setBounds((int)(((520+510+400-30)*width)/1920),(int)(((120-50)*height)/1080),(int)(((500)*width)/1920), (int)(((300)*height)/1080));
		champlist2.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		
		
		stats1 = new JTextArea();
		stats1.setBounds((int)(((520+10)*width)/1920), (int)(((10)*height)/1080), (int)(((400)*width)/1920), (int)(((720-460)*height)/1080));
		stats1.setText("Select Leader");
		stats1.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		stats1.setBackground(new Color(255, 79, 75));
		select.add(stats1);
		
		
		stats2 = new JTextArea();
		stats2.setBounds((int)(((520+510-100+50)*width)/1920),(int)(((10)*height)/1080), (int)(((400)*width)/1920), (int)(((720-460)*height)/1080));
		stats2.setText("Select Leader");
		stats2.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		stats2.setBackground(new Color(82, 89, 169));
		select.add(stats2);
		
		
		name1 = new JTextField();
		name2 = new JTextField();
		name1.setBounds((int)(((10)*width)/1920), (int)(((10)*height)/1080), (int)(((500)*width)/1920), (int)(((50)*height)/1080));
		name1.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		name1.setBackground(new Color(220, 0, 0));
		name1.setText("Player 1 Name");
		name1.setForeground(Color.gray);
		
		name2.setBounds((int)(((520+510+400-30)*width)/1920), (int)(((10)*height)/1080), (int)(((500)*width)/1920), (int)(((50)*height)/1080));
		name2.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		name2.setBackground(new Color(0, 0, 220));
		name2.setText("Player 2 Name");
		name2.setForeground(Color.gray);
		select.add(listScroller2);
		select.add(listScroller);
		
		
		startGame = new JButton("Start Game");
		//startGame.setActionCommand("Start game");
		startGame.addActionListener(this);
		startGame.setBounds(width/2 - (int)(((100)*width)/1920), (int)(((690)*height)/1080), (int)(((200)*width)/1920), (int)(((100)*height)/1080));
		startGame.setFont(new Font("Agency FB", Font.BOLD, (int)(((35)*width)/1920)));
		startGame.setBackground(Color.black);
		startGame.setForeground(Color.white);
		
		
		add1 = new JButton("Lock-In Champion");
		add1.setBounds((int)(((205-100)*width)/1920), (int)(((530)*height)/1080), (int)(((250)*width)/1920), (int)(((50)*height)/1080));
		add1.addActionListener(this);
		add1.setFont(new Font("Agency FB", Font.BOLD, (int)(((25)*width)/1920)));
		add1.setBackground(new Color(255, 168, 1));
		
		add2 = new JButton("Lock-In Champion");
		add2.setBounds((int)(((715+840)*width)/1920), (int)(((530)*height)/1080), (int)(((250)*width)/1920), (int)(((50)*height)/1080));
		add2.addActionListener(this);
		add2.setFont(new Font("Agency FB", Font.BOLD, (int)(((25)*width)/1920)));
		add2.setBackground(new Color(12, 132, 252));
		
		t1="";
		t2="";
		team1 = new JTextArea("");
		team2 = new JTextArea("");
		team1.setBounds((int)(((10)*width)/1920), (int)(((370)*height)/1080), (int)(((500)*width)/1920), (int)(((150)*height)/1080));
		team1.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		team1.setBackground(new Color(255, 79, 75));
		team2.setBounds((int)(((520+510+400-30)*width)/1920), (int)(((370)*height)/1080), (int)(((500)*width)/1920), (int)(((150)*height)/1080));
		team2.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		team2.setBackground(new Color(82, 89, 169));
		ab1 = new JButton("Ability 1");
		ab2 = new JButton("Ability 2");
		ab3 = new JButton("Ability 3");
		ab4 = new JButton("Ability 1");
		ab5 = new JButton("Ability 2");
		ab6 = new JButton("Ability 3");
		ab1.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab1.setBackground(new Color(255, 168, 1));
		ab2.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab2.setBackground(new Color(255, 168, 1));
		ab3.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab3.setBackground(new Color(255, 168, 1));
		ab4.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab4.setBackground(new Color(12, 132, 252));
		ab5.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab5.setBackground(new Color(12, 132, 252));
		ab6.setFont(new Font("Arial", Font.BOLD, (int)(((15)*width)/1920)));
		ab6.setBackground(new Color(12, 132, 252));
		
		ab1.setBounds((int)(((520+50-20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ab2.setBounds((int)(((630+50+5-20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ab3.setBounds((int)(((740+50+10-20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ab4.setBounds((int)(((850+50+55+50+20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ab5.setBounds((int)(((960+50+60+50+20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ab6.setBounds((int)(((1070+50+65+50+20)*width)/1920), (int)(((280)*height)/1080), (int)(((110)*width)/1920), (int)(((20)*height)/1080));
		ability1stats = new JTextArea();
		ability2stats = new JTextArea();
		ability1stats.setBounds((int)(((520)*width)/1920), (int)(((310)*height)/1080), (int)(((420)*width)/1920), (int)(((720-460)*height)/1080));
		ability2stats.setBounds((int)(((520+400+50)*width)/1920), (int)(((310)*height)/1080), (int)(((420)*width)/1920), (int)(((720-460)*height)/1080));
		ability1stats.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		ability1stats.setBackground(new Color(255, 79, 75));
		ability2stats.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		ability2stats.setBackground(new Color(82, 89, 169));
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
		name1.addMouseListener(this);
		name2.addMouseListener(this);
		
		ld1b = new JLabel(new ImageIcon(leaderbordericon.getImage().getScaledInstance(250, 250,Image.SCALE_SMOOTH)));
		ld2b = new JLabel(new ImageIcon(leaderbordericon.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)));
		ch1b = new JLabel(new ImageIcon(defaultbordericon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH)));
		ch2b = new JLabel(new ImageIcon(defaultbordericon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH)));
		ch3b = new JLabel(new ImageIcon(defaultbordericon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH)));
		ch4b = new JLabel(new ImageIcon(defaultbordericon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH)));
		
		
		ld1 = new JLabel("", JLabel.CENTER);
		ld2 = new JLabel("", JLabel.CENTER);
		ch1 = new JLabel("", JLabel.CENTER);
		ch2 = new JLabel("", JLabel.CENTER);
		ch3 = new JLabel("", JLabel.CENTER);
		ch4 = new JLabel("", JLabel.CENTER);
		
//		ld1.setOpaque(true);
//		ld2.setOpaque(true);
//		ch1.setOpaque(true);
//		ch2.setOpaque(true);
//		ch3.setOpaque(true);
//		ch4.setOpaque(true);
//		
//		ld1.setBackground(Color.WHITE);
//		ld2.setBackground(Color.WHITE);
//		ch1.setBackground(Color.WHITE);
//		ch2.setBackground(Color.WHITE);
//		ch3.setBackground(Color.WHITE);
//		ch4.setBackground(Color.WHITE);
		
		ch1b.setBounds((int)((((width/4)-125-200-100)*width)/1920), (int)(((530+150)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		ld1b.setBounds((int)((((width/4)-125-100)*width)/1920), (int)(((530+125)*height)/1080), (int)(((250)*width)/1920), (int)(((250)*height)/1080));
		ch2b.setBounds((int)((((width/4)-125+225-100+30)*width)/1920), (int)(((530+125+25)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		
		ch1.setBounds((int)((((width/4)-125-200-100)*width)/1920), (int)(((530+150)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		ld1.setBounds((int)((((width/4)-125-100)*width)/1920), (int)(((530+125)*height)/1080), (int)(((250)*width)/1920), (int)(((250)*height)/1080));
		ch2.setBounds((int)((((width/4)-125+225-100+30)*width)/1920), (int)(((530+125+25)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		
		ch3b.setBounds((int)(((((width)*3/4)-45-200)*width)/1920), (int)(((530+150)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		ld2b.setBounds((int)(((((width)*3/4)-45)*width)/1920), (int)(((530+125)*height)/1080), (int)(((250)*width)/1920), (int)(((250)*height)/1080));
		ch4b.setBounds((int)(((((width)*3/4)-45+225+30)*width)/1920), (int)(((530+125+25)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		
		ch3.setBounds((int)(((((width)*3/4)-45-200)*width)/1920), (int)(((530+150)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		ld2.setBounds((int)(((((width)*3/4)-45)*width)/1920), (int)(((530+125)*height)/1080), (int)(((250)*width)/1920), (int)(((250)*height)/1080));
		ch4.setBounds((int)(((((width)*3/4)-45+225+30)*width)/1920), (int)(((530+125+25)*height)/1080), (int)(((200)*width)/1920), (int)(((200)*height)/1080));
		
		
		

//		
		//warning.setForeground(Color.green);
		select.add(ab1);
		select.add(ab2);
		select.add(ab3);
		select.add(ab4);
		select.add(ab5);
		select.add(ab6);
		select.add(startGame);
//		select.add(startGame);
//		select.add(startGame);
  	    select.add(name1);
		select.add(name2);
		select.add(add1);
		select.add(add2);
		select.add(team1);
		select.add(team2);
		select.add(ability1stats);
		select.add(ability2stats);
		selectframe.add(select);
		select.add(ld1);
		select.add(ld2);
		select.add(ch1);
		select.add(ch2);
		select.add(ch3);
		select.add(ch4);
		select.add(ld1b);
		select.add(ld2b);
		select.add(ch1b);
		select.add(ch2b);
		select.add(ch3b);
		select.add(ch4b);
		selectframe.add(loading);
		
		
		
		
		select.add(strips);
		
		selectframe.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("HAHAHAHA");
		if(e.getSource()==(startGame))
		{
			boolean alldone = false;
			if(!name1.getText().equals("") && !name2.getText().equals("") && !(name1.getForeground()==Color.gray) && !(name2.getForeground()==Color.gray)){
				p1 = new Player(name1.getText());
				p2 = new Player(name2.getText());
				if(temp1.size()<3||temp2.size()<3)
					JOptionPane.showMessageDialog(null, "Select 3 Champions for each team", "WARNING", JOptionPane.WARNING_MESSAGE);
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
					//MainGUI newgame = new MainGUI(new Game(p1,p2));
					//selectframe.dispatchEvent(new WindowEvent(selectframe, WindowEvent.WINDOW_CLOSING));
					//selectframe.setVisible(false);
					select.setVisible(false);
					loading.setVisible(true);
					timer.start();
					}
			}
			else
				switch(count){
				case 0:
					JOptionPane.showMessageDialog(null, "Please type a name for each player", "WARNING", JOptionPane.WARNING_MESSAGE);
				count++;
				break;
				case 1:
					JOptionPane.showMessageDialog(null, "enta fakerni haseebak?", "WARNING", JOptionPane.WARNING_MESSAGE);
				count++;
				break;
				case 2:
					JOptionPane.showMessageDialog(null, "3awez eh ya 5effa?", "WARNING", JOptionPane.WARNING_MESSAGE);
					count++;
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "abo to2l dammak ya b3eed...", "WARNING", JOptionPane.WARNING_MESSAGE);
					count++;
					break;
				case 4:
					JOptionPane.showMessageDialog(null, "ekteb el asami ya roo7 mother", "WARNING", JOptionPane.WARNING_MESSAGE);
					count = 0;
					break;
				}
				
		}
		
		if(e.getSource()==add1){
			Champion c = null;
			if(champlist1.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist1.getSelectedIndex());
			else
				JOptionPane.showMessageDialog(null,"maybe select smth first?", "WARNING", JOptionPane.WARNING_MESSAGE);
			if(!temp2.contains(c) && !temp1.contains(c)){
				if(t1.equals("") && ld1.getIcon()==null)
					ld1.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
				else if(ch1.getIcon() == null)
					ch1.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
				else if(ch2.getIcon() == null)
					ch2.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
					
				if(temp1.size()<3)
				{
				    temp1.add(c);
				    System.out.println(c.getName());
				    if(temp1.size() == 1)
				    	t1 = t1 + c.getName()+ " (Leader)" +'\n';
				    else
				    	t1 = t1 + c.getName()+'\n';
				    team1.setText(t1);
				}
				else
					JOptionPane.showMessageDialog(null,"only 3 per team plz. coroona b2a w keda ", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null,"This champion has already been selected", "WARNING", JOptionPane.WARNING_MESSAGE);
		}
		
		if(e.getSource()==add2){
			Champion c = null;
			if(champlist2.getSelectedIndex()>=0)
			    c = availableChampions.get(champlist2.getSelectedIndex());
			else
				JOptionPane.showMessageDialog(null,"maybe select smth first?", "WARNING", JOptionPane.WARNING_MESSAGE);
			if(!temp2.contains(c) && !temp1.contains(c)){
				if(t2.equals("") && ld2.getIcon()==null)
					ld2.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
				else if(ch3.getIcon() == null)
					ch3.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
				else if(ch4.getIcon() == null)
					ch4.setIcon(new ImageIcon(c.getIcon().getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH)));
				
				if(temp2.size()<3)
				{
				    temp2.add(c);
				    System.out.println(c.getName());
				    if(temp2.size() == 1)
				    	t2 = t2 + c.getName()+ " (Leader)" +'\n';
				    else
				    	t2 = t2 + c.getName()+'\n';
				    team2.setText(t2);
				}
				else
					JOptionPane.showMessageDialog(null,"only 3 per team plz. coroona b2a w keda ", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null,"This champion has already been selected", "WARNING", JOptionPane.WARNING_MESSAGE);
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
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName()+ " (" +((CrowdControlAbility)a).getEffect().getDuration() + " Turns)";
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
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName()+ " (" +((CrowdControlAbility)a).getEffect().getDuration() + " Turns)";
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
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName()+ " (" +((CrowdControlAbility)a).getEffect().getDuration() + " Turns)";
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
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName()+ " (" +((CrowdControlAbility)a).getEffect().getDuration() + " Turns)";
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
				temp += "Effect: "+((CrowdControlAbility)a).getEffect().getName()+ " (" +((CrowdControlAbility)a).getEffect().getDuration() + " Turns)";
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
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==name1 || e.getSource()==name2){
			((JTextField)e.getSource()).setText("");
			if(e.getSource()==name1)
				((JTextField)e.getSource()).setForeground(Color.black);
			else
				((JTextField)e.getSource()).setForeground(Color.WHITE);
		}
		
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
