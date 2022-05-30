package design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

import engine.Game;

@SuppressWarnings("serial")

public class MainGUI extends JFrame implements ActionListener, MouseInputListener {
	private Game game;
	private JFrame gameframe;
	private ImageIcon icon;
	private JPanel info,main, container, current, actions;
	private JButton up,down,right,left,attack,castability,useleaderab
				/*,b11,b12,b13,b14,b15,
					b21,b22,b23,b24,b25,
					b31,b32,b33,b34,b35,
					b41,b42,b43,b44,b45,
					b51,b52,b53,b54,b55*/;
	private JButton[][] button;
	
	public MainGUI(){
		
		icon = new ImageIcon("Marvel_Logo.png");
		ImageIcon upicon = new ImageIcon("up-arrow.png");
		ImageIcon downicon = new ImageIcon("down-arrow.png");
		ImageIcon righticon = new ImageIcon("right-arrow.png");
		ImageIcon lefticon = new ImageIcon("left-arrow.png");
		ImageIcon attackicon = new ImageIcon("attack.png");
		ImageIcon casticon = new ImageIcon("ability.png");
		ImageIcon leadicon = new ImageIcon("wizard.png");
		
		gameframe = new JFrame();
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setSize(1500, 1050);
		gameframe.setLocation(100,0);
		gameframe.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		gameframe.setTitle("Marvel: Ultimate War");
		//gameframe.setLayout(new GridLayout(5, 5));
		gameframe.setIconImage(icon.getImage());
		
		main = new JPanel();			//the new Panels
		main.setLayout(new GridLayout(5,5));
		info = new JPanel();
		info.setBackground(new Color(0xBB381D));
		info.setPreferredSize(new Dimension(500, 600));
		info.setLayout(new FlowLayout(FlowLayout.LEADING, 50,10));
		container = new JPanel();
		container.setBackground(new Color(0xFFFFFF));
		container.setPreferredSize(new Dimension(600, 200));
		container.setLayout(new BorderLayout());
		current = new JPanel();
		current.setBackground(new Color(0x353535));
		current.setPreferredSize(new Dimension(800, 200));
		current.setLayout(new FlowLayout(FlowLayout.LEADING, 50,10));
		actions = new JPanel();
		actions.setBackground(new Color(0x555510));
		actions.setPreferredSize(new Dimension(500, 350));
		actions.setLayout(null);
		
		gameframe.add(current, BorderLayout.SOUTH);
		gameframe.add(main, BorderLayout.CENTER);
		gameframe.add(container, BorderLayout.EAST);
		container.add(actions, BorderLayout.SOUTH);
		container.add(info, BorderLayout.CENTER);
		
		
		up= new JButton("UP", new ImageIcon(upicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		up.setHorizontalTextPosition(JButton.CENTER);
		up.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		up.setBounds(270,30,75,75);
		
		right= new JButton("RIGHT", new ImageIcon(righticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		right.setHorizontalTextPosition(JButton.CENTER);
		right.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		right.setBounds(360,125,75,75);
		
		left= new JButton("LEFT", new ImageIcon(lefticon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		left.setHorizontalTextPosition(JButton.CENTER);
		left.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		left.setBounds(180,125,75,75);
		
		down= new JButton("DOWN", new ImageIcon(downicon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
		down.setHorizontalTextPosition(JButton.CENTER);
		down.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		down.setBounds(270,220,75, 75);
		
		attack= new JButton("ATTACK",new ImageIcon(attackicon.getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH)));
		attack.setHorizontalTextPosition(JButton.CENTER);
		attack.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		attack.setBounds(270,125,75,75);
		
		castability= new JButton("CAST ABILITY", new ImageIcon(casticon.getImage().getScaledInstance(120,80,Image.SCALE_SMOOTH)));
		castability.setVerticalTextPosition(JButton.BOTTOM);
		castability.setHorizontalTextPosition(JButton.CENTER);
		castability.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		castability.setBounds(20,205,150,100);
		
		useleaderab= new JButton("USE LEADER ABILITY", new ImageIcon(leadicon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH)));
		useleaderab.setVerticalTextPosition(JButton.BOTTOM);
		useleaderab.setHorizontalTextPosition(JButton.CENTER);
		useleaderab.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		useleaderab.setBounds(450,205,150,100);
		
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
		
		
		
		
		button = new JButton[5][5];
		for(int i =0; i<5; i++)
			for(int j =0; j<5; j++)
				button[i][j]= new JButton();			//the new Buttons						


		for(int i =0; i<5; i++)								//button font
			for(int j =0; j<5; j++)
				button[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 16));

//		for(int i =0; i<5; i++)
//			for(int j =0; j<5; j++)
//				button[i][j].setBorder(BorderFactory.);
//		for(int i =0; i<5; i++)
//			for(int j =0; j<5; j++)
//				button[i][j].setBackground(new Color(0xF9F3D3));
		
		for(int i =0; i<5; i++)								//_______________
			for(int j =0; j<5; j++)
				button[i][j].setFocusable(false);

		for(int i =0; i<5; i++)								//_______________
			for(int j =0; j<5; j++)
				button[i][j].addActionListener(this);
		
		for(int i =0; i<5; i++)
			for(int j =0; j<5; j++)
				main.add(button[i][j]);
		
		

		gameframe.setVisible(true);
		//gameframe.validate(); 3ayzeen net check law di sa7
		//game=new Game( firstPlayer,  secondPlayer);	
	}


	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("HAHAHAHA");

	}
	
	
	
	
	public static void main(String[] args) {
		//MainGUI bla = new MainGUI();

	}

	
	




	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}











	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
}
