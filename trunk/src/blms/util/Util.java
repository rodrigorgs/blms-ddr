package blms.util;

/**
 * Helper methods. 
 */
public class Util {
	/**
	 * Returns a string created by concatenating each element in array, 
	 * separated by delim.
	 * @param array the array.
	 * @param delim the delimiter that will be inserted between array 
	 * elements.
	 * @return a string consisting of elements of array separated by the
	 * given delimiter.
	 */
	public static String join(String[] array, String delim) {
		String j = "";
		for (int i = 0; i < array.length; i++) {
			if (i != 0)
				j += delim;
			j += array[i];
		}
		return j;
	}

	/**
	 * Checks if a String is whitespace, empty ("") or null.
	 * @param s the string to check, may be null.
	 * @return <code>true</code> if the String is null, empty or whitespace. 
	 */
	public static boolean isBlank(String s) {
		return (s == null || s.trim().length() == 0);
	}
}
