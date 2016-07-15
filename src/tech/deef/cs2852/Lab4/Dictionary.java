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
import java.util.Scanner;

public class Dictionary {
	private Collection<String> words;

	//set the class variable Words to the input words.
	public Dictionary(Collection<String> words) {
		if (words == null) {
			throw new NullPointerException();
		}
		this.words = words;
		clear();
	}

	//clears class variable words.
	public void clear() {
		words.clear();
	}

	//checks for string in words
	public boolean Contains(String string) {
		for (String s : words) {
			if (s == string) {
				return true;
			}
		}
		return false;
	}

	//loads a file into words.
	public long load(File file) {
		Scanner scanner;
		Long time = System.nanoTime(); 
		try {
			scanner = new Scanner(file);

			String tempString = "";
			while (scanner.hasNextLine()) {

				tempString += scanner.nextLine();
			}
			time = System.nanoTime();
			int start = 0;
			for (int i = 0; i < tempString.length(); i++) {

				if (tempString.charAt(i) == ' ' || tempString.charAt(i) == '\n') {
					//System.out.println(i + ":" + tempString.substring(start, i));
					//System.out.flush();
					words.add(tempString.substring(start,i));
					start = i + 1;
				}
			}
			words.add(tempString.substring(start, tempString.length()-1));
			//System.out.println(tempString.substring(start, tempString.length()-1));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return System.nanoTime() - time;
	}
}
