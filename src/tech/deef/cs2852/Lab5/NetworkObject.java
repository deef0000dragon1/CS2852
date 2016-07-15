/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 5
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab5;

//interface used to make sure that network items can be in the same list.
public interface NetworkObject {

	public long address = 0;
	public long getAddress();
	
	public void send();
	public void recieve(Packet p);
}
