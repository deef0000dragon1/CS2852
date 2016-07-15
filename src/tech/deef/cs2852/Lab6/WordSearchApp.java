package tech.deef.cs2852.Lab6;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import tech.deef.cs2852.Lab6.Dictionary;
import tech.deef.cs2852.Lab6.WordFinder;

/**
 * This is the main class for the Word Search application.
 * It obtains asks the user for the files containing the dictionary words
 * and the letter grid to be searched, and then turns on the powerhouse of this
 * application - the WordFinder. When the results are returned, it prints
 * those results to a file and (optionally) displays the file to the user.
 * @author taylor [based on a similar class by hornick]
 * @version 2014.04.03
 */
public class WordSearchApp {
    /**
     * Collection of words known to be spelled correctly
     */
//TODO:
	// Replace SortedArrayList with ArrayList or LinkedList to observe
	// changes in how fast the application runs.
	private final Dictionary dictionary = new Dictionary(new ArrayList<String>());

    /**
     * List of game pieces that compose the game board
     */
	private final List<GamePiece<Character>> gameBoard = new ArrayList<>();

    /**
     * Default number of rows
     */
	private int rows = 0;

    /**
     * Default number of columns
     */
	private int cols = 0;

	/**
	 * Static entry point
	 * @param ignored Command line parameters (none for this app)
	 */
	public static void main(String[] ignored) {
		// just create an instance of this class and start running
		new WordSearchApp().start();
	}

	/**
	 * Non-static entry point Starts the program main program sequence
	 */
	private void start() {

		dictionary.load(selectFile("dictionary.txt", "dictionary"));
		loadGameBoard(selectFile(null, "game board"));

		WordFinder wf = new WordFinder(gameBoard, dictionary, rows, cols);
		wf.startSearching();
		outputResults(wf.getResults(), "results.txt");
		
		System.out.println("done");
	}

    /**
     * Verifies that the specified file exists.  If not, prompts user
     * for a valid filename.  Once valid file exists, the File is returned.
     * @param filename Name of desired file.  If <tt>null</tt>, then prompts user
     *                 to enter a filename
     * @return The desired file
     */
    private File selectFile(String filename, String description) {
        File file;
        if(filename==null) {
            filename = JOptionPane.showInputDialog("Enter the path to the " + description + " file.");
        }
        do {
            if(filename==null) {
                System.exit(0);
            }
            file = new File(filename);
            if(!file.exists()) {
                filename = JOptionPane.showInputDialog("Could not find:\n\n" + file.getAbsolutePath()
                        + "\n\nEnter the path to the " + description + " file.");
            }
        } while(!file.exists());
        return file;
    }

	/**
	 * This method will parse through the input file and translate the text
	 * file into a List of game pieces
	 * @param boardFile File supplied by the user for the game board
	 */
	private void loadGameBoard(File boardFile) {
		try (Scanner inputScanner = new Scanner(boardFile)) {
			inputScanner.useDelimiter(System.getProperty("line.separator"));
            cols = -1;
			while(inputScanner.hasNextLine()) {
				char[] currLine = inputScanner.nextLine().toCharArray();
                if(cols!=-1 && cols!=currLine.length) {
                    throw new IOException("Not all rows have the same number of columns.");
                }
				cols = currLine.length;
                for(Character letter : currLine) {
                    gameBoard.add(new GamePiece<>(letter, false));
				}
				rows++;
			}
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Failed to load the game board file. Exiting...");
			System.exit(1);
		}
	}

	/**
	 * Output method for storing the results in a file
	 * @param results A copy of the words found from the WordFinder
	 */
	private void outputResults(Collection<String> results, String filename) {
		if(results!=null) {
            try (PrintWriter printStream = new PrintWriter(new FileOutputStream(new File(filename)))) {

    			printStream.println("The following " + results.size() + " words were found:");
    			printStream.print(System.getProperty("line.separator"));
    			for(String found : results) {
    				printStream.println("\t" + found);
    			}
    			printStream.print(System.getProperty("line.separator"));
    			printStream.flush();
			} catch(FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Error writing output file.");
			}
		}
	}
}
