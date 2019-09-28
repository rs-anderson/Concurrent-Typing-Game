/** WordPanel class to animate the words.
 * @author ANDRYA005
**/

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

		private WordRecord[] words;
		private int noWords;							// number of words to be displayed on screen at any time
		private int maxY;
		private Timer timer;
		private WordThread [] thrds;

		/**
		 * For drawing the words onto the panel
		 * @param g Graphics object
		 */
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));

		    for (int i=0;i<noWords;i++){
		    	g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
		    }

		  }

		/**
		 * Constructor
		 * @param words array of WordRecords
		 * @param maxY  for drawing the red rectangle
		 */
		WordPanel(WordRecord[] words, int maxY) {
			this.words=words; //will this work?
			noWords = words.length;
			this.maxY=maxY;
			timer = new Timer(100, this);
			timer.setInitialDelay(0);
		}

		/**
		 * For creating the threads
		 */
		public void run() {

			timer.start();

			thrds= new WordThread[noWords];

			for (int i=0;i<noWords;i++) {
				thrds[i]=new WordThread(words[i]);
			}

			for (int i=0;i<noWords;i++) {
				thrds[i].start();
			}
		}

		/**
		 * Action listener for the timer
		 * @param e ActionEvent object
		 */
		public void actionPerformed(ActionEvent e){
			validate();
			repaint();

			// if the game is finished, show the final GUI
			if (WordApp.finished){
				EndGui gui = new EndGui();
				gui.setVisible(true);
				timer.stop();
			}

		}

		/**
		 * For testing if the entered word is equal to any of the current words
		 * @param  word entered word
		 * @return      signal of whether a word was matched
		 */
		public int testWord(String word) {
			int successCounter = 0;
			for (int i=0;i<noWords;i++){
				if(thrds[i].match(word)==true){
					successCounter++;
				}
			}
			return successCounter;
		}

		/**
		 * Ends all threads
		 */
		public void endThreads(){
			for (int i=0;i<noWords;i++){
				thrds[i].endThread();
			}
		}

}
