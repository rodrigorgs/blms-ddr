package blms.sandbox;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeSet;

import org.cheffo.jeplite.JEP;

import blms.BlmsDataStore;
import blms.User;
import blms.exceptions.BlmsException;

import com.db4o.Db4o;
import com.db4o.ext.ExtObjectContainer;

class Inteiro implements Comparable {
	int x;
	public Inteiro(int i) {
		x = i;
	}
	public void set(int i) {
		x = i;
	}

	public int compareTo(Object o) {
		int y = ((Inteiro)o).x;
		return x - y;
	}
	
	public String toString() {
		return "" + x;
	}
}


public class Sandbox {
	final static String dbfilename = "sandbox.db";
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
		db4o();
	}
	
	private static void db4o() throws BlmsException {
		assert (new File(dbfilename).delete());
		Db4o.configure().updateDepth(10);
		ExtObjectContainer db = (ExtObjectContainer)Db4o.openFile(dbfilename);
		BlmsDataStore store = new BlmsDataStore();
		
		User u1 = new User("Rodrigo", "Souza", "555", "444", "333", "eu@x", "bli.jpg");
		User u2 = new User("Henrique", "Souza", "555", "444", "333", "eu@yyy", "bla.jpg");
		store.users.add(u1);
		db.set(store); 
		store.users.add(u2);
		db.set(store);
		assert db.getID(u1) != 0;
		assert db.getID(u2) != 0;
//		System.out.printf("Object %s has id %s\n", u1.toString(), "" + db.getID(u1));
//		System.out.printf("Object %s has id %s\n", u2.toString(), "" + db.getID(u2));
		db.close();
		
		db = (ExtObjectContainer)Db4o.openFile(dbfilename);
		store = (BlmsDataStore)db.get(BlmsDataStore.class).next();
		assert store.users.size() == 2;
		store.users.remove(u1);
		db.set(store);
		db.close();
		
		db = (ExtObjectContainer)Db4o.openFile(dbfilename);
		store = (BlmsDataStore)db.get(BlmsDataStore.class).next();
		assert store.users.size() == 1;
		db.close();
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
