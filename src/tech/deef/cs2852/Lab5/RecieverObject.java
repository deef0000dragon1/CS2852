/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 5
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab5;

import java.util.LinkedList;
import java.util.Queue;

public class RecieverObject implements NetworkObject {
	private long networkAddress;
	private Queue<Packet> input;
	private Packet currentPacket;
	private int packetTracker;
	private long senderLocation;
	private NetworkHandlerObject network;
	private Queue<Packet> send;
	private int dataSize;
	private boolean recievingMode;
	private boolean done;

	//sets sender id and creates queues
	public RecieverObject(long senderID) {
		send = new LinkedList<Packet>();
		senderLocation = senderID;
		input = new LinkedList<Packet>();// initalize input
	}

	//set network for reference. 
	public void setNetworkHandlerObject(NetworkHandlerObject network) {
		this.network = network;
	}

	//get the address of the network
	public long getAddress() {
		return networkAddress;
	}

	//tick method
	public void tick() {
		send();
	}

	@Override//send the conformation packets in the send queue to the network
	public void send() {
		Packet p = send.peek();
		if (send.size() != 0 && (network.offer(p))) {
			send.poll();
		}
	}

	@Override//recieves the packets pushed from the network.
	public void recieve(Packet packet) {
		//if the first packet has been recieved or not
		if (recievingMode) {
			if (currentPacket.getSequenceNumber() + 1 == packet.getSequenceNumber()) {
				// packet is exoected packet.
				input.offer(packet);
				System.out.println(packet.getData());
				currentPacket = packet;
				char[] data = { 't' };
				send.offer(new Packet(data, packet.getSequenceNumber(), 1, senderLocation));
				packetTracker++;
			} else if (currentPacket.getSequenceNumber() + 1 < packet.getSequenceNumber()) {
				input.offer(packet);
				char[] datat = { 't' };//unexpected packet
				send.offer(new Packet(datat, packet.getSequenceNumber(), 1, senderLocation));// send
																								// true.

				for (int i = 1; i < currentPacket.getSequenceNumber() - packet.getSequenceNumber() - 1; i++) {
					char[] dataf = { 'f' };
					send.offer(new Packet(dataf, (packet.getSequenceNumber() - i) - 1, 1, senderLocation));
				}
				// send false recival location
				packetTracker++;
				currentPacket = packet;
			} else {
			}
		} else {
			currentPacket = packet;//irst packet recieved. 
			dataSize = packet.getPacketSize();
			// not in recieving mode yet. this is a first packet packet.
			recievingMode = true;
		}
	}

	public void start() {
		// no use
	}
	
	//test and return if done.
	public boolean getDone() {
		done();
		return done;
	}

	//test if done
	private void done() {
		if (input.size() == dataSize) {
			done = true;
		} else {
			done = false;
		}
	}
	
	//prints the data for testing putposes. 
	public String toString(){
		String data = "";
		int i = 0;
		while(!input.isEmpty()){
			if (input.peek().getSequenceNumber() == i){
				String s = String.valueOf(input.poll().getData());
				data = (data +s);
				i++;
				
			}
		}
		return data;
	}

	//sets teh network address of the network item.
	public void setNetworkAddress(long l) {
		networkAddress = l;
		
	}
}
