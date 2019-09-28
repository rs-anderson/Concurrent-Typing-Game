/** WordThread class to implement multi-threading.
 * @author ANDRYA005
**/

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;


public class WordThread extends Thread {

  public WordRecord word;
  private Timer timer = new Timer(200, new ActionListener() {

        // action listener each time the time object triggers
        public void actionPerformed(ActionEvent e) {

            // if the words were exceeded
            if (word.getWord().equals("")){
              endThread();
            }

            // if the player clicked "End"
            else if (WordApp.ended==true){
              endThread();
              word.setWord("");
            }

            // normal course of the game
            else{
              word.drop((int)word.getSpeed()/100);

              // word is in the red
              if (word.getY()>=WordApp.yLimit){
                File soundFile = new File("253886__themusicalnomad__negative-beeps.wav");             // sound effect if word is missed
                try{
                  AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                  Clip clip = AudioSystem.getClip();
                  clip.open(audioIn);
                  clip.start();
                }
                catch(Exception f){System.out.println(f);}

                WordApp.score.missedWord();                      // incrementing the missedWord counter
                if (word.exceededTest()){                        // the maximum amount of words have been shown
                  endThread();
                  word.setWord("");
                }
                else{
                  word.resetWord();                               // generating new word when word missed
                }

              WordApp.missed.setText("Missed:" + WordApp.score.getMissed()+ "    ");   // changing the missed words text
              }
            }
        }
      }
  );

  /**
   * Constructor
   * @param word WordRecord object attached to thread
   */
  public WordThread(WordRecord word){
    this.word=word;
    timer.setInitialDelay(0);
  }

  /**
   * To update the score totals
   */
  public void updateTotals(){
    File soundFile = new File("135936__bradwesson__collectcoin.wav");
    try{
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start();
    }
    catch(Exception e){System.out.println(e);}

    WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
    WordApp.scr.setText("Score: " + WordApp.score.getScore()+ "    ");
  }

  /**
   * method to match the word with the entered word
   * @param  testWord word to be matched with
   * @return          true if the word was matched. False otherwise
   */
  public boolean match(String testWord){
    if(word.matchWord(testWord)==true){
      updateTotals();
      return true;
    }
    return false;
  }

  /**
   * executed when the thread is started.
   */
  public void run() {
    timer.start();
	}

  /**
   * stops the thread
   */
  public void endThread(){
    timer.stop();
  }
}
