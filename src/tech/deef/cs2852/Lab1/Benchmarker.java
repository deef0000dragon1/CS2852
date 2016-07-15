package tech.deef.cs2852.Lab1;

import java.util.List;
import java.util.Random;

/** 
 * This class implements various methods that benchmark the execution time of
 * specific List-implementing collection class operations, specifically, add(),
 * contains(), and random access via get(). Any user-supplied collection class
 * can be benchmarked with these methods so long as the class implements the
 * List interface.<br />
 * <br />
 * The collection class must be a container of Integers.
 * 
 * @author hornick
 * @author taylor (minor updates)
 * @version 2010-02-26-1.1
 */
public class Benchmarker {

	/**
	 * Pseudo-random number generator
	 */
	private static final Random randomizer = new Random();

	/**
	 * Determines the number of nanoseconds required to insert n random valued
	 * integers into the list; each element is added to the end of the list
	 * 
	 * @param list
	 *            The container to which to add the random valued integers
	 * @param n
	 *            The number of random valued integers to add to the container
	 * @return The number of nanoseconds required to perform the additions
	 */
	public long benchAddToBack(List<Integer> list, int n) {
		long delta = 0;
		for (int i = 0; i < n; i++) {
			int value = randomizer.nextInt(Integer.MAX_VALUE);
			long startTime = System.nanoTime();
			list.add(value);
			long stopTime = System.nanoTime();
			delta += (stopTime - startTime);
		}
		return delta;
	}

	/**
	 * Determines the number of nanoseconds required to insert n random valued
	 * integers into the list. Each element is inserted at the front of the
	 * list.
	 * 
	 * @param list
	 *            The container to which to add the random valued integers
	 * @param n
	 *            The number of random valued integers to add to the container
	 * @return The number of nanoseconds required to perform the additions
	 */
	public long benchAddToFront(List<Integer> list, int n) {
		long delta = 0;
		for (int i = 0; i < n; i++) {
			int value = randomizer.nextInt(Integer.MAX_VALUE);
			long startTime = System.nanoTime();
			list.add(0, value);
			long stopTime = System.nanoTime();
			delta += (stopTime - startTime);
		}
		return delta;
	}

	/**
	 * Determines the number of nanoseconds required determine if target integer
	 * is contained in list. The target is not required to exist within the
	 * list. In this case, the returned time indicates how long it took to
	 * perform the futile search.
	 * 
	 * @param list
	 *            The container that is to be searched
	 * @param target
	 *            The integer to be found
	 * @return The number of nanoseconds required to perform the search (whether
	 *         successful or not).
	 */
	public long benchContains(List<Integer> list, int target) {
		long startTime = System.nanoTime();
		list.contains(target);
		long stopTime = System.nanoTime();
		long delta = (stopTime - startTime);
		return delta;
	}

	/**
	 * Determines the number of nanoseconds required to access a random index
	 * within the list. Nothing is done with the object within.
	 * 
	 * @param list
	 *            The container that is to be accessed
	 * @return The number of nanoseconds required access the element
	 */
	public long benchAccess(List<Integer> list) {
		int index = randomizer.nextInt(Math.max(1, list.size() - 1));
		long startTime = System.nanoTime();
		// just do the get; don't bother assigning it to anything
		list.get(index);
		long stopTime = System.nanoTime();
		long delta = (stopTime - startTime);
		return delta;
	}

	/**
	 * Determines the number of nanoseconds required to access an element at
	 * index i within the list. The value of the passed-in index is
	 * automatically adjusted if out of bounds.
	 * 
	 * @param list
	 *            The container that is to be accessed
	 * @param index
	 *            The index of the element to be accessed
	 * @return The number of nanoseconds required access the element
	 */
	public long benchAccessIndex(List<Integer> list, int index) {
		// Correct index if it is out of bounds
		if (index > list.size() - 1) {
			index = list.size() - 1;
		} else if (index < 0) {
			index = 0;
		}

		long startTime = System.nanoTime();
		// just do the get; don't bother assigning it to anything
		list.get(index);
		long stopTime = System.nanoTime();
		long delta = (stopTime - startTime);
		return delta;
	}
}