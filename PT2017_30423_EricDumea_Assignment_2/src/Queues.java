import java.util.*;

public class Queues extends Thread {
	private Vector clients= new Vector();
	private int index;
	private int sumtime=0;
	//public static JProgressBar p;
	
	
	public Queues( String name){
		setName(name);
	}
	public Queues( String name, int index){
		setName(name);
		this.index=index;
	}
	
	public synchronized void updatePB(){
		if(!clients.isEmpty()) 
			GUI.updatep(this.index, clients.size());
		else
			GUI.updatep(this.index, 0);
		/*	GUI.pb[this.index].setValue(clients.size());
		GUI.pb[this.index].revalidate();
		GUI.pb[this.index].repaint();
		GUI.p0.repaint();
		GUI.f.revalidate();
		GUI.f.repaint();*/
	}
	
	
	public void run(){
		while(true){
			try {
				rem_client();
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{
				//if(!clients.isEmpty())
				//	sleep((((Customer) clients.elementAt(0)).getStime()));
				//else
					sleep(1000);
			}
			catch(InterruptedException e){
				System.out.println("Interruption");
				System.out.println(e.toString());
			}
		}
	}
	
	public synchronized void add_client(Customer c) throws InterruptedException{
		clients.addElement(c);
		updatePB();
		
		
		notifyAll();
	}
	
	public synchronized void rem_client() throws InterruptedException{
		while(clients.size()==0){
			wait();
		}
		Customer c= (Customer) clients.elementAt(0);
		clients.removeElementAt(0);
		updatePB();
		System.out.println("Client "+c.getId()+" dispatched from "+this.getName()+" ["+System.currentTimeMillis()%1000000+"]");
		GUI.ta.append("Client "+c.getId()+" dispatched from "+this.getName()+" ["+System.currentTimeMillis()%1000000+"]\n");
		
		notifyAll();
	}
	
	public synchronized int q_length() throws InterruptedException{
		notifyAll();
		return clients.size();
	}
	
	public synchronized int getAtime(){
		int sum=0;
		for(int i=0;i<clients.size();i++){
			sum+=((Customer)clients.get(i)).getStime();
		}
		return sum/clients.size();
	}
}
