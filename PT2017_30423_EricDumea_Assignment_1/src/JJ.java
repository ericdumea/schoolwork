import static org.junit.Assert.*;

import org.junit.Test;

public class JJ {

	@Test
	public void test1() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		String[] p2= { "3x^0", "3x^2+2x^0" };
		String[] op= {"3x^2+2x^1+3x^0","12x^4-6x^3+3x^2+2x^1+9x^0"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			Polynome s2= new Polynome(p2[i]);
			Polynome s3= Operation.add(s1, s2);
			assertEquals(s3.printPoly(),op[i]);
		}
		
	}
	
	@Test
	public void test2() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		String[] p2= { "3x^0", "3x^2+2x^0" };
		String[] op= {"3x^2+2x^1-3x^0","12x^4-6x^3-3x^2+2x^1+5x^0"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			Polynome s2= new Polynome(p2[i]);
			Polynome s3= Operation.sub(s1, s2);
			assertEquals(s3.printPoly(),op[i]);
		}
		
	}
	
	@Test
	public void test3() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		String[] p2= { "3x^0", "3x^2+2x^0" };
		String[] op= {"9x^2+6x^1","36x^6-18x^5+24x^4-6x^3+21x^2+4x^1+14x^0"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			Polynome s2= new Polynome(p2[i]);
			Polynome s3= Operation.multiply(s1, s2);
			assertEquals(s3.printPoly(),op[i]);
		}
		
	}
	
	@Test
	public void test4() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		//String[] p2= { "3x^0", "3x^2+2x^0" };
		String[] op= {"6x^1+2x^0","48x^3-18x^2+2x^0"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			Polynome s3= Operation.derive(s1);
			assertEquals(s3.printPoly(),op[i]);
		}
		
	}
	
	@Test
	public void test5() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		//String[] p2= { "3x^0", "3x^2+2x^0" };
		String[] op= {"1x^3+1x^2","12/5x^5-3/2x^4+1x^2+7x^1"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			String s3= Operation.integrate(s1);
			assertEquals(s3,op[i]);
		}
		
	}
	
	@Test
	public void test6() {
		//fail("Not yet implemented");
		String[] p1= { "3x^2+2x^1", "12x^4-6x^3+2x^1+7x^0"};
		String[] p2= { "3x^1", "3x^2+2x^0" };
		String[] op= {"Quotient:1x^1  Remainder:2x^1","Quotient:4x^2-2x^1-2x^0  Remainder:-2x^2+6x^1+11x^0"};
		for(int i=0;i<2;i++){
			Polynome s1= new Polynome(p1[i]);
			Polynome s2= new Polynome(p2[i]);
			String s3= Operation.divide(s1,s2);
			assertEquals(s3,op[i]);
		}
		
	}
}
