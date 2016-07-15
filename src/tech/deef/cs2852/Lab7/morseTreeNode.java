/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 7
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab7;

public class morseTreeNode {

	private final char EXCEPTION_CHARACTER = '*';
	private morseTreeNode left = null;
	private morseTreeNode right = null;
	private char character;
	
	//mothing needs to be set in the constructor. 
	public morseTreeNode() {
		
	}
	
	//prints the node in postorder.
	public void print(){
		if (left!= null){
			left.print();
		}
		if (right!= null){
			right.print();
		}
		System.out.println(character + "");
		
	}
	
	//adds a character to the tree
	public void add(String sequence, char val) {
		
		//System.out.println("Adding tree : "+sequence + ":\t" +  val);
		if(sequence.length() < 1){//if it is an empty string, this is the final node. set char. 
			character = val;
			System.out.println( "Character now in this location : " +  character + " : " + this.toString());
			return;
		}
		//System.out.println("Adding tree : "+sequence + ":\t" +  val);
		if(sequence.charAt(0) == '.'){//if the next char is ., put it in the dot node, creating if necessaary.
			if(right == null){
				right = new morseTreeNode();
			}
			right.add(sequence.substring(1), val);
			return;
		}
		if(sequence.charAt(0) == '-'){//if the next char is -, put it in the dash node, creating if necessaary.
			if(left == null){
				left = new morseTreeNode();
			}
			left.add(sequence.substring(1), val);
			return;
		}
	}
	
	public char decode(String code) {//decodes the character.
		//System.out.println(code);
		if(code.equals("")){//if the code is empty, it is the result. returns the character in the 
			//System.out.println("\n\t" + character);
			return character;
		}else{
			try {
				if(code.charAt(0) == '.'){//goes a level deper if necessary
					return right.decode(code.substring(1));
				}
				if(code.charAt(0) == '-'){
					//System.out.println("");
					return left.decode(code.substring(1));
				}
			} catch (NullPointerException e) {//if the node does not exist, ierrrs, returning an error character. 
				//the tree does not contain a character sequence beyond
				//the given character. 
				//return warning/exception character.
				System.out.println("THERE WAS SOME SORT OR ERROR IDIOT! : " + code);
				//e.printStackTrace();
				return EXCEPTION_CHARACTER;
			}
		}
		return 0;
	}

}
