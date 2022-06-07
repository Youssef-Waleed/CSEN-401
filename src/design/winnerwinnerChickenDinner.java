package design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import engine.*;

public class winnerwinnerChickenDinner{
	JFrame window;
	JPanel temp;
	public winnerwinnerChickenDinner(Player winner){
		window = new JFrame();
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		window.setSize(width+1, height);
		window.setLocation(1920/2-width/2,0);
		temp = new JPanel();
		//temp.setLayout(null);
		temp.setBounds(0, height/2-75, width, 350);
		temp.setBackground(new Color(0,0,0,0));
		JLabel text =  new JLabel();
		text.setText(winner.getName()+" WINS");
		text.setBounds(width/2-250, height/2-75, width, 350);
		text.setHorizontalTextPosition(JLabel.CENTER);
		text.setFont(new Font("Agency FB", Font.BOLD, 50));
		text.setForeground(Color.WHITE);
		text.setBackground(null);
		ImageIcon bkground= new ImageIcon(this.getClass().getResource("/resources/icons/unknown.png"));
		JLabel backround = new JLabel();
		backround.setBounds(0, 0, width, height);
		backround.setIcon(new ImageIcon(bkground.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH)));
		temp.add(text);
		window.add(temp);
		window.add(backround);
		window.setVisible(true);
	}
	public static void main(String[]args){
		Player test = new Player("Yoooooooooooooooooooooooussef el Gamed awy");
		winnerwinnerChickenDinner w = new winnerwinnerChickenDinner(test);
	}

}
