package design;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.*;

public class winnerwinnerChickenDinner{
	JFrame window;
	public winnerwinnerChickenDinner(Player winner,Game G){
		window = new JFrame();
		window.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		window.setSize(width+1, height);
		window.setLocation(1920/2-width/2,0);
		JPanel temp = new JPanel();
		temp.setBounds(width-250, height-75, 300, 150);
		JLabel text =  new JLabel(winner.getName()+" WINS");
		text.setBounds(width-250, height-75, 300, 150);
		text.setFont(new Font("Agency FB", Font.BOLD, 50));
		text.setVisible(true);
		temp.add(text);
		window.add(temp);
		window.setVisible(true);
	}
	public static void main(String[]args){
		Player test = new Player("Youssef el Gamed awy");
		winnerwinnerChickenDinner w = new winnerwinnerChickenDinner(test,null);
	}

}
