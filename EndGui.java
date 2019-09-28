/** EndGui class to display the results-GUI.
 * @author ANDRYA005
**/

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class EndGui extends JFrame implements ActionListener {

   JButton button;

   /**
    * Constructor for the GUI
    */
   public EndGui() {
       super("Results");
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setSize(600, 400);
       setLocationRelativeTo(null);
       setLayout(new GridLayout(4,1));
       button = new JButton("Close Window");
       button.addActionListener(this);
       JLabel caught =new JLabel("Caught: " + WordApp.score.getCaught() + "    ");
       caught.setFont (caught.getFont ().deriveFont (64.0f));
 	     JLabel missed =new JLabel("Missed: " + WordApp.score.getMissed()+ "    ");
       missed.setFont (missed.getFont ().deriveFont (64.0f));
 	     JLabel scr =new JLabel("Score: " + WordApp.score.getScore()+ "    ");
       scr.setFont (scr.getFont ().deriveFont (64.0f));
       add(caught);
       add(missed);
       add(scr);
       add(button);
   }

    /**
     * Closed GUI when event triggered
     * @param e an ActionEvent object
     */
    public void actionPerformed(ActionEvent e){
        String buttonString = e.getActionCommand();
        if (buttonString.equals("Close Window")){
            dispose();
            WordApp.score.resetScore();
            WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
            WordApp.missed.setText("Missed: " + WordApp.score.getMissed() + "    ");
            WordApp.scr.setText("Score: " + WordApp.score.getScore()+ "    ");
            }
        else
            System.exit(0);
    }
}
