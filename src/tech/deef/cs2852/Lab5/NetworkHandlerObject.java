/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 5
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class NetworkHandlerObject {

	private Queue<Packet> data;
	private Random rand;
	private int sizeLimit;
	private ArrayList<NetworkObject> networkItems;
	private boolean done;

	//network constructor, giving a size limit and the list of other network item
	public NetworkHandlerObject(int sizeLimit, ArrayList<NetworkObject> networkItems) {
		data = new LinkedList<Packet>();

		rand = new Random();
		this.sizeLimit = sizeLimit;
		this.networkItems = networkItems;
	}

	//add the packet to the networ list, removing if it is to large. 
	public boolean offer(Packet packet) {
		int r = rand.nextInt(3) + 1;
		if(rand.nextDouble() <= .001){
			return true;
			//the packet was accepted and lost. 
		}
		if (data.size() + r <= sizeLimit) {
			for (int i = 0; i < r; i++) {
				data.offer(packet);
			}

			return true;
		}
		return false;
	}

	//tick method
	public void tick() {
		if (!data.isEmpty()) {//if there is data to send
			Packet packet = data.poll();//pull the data into a packet var
			// packet has another turn to tick
			if (data.peek() == packet) {
				// packet has another turn to tick
			} else {
				//look for and sent to destination
				long destination = packet.getFinalLocation();
				for (NetworkObject n : networkItems) {
					if (n.getAddress() == destination) {
						n.recieve(packet);
					}
				}
			}
		}
	}

	public void start() {
		//no code necessary. 
	}

	private void done() {
		//is done if there is no more data to send. 
		done = false;
		if(data.size() == 0){
			done = true;
		}
	}

	public boolean getDone() {
		//check f done and return. 
		done();
		return done;
	}
}
