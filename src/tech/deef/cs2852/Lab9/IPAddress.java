package tech.deef.cs2852.Lab9;

public class IPAddress {

	private String IP;
	
	public IPAddress(String address){
		String[] disected = address.split("\\.");
		if(disected.length != 4){
			//TODO fix this. 
			throw new IllegalArgumentException();
		}
		try{
			for(String s: disected){
				int i = Integer.parseInt(s);
				if(i < 0 || i > 255){
					throw new IllegalArgumentException();
				}
			}
			
			
		}catch (NumberFormatException e){
			throw new IllegalArgumentException();
		}
		
		IP = address;
		
	}
	
	public String toString(){
		return IP;
	}
	
	public int hashCode(){
		String s = IP;
		String[] split = s.split("\\.");
		
		int i = Integer.parseInt(split[0]);
		i = Integer.parseInt(split[1]);
		i = Integer.parseInt(split[2]);
		i = Integer.parseInt(split[3]);
		return i%512;
	}
	
	public boolean equals(Object object){
		String testing = object.toString();
		return testing.equals(IP);
	}
	
}
