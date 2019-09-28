/** WordRecord class to store information about the words.
 * @author ANDRYA005
**/

public class WordRecord {

	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;

	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;

	public static WordDictionary dict;

	/**
	 * Default constructor
	 */
	WordRecord() {
		text="";
		x=0;
		y=0;
		maxY=300;
		dropped=false;
		fallingSpeed=(int)((WordApp.score.getTotal()+1) * Math.random() * (maxWait-minWait)+minWait);
	}

	/**
	 * Another constructor
	 * @param text text of the word
	 */
	WordRecord(String text) {
		this();
		this.text=text;
	}

	/**
	 * Another constructor
	 * @param text text of the word
	 * @param x    x-position of the word
	 * @param maxY the highest position of the word in the GUI
	 */
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}


	/**
	 * Setting the y-position of the word
	 * @param y y-position of the word
	 */
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}

	/**
	 * Setting the x-position of the word
	 * @param x x-position of the word
	 */
	public synchronized  void setX(int x) {
		this.x=x;
	}

	/**
	 * Setting the text of the word
	 * @param text the text to be set
	 */
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	/**
	 * getting the text of the word
	 * @return the text of the word
	 */
	public synchronized  String getWord() {
		return text;
	}

	/**
	 * getting the x-position of the word
	 * @return the x-position of the word
	 */
	public synchronized  int getX() {
		return x;
	}

	/**
	 * getting the y-position of the word
	 * @return the y-position of the word
	 */
	public synchronized  int getY() {
		return y;
	}

	/**
	 * getting the falling speed of the word
	 * @return the falling speed
	 */
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	/**
	 * Setting the x- and y-position of the word
	 * @param x [description]
	 * @param y [description]
	 */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/**
	 * re-setting the y position of the word to the top of the screen
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * generating a new word to be displayed at the top of the screen
	 */
	public synchronized void resetWord() {
		if (WordApp.exceeded==true){
			text="";
		}
		else{
			resetPos();
			text=dict.getNewWord();
			dropped=false;
			fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait);
		}
	}

	/**
	 * Method for testing equality between the entered word and this word
	 * @param  typedText the text to be compared
	 * @return           true if the word does match. False otherwise
	 */
	public synchronized boolean matchWord(String typedText) {
		if (typedText.equals(this.text)) {
				WordApp.score.caughtWord(typedText.length());
				if (exceededTest()){
					WordApp.exceeded = true;
				}
				resetWord();
				return true;
		}
		else
			return false;
	}


	/**
	 * "dropping" the word by a certain value
	 * @param inc y-amount that the word should be dropped
	 */
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	/**
	 * word to determine if the word has been dropped
	 * @return true if the word has been dropped. False otherwise.
	 */
	public synchronized  boolean dropped() {
		return dropped;
	}

	/**
	 * Test if the amount of words that have been displayed exceeds the total amount.
	 * @return true, if they have exceeded. False, if they have not.
	 */
	public synchronized boolean exceededTest(){
		if (WordApp.score.getTotal() + WordApp.noWords >= WordApp.totalWords + 1){
			if (WordApp.score.getTotal() == WordApp.totalWords){
				WordApp.finished=true;
			}
			return true;
		}
		else{
			return false;
		}
	}

}
