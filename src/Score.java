/** Score class to store information about the scores.
 * @author ANDRYA005
**/

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
	private static AtomicInteger missedWords;
	private static AtomicInteger caughtWords;
	private static AtomicInteger gameScore;

	/**
	 * Constructor
	 */
	Score() {
		missedWords= new AtomicInteger(0);
		caughtWords= new AtomicInteger(0);
		gameScore= new AtomicInteger(0);
	}

	/**
	 * gets the missed words
	 * @return amount of words missed
	 */
	public synchronized int getMissed() {
		return missedWords.get();
	}

	/**
	 * gets the caught words
	 * @return amount of words caught
	 */
	public synchronized int getCaught() {
		return caughtWords.get();
	}

	/**
	 * gets the total amount of words
	 * @return the total amount of words
	 */
	public synchronized int getTotal() {
		return (missedWords.get()+caughtWords.get());
	}

	/**
	 * gets the score for the game
	 * @return the score for the game
	 */
	public synchronized int getScore() {
		return gameScore.get();
	}

	/**
	 * incrementing the missed words
	 */
	public synchronized void missedWord() {
		missedWords.getAndIncrement();
	}

	/**
	 * incrementing the caught words and updating the score
	 * @param length number of characters of the word
	 */
	public synchronized static void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	/**
	 * reset score variables to 0.
	 */
	public void resetScore() {
		caughtWords.set(0);
		missedWords.set(0);
		gameScore.set(0);
	}
}
