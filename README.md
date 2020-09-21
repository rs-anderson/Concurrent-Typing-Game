# Concurrent Typing Game

An epic typing game implementing concurrency in Java.

## Gameplay

For an illustration of the game-play, see the GIF below:

![Alt Text](https://github.com/ANDRYA005/Concurrent-Typing-Game/blob/master/game_play.gif)

The player has to correctly type the words that are falling before they reach the bottom of the screen. When a word is correctly entered, the *caught* counter is incremented and the score is updated. Alternatively, when a player does not enter a word in time, the *missed* counter is incremented. At the end of the game, the following screen appears, outlining the summary of the game:

![Alt Text](https://github.com/ANDRYA005/Concurrent-Typing-Game/blob/master/results.PNG)

## Concurrency

Each of the "columns" of words falling from the screen have their own thread. Furthermore, the GUI is run on a separate thread from the main thread.


For more information on the classes created, the concurrent features used and how I ensured thread-safety, see the file *Report.pdf*


## A few things to note on running the scripts:

1. After running the "make" command, all the necessary compilations of source files occurs.

2. Do not call "make run" because it does not allow you to pass command-line arguments in. Rather run the program by calling "java WordApp \<totalWords> \<noWords> <dictionary_file>" from the bin directory. The game will then begin.
  
  * \<totalWords> - the total number of words that will fall from the screen
  * \<noWords> - the number of words falling at any given time
  * <dictionary_file> - a .csv file containing the dictionary of words to use. We used example_dict.txt (seen in the bin directory).
  
3. Ensure the dictionary file is in the bin directory.

4. Ensure the sound files are in the bin directory.

5. When running "make clean", the sound files will be removed from the bin directory. In order to run the code again, please copy and paste the sound files from "SoundFiles" back into the directory.
