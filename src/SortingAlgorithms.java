import java.util.Arrays;
import java.util.Random;

/***********************************************************************************
 * ICS3U ACES: Implement the Selection, Insertion, MergeSort, and Quicksort
 * Algorithms
 ***********************************************************************************/
public class SortingAlgorithms {

	// define the maximum array capacity
	private static final int LIMIT = 100000;
	// define the integer container for the array
	private static int[] data, sorted, scratch;

	public static void main(String[] args) {
		//The top of the console print out...
		System.out.println("ALGORITHM\t0\t20000\t40000\t60000\t80000\t100000");
		long start, stop;
		//This for loop is used to create, sort, and display the times...
		for (int sortNum = 0; sortNum < 4; sortNum++) {
			System.out.print(sortName(sortNum));
			for (int i = 0; i <= LIMIT; i += 20000) {
				// populate the array with random integers
				data = createRandomIntArray(i, i);
				scratch = new int[LIMIT];
				//Start and stop are used for timing...
				start = System.currentTimeMillis();
				//Sorts the array
				sorted = sort(data, sortNum);
				stop = System.currentTimeMillis();
				//Printing the times
				System.out.printf("\t%5.3f", (stop - start) / 1000.0);
			}

		}

	}

	/**
	 * The sort method takes an integer array and sorts it.
	 * 
	 * @param array
	 *            The array to be sorted
	 * @param i
	 *            The integer to determine which sort is used
	 * @return Returns the sorted array
	 */
	public static int[] sort(int[] array, int i) {
		if (i == 0)
			return selectionSort(array);
		if (i == 1)
			return insertionSort(array);
		if (i == 2)
			return mSort(array);
		if (i == 3)
			return qSort(array);
		return null;
	}

	/*
	 * The sortName array is used to create the console window correctly
	 * (Basically used to clean up the main method)...
	 */
	private static String sortName(int i) {
		String name = "";
		if (i == 0)
			name = "Selection:";
		if (i == 1)
			name = "\nInsertion:";
		if (i == 2)
			name = "\nMerge Sort:";
		if (i == 3)
			name = "\nQuick Sort:";
		return name;
	}

	/**
	 * Selection Sort The Selection Sort takes an array and sorts it using the
	 * Selection sort algorithm.
	 */
	public static int[] selectionSort(int[] array) {
		int[] sorted = new int[array.length];
		System.arraycopy(array, 0, sorted, 0, array.length);
		int temp, indexOfMax;

		for (int pass = 0; pass < sorted.length - 1; pass++) {
			indexOfMax = 0;
			for (int comp = 1; comp < sorted.length - pass; comp++) {
				if (sorted[comp] > sorted[indexOfMax])
					indexOfMax = comp;
			}
			// swap 'em
			temp = sorted[indexOfMax];
			sorted[indexOfMax] = sorted[array.length - pass - 1];
			sorted[array.length - pass - 1] = temp;
		}
		return sorted;
	}

	/**
	 * Insertion Sort The Insertion Sort method sorts an integer array using the
	 * Insertion Sort algorithm
	 */
	public static int[] insertionSort(int[] array) {
		int[] sorted = new int[array.length];
		System.arraycopy(array, 0, sorted, 0, array.length);
		int value, comp;
		for (int pass = 1; pass < sorted.length; pass++) {
			value = sorted[pass];
			comp = pass - 1;
			// look backwards to find correct position...
			// move adjacent values out of the way...
			while (comp >= 0 && value < sorted[comp]) {
				sorted[comp + 1] = sorted[comp];
				comp--;
			}
			// drop it in...
			sorted[comp + 1] = value;
		}
		return sorted;
	}

	/**
	 * The mSort method is used to make the Merge Sort Method non-destructive
	 * 
	 * @param array
	 *            The array to be sorted
	 * @return Returns the sorted array...
	 */

	public static int[] mSort(int[] array) {
		int[] sorted = new int[array.length];
		System.arraycopy(array, 0, sorted, 0, array.length);
		mergeSort(sorted, 0, sorted.length - 1);
		return sorted;
	}

	/**
	 * THe mergeSort method sorts an integer array using the Merge Sort
	 * algorithm
	 * 
	 * @param sorted
	 *            The array to be sorted
	 * @param lo
	 *            The starting point on the low end of the array
	 * @param hi
	 *            The starting point on the higher end of the array
	 */
	public static void mergeSort(int[] sorted, int lo, int hi) {
		int mid;
		if (lo < hi) {
			mid = (lo + hi) / 2;
			mergeSort(sorted, lo, mid);
			mergeSort(sorted, mid + 1, hi);
			int L = lo;
			int H = mid + 1;
			for (int k = lo; k <= hi; k++) {
				if (L <= mid && (H > hi || sorted[L] < sorted[H])) {
					scratch[k] = sorted[L];
					L++;
				} else {
					scratch[k] = sorted[H];
					H++;
				}
			}
			System.arraycopy(scratch, lo, sorted, lo, hi - lo + 1);
		}
	}

	/**
	 * Quick Sort The qSort is used to make the quick sort method
	 * non-destructive...
	 * 
	 * @param array
	 *            The array to be sorted
	 * @return Returns the sorted array...
	 */
	public static int[] qSort(int[] array) {
		int[] sorted = new int[array.length];
		System.arraycopy(array, 0, sorted, 0, array.length);
		quickSort(sorted, 0, sorted.length - 1);
		return sorted;
	}

	/**
	 * The quickSort method sorts an integer array using the quick sort
	 * algorithm
	 * 
	 * @param A
	 *            The array to be sorted
	 * @param p
	 *            The pivot to be used
	 * @param r
	 */
	public static void quickSort(int[] A, int p, int r) {
		if (p < r) {
			int q = partition(A, p, r);
			quickSort(A, p, q);
			quickSort(A, q + 1, r);
		}

	}

	/**
	 * The partition method is used in the quickSort method. It takes a
	 * partition(A smaller part) of the array and sorts it. Each time through
	 * the partition increases.
	 * 
	 * @param A
	 * 		The array to be partitioned
	 * @param p
	 * 		the pivot to be used
	 * @param r
	 * @return
	 * 		Returns the sorted array...
	 */
	public static int partition(int[] A, int p, int r) {
		int piv = A[p];
		int i = p - 1;
		int j = r + 1;
		while (true) {
			// move right marker to the left...
			do {
				--j;
			} while (A[j] > piv);
			// advance left marker to the right
			do {
				++i;
			} while (A[i] < piv);
			if (i < j) {
				// The one line swap method
				A[i] += A[j] - (A[j] = A[i]);
			} else
				return j;

		}

	}

	/**
	 * Creates an array with the length specified containing random ints over
	 * the interval [0..n).
	 * 
	 * @param length
	 *            the length of the required array
	 * @param n
	 *            the maximum value (exclusive) of the random ints
	 * @return the array containing the random ints
	 */
	public static int[] createRandomIntArray(int length, int n) {
		Random random = new Random();
		int[] array = new int[length];
		for (int i = 0; i < length; i++)
			array[i] = random.nextInt(n);

		return array;
	}

	/**
	 * This method is not needed in the project but is used to display the
	 * contents of the array given
	 * 
	 * @param array
	 *            The array to be displayed...
	 */
	private static void display(int[] array) {
		for (int i : array) {
			System.out.println(i);
		}
	}

}
