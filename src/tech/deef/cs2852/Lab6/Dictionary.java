package tech.deef.cs2852.Lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Dictionary {
	private Collection<String> words;

	public Dictionary(Collection<String> words) {
		if (words == null) {
			throw new NullPointerException();
		}
		this.words = words;
		clear();
	}

	public void clear() {
		words.clear();
	}

	public boolean Contains(String string) {
		//System.out.println(string);
		return words.contains(string);
		/*for (String s : words) {
			if (s.equals(string)) {
				return true;
			}
		}
		return false;
		*/
	}

	public long load(File file) {
		Scanner scanner;
		Long time = System.nanoTime(); 
		try {
			scanner = new Scanner(file);

			/*String tempString = "";
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
			}*/
			
			String ss = "";
			while(scanner.hasNextLine()){
				ss = scanner.nextLine();
				//System.out.println(ss);
				words.add(ss);
				
			}
			
			
			
			//System.out.println(tempString.substring(start, tempString.length()-1));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return System.nanoTime() - time;
	}
}
