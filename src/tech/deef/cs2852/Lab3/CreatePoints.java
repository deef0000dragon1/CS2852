/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 2
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



public class CreatePoints {

	/**
	 * 
	 * Accepts a list of dots, original, and copies the dots into a list,
	 * 
	 * result, that starts out empty. The method then uses the technique
	 * described
	 * 
	 * in the lab assignment to remove all but the numDesired number of dots.
	 * 
	 * <br />
	 * 
	 * If fewer than numDesired dots are found in original, then a copy of all
	 * 
	 * the dots in original is returned.
	 * 
	 * @param original
	 *            The list of dots read in from the data file
	 * 
	 * @param result
	 *            An empty list that will contain the numDesired most critical
	 * 
	 *            dots from the original list
	 * 
	 * @param numDesired
	 *            The number of dots desired in the resulting list, must be
	 * 
	 *            at least 2
	 * 
	 */

	public static long getDesiredDots(List<Dot> original, List<Dot> result, int numDesired) {
		long time = System.nanoTime(); // get current time
		//handle exceptional circumstaces
		if (original == null || result == null || original.size() <= 2 || numDesired <= 2) {
			throw new IllegalArgumentException();
		}
		result.clear();

		double lowest = Double.MAX_VALUE;
		int lowestIndex;
		while (numDesired < original.size()) {
			//System.out.println(original.size()); //conformation that not infinate loop code.
			ArrayList<Double> variance = new ArrayList<Double>(original.size());
			
			double n = calculateVariance(original.get(original.size() - 1), original.get(0), original.get(1));
			variance.add(n);

			lowest = n;
			lowestIndex = 0;

			for (int i = 1; i < original.size() - 1; i++) {
				// runs from 1 to size minus 1 because edges are special cases.
				Dot a = original.get(i - 1);
				Dot b = original.get(i);
				Dot c = original.get(i + 1);
				n = calculateVariance(a, b, c);
				variance.add(n);

				if (n < lowest) {
					lowest = n;
					lowestIndex = i;
				}
			}
			// calculate the varnce factor for the edge points.
			n = calculateVariance(original.get(original.size() - 2), original.get(original.size() - 1),
					original.get(0));
			variance.add(n);
			if (n < lowest) {
				lowest = n;
				lowestIndex = original.size() - 1;
			}

			variance.remove(lowestIndex);
			original.remove(lowestIndex);

		}
		for (int i = 0; i < original.size(); i++) {
			Dot d = original.get(i);
			result.add(d);
		}
		return System.nanoTime() - time;

	}

	
	
	public static long getDesiredDots2(Collection<Dot> original, Collection<Dot> result, int numDesired) {
		long time = System.nanoTime(); // get current time
		//handle exceptional circumstaces
		if (original == null || result == null || original.size() <= 2 || numDesired <= 2) {
			throw new IllegalArgumentException();
		}
		result.clear();

		double lowest = Double.MAX_VALUE;
		int lowestIndex;
		while (numDesired < original.size()) {
			
			ArrayList<Double> variance = new ArrayList<Double>(original.size());
			
			
			Iterator<Dot> ittr1 = original.iterator();
			Dot dotA = ittr1.next();
			Dot dotB = ittr1.next();
			Dot dotC = null;
			Dot start = dotA;
			Dot end =  null;
			Dot lowDot = null;

			//get end, zero, and one and test. 
			double lowVal = calculateVariance(end, dotA, dotB);
			double temp = 0;
			lowDot = dotA;
			
			while(ittr1.hasNext()) {
				// runs from 1 to size minus 1 because edges are special cases.
				dotC = dotB;
				dotB = dotA;
				dotA = ittr1.next();
				
				temp = calculateVariance(dotA, dotB, dotC);

				if (temp < lowVal) {
					lowVal = temp;
					lowDot = dotB;
				}
			}
			end = dotA;
			// calculate the varnce factor for the edge points.
			temp = calculateVariance(start, end, dotB);
			if (temp < lowVal) {
				lowVal = temp;
				lowDot = dotB;
			}

			original.remove(lowDot);

		}
		Iterator<Dot> ittrEnd = original.iterator();
		result.clear();
		while(ittrEnd.hasNext()){
			result.add(ittrEnd.next());
		}
		
		return System.nanoTime() - time;

	}
	
	
	
	private static Dot getEndDot(Collection<Dot> original) {

		Dot end = null;
		Iterator<Dot> endIttr = original.iterator();
		while(endIttr.hasNext()){
			end = endIttr.next();
		}
		return end;
	}



	public static List<Dot> ReadFromFile(File file) {
		ArrayList<Dot> points = new ArrayList<Dot>();
		String line;

		try (Scanner scanner = new Scanner(file);) {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				points.add(new Dot(Double.parseDouble(line.substring(0, line.lastIndexOf(','))),
						Double.parseDouble(line.substring(line.lastIndexOf(',') + 1))));
			}
		} catch (FileNotFoundException e) {

		}

		return points;

	}

	private static double calculatePointDistance(Dot pt1, Dot pt2) {
		return Math.pow(((Math.abs(pt1.getX() - pt2.getX()) * Math.abs(pt1.getX() - pt2.getX()))
				+ (Math.abs(pt1.getY() - pt2.getY()) * Math.abs(pt1.getY() - pt2.getY()))), .5);

	}

	private static double calculateVariance(Dot a, Dot b, Dot c) {
		// b is presumed the middle point
		return (calculatePointDistance(a, b) + calculatePointDistance(b, c) - calculatePointDistance(a, b));
	}
}
