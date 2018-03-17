
public class SpendAcc extends Account implements java.io.Serializable{

	private final static String TYPE = "Spending";
	
	public SpendAcc(int id, double sum) {
		super(id, sum, TYPE);
		
	}
	@Override
	public void deposit(double sum2) {
		this.setSum(this.getSum()+sum2);
		setChanged();
		notifyObservers();
	}

	@Override
	public void withdraw(double sum2) {
		if(sum2<this.getSum()){
			this.setSum(this.getSum()-sum2);
			setChanged();
			notifyObservers();
		}
		else{
			System.out.println("Not enough money in the account");
			
		}
		
	}

}
