import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordThread extends Thread {

  WordRecord word;
  private volatile boolean flag = true;
  private Timer timer = new Timer(200, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            word.drop((int)word.getSpeed()/100);
            if (word.getY()>=WordApp.yLimit){
              word.resetWord();
              WordApp.score.missedWord();
              WordApp.missed.setText("Missed:" + WordApp.score.getMissed()+ "    ");
            }
        }
      }
  );

  public WordThread(WordRecord word){
    this.word=word;
    timer.setInitialDelay(0);
  }


  public void run() {
    timer.start();
	}

}
