package design;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

    public class Media {
    	//private File file;
    	private Clip clip;
    	private URL path;
    	private String path2;
    	private File file;
    	private boolean loop;

    	public Media(URL path, boolean loop){
    		this.path=path;
    		this.loop=loop;
    	}
    	public Media(String path, boolean loop){
    		this.path2=path;
    		file =  new File(path2);
    		this.loop=loop;
    	}
        public void play() {
            try {
            	AudioInputStream sound;
            	if(file==null)
            		sound =AudioSystem.getAudioInputStream(path); 
            	else
            		sound =AudioSystem.getAudioInputStream(file); 
                clip = AudioSystem.getClip();
                clip.open(sound);
                if(loop)
                	clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception e) {
                System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
            }
        }
        
        public void pause() {
            try {
                clip.stop();
            } catch (Exception e) {
                System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
            }
        }
        public boolean isPlaying(){
        	return clip.isRunning();
        }
        


        //private static String arg;

        public static void main(String[] args){
//
//TESTING
//        JFrame f = new JFrame();
//        //JPanel p = new JPanel();
//        JLabel l = new JLabel();
//        JButton b = new JButton("POKE ME");
//        ImageIcon icon = new ImageIcon("icons/Cover.png");    
//        b.setBounds(310,210,190,60);
//        b.setFont(new Font("", Font.BOLD,25));
//        b.setForeground(Color.WHITE);
//        b.setFocusable(false);
//        b.setOpaque(false);
//        b.setBackground(new Color(0,0,0,0));
//        b.addActionListener(new ActionListener() {
//			@Override public void actionPerformed(ActionEvent arg0) {l.setIcon(new ImageIcon("icons/ProfessionalCover.png"));} });
//        f.setSize(720, 480);
//        f.setVisible(true);
//        l.setIcon(icon);
//        l.setVerticalAlignment(JLabel.CENTER);
//        l.setHorizontalAlignment(JLabel.CENTER);
//        f.getContentPane().add(l, BorderLayout.CENTER);
//        //f.getContentPane().add(p);
//        f.setLocationRelativeTo(null);
//        //f.setResizable(false);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setContentPane(l);
////        f.add(b, BorderLayout.CENTER);
//        
////		  Media audio = new Media();
////          audio.play();
        	
        	

            }
        }
