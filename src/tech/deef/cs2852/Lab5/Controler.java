/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 5
 * cs2852 -051
 * 
 * */


package tech.deef.cs2852.Lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controler {
	static NetworkHandlerObject network;
	static SenderObject sender;
	static RecieverObject reciever;

	public static void main(String[] args) {
		try {
			//create the data
			Scanner scanner = new Scanner(new File("story.txt"));
			String data = "";
			while (scanner.hasNextLine()) {
				data = data + scanner.nextLine();
			}

			//build the network items and set addresses
			sender = new SenderObject(data, 1);
			reciever = new RecieverObject(2);
			sender.setNetworkAddress(2);
			reciever.setNetworkAddress(1);

			//add items to list being sent to network
			ArrayList<NetworkObject> networkItems = new ArrayList<NetworkObject>();
			networkItems.add(sender);
			networkItems.add(reciever);

			
			//create network
			network = new NetworkHandlerObject(32, networkItems);

			//set network in network items. 
			sender.setNetworkHandlerObject(network);
			reciever.setNetworkHandlerObject(network);

			//initalize items.
			sender.start();
			network.start();
			reciever.start();

			boolean done = false;
			while (!done) {//tick loop
				sender.tick();
				network.tick();
				reciever.tick();
				
				//is done loop.
				if (sender.getDone() && reciever.getDone() && network.getDone()) {
					done = true;
				}
			}

			//test data. 
			if (data.equals(reciever.toString())) {
				System.out.println("done.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
