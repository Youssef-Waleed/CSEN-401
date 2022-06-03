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

import javax.swing.*;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class StartPage implements ActionListener,
		MouseInputListener {
	private JFrame startwindow;
	private JButton startbt;
	private JPanel pane;
	private JLabel marvel;
	private Media audio;
	private static boolean loop = true;
	
	
	public StartPage(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		audio = new Media(this.getClass().getResource("/resources/audios/AvengersMarvelTheme.wav") , loop);
		startwindow = new JFrame();
		startbt = new JButton("START");
		marvel = new JLabel();
		
		startbt.addActionListener(this);
		startbt.addMouseListener(this);
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/icons/Marvel_Logo.png"));
		ImageIcon bkground= new ImageIcon(this.getClass().getResource("/resources/icons/Marvelstart.png"));
		startwindow = new JFrame();
		startwindow.setSize(1500, height);
		startwindow.setLocation(1920/2-width/2,0);
		startwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startwindow.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		startwindow.setTitle("Marvel: Ultimate War");
		startwindow.setIconImage(icon.getImage());
		
		pane = new JPanel();
		startwindow.add(pane, BorderLayout.CENTER);
		pane.setLayout(null);
		
		marvel.setIcon(new ImageIcon(bkground.getImage().getScaledInstance(1500, 1050,Image.SCALE_SMOOTH)));
		
		marvel.setBounds(0, 0, startwindow.getWidth(), startwindow.getHeight());
		
		pane.add(startbt);
		pane.add(marvel);
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 45));
		startbt.setFocusable(false);
		startbt.setBackground(new Color(0xFFE20B));
		startbt.setBounds(650, 700, 250, 100);
		
		audio.play();
		startwindow.setVisible(true);
	}

	
	//  x.setBounds(x, y, W, H);
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		startbt.setBounds(625, 675, 300, 150);
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 65));
		startbt.setBackground(new Color(0xD99F6A));

	}

	@Override
	public void mouseExited(MouseEvent e) {
		startbt.setBounds(650, 700, 250, 100);
		startbt.setFont(new Font("Comic Sans MS",Font.PLAIN, 45));
		startbt.setBackground(new Color(0xFFE20B));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//selectScreen m = new selectScreen();
		audio.pause();
		//startwindow.dispatchEvent(new WindowEvent(startwindow, WindowEvent.WINDOW_CLOSING));
		startwindow.setVisible(false);

	}
	
	
	public static void main(String[]args){
		StartPage p = new StartPage();
		
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
