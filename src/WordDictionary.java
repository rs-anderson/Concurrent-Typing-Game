/** WordDictionary class to store the words to be used.
 * @author ANDRYA005
**/

public class WordDictionary {

	int size;
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; 																		//default dictionary

	/**
	 * Constructor
	 * @param tmp array of words
	 */
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
	}

	/**
	 * default constructor
	 */
	WordDictionary() {
		size=theDict.length;
	}

	/**
	 * gets a new word from the dictionary array
	 * @return a word
	 */
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}

}
