/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 5
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab5;

public class Packet {

	private char[] data;
	private int sequenceNumber;
	private int packetSize;
	private long finalLocation;

	//basic set data constructor.
	public Packet(char[] data, int sequenceNumber, int packetSize, long finalLocation) {
		this.data = data;
		this.sequenceNumber = sequenceNumber;
		this.packetSize = packetSize;
		this.finalLocation = finalLocation;
	}

	//accessor methods. 
	public char[] getData() {
		return data;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public int getPacketSize() {
		return packetSize;
	}

	public long getFinalLocation() {
		return finalLocation;
	}

}
