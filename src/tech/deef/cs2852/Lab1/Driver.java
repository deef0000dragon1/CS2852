package tech.deef.cs2852.Lab1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Driver class for benchmarking lab.<br />
 * <br />
 * Students must make modifications in the areas highlighted by TODO comments.
 * 
 * @author hornick
 * @author taylor (minor updates)
 * @version 2014.03.10-1.2
 */
public class Driver {

	/**
	 * Writer for file output.
	 */
	private PrintWriter printStream;

	/**
	 * Runs the benchmarking tests.
	 * 
	 * @param args
	 *            Ignored
	 */
	public static void main(String[] args) {

		try {
			new Driver().start();
		} catch (FileNotFoundException e) {
			System.err.println("The file cannot be opened.\n" + e.getMessage());
		}
		System.out.println("All done!");
	}

	/**
	 * Non-static entry point. Opens an output file, creates a Benchmarker
	 * object, sets up a loop to run a benchmark test repeatedly, prints timing
	 * results to a file, and finally closes the file.
	 */
	private void start() throws FileNotFoundException {

		// TODO create a different file for each type of collection you test.
		String strFile = "SortedArrayList_100K.csv";
		try {
			printStream = new PrintWriter(strFile);

			Benchmarker benchmarker = new Benchmarker();

			List<Integer> list;

			// TODO create the specific type of collection class here
			// (LinkedList,
			// ArrayList, SortedArrayList, etc)
			list = new SortedArrayList<Integer>();

			// We want to take measurements for different numbers of items
			// (N) in the collection, so that we can see how the performance
			// varies with N. We'll vary N from some very small value (1) to
			// some large value (NMAX), incrementing N by some amount (NINC)
			// as we go.
			// TODO pick appropriate values for NMAX and NINC
			int NMAX = 100000;
			int NINC = 100; // ...or some reasonable value

			// Run the tests for increasing values of N (from 1 to NMAX)
			for (int n = 1; n < NMAX; n += NINC) {
				// Each time through this loop, the List is
				// reinitialized and rebuilt, but bigger each time.
				list.clear();

				// Get the time it takes to add N elements to the List
				long nsAddToBack = benchmarker.benchAddToBack(list, n);

				// clear again to prepare for rebuilding from the front
				list.clear();

				// Get the time it takes to add N elements to the List
				
				long nsAddToFront = benchmarker.benchAddToFront(list, n);
				//long nsAddToFront = 0; // to accommodate for the fact that you
										// can not add elements to the front of
										// a SortedArrayList

				// Get the time it takes to determine that a value is not
				// in the list.
				// Note: -1 is never found since all values are positive
				long nsContains = benchmarker.benchContains(list, -1);

				// Get the time it takes to access a random element in list
				long nsAccessRandom = benchmarker.benchAccess(list);

				// Get the time it takes to access approximate middle element
				long nsAccessMiddle = benchmarker.benchAccessIndex(list, n / 2);

				// print the values to a data file; time are in microseconds
				printStream.println(n + ", " + nsAddToBack / 1000.0 + ", " + nsAddToFront / 1000.0 + ", "
						+ nsContains / 1000.0 + ", " + nsAccessRandom / 1000.0 + ", " + nsAccessMiddle / 1000.0);
				System.out.println("Finished test for N=" + n);
			}
		} finally {
			// Be sure to flush all pending output and close the file
			if (printStream != null) {
				printStream.flush();
				printStream.close();
			}
		}
	}
}