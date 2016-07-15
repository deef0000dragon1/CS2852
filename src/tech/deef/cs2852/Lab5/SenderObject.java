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

public class SenderObject implements NetworkObject{
	private String data;
	private long networkAddress;
	private long finalLocation;
	private Queue<Packet> dataQueue;
	private int counterLocation;
	private NetworkHandlerObject network;
	private Queue<Packet> dataSent;
	private boolean done;

	//creates the sender, initalizes the queues and sets the location and data. 
	public SenderObject(String sendingObject, long finalLocation) {
		this.data = sendingObject;
		this.finalLocation = finalLocation;
		dataQueue = new LinkedList<Packet>();
		dataSent = new LinkedList<Packet>();
	}

	//sets the "IP" address
	public void setNetworkAddress(long address) {
		this.networkAddress = address;
	}

	//sets the network for referenceing
	public void setNetworkHandlerObject(NetworkHandlerObject network) {
		this.network = network;
	}

	//calculates the number of packets and creates and offers the starter packet. 
	public void start() {
		int packetSize = (data.length() + 1) / 1500;
		Packet startingPacket = new Packet(null, -1, packetSize+1, finalLocation);
		network.offer(startingPacket);
		//KNOWN BUG: If the starter packet is lost, there is no way for the program to timeout and resend. 
	}

	//tick mehthod
	public void tick() {
		//if the datacoundet is less than the data, it needs to add another data item to be split. 
		if (counterLocation < data.length()) {
			char[] tempData = null;

			if (data.length() - counterLocation > 1500) { //create full packet
				tempData = data.substring(counterLocation, counterLocation + 1500).toCharArray();
			} else {
				tempData = data.substring(counterLocation).toCharArray();//create small packet
			}
			Packet tempPacket = new Packet(tempData, (int) ((counterLocation) / 1500), tempData.length, finalLocation);
			counterLocation += 1500;
			dataQueue.offer(tempPacket);//send packet to waiting queue
		}
		send();//send 
		isDone();//check if the program is done
		//if it can be added to the network, add it, and set it aside for the confirm. 
		
	}
	
	//designed to recieve the conformation packets from the network. 
	public void recieve(Packet packet){
		//packets are the same in the series. 
		if(packet.getSequenceNumber() == dataSent.peek().getSequenceNumber()){
			if(packet.getData()[0] == 't'){
				//packet sent successfully.
				dataSent.poll();
			}else{
				//something happened. presume that the data fialed to send. 
				dataQueue.offer(dataSent.poll());
			}
			
		}else{
			//something happened. Timeout. resend packet.
			dataQueue.offer(dataSent.poll());
			
		}
	}

	@Override//returns the netowrk address
	public long getAddress() {
		return networkAddress;
	}

	@Override//sends the data in waiting to the network, and adds it to the wait for response if recieved. 
	public void send() {

		if (dataQueue.size()!= 0 &&(network.offer(dataQueue.peek()))) {
			dataSent.offer(dataQueue.poll());
		}
	}
	
	private void isDone(){//checks if both sending and waiting on conformation are empty, and if the data is all gone. 
		//check sending arraylists to be empty
		if(dataQueue.isEmpty() && dataSent.isEmpty()){
			//check to see if the system is done encoding. 
			if(counterLocation > data.length()) {
				
				done = true;
				return;
			}
		}
		done = false;
		return;
	}
	
	//returns if done or not. 
	public boolean getDone(){
		return done;
	}
}
