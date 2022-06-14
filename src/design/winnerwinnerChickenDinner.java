package design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import engine.*;

public class winnerwinnerChickenDinner{
private JFrame window;
private JPanel temp, creditspane;
private JTextPane credits;
private JLabel creditsicon;
private Timer timer;
private Media creditsong;
private float time=0.1f;
private int width , height, yloc;
private ActionListener al;
private boolean playing=false;
	
	public winnerwinnerChickenDinner(Player winner){
		
		window = new JFrame();
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		yloc=height/2;
		
		window.setSize(width+1, height);
		window.setLocation(1920/2-width/2,0);
		window.setResizable(false);
		
		creditsong= new Media("audios/starwars-theme.wav" , false);
		JLabel text =  new JLabel();
		JLabel backround = new JLabel();
		creditsicon = new JLabel();
		creditsicon.setVerticalAlignment(JLabel.CENTER);
		creditsicon.setHorizontalAlignment(JLabel.CENTER);
		creditsicon.setBounds(0,0 ,width,408);
		
		temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.setBounds(0,0, width, height);
		temp.setBackground(new Color(0,0,0,0));
		
		
		creditspane=new JPanel();
		creditspane.setOpaque(true);
		creditspane.setBackground(Color.BLACK);
		creditspane.setBounds(0, 900, width,height+3400);
		window.add(creditspane);
		creditspane.setVisible(false);
		creditspane.setLayout(null);
		
		creditspane.add(creditsicon);

		creditsicon.setIcon(new ImageIcon(new ImageIcon(this.getClass()
				.getResource("/resources/icons/creditsicon.png"))
				.getImage().getScaledInstance(1384, 408, Image.SCALE_SMOOTH)));
		
		
		credits = new JTextPane();
		credits.setText("A VERY NOT SO VERY LOOOOONG TIME"+'\n'+ "AGO, WE STARTED WORKING ON THIS GAME PROJECT"
		+'\n'+ ",WHICH WE HOPE YOU LIKED"+'\n'+'\n'+'\n'+ 
		"LET ME TAKE THIS OPPORTUNITY TO PRESENT YOU THE PEOPLE"+'\n'+ "THAT WERE WORKING BEHIND THE SCENES"+'\n'+'\n'
		+'\n'+ "This game was presented to you by the Developers..."+'\n'+ '\n'+'\n'
					+ "Youssef Walid Ibrahim"+'\n'+ '\n'+ "Mohamed Ayman Tammaa"+'\n'+'\n'
					+ ",and the great to the end,"+'\n'+"Mahmoud Gamal (The Greek-Kopis)"+'\n' +'\n'+'\n'+'\n'
					+ "We would like to thank our sponsors and the respective Copyright Holders"+'\n'+ " "+(char)169+"Marvel & "+(char)169+ "StarWars"+
					'\n'+'\n'+'\n'+'\n'+'\n'+ ((char)169)+"TEAM-177_ULTIMATE WAR 2022"+'\n'+ '\n'+ "THANK YOU!");
		
		credits.setFont(new Font("", Font.BOLD, 80));
		credits.setBackground(Color.BLACK);
		credits.setForeground(Color.YELLOW);
		credits.setEditable(false);
		credits.setFocusable(false);
		StyledDocument doc = credits.getStyledDocument();				//Don't mind these here
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		credits.setForeground(Color.YELLOW);
		
		credits.setLocation(0,creditsicon.getHeight()+400);
		credits.setSize(new Dimension(width,height+3400));
		creditspane.setSize(width  , credits.getSize().height+  creditsicon.getSize().height);
		creditspane.add(credits);
		
		
		ImageIcon icon = new ImageIcon("icons/Marvel_Logo.png");
		window.setIconImage(icon.getImage());
		window.setTitle("Marvel: Ultimate War credits");

		
		al=new ActionListener() { public void actionPerformed(ActionEvent ae) {
									//do your task
									time+=0.1f;
									if(!playing&& time>17f){
										creditsong.play();
										playing =true;}
									if(time>20f){
										backround.setVisible(false);
										text.setVisible(false);
										temp.setBackground(Color.BLACK);
										temp.setOpaque(true);
										creditspane.setVisible(true);
										
										
										creditspane.setLocation(0, yloc--);
										System.out.println("timer running babee");
										//yloc-=5;
									
									}
									if(creditspane.getLocation().y<-creditspane.getHeight()){
										timer.stop();
										window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
									}
									
								 }
    							};
    
	
	timer = new Timer(20, al);
    //timer.setInitialDelay(500);
		
		text.setText(winner.getName()+" WINS");
		text.setBounds(width/2-250, height/2-75, width, 350);
		text.setHorizontalTextPosition(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setFont(new Font("Agency FB", Font.BOLD, 50));
		text.setForeground(Color.WHITE);
		text.setBackground(null);
		ImageIcon bkground= new ImageIcon("icons/unknown.png");
		
		backround.setBounds(0, 0, width, height);
		backround.setIcon(new ImageIcon(bkground.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH)));
		temp.add(text,BorderLayout.CENTER);
		window.add(temp);
		window.add(backround);
		window.setVisible(true);
		timer.start();
	}
	public static void main(String[]args){
		Player test = new Player("Mohaaaaaaaaameeeeeeeeeedd el Gamed awy");
		winnerwinnerChickenDinner w = new winnerwinnerChickenDinner(test);
	}

}
