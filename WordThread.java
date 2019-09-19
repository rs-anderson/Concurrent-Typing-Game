import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class WordThread extends Thread {

  public WordRecord word;
  private volatile boolean flag = true;
  private Timer timer = new Timer(200, new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            if (word.getWord().equals("")){
              timer.stop();
              // System.out.println("Thread stopped");
            }

            else if (WordApp.ended==true){
              timer.stop();
              // System.out.println("Thread stopped");
              word.setWord("");
            }

            else{
              word.drop((int)word.getSpeed()/100);

              if (word.getY()>=WordApp.yLimit){
                File soundFile = new File("253886__themusicalnomad__negative-beeps.wav");
                try{
                  AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                  Clip clip = AudioSystem.getClip();
                  clip.open(audioIn);
                  clip.start();
                }
                catch(Exception f){System.out.println(f);}

                WordApp.wordCounter++;
                if (word.exceededTest()){
                  timer.stop();
                  // System.out.println("Thread stopped");
                  word.setWord("");
                }
                else{
                  word.resetWord();
                }
                WordApp.score.missedWord();
                WordApp.missed.setText("Missed:" + WordApp.score.getMissed()+ "    ");
              }
            }
        }
      }
  );

  public WordThread(WordRecord word){
    this.word=word;
    timer.setInitialDelay(0);
  }

  public void updateTotals(String word){
    // System.out.println(word.length());
    File soundFile = new File("135936__bradwesson__collectcoin.wav");
    try{
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start();
    }
    catch(Exception e){System.out.println(e);}

    WordApp.score.caughtWord(word.length());
    WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
    WordApp.scr.setText("Score: " + WordApp.score.getScore()+ "    ");
  }

  public boolean match(String testWord){
    // System.out.println(word.length());
    if(word.matchWord(testWord)==true){
      updateTotals(testWord);
      return true;
    }
    return false;
  }

  public void run() {
    timer.start();
	}
}
