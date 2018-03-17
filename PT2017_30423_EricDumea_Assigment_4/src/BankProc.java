
public interface BankProc {
	public void addPerson(long cnp, String name, String addr );
	public void removePerson(long cnp, String name, String addr);
	public void addAcc(String type, double sum, Person p);
	public void removeAcc(int accId, Person p);
	public void wMoney(int accId, Person p, double sum);
	public void dMoney(int accId, Person p, double sum);
}
