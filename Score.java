

public class Score {
	private static int missedWords;
	private static int caughtWords;
	private static int gameScore;

	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}

	// all getters and setters must be synchronized

	public synchronized int getMissed() {
		return missedWords;
	}

	public synchronized int getCaught() {
		return caughtWords;
	}

	public synchronized int getTotal() {
		return (missedWords+caughtWords);
	}

	public synchronized int getScore() {
		return gameScore;
	}

	public synchronized void missedWord() {
		missedWords++;
	}

	public synchronized static void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

	public synchronized void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
