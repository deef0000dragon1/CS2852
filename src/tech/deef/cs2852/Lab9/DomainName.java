package tech.deef.cs2852.Lab9;

public class DomainName {

	private String name;
	
	public DomainName(String domain){
		char[] testingArray = domain.toCharArray();
		boolean peroid = true;
		
		for(char c : testingArray){
			if(((c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || (c== '-') || (c == '.' && !peroid))){
				peroid = false;
				if(c == '.'){
					//if it is a '.' set peroid to false to watch for pervious peroids
					peroid = true;
				}
			}else {
				throw new IllegalArgumentException();
			}
		}
		
		//if no exception was thrown, set the name to Domain
		name = domain.toLowerCase();
	}
	
	public int hashCode(){
		char[] split = name.toLowerCase().toCharArray();
		int code = 0;
		
		for(char c: split){
			code += c;
		}
		
		//return the total strig value moded by the number of different possilble 
		//characters in the string squared
		return code % (28*28);
		
	}
	
	public String toString(){
		return name;
	}
	
	public boolean equals(Object object){
		String testing = object.toString().toLowerCase();
		return testing.equals(name.toLowerCase());
	}
}
