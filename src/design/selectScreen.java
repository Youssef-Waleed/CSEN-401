package design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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

 public class selectScreen implements MouseInputListener, KeyListener, WindowListener {
	
	private int width,height;
	private JTextPane stats1,ability1stats,ability2stats  ,ability3stats;
	private ImageIcon icon, selbkground;
	private JTextField name1;
	private JFrame selectframe,f;
	private JPanel select, loading =new JPanel(), names, characters,stats,currentpane, gettingready, gettingready1, gettingready2;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private JButton continueB,continueB2,ready1,ready2;
	private JLabel strips,player,PL1,PL2, ch1, ch2, ch3, ch4,ch5,ch6,
							loadingframe, loadingbar, currentpl;
	private Media theme, record;
	private Timer timer, BGtimer;
	private ActionListener al;
	private Player p1,p2;
	private ArrayList <JLabel> charlabels;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	
	public static void main(String[]args){
		selectScreen test = new selectScreen();
	}
	
	public selectScreen(){
		
		record = new Media("audios/transition.wav" ,false);
		
		
		
		icon = new ImageIcon("icons/Marvel_Logo.png");
		theme = new Media("audios/selectpage-theme.wav" , true);
		theme.play();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		//
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
		loading.setPreferredSize(new Dimension(width,height));
		loading.setVisible(false);
		
		loadingframe = new JLabel();
		loadingframe.setBounds(width/2 - 270, height/2- 40, 530, 100);
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
										record.play();
										selectframe.setVisible(false);
										timer.stop();
									}
								 }
    							};
    
	
	timer = new Timer(100, al);
    //timer.setInitialDelay(500);
   // timer.start();

	BGtimer= new Timer(2000,new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setIcon(strips,selbkground,width,height);
			BGtimer.stop();
			
		}
	});
		
		
		
		selbkground = new ImageIcon("icons/SelectBackground.jpg");
		selectframe = new JFrame();
		selectframe.setTitle("Character Select");
		selectframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		selectframe.setLayout(new BorderLayout());
		selectframe.setSize(width+1, height);
	    selectframe.setLocationRelativeTo(null);
	    selectframe.setIconImage(icon.getImage());
	    selectframe.setFocusable(true);
	    selectframe.addKeyListener(this);
	    selectframe.addWindowListener(this);
	    
	    setIcon(strips, "icons/SelectBackgroundBlurred.jpg",width,height);
	    
	    selectframe.getContentPane().add(strips,BorderLayout.CENTER);
	    selectframe.setContentPane(strips);
	    
	    names= new JPanel();
	    names.setOpaque(false);
	    names.setLayout(new FlowLayout(FlowLayout.CENTER,500,100));
	    names.setBackground(null);
	    names.setBounds(0,0,width,height);
	    
	    
	    player= new JLabel("Player 1"); 
	    player.setFont(new Font("Agency FB", Font.BOLD, (int)(((70)*width)/1920)));
	    player.setForeground(Color.WHITE);
	    
	    name1 = new JTextField("Player 1: Enter your name                           ");
	    name1.setFont(new Font("Agency FB", Font.BOLD, (int)(((90)*width)/1920)));
		name1.setBackground(new Color(220, 55, 55));
		name1.setForeground(Color.gray);
	    name1.setBounds( 0,0,(int)(((1100)*width)/1920), (int)(((500)*height)/1080));
	    name1.addMouseListener(this);
	    
	    name1.addKeyListener(new KeyListener() {
			
			@Override public void keyPressed(KeyEvent k) {
				if(k.getKeyCode() != KeyEvent.VK_ESCAPE && k.getKeyCode() != KeyEvent.VK_ENTER && k.getKeyCode() != KeyEvent.VK_WINDOWS)
					if(name1.getText().contains("Enter your name")){
		    	    	name1.setText("");
		    	    	if(player.getText().contains("1"))
		    				(name1).setForeground(Color.BLACK);
		    			else
		    				(name1).setForeground(Color.WHITE);
		    		}
				if(k.getKeyCode() == KeyEvent.VK_ENTER)
					if(name1.getText().contains("Enter your name")|| name1.getText().equals("")){
						showErrorMessage("Please enter your Name.");
						return;}
					else{
						if(player.getText().contains("Player 1")){
							p1= new Player( name1.getText());
							name1.setText("Player 2: Enter your name                         ");
							player.setText("Player 2");
							name1.setBackground(new Color(55, 55, 220));
							name1.setForeground(Color.WHITE);
						}else{
							p2= new Player( name1.getText());
							setIcon(strips ,"icons/SelectBackground.jpg",width,height);
							names.setVisible(false);
							select.setVisible(true);
						}
						
					} 
				
				if(k.getKeyCode()== KeyEvent.VK_ESCAPE)
					confirmExitMessage();
			}
			@Override public void keyReleased(KeyEvent k) { }	@Override public void keyTyped(KeyEvent k) { }
		});
	    
	    
	    continueB=new JButton("Continue");
	    continueB.setFont(new Font("Agency FB", Font.BOLD, (int)(((50)*width)/1920)));
	    continueB.setFocusable(false);
	    continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name1.getText().contains("Enter your name")|| name1.getText().equals("")){
					showErrorMessage("Please enter your Name.");
					return;}
				else{
					if(player.getText().contains("Player 1")){
						p1= new Player( name1.getText());
						name1.setText("Player 2: Enter your name                         ");
						player.setText("Player 2");
						name1.setBackground(new Color(55, 55, 220));
						name1.setForeground(Color.WHITE);
					}else{
						p2= new Player( name1.getText());
						setIcon(strips ,"icons/SelectBackground.jpg",width,height);
						names.setVisible(false);
						select.setVisible(true);
						selectframe.transferFocus();
					}
					
				}
				
		}});
	    
	    names.add(player);
	    names.add(name1);
	    names.add(continueB);
	    
	    
	    continueB2=new JButton("Continue");
	    continueB2.setFont(new Font("Agency FB", Font.BOLD, (int)(((50)*width)/1920)));
	    continueB2.setFocusable(false);
	    continueB2.setBackground(Color.WHITE);
	    continueB2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				select.setVisible(false);
				setIcon(strips, "icons/SelectBackgroundBlurred.jpg", width, height);

				setIcon(ch1,"icons/"+p1.getTeam().get(0).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				setIcon(ch2,"icons/"+p1.getTeam().get(1).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				setIcon(ch3,"icons/"+p1.getTeam().get(2).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				setIcon(ch4,"icons/"+p2.getTeam().get(0).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				setIcon(ch5,"icons/"+p2.getTeam().get(1).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				setIcon(ch6,"icons/"+p2.getTeam().get(2).getName()+"CH.png",(int)((width/2)/3)-5,(int)((650)*width)/1920);
				
				PL1.setText(p1.getName());
				PL2.setText(p2.getName());
				//f.setVisible(true);
				selectframe.setBackground(Color.WHITE);
				selectframe.setContentPane(gettingready);
				selectframe.revalidate();
				
			}
		});
	    
	    selectframe.getContentPane().add(names,BorderLayout.CENTER);
	    
	    select = new JPanel();
	    select.setLayout(new BorderLayout());
	    select.setOpaque(false);
	    select.setBackground(null);
	    select.setBounds(0,0,width,height);
	    select.setVisible(false);
	    selectframe.getContentPane().add(select,BorderLayout.CENTER);
	    
	    characters = new JPanel();
	    characters.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
	    characters.setOpaque(false);
	    characters.setBackground(null);
	    characters.setBounds(0,0,width,height);
	    select.add(characters,BorderLayout.CENTER);
	    
	    stats = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
	    stats.setOpaque(false);
	    stats.setBackground(null);
	    stats.setPreferredSize(new Dimension(300,height));
	    select.add(stats,BorderLayout.WEST);
	    
	    charlabels = new ArrayList<JLabel>();
	    int avCHsize=availableChampions.size();
	    for(int i=0; i<avCHsize;i++){
	    	int curdim =170-30*(int)Math.floor((avCHsize-14)/4);
	    	JLabel l =new JLabel(availableChampions.get(i).getIcon());
	    	l.setPreferredSize(new Dimension(curdim+60,curdim+60));
	    	setIcon(l, (ImageIcon) l.getIcon(),curdim,curdim);
	    	l.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	l.addMouseListener(new MouseListener() {
				
				@Override public void mouseReleased(MouseEvent e) {}
				
				@Override public void mousePressed(MouseEvent e) { }
				
				@Override public void mouseExited(MouseEvent e) {
					if(l.isEnabled()){
						ability1stats.setVisible(false);
						ability2stats.setVisible(false);
						ability3stats.setVisible(false);
						stats1.setVisible(false);
						ability1stats.setText("");
						ability2stats.setText("");
						ability3stats.setText("");
						stats1.setText("");
						
					}
					
					if(p1.getTeam().size()==3 && p2.getTeam().size()==3){
						currentpane.add(continueB2);
						currentpl.setForeground(Color.WHITE);
						currentpl.setText("Teams are now Full. Please Continue!");
						for(JLabel jl :charlabels)
							jl.setEnabled(false);
						selectframe.revalidate();
					}
					if(l.isEnabled()){
						setIcon(l, (ImageIcon) l.getIcon(),curdim,curdim); 
						if(l.getBorder() == null)
							setIcon(strips, selbkground, width, height);
						}
					}
				
				@Override public void mouseEntered(MouseEvent e) {
					if(l.isEnabled()){
						ability1stats.setVisible(true);
						ability2stats.setVisible(true);
						ability3stats.setVisible(true);
						stats1.setVisible(true);

						Champion c = availableChampions.get(charlabels.indexOf(l));
						String type ="";
						if(c instanceof Hero)
							type = "Hero";
						else if(c instanceof Villain)
							type = "Villain";
						else
							type = "Antihero";
						stats1.setText(c.getName()+'\n'+'\n'
								+"HP: "+c.getMaxHP()+'\n'
								+"Mana: "+c.getMana()+'\n'
								+"Speed: "+c.getSpeed()+'\n'
								+"Action Points "+c.getMaxActionPointsPerTurn()+'\n'
								+"Type: "+type);
						
						//Abilities
						
						Ability a = availableChampions.get(charlabels.indexOf(l)).getAbilities().get(0);
						String temp = "Ability 1: " + a.getName()+'\n'+
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
					
						Ability a2 = availableChampions.get(charlabels.indexOf(l)).getAbilities().get(1);
						String temp2 = "Ability 2: " + a2.getName()+'\n'+
								"Mana Cost: "+a2.getManaCost()+'\n'+
								"Base Cooldown: "+a2.getBaseCooldown()+'\n'+
								"Cast Range: "+a2.getCastRange()+'\n'+
								"Cast Area: "+a2.getCastArea()+'\n'+
								"Required Action Points: "+a2.getRequiredActionPoints()+'\n';
						if(a2 instanceof CrowdControlAbility)
							temp2 += "Effect: "+((CrowdControlAbility)a2).getEffect().getName()+ " (" +((CrowdControlAbility)a2).getEffect().getDuration() + " Turns)";
						else if (a2 instanceof DamagingAbility)
							temp2 += "Damage Amount: "+((DamagingAbility)a2).getDamageAmount();
						else
							temp2 += "Healing Amount: "+((HealingAbility)a2).getHealAmount();
						ability2stats.setText(temp2);
					//
						
						Ability a3 = availableChampions.get(charlabels.indexOf(l)).getAbilities().get(2);
						String temp3 = "Ability 3: " + a3.getName()+'\n'+
								"Mana Cost: "+a3.getManaCost()+'\n'+
								"Base Cooldown: "+a3.getBaseCooldown()+'\n'+
								"Cast Range: "+a3.getCastRange()+'\n'+
								"Cast Area: "+a3.getCastArea()+'\n'+
								"Required Action Points: "+a3.getRequiredActionPoints()+'\n';
						if(a3 instanceof CrowdControlAbility)
							temp3 += "Effect: "+((CrowdControlAbility)a3).getEffect().getName()+ " (" +((CrowdControlAbility)a3).getEffect().getDuration() + " Turns)";
						else if (a3 instanceof DamagingAbility)
							temp3 += "Damage Amount: "+((DamagingAbility)a3).getDamageAmount();
						else
							temp3 += "Healing Amount: "+((HealingAbility)a3).getHealAmount();
						ability3stats.setText(temp3);
					}
					
					if(l.isEnabled()){
						setIcon(l, (ImageIcon) l.getIcon(),l.getWidth(),l.getHeight());
						setIcon(strips, "icons/"+availableChampions.get(charlabels.indexOf(l)).getName()+"BG.jpg", width, height);
					}
					if(BGtimer.isRunning())
						BGtimer.stop();
				}
				
				@Override public void mouseClicked(MouseEvent e) {
				  if(l.isEnabled())
					if(l.getBorder()!=null)
						showErrorMessage("This Player is Already chosen. Please choose another.");
					else{
						if(currentpl.getText().contains("1")){	
							if(currentpl.getText().contains("Leader"))   p1.setLeader(availableChampions.get(charlabels.indexOf(l)));
							if(p2.getTeam().size()==0)
								currentpl.setText("Player 2: Choose Leader");
							else
								currentpl.setText("Player 2: Choose a character");
							p1.getTeam().add(availableChampions.get(charlabels.indexOf(l)));
							currentpl.setForeground(new Color(80,120,240));
							l.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
							BGtimer.start();
						
						}else{
							if(currentpl.getText().contains("Leader"))     p2.setLeader(availableChampions.get(charlabels.indexOf(l)));
								if(p1.getTeam().size()==0)
									currentpl.setText("Player 1: Choose Leader");
								else
									currentpl.setText("Player 1: Choose a character");
								p2.getTeam().add(availableChampions.get(charlabels.indexOf(l)));
								currentpl.setForeground(Color.RED);
								l.setBorder(BorderFactory.createLineBorder(new Color(80,120,240), 4));
								BGtimer.start();
						}
							
					}
				}
			});
	    	charlabels.add(l);
	    	characters.add(l);
	    }
	    
	    
	    currentpane= new JPanel(new FlowLayout(FlowLayout.CENTER,100,20));
	    currentpane.setOpaque(false);
	    currentpane.setBackground(null);
	    currentpane.setBounds(0,0,width,height);
	    select.add(currentpane,BorderLayout.NORTH);
	    
	    currentpl= new JLabel();
	    currentpl.setFont(new Font("Agency FB", Font.BOLD,60));
		if (Math.random() > 0.5) {
			currentpl.setText("Player 1: Choose Leader");
			currentpl.setForeground(Color.RED);
		} else{
			currentpl.setText("Player 2: Choose Leader");
			currentpl.setForeground(new Color(80,120,240));
		}
		currentpane.add(currentpl);	
		
		ch1= new JLabel();
		//ch1.setPreferredSize(new Dimension((int)((width/2)/3),200));
		ch2 = new JLabel();
//		ch2.setPreferredSize(new Dimension((int)((width/2)/3),200));
		ch3= new JLabel();
//		ch3.setPreferredSize(new Dimension((int)((width/2)/3),200));
		ch4 = new JLabel();
//		ch4.setPreferredSize(new Dimension((int)((width/2)/3),200));
		ch5= new JLabel();
//		ch5.setPreferredSize(new Dimension((int)((width/2)/3),200));
		ch6 = new JLabel();
//		ch6.setPreferredSize(new Dimension((int)((width/2)/3),200));
//
		setIcon(ch1,"icons/Cover.png",(int)((width/2)/3)-5, (int)((600)*width)/1920);
		setIcon(ch2,"icons/Cover.png",(int)((width/2)/3)-5,(int)((600)*width)/1920);
		setIcon(ch3,"icons/Cover.png",(int)((width/2)/3)-5,(int)((600)*width)/1920);
		setIcon(ch4,"icons/Cover.png",(int)((width/2)/3)-5,(int)((600)*width)/1920);
		setIcon(ch5,"icons/Cover.png",(int)((width/2)/3)-5,(int)((600)*width)/1920);
		setIcon(ch6,"icons/Cover.png",(int)((width/2)/3)-5,(int)((600)*width)/1920);
		
		
		
		 f=new JFrame();
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 JLabel strips2 = new JLabel();
		 setIcon(strips2, "icons/SelectBackgroundBlurred.jpg",width,height);
		 f.getContentPane().add(strips2,BorderLayout.CENTER);
		 f.setContentPane(strips2);
		f.setSize(width, height);
		gettingready = new JPanel(new BorderLayout());
		gettingready.setOpaque(false);
		//gettingready.setBackground(null);
		gettingready.setPreferredSize(new Dimension(width,height));
		f.add(gettingready,BorderLayout.CENTER);

		//f.setVisible(true);	
		
		gettingready1 = new JPanel(new BorderLayout());
		gettingready1.setOpaque(false);
		//gettingready1.setBackground(null);
		gettingready1.setPreferredSize(new Dimension(f.getWidth()/2-2,f.getHeight()));
		gettingready2 = new JPanel(new BorderLayout());
		gettingready2.setOpaque(false);
		//gettingready2.setBackground(null);
		gettingready2.setPreferredSize(new Dimension(f.getWidth()/2-2,f.getHeight()));
		
		gettingready.add(gettingready1,BorderLayout.CENTER);
		gettingready.add(gettingready2,BorderLayout.EAST);
		
		PL1= new JLabel("PLAYER 1");
		PL1.setHorizontalTextPosition(JLabel.CENTER);
		PL1.setHorizontalAlignment(JLabel.CENTER);
		PL1.setFont(new Font("Agency FB", Font.BOLD, (int)(((40)*width)/1920)));
		PL1.setForeground(Color.RED);
		
		PL2= new JLabel("PLAYER 2");
		PL2.setHorizontalTextPosition(JLabel.CENTER);
		PL2.setHorizontalAlignment(JLabel.CENTER);
		PL2.setFont(new Font("Agency FB", Font.BOLD, (int)(((40)*width)/1920)));
		PL2.setForeground(new Color(80,120,240));
		
		JPanel PL1pane=new JPanel(new BorderLayout());
		PL1pane.setOpaque(false);
		//PL1pane.setBackground(null);
		PL1pane.setPreferredSize(new Dimension(f.getWidth()/2-2,150));
		gettingready1.add(PL1pane,BorderLayout.NORTH);
		PL1pane.add(PL1, BorderLayout.CENTER);
		//
		JPanel VS1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
		VS1.setOpaque(false);
		//VS1.setBackground(null);
		VS1.setPreferredSize(new Dimension(100,150));
		PL1pane.add(VS1,BorderLayout.EAST);
		//
		JLabel V= new JLabel("V");
		V.setFont(new Font("Agency FB", Font.BOLD, (int)(((120)*width)/1920)));
		V.setForeground(new Color(172, 174, 0));
		VS1.add(V);
		
		JPanel Charc1= new JPanel(new FlowLayout(FlowLayout.CENTER,0,100));
		Charc1.setOpaque(false);
		//Charc1.setBackground(null);
		gettingready1.add(Charc1,BorderLayout.CENTER);
		Charc1.add(ch1);
		Charc1.add(ch2);
		Charc1.add(ch3);
		
		JPanel PL2pane=new JPanel(new BorderLayout());
		PL2pane.setOpaque(false);
		//PL2pane.setBackground(null);
		PL2pane.setPreferredSize(new Dimension(f.getWidth()/2,150));
		gettingready2.add(PL2pane,BorderLayout.NORTH);
		PL2pane.add(PL2, BorderLayout.CENTER);
		//
		JPanel VS2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		VS2.setOpaque(false);
		//VS2.setBackground(null);
		VS2.setPreferredSize(new Dimension(100,150));
		PL2pane.add(VS2,BorderLayout.WEST);
		//
		JLabel S= new JLabel(" S");
		S.setFont(new Font("Agency FB", Font.BOLD, (int)(((120)*width)/1920)));
		S.setForeground(new Color(172, 174, 0));
		VS2.add(S);
		
		JPanel Charc2= new JPanel(new FlowLayout(FlowLayout.CENTER,0,100));
		Charc2.setOpaque(false);
		//Charc2.setBackground(null);
		gettingready2.add(Charc2,BorderLayout.CENTER);
		Charc2.add(ch4);
		Charc2.add(ch5);
		Charc2.add(ch6);
		
		ready1 = new JButton("Ready!");
		ready1.setFont(new Font("Agency FB", Font.BOLD, (int)(((70)*width)/1920)));
		ready1.setForeground(Color.WHITE);
		ready1.setBackground(new Color(73,0,0));
		ready1.setFocusable(false);
		gettingready1.add(ready1,BorderLayout.SOUTH);
		ready1.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent arg0) {
				if(ready2.isEnabled()){
					ready1.setFont(new Font("", Font.PLAIN, (int)(((65)*width)/1920)));
					ready1.setText("<html>\u2713</html>");
					ready1.setEnabled(false);
					}
				
				else{
					gettingready.setVisible(false);
					selectframe.setBackground(Color.BLACK);
					JFrame x = new JFrame();
					x.setUndecorated(true);

					device.setFullScreenWindow(x);		//Fullscreen capability
					x.setAlwaysOnTop(true);
					x.setSize(width,height);
					x.add(loading,BorderLayout.CENTER);
					x.setVisible(true);
					loading.setVisible(true);
					timer.start();
					}
			}
		});
		

		ready2 = new JButton("Ready!");
		ready2.setFont(new Font("Agency FB", Font.BOLD, (int)(((70)*width)/1920)));
		ready2.setForeground(Color.WHITE);
		ready2.setBackground(new Color(0,0,73));
		ready2.setFocusable(false);
		gettingready2.add(ready2,BorderLayout.SOUTH);
		ready2.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent arg0) {
				
				if(ready1.isEnabled()){
					ready2.setFont(new Font("", Font.PLAIN, (int)(((65)*width)/1920)));
					ready2.setText("<html>\u2713</html>");
					ready2.setEnabled(false);
				}
				else{

					gettingready.setVisible(false);
					selectframe.setBackground(Color.BLACK);
					JFrame x = new JFrame();
					x.setUndecorated(true);

					device.setFullScreenWindow(x);		//Fullscreen capability
					x.setAlwaysOnTop(true);
					x.setSize(width,height);
					x.add(loading,BorderLayout.CENTER);
					x.setVisible(true);
					loading.setVisible(true);
					timer.start();
					}
			}
		});
		
											// TEXTPANE
		
		ability1stats = new JTextPane();
		ability1stats.setText("Ability 1 Details...");
		ability1stats.setPreferredSize(new Dimension( 250, 150));
		ability1stats.setFont(new Font("Agency FB", Font.BOLD, (int)(((20)*width)/1920)));
		ability1stats.setBackground(new Color(0, 79, 127));
		ability1stats.setForeground(Color.WHITE);
		StyledDocument doc = ability1stats.getStyledDocument(); 	// Don't mind					
		SimpleAttributeSet center = new SimpleAttributeSet();		// these here
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		ability1stats.setEditable(false);
		
		ability2stats = new JTextPane();
		ability2stats.setText("Ability 2 Details...");
		ability2stats.setPreferredSize(new Dimension( 250, 150));
		ability2stats.setFont(new Font("Agency FB", Font.BOLD, (int)(((20)*width)/1920)));
		ability2stats.setBackground(new Color(0, 79, 127));
		ability2stats.setForeground(Color.WHITE);
		StyledDocument doc2 = ability2stats.getStyledDocument(); 	// Don't mind					
		SimpleAttributeSet center2 = new SimpleAttributeSet();		// these here
		StyleConstants.setAlignment(center2, StyleConstants.ALIGN_CENTER);
		doc2.setParagraphAttributes(0, doc2.getLength(), center2, false);
		ability2stats.setEditable(false);
		
		
		ability3stats = new JTextPane();
		ability3stats.setText("Ability 3 Details...");
		ability3stats.setPreferredSize(new Dimension( 250, 150));
		ability3stats.setFont(new Font("Agency FB", Font.BOLD, (int)(((20)*width)/1920)));
		ability3stats.setBackground(new Color(0, 79, 127));
		ability3stats.setForeground(Color.WHITE);
		StyledDocument doc3 = ability3stats.getStyledDocument(); 	// Don't mind					
		SimpleAttributeSet center3 = new SimpleAttributeSet();		// these here
		StyleConstants.setAlignment(center3, StyleConstants.ALIGN_CENTER);
		doc3.setParagraphAttributes(0, doc3.getLength(), center3, false);
		ability3stats.setEditable(false);
		
		
		stats1 = new JTextPane();
		stats1.setText("Ability 1 Details...");
		stats1.setPreferredSize(new Dimension( 300, 260));
		stats1.setFont(new Font("Agency FB", Font.BOLD, (int)(((30)*width)/1920)));
		stats1.setBackground(new Color(0, 79, 190));
		stats1.setForeground(Color.WHITE);
		StyledDocument doc4 = stats1.getStyledDocument(); 	// Don't mind					
		SimpleAttributeSet center4 = new SimpleAttributeSet();		// these here
		StyleConstants.setAlignment(center4, StyleConstants.ALIGN_CENTER);
		doc4.setParagraphAttributes(0, doc4.getLength(), center4, false);
		stats1.setEditable(false);
		
		
		stats.add(stats1);
		stats.add(ability1stats);
		stats.add(ability2stats);
		stats.add(ability3stats);

		ability1stats.setVisible(false);
		ability2stats.setVisible(false);
		ability3stats.setVisible(false);
		stats1.setVisible(false);
		
		
		//device.setFullScreenWindow(selectframe);		//Fullscreen capability
		selectframe.setVisible(true);
	    selectframe.validate();
	    name1.transferFocus();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==name1 && name1.getText().contains("Enter your")){
			((JTextField)e.getSource()).setText("");
			if(player.getText().contains("1"))
				((JTextField)e.getSource()).setForeground(Color.BLACK);
			else
				((JTextField)e.getSource()).setForeground(Color.WHITE);
		}
		
	}

	@Override public void mouseReleased(MouseEvent arg0) {} @Override public void mouseDragged(MouseEvent arg0) { } @Override public void mouseMoved(MouseEvent arg0) { }
	
	
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
	
	
	
	public static void setIcon(JLabel l, ImageIcon ic,int w, int h){
	    l.setIcon(new ImageIcon(ic.getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
	}

	public static void setIcon(JLabel l, String ic,int w, int h){
		l.setIcon(new ImageIcon(new ImageIcon(ic).getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
	}
	
	public static void setIcon(JLabel l, URL ic,int w, int h){
		l.setIcon(new ImageIcon(new ImageIcon(ic).getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
	}
	
	public static void setIcon(JButton b, ImageIcon ic,int w, int h){
	    b.setIcon(new ImageIcon(ic.getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
	}

	public static void setIcon(JButton b, String ic,int w, int h){
		b.setIcon(new ImageIcon(new ImageIcon(ic).getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
	}
	
	public static void setIcon(JButton b, URL ic,int w, int h){
		b.setIcon(new ImageIcon(new ImageIcon(ic).getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH)));
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
		StyledDocument doc10 = mg.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center10 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center10, StyleConstants.ALIGN_CENTER);
		doc10.setParagraphAttributes(0, doc10.getLength(), center10, false);
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
					err.dispatchEvent(new WindowEvent(err, WindowEvent.WINDOW_CLOSING));
				}});
		
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
				selectframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				selectframe.dispatchEvent(new WindowEvent(selectframe, WindowEvent.WINDOW_CLOSING));
				
			}
		});
		
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				selectframe.transferFocus();
			}
		});
		
		minimize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectframe.setExtendedState(JFrame.ICONIFIED);
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
		selectframe.setExtendedState(JFrame.NORMAL);
		confirmExitMessage();
	}
 
 }
