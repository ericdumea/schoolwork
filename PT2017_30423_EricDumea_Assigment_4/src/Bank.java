import java.util.*;

import javax.swing.JOptionPane;



public class Bank implements BankProc,java.io.Serializable{
	private HashMap<Person, ArrayList<Account>> a = new HashMap<Person, ArrayList<Account>>();
	private static int ID = 901;
	
	public void addPerson(long cnp, String name, String addr ){
		Person p = new Person(name, cnp, addr);
		a.put(p, new ArrayList<Account>());
	}
	
	public void removePerson(long cnp, String name, String addr){
		Person p = new Person(name, cnp, addr);
		a.remove(p);
	}
	
	public void editPerson(Person p){
		
	}
	
	public boolean isWellFormed(){
		if(a==null){
			return false;
		}
		return true;
		
	}
	
	public void addAcc(String type, double sum, Person p){
		assert p!=null : "Customer must not be null";
		assert type!=null : "Type must not be null";
		assert this.isWellFormed() == true : "HashTable empty, insert niggaz";
		assert sum>0 : "Sum must be positive";
		if(type == "Saving"){
			if(sum<100.0){
				System.out.println("Deposit too small to be a saving.");
				JOptionPane.showMessageDialog(GUI.a, "Deposit too small to be a saving");
			}
			else{
				SavingAcc x = new SavingAcc(ID++, sum);
				x.addObserver(p);
				a.get(p).add(x);			}
		}else if(type == "Spending"){
			SpendAcc x = new SpendAcc(ID++, sum);
			x.addObserver(p);
			a.get(p).add(x);	
		}
	
	}
	
	public void removeAcc(int accId, Person p){
		ArrayList<Account> acc = a.get(p);
		for(Account x : acc){
			if(x.getId()==accId){
				acc.remove(x);
				break;
			}
		}
	}
	
	public void wMoney(int accId, Person p, double sum){
		assert sum>0 : "Sum must be positive";
		for(Account x : a.get(p)){
			if(x.getId()==accId){
				if(x instanceof SavingAcc&&sum<x.getSum()*0.8){
					System.out.println("Cannot withdraw from saving account");
					JOptionPane.showMessageDialog(GUI.a, "Cannot withdraw from saving account such a small amount");
				}
				else{
					x.withdraw(sum);
				}
			}
		}
	}
	
	public void dMoney(int accId, Person p, double sum){
		assert sum>0 : "Sum must be positive";
		for(Account x : a.get(p)){
			if(x.getId()==accId){
				if(x instanceof SavingAcc){
					if(x.getSum()==0.0){
						x.deposit(sum);
					}
					else if(x.getSum()*0.5>sum){
						System.out.println("Cannot deposit into saving account");
						JOptionPane.showMessageDialog(GUI.a, "Cannot deposit into saving account such a small amount");
					}
				}
				else{
					x.deposit(sum);
				}
			}
		}
	}
	
	
	public void showTable(){
		for(Map.Entry<Person,ArrayList<Account> > entry : a.entrySet()){
			Person p=entry.getKey();
			if(a.get(p).isEmpty())
				System.out.println(p.name+" "+p.cnp);
			else{
				for(Account x : a.get(p)){
					System.out.println(p.name+" "+p.cnp + "    "+ x.getType() +" " +x.getSum());
				}
			}
		}
	}

	public HashMap<Person, ArrayList<Account>> getA() {
		return a;
	}
	
}
