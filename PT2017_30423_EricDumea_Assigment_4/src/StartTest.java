import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class StartTest {

	@Test
	public void test() {
		Bank b = new Bank();
		Person p = new Person("Nicu Coman",1935276874, "Strada bauturiii 5");
		b.addPerson(p.getCnp(), p.getName(), p.getAddr());
		b.addAcc("Spending", 100, p);
		HashMap<Person, ArrayList<Account>> a =b.getA();
		assertEquals(a.containsKey(p), true);
	}
	
	@Test
	public void test2() {
		Bank b = new Bank();
		Person p = new Person("Nicolae Coman",19365276874L, "Strada bauturiii 5");
		b.addPerson(p.getCnp(), p.getName(), p.getAddr());
		b.addAcc("Spending", 100, p);
		HashMap<Person, ArrayList<Account>> a =b.getA();
		assertEquals(a.containsKey(p), true);
	}

}
