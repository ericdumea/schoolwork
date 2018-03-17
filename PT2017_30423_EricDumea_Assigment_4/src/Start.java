import java.io.FileOutputStream;
import java.io.*;

public class Start {
	
	public static void ss(Bank b){
		try {
	         FileOutputStream fileOut =new FileOutputStream("D:/PTemployee.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(b);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/bank.ser");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}
	
	public static void ds(Bank b){
		try {
	         FileInputStream fileIn = new FileInputStream("D:/PTemployee.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         b = (Bank) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bank b = new Bank();
		
		try {
	         FileInputStream fileIn = new FileInputStream("D:/PTemployee.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         b = (Bank) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
		
		b.showTable();
		/*
		
		b.addPerson(1961001347322L, "Strut Fodo", "Bisericii, 4");
		b.addPerson(19610013312L, "Staro Fodo", "Bisericiidssd, 4");
		b.addPerson(19612311347322L, "Strutul Fodo", "Bisericii, 4");
		
		b.addPerson(19610013217322L, "Strutache Fodo", "Bisericii, 4");
		
		Person p = new Person( "Strutache Fodo",19610013217322L, "Bisericii, 4");
		
		b.showTable();
		
		b.removePerson(19612311347322L, "Strutul Fodo", "Bisericii, 4");
		
		System.out.println("\n\n\n\n After del:");
		
		b.showTable();
		
		System.out.println("\n\n\n\n After adding acc:");
		
		b.addAcc("Saving", 150000, p);
		b.addAcc("Saving", 2000000, p);
		b.addAcc("Spending", 15000, p);
		b.addAcc("Spending", 15, p);
		
		b.showTable();
		
		//b.wMoney(901, p, 100);
		//b.wMoney(903,p, 100);
		b.dMoney(904, p, 1000);*/
		
		b.showTable();
		//ss(b);
		

		GUI g = new GUI(b);

		
	}

}
