package blms;

public class Util {
    public static String join(String[] array, String delim) {
        String j = "";
        for ( int i=0; i<array.length; i++ ) {
            if (i!=0) j += delim;
            j += array[i];
        }
        return j;
    }

	public static boolean isNullOrEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}
}
