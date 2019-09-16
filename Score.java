

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

	public int getMissed() {
		return missedWords;
	}

	public int getCaught() {
		return caughtWords;
	}

	public int getTotal() {
		return (missedWords+caughtWords);
	}

	public int getScore() {
		return gameScore;
	}

	public void missedWord() {
		missedWords++;
	}

	public static void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

	public void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
