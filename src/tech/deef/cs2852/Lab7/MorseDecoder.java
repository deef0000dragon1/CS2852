/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 7
 * cs2852 -051
 * 
 * */


package tech.deef.cs2852.Lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MorseDecoder {

	private static File codeIndexFile;
	private static File codedFile;
	private static File outputFile;
	private static MorseTree decodeTree;
	
	//selects the specific files, creates the tree, and decodes the message.
	public static void main(String[] args) {
		initateLibrary();
		codedFile = fileFinder("Please Select Coded File");
		outputFile = fileFinder("Please Select Output Message Location");
		//outputFile = new File("Output.txt");
		//codedFile = new File("MorseCode.txt");
		
		decodeTree.print();
		
		decodeMessage();
		// initate library
		// get message
		// decode message
		// print message
	}

	
	//takes the coded file and morse file and decodes the file. 
	private static void decodeMessage() {
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(codedFile);
		} catch (FileNotFoundException e) {
			//file not found or null.
			e.printStackTrace();
		}
		
		LinkedList<String> code = new LinkedList<String>();
		
		while (scanner.hasNextLine()) {
			Scanner s = new Scanner(scanner.nextLine());
			while (s.hasNext()) {
				String line = s.next();
				//System.out.print(line + ":");
				String character = decodeTree.decode(line);
				code.add(character);
				//System.out.println( "\t\t" + character);
			} 
			code.add("\n");
		}
		for(String c: code ){
			System.out.print(c);
		}
		
		
	}

	//loads the library of morse code characters. 
	private static void initateLibrary() {
		
		codeIndexFile = fileFinder("Please Load Sequence Library");
		//codeIndexFile = new File("characters.txt");
		decodeTree = new MorseTree();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(codeIndexFile);
		} catch (FileNotFoundException e) {
			//file not found or null.
			e.printStackTrace();
		}
		
		String line = "";
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			//System.out.println("This is a character being added to the library : "+line.substring(1) + "\t" +  line.charAt(0));
			decodeTree.add(line.substring(1), line.charAt(0));
		}
		
	}

	
	//file finder class that takes a string to put in the title and returns a selected file. 
	private static File fileFinder(String titleText) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", ".txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(titleText);
		int returnVal = chooser.showOpenDialog(null); 
		// gets the file using a file chooser
		// lists must be initalized once for addition of points to work.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return new File(chooser.getSelectedFile().getPath());
		} else {
			return null;
		}
	}
}
