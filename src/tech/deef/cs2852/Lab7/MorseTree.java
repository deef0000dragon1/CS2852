/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 7
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab7;

public class MorseTree<E> {
	private morseTreeNode root;
	//creates the tree and root node, 
	public MorseTree(){
		root = new morseTreeNode();
	}
	
	//adds a character to the tree.
	public void add(String sequence, char val){
		root.add(sequence, val);
	}
	
	//print test function
	public void print(){
		root.print();
	}
	
	//takes the string of characters and removes all non . and - and \n,
	//puts in a decode function and returning the decode oof the character
	public String decode(String code){
		
		String decodeThis = "";
		
		for(char c: code.toCharArray()){
			
			
			if(c == '|' || c== ' '){
				return " ";	
			}else if(c == '\n'){
				return "\n";
			}else if(c == '.' || c == '-'){
				decodeThis += c;
			} else{
				return c + "";
			}
			
		}
		//System.out.println("This is a character decode string:" + decodeThis);
		return root.decode(decodeThis) + "";
	}
}