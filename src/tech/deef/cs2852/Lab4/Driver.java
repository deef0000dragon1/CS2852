/*
 * @Author Jeffrey Koehler
 * @purpose Data Structures Lab 4
 * cs2852 -051
 * 
 * */
package tech.deef.cs2852.Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

import tech.deef.cs2852.Lab1.SortedArrayList;

public class Driver {
	public static void main(String[] args) {
		//different testinf files.
		File words = new File("words.txt");
		File story = new File("story.txt");
		File bible = new File("kjV10.txt");

		// one set of checks.
		boolean test = false;
		System.out.println(
				"| Dictionary |  Document |      Collection |            Time to add |      Time to spell-check |");
		if (test) {

			timeingTests(words, story, "ArrayList");
			timeingTests(words, story, "LinkedList");
			timeingTests(words, story, "SortedArrayList");
			timeingTests(story, words, "ArrayList");
			timeingTests(story, words, "LinkedList");
			timeingTests(story, words, "SortedArrayList");
		}
		// predictions, if test = false
		if (!test) {
			timeingTests(words, bible, "ArrayList");
			timeingTests(words, bible, "LinkedList");
			timeingTests(words, bible, "SortedArrayList");
			timeingTests(bible, words, "ArrayList");
			timeingTests(bible, words, "LinkedList");
			timeingTests(bible, words, "SortedArrayList");
		}
	}

	//testing code to test the times of different data structures. 
	public static void timeingTests(File dictionary, File document, String Collection) {
		if (Collection.equals("ArrayList")) {

			ArrayList<String> dictList = new ArrayList<String>();
			ArrayList<String> spellCheckList = new ArrayList<String>();
			Dictionary Dic1 = new Dictionary(dictList);
			long timeAdd = Dic1.load(dictionary);
			long timeSpellCheck = spellCheck(Dic1, document, spellCheckList);

			// print statement for time
			System.out.printf(
					"| " + dictionary.getName() + " | " + document.getName()
							+ " |       ArrayList | %d mins %10.7f secs | %d mins %10.7f secs |\n",
					((timeAdd / 1000000000) / 60), (timeAdd / 1000000000.0) % 60, ((timeSpellCheck / 1000000000) / 60),
					(timeSpellCheck / 1000000000.0) % 60);

		}
		if (Collection.equals("LinkedList")) {
			LinkedList<String> dictList = new LinkedList<String>();
			LinkedList<String> spellCheckList = new LinkedList<String>();
			Dictionary Dic1 = new Dictionary(dictList);
			long timeAdd = Dic1.load(dictionary);
			long timeSpellCheck = spellCheck(Dic1, document, spellCheckList);
			// print statement
			System.out.printf(
					"| " + dictionary.getName() + " | " + document.getName()
							+ " |      LinkedList | %d mins %10.7f secs | %d mins %10.7f secs |\n",
					((timeAdd / 1000000000) / 60), (timeAdd / 1000000000.0) % 60, ((timeSpellCheck / 1000000000) / 60),
					(timeSpellCheck / 1000000000.0) % 60);
		}
		if (Collection.equals("SortedArrayList")) {
			SortedArrayList<String> dictList = new SortedArrayList<String>();
			SortedArrayList<String> spellCheckList = new SortedArrayList<String>();
			Dictionary Dic1 = new Dictionary(dictList);
			long timeAdd = Dic1.load(dictionary);
			long timeSpellCheck = spellCheck(Dic1, document, spellCheckList);
			// print statement
			System.out.printf(
					"| " + dictionary.getName() + " | " + document.getName()
							+ " | SortedArrayList | %d mins %10.7f secs | %d mins %10.7f secs |\n",
					((timeAdd / 1000000000) / 60), (timeAdd / 1000000000.0) % 60, ((timeSpellCheck / 1000000000) / 60),
					(timeSpellCheck / 1000000000.0) % 60);
		}
	}

	//returns the time it takes to check a document against a dictionary. 
	public static long spellCheck(Dictionary d, File Document, Collection<String> checked) {

		// load the document into a series of words to be cross checked.
		try {
			Scanner scanner = new Scanner(Document);

			String tempString = "";
			while (scanner.hasNextLine()) {
				tempString += scanner.nextLine();
			}
			ArrayList<String> DocWords = new ArrayList<String>();

			int start = 0;
			for (int i = 0; i < tempString.length(); i++) {
				if (tempString.charAt(i) == ' ' || tempString.charAt(i) == '\n') {
					// System.out.println(i + ":" + tempString.substring(start,
					// i));
					// System.out.flush();
					DocWords.add(tempString.substring(start, i));
					start = i + 1;
				}
			}
			DocWords.add(tempString.substring(start, tempString.length() - 1));

			long time = System.nanoTime();

			for (String s : DocWords) {
				if (d.Contains(s)) {
					checked.add(s);
				}
			}

			return System.nanoTime() - time;
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		return (Long) null;
	}
}
