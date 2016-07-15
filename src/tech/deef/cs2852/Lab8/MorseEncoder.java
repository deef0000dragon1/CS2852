/*
 * @Author Jeffrey Koehler
 * @date 5/10/2016
 * @purpose Data Structures Lab 8
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MorseEncoder {
	private static File codeIndexFile;
	private static File codedFile;
	private static File outputFile;
	private static LookupTable table;

	public static void main(String[] args) {
		outputFile = new File("Output.txt");
		codedFile = new File("text.txt");
		codeIndexFile = new File("characters.txt");
		table = new LookupTable<Character>();

		Scanner scanner = null;
		try {
			scanner = new Scanner(codeIndexFile);
		} catch (FileNotFoundException e) {
			// file not found or null.
			e.printStackTrace();
		}

		String line = "";
		while (scanner.hasNextLine()) {

			line = scanner.nextLine();
			table.put(line.charAt(0) + "", line.substring(1));
		}

		decode();

	}

	private static void decode() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(codedFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = "";
		while (scanner.hasNextLine()) {
			s = s + scanner.nextLine() + "\n";
		}
		
		char[] c = s.toCharArray();

		for (char character : c) {
			
			character = (character + "").toLowerCase().charAt(0);

			if (table.containsKey(character)) {
				System.out.print(table.get(character) + " ");
			} else if (character == '\n') {
				System.out.println();
			}else if (character == ' ') {
				System.out.print("|");
			} else {
				System.out.print(character);
			}
		}
	}

}
