package tech.deef.cs2852.Lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class DNS {

	File dnsFile;
	Map<DomainName, IPAddress> DNSMap;
	boolean state;

	public DNS(String filename) {
		dnsFile = new File(filename);
		DNSMap = new HashMap<DomainName, IPAddress>();
		state = false;
	}

	public boolean start() {
		if (state) {
			return true;
		}
		Scanner s = null;
		String line = null;
		String ip = null;
		String nameString = null;
		IPAddress address;
		DomainName domain;

		try {
			s = new Scanner(dnsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (s.hasNextLine()) {
			line = s.nextLine();
			Scanner substring = new Scanner(line);
			if (substring.hasNext()) {
				ip = substring.next();
			} else {
				System.err.println("MisFormat: " + line);
			}

			if (substring.hasNext()) {
				nameString = substring.next().toLowerCase();
			} else {
				System.err.println("MisFormat: " + line);
			}

			try {
				address = new IPAddress(ip);
				domain = new DomainName(nameString);
				DNSMap.put(domain, address);
			} catch (IllegalArgumentException e) {
				System.err.println("MisFormat: " + line);
			}

		}

		state = true;
		return state;
	}

	public boolean stop() {
		if (!state) {
			return true;
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(dnsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		
		
		
		for (Map.Entry<DomainName, IPAddress> entry : DNSMap.entrySet()) {

			writer.write(entry.getValue() + "\t" + entry.getKey() + "\n");
		}

		state = false;
		return true;

	}

	public IPAddress lookup(DomainName key) {
		if (DNSMap.containsKey(key)) {
			return DNSMap.get(key);
		} else {
			return null;
		}
	}

	public IPAddress update(String command) {
		if (!state) {
			return null;
		}

		String ip = null;
		String domain = null;
		String cmd = null;

		Scanner substring = new Scanner(command);
		if (substring.hasNext()) {
			cmd = substring.next();
		} else {
			System.err.println("MisFormat");
			return null;
		}

		if (substring.hasNext()) {
			ip = substring.next();
		} else {
			System.err.println("MisFormat");
			return null;
		}

		if (substring.hasNext()) {
			domain = substring.next().toLowerCase();
		} else {
			System.err.println("MisFormat");
			return null;
		}

		if (cmd.equals("ADD")) {
			try {
				IPAddress address = new IPAddress(ip);
				DomainName domainName = new DomainName(domain);
				return DNSMap.put(domainName, address);
			} catch (IllegalArgumentException e) {
				System.err.println("MisFormat:" + ip + " " + domain);
				return null;
			}

		} else if (cmd.equals("DEL")) {
			DomainName delDomain = new DomainName(domain);
			IPAddress delIP = new IPAddress(ip);

			if (DNSMap.containsKey(delDomain)) {
				if (DNSMap.get(delDomain).equals(delIP)) {
					return DNSMap.remove(delDomain);
				} else {
					throw new InputMismatchException();
				}
			}
		}

		return null;
	}
	//I chose to use hash map because the tree sortig is rather unnecessary for the system. The hash is simple enough 
	//and a hash tree could result in an overly complex, and O(n) search, while hash, with it's current arangement only 
	//needs the hash algorithim to be changed prevent an O(n) search
}
