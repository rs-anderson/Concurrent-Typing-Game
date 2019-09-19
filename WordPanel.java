

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.concurrent.ForkJoinPool;


public class WordPanel extends JPanel implements Runnable, ActionListener {

		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		private Timer timer;
		static final ForkJoinPool fjPool = new ForkJoinPool();
		private WordThread [] thrds;


		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added
		    for (int i=0;i<noWords;i++){
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
		    }

		  }

		WordPanel(WordRecord[] words, int maxY) {
			this.words=words; //will this work?
			noWords = words.length;
			done=false;
			this.maxY=maxY;
			timer = new Timer(100, this);
			timer.setInitialDelay(0);
		}

		public void run() {
			//add in code to animate this
			timer.start();

			thrds= new WordThread[noWords];

			for (int i=0;i<noWords;i++) {
				thrds[i]=new WordThread(words[i]);
			}

			for (int i=0;i<noWords;i++) {
				thrds[i].start();
			}
		}

		public void actionPerformed(ActionEvent e){
			validate();
			repaint();
			if (WordApp.finished){
				EndGui gui = new EndGui();
				gui.setVisible(true);
				timer.stop();
			}

		}


		public int testWord(String word) {
			int successCounter = 0;
			for (int i=0;i<noWords;i++){
				if(thrds[i].match(word)==true){
					successCounter++;
				// if (words[i].matchWord(word)==true){
					// WordApp.score.caughtWord(word.length());
					// WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
					// WordApp.scr.setText("Score: " + WordApp.score.getScore()+ "    ");

				}
			}
			return successCounter;
		}

}
