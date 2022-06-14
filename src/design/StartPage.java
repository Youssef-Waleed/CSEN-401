package design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class StartPage implements ActionListener,
		MouseInputListener {
	private JFrame startwindow;
	private JButton startbt;
	private JPanel pane;
	private JLabel marvel, marvelgif, teamlogo;
	private Media audio, intro,record;
	private int width , height, tMode=1;
	private static boolean loop = true;
	private Timer timer;
	
	private float opacity=1f;
	private ActionListener al;
	
	public StartPage(){
        
        
		
		ImageIcon icon = new ImageIcon("icons/Marvel_Logo.png");
		ImageIcon bkground= new ImageIcon("icons/Marvelstart.png");
		ImageIcon logo = new ImageIcon("icons/team-logo.png");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		audio = new Media("audios/startpage-theme.wav" , loop);
		record = new Media("audios/yt1s (mp3cut.net) - Copy.wav" ,!loop);
		intro = new Media("audios/intro-music.wav" , !loop);
		intro.play();
		startwindow = new JFrame();
		
		startbt = new JButton("START");
		startbt.setVisible(false);
		startbt.addActionListener(this);
		startbt.addMouseListener(this);
		
		marvel = new JLabel();
		
		teamlogo = new JLabel();
		teamlogo.setOpaque(true);
		teamlogo.setBackground(Color.BLACK);
		teamlogo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(width/4, height/2, Image.SCALE_SMOOTH)));
		teamlogo.setPreferredSize(new Dimension(width/4,height/2));
		teamlogo.setHorizontalAlignment(JLabel.CENTER);
		
		JFrame gifcontain= new JFrame();
		gifcontain.setUndecorated(true);
		gifcontain.setAlwaysOnTop(true);
		
		
		marvelgif = new JLabel();
		
		
		
			al=new ActionListener() { public void actionPerformed(ActionEvent ae) {
										//do your task
            	
										System.out.println("lllll");
										if(tMode==1)
											opacity-=0.001f;
										else
											opacity+=0.001f;
										gifcontain.setOpacity(opacity);
										System.out.println(opacity);
            	
										if(opacity<0.01f){
											marvelgif.setVisible(false);
											gifcontain.add(teamlogo, BorderLayout.CENTER);
											//opacity=1f;
											tMode++;
										}
										else if(opacity>0.99f && tMode==2){
											tMode++;
										}
										if(tMode==3){
											marvel.setIcon(new ImageIcon(bkground.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH)));
											startbt.setBackground(new Color(0xFFE20B));
											startbt.setVisible(true);
											timer.stop();
											intro.pause();
											audio.play();
											gifcontain.dispatchEvent(new WindowEvent(gifcontain, WindowEvent.WINDOW_CLOSING));
										}	
									 }
        							};
        
		
		timer = new Timer(5, al);
        //timer.setInitialDelay(500);
        timer.start();


        
        
		gifcontain.setSize(width,height-100);
		gifcontain.setLocation(0, 30);
		
		
		startwindow.setSize(width, height);
		startwindow.setLocation(0,0);
		startwindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		startwindow.setResizable(false);
		startwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startwindow.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		startwindow.setTitle("Marvel: Ultimate War");
		startwindow.setIconImage(icon.getImage());
		
		pane = new JPanel();
		pane.setLayout(null);
		pane.setOpaque(true);
		pane.setBackground(Color.BLACK);
		startwindow.add(pane, BorderLayout.CENTER);
		
		marvel.setBounds(0, 0, width, height);
		
		marvelgif.setIcon(new ImageIcon("icons/marvel-comics.gif"));
		marvelgif.setPreferredSize(new Dimension( 550, 206));
		marvelgif.setHorizontalAlignment(JLabel.CENTER);
		marvelgif.setOpaque(true);
		marvelgif.setBackground(Color.BLACK);
		
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 45));
		startbt.setFocusable(false);
		startbt.setBackground(new Color(0x3F3E00));
		startbt.setBounds(width/2-100, (3*height)/4, 250, 100);
		startbt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		
		gifcontain.add(marvelgif, BorderLayout.CENTER);
		pane.add(startbt);
		pane.add(marvel);
		startwindow.setVisible(true);

		gifcontain.setVisible(true);
	}

	
	//  x.setBounds(x, y, W, H);
	
	
	

	@Override
	public void mouseEntered(MouseEvent e) {
		startbt.setBounds(width/2-100-25, (3*height)/4-25, 300, 150);
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 65));
		startbt.setBackground(new Color(0xD99F6A));
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		startbt.setBounds(width/2-100, (3*height)/4, 250, 100);
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 45));
		startbt.setBackground(new Color(0xFFE20B));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		selectScreen m = new selectScreen();
		audio.pause();
		record.play();
		//startwindow.dispatchEvent(new WindowEvent(startwindow, WindowEvent.WINDOW_CLOSING));
		startwindow.setVisible(false);

	}
	
	
	
	
	
	
	public static void main(String[]args)	{	StartPage p = new StartPage();	}

	
	
	
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
	@Override public void mouseDragged(MouseEvent arg0) {}
	@Override public void mouseMoved(MouseEvent arg0) {}

}
