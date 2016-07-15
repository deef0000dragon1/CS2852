/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 2
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CreatePoints {

	/**
	 * 
	 * Accepts a list of dots, original, and copies the dots into a list,
	 * 
	 * result, that starts out empty. The method then uses the technique described
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
	 *            An empty list that will contain the numDesired most critical dots from the original list
	 * 
	 * @param numDesired
	 *            The number of dots desired in the resulting list, must be
	 * 
	 *            at least 2
	 * 
	 */

	public static void getDesiredDots(List<Dot> original, List<Dot> result, int numDesired) {
		//double check for breaking list size.
		if (original.size() <= 2 || numDesired <= 2) {
			JOptionPane.showMessageDialog(null, "Input Points Too Few. Please Increase number of points. ");
			return;
		}
		//clear the result list so that there isnt two images being drawn at once. 
		result.clear();

		
		double lowest = Double.MAX_VALUE;
		int lowestIndex;
		while (numDesired < original.size()) {//loop, removing dots, until thereare few enough
			
			//create a variance array
			ArrayList<Double> variance = new ArrayList<Double>(original.size());
			
			//calculate the variance of the start point
			double n = calculateVariance(original.get(original.size() - 1), original.get(0), original.get(1));
			variance.add(n);

			lowest = n;
			lowestIndex = 0;

			//loop that calculates the variances for all non edge points
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
			// calculate the varnce factor for the 
			n = calculateVariance(original.get(original.size() - 2), original.get(original.size() - 1),
					original.get(0));
			variance.add(n);
			if (n < lowest) {
				lowest = n;
				lowestIndex = original.size() - 1;
			}
			
			//remove the lowest variance points.
			variance.remove(lowestIndex);
			original.remove(lowestIndex);

		}
		for (int i = 0; i < original.size(); i++) {
			//add all points to the result. 
			Dot d = original.get(i);
			result.add(d);
		}

	}

	//reads in the information from a file, line by line. 
	public static List<Dot> ReadFromFile(File file) {
		ArrayList<Dot> points = new ArrayList<Dot>();
		String line;

		try (Scanner scanner = new Scanner(file);) {
			while (scanner.hasNextLine()) {//read loop.
				line = scanner.nextLine();
				points.add(new Dot(Double.parseDouble(line.substring(0, line.lastIndexOf(','))),
						Double.parseDouble(line.substring(line.lastIndexOf(',') + 1))));
			}
		} catch (FileNotFoundException e) {

		}

		return points;

	}
	
	
//calculates the distance through pithagorean theorem one liner. Math.abs() is excessive. 
	private static double calculatePointDistance(Dot pt1, Dot pt2) {
		return Math.pow(((Math.abs(pt1.getX() - pt2.getX()) * Math.abs(pt1.getX() - pt2.getX()))
				+ (Math.abs(pt1.getY() - pt2.getY()) * Math.abs(pt1.getY() - pt2.getY()))), .5);

	}

	//calculates the variance by the distance change from a to b to c Minus the distance from a to c
	private static double calculateVariance(Dot a, Dot b, Dot c) {
		// b is presumed the middle point
		return (calculatePointDistance(a, b) + calculatePointDistance(b, c) - calculatePointDistance(a, b));
	}
}
