/** WordApp class to run the game.
 * @author ANDRYA005
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.io.*;
import javax.sound.sampled.*;


public class WordApp {

	//shared variables
	static int noWords;																// number of words to appear on screen at given time
	static int totalWords;														// total words to fall

  static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static Score score = new Score();

	static WordPanel w;
	static JPanel txt;
	static JLabel caught;
	static JLabel missed;
	static JLabel scr;

	static volatile boolean ended=false;									// signal that the ended button was pressed
	static volatile boolean finished=false; 							// signal that the game is finished
	static volatile boolean exceeded=false;								// signal to show no more words
	private static boolean initialRun=true;								// true if first run of game


	/**
	 * Method to create the GUI and handle action events.
	 * @param frameX x-size of the GUI
	 * @param frameY y-size of the GUI
	 * @param yLimit the maximum y value for words to fall
	 */
	public static void setupGUI(int frameX,int frameY,int yLimit) {

			// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);

			// JPanel creation
    	JPanel g = new JPanel();
      g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
    	g.setSize(frameX,frameY);

			// WordPanel creation
			w = new WordPanel(words,yLimit);
			w.setSize(frameX,yLimit+100);
	    g.add(w);

			// Jpanel creation for the score text
	    txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
	    caught =new JLabel("Caught: " + score.getCaught() + "    ");
	    missed =new JLabel("Missed: " + score.getMissed()+ "    ");
	    scr =new JLabel("Score: " + score.getScore()+ "    ");
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);

			// for handling entered words
	    final JTextField textEntry = new JTextField("",20);
	   	textEntry.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent evt) {
	          String text = textEntry.getText();
						if (!text.equals("")){
							if(w.testWord(text)==0){
								File soundFile = new File("142608__autistic-lucario__error.wav");
								try{
									AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
									Clip clip = AudioSystem.getClip();
									clip.open(audioIn);
									clip.start();
								}
								catch(Exception e){System.out.println(e);}
							}
						}
	          textEntry.setText("");
						if(!finished){
							textEntry.requestFocus();
						}

	      }
	    });

	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);

			// JPanel for the buttons
	    JPanel b = new JPanel();
      b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
	   	JButton startB = new JButton("Start");

			// added the listener to the jbutton to handle the "pressed" event
			startB.addActionListener(new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e)
		      {
						if (!initialRun){
							w.endThreads();
						}
						initialRun=false;
						score.resetScore();
						caught.setText("Caught: " + score.getCaught() + "    ");
						missed.setText("Missed: " + score.getMissed() + "    ");
						scr.setText("Score: " + score.getScore()+ "    ");
						finished=false;
						ended=false;
						exceeded=false;
		    	  textEntry.requestFocus();  //return focus to the text entry field
						createWordArray();
						w.run();
		      }
		  });
			JButton endB = new JButton("End");;

				// added the listener to the jbutton to handle the "pressed" event
				endB.addActionListener(new ActionListener()
		    {
		    public void actionPerformed(ActionEvent e)
		      {
		    	  //[snip]
						ended = true;
						initialRun = true;
						score.resetScore();
						caught.setText("Caught: " + score.getCaught() + "    ");
						missed.setText("Missed: " + score.getMissed() + "    ");
						scr.setText("Score: " + score.getScore()+ "    ");
		      }
		    });

			JButton quitB = new JButton("Quit");

				// added the listener to the jbutton to handle the "pressed" event
				quitB.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
						//[snip]
						System.exit(0);
					}
				});

		b.add(startB);
		b.add(endB);
		b.add(quitB);

		g.add(b);

  	frame.setLocationRelativeTo(null);  			// Center window on screen.
  	frame.add(g); 														//add contents to window
    frame.setContentPane(g);
    frame.setVisible(true);


	}

/**
 * For retreiving a dictionary of words from a text file.
 * @param  filename file to read from
 * @return          array of words
 */
public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}

	/**
	 * Creating an array of WordRecord objects
	 */
	public static void createWordArray(){
		int x_inc=(int)frameX/noWords;
			//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}
	}


	/**
	 * Main method to run the game.
	 * @param args 0 = total words, 1 = number of words to fall, 2 = dictionary file.
	 */
	public static void main(String[] args) {

		//Command line arguments
		totalWords=Integer.parseInt(args[0]);  						//total words to fall
		noWords=Integer.parseInt(args[1]);							  //total words falling at any point
		String[] tmpDict=getDictFromFile(args[2]);			  //file of words


		if (totalWords < noWords){
			System.out.println("Total words is less than no Words");
			System.exit(0);
		}

		if (tmpDict!=null)
			dict = new WordDictionary(tmpDict);

		WordRecord.dict=dict;															//set the class dictionary for the words.

		words = new WordRecord[noWords];  								//shared array of current words

		setupGUI(frameX, frameY, yLimit);
		createWordArray();

		//sound effect for start of game
		File soundFile = new File("146434__copyc4t__dundundunnn.wav");
		try{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
		catch(Exception f){System.out.println(f);}
	}

}
