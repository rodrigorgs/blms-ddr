package blms.sandbox;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeSet;

import org.cheffo.jeplite.JEP;

class Inteiro implements Comparable {
	int x;
	public Inteiro(int i) {
		x = i;
	}
	public void set(int i) {
		x = i;
	}
	@Override
	public int compareTo(Object o) {
		int y = ((Inteiro)o).x;
		return x - y;
	}
	
	public String toString() {
		return "" + x;
	}
}


public class Sandbox {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		treeSet();
		toArray();
		date();
		expression();
		expressionWithUndefinedVariable();
		expressionInvalid();
		expressionDivideByZero();
	}
	
	private static void expressionDivideByZero() {
		JEP jep = new JEP();
		jep.parseExpression("1/0");
		try {
			System.out.println(jep.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void expressionInvalid() {
		JEP jep = new JEP();
		jep.parseExpression("1+");
		try {
			System.out.println(jep.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void expressionWithUndefinedVariable() {
		JEP jep = new JEP();
		jep.addVariable("x", 2);
		jep.parseExpression("x+y");
		try {
			System.out.println(jep.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void expression() throws Exception {
		JEP jep = new JEP();
		jep.addVariable("x", 2);
		jep.parseExpression("x+x");
		System.out.println(jep.getValue());
	}

	private static void date() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = format.parse("31-02-2008");
	}

	private static void toArray() {
		LinkedList<String> list = new LinkedList<String>();
		String x = "abc";
		String y = "def";
		list.add(x);
		list.add(y);
		String[] strs = list.toArray(new String[] {});
		Arrays.sort(strs);
		assert strs[0] == x;
	}

	private static void treeSet() {
		TreeSet<Inteiro> set = new TreeSet<Inteiro>();
		Inteiro i1 = new Inteiro(1);
		Inteiro i2 = new Inteiro(2);
		set.add(i2);
		set.add(i1);
		System.out.println(Arrays.toString(set.toArray()));
		i1.set(3);
		System.out.println(Arrays.toString(set.toArray()));
	}

}
