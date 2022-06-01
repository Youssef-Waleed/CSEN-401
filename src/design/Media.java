package design;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

    public class Media {
    	private File file;
    	private Clip clip;
    	private String path;
    	
    	public Media(String path){
    		this.path=path;
    	}
        public void play() {
            try {
            	file = new File(path);
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(file));
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
        


        //private static String arg;

        public static void main(String[] args){
//
//
//        JFrame f = new JFrame();
//        JPanel p = new JPanel();
//        JLabel l = new JLabel();
//        ImageIcon icon = new ImageIcon("left-arrow.png");    
//        f.setSize(480, 360);
//        f.setVisible(true);
//        l.setIcon(icon);
//        p.add(l);
//        f.getContentPane().add(p);
//        f.setLocationRelativeTo(null);
//        f.setResizable(false);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		  Media audio = new Media();
//          audio.play();

            }
        }
