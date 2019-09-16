import java.util.concurrent.RecursiveAction;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThreadClass extends RecursiveAction {

    WordRecord word;
    Timer timer = new Timer(200, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            word.drop((int)word.getSpeed()/100);
          }
        }
    );

    public ThreadClass(WordRecord word){
      this.word=word;
      timer.setInitialDelay(0);
    }

    protected void compute(){
      System.out.println("Thread Created for " + word.getWord());
      timer.start();
	  }






}
