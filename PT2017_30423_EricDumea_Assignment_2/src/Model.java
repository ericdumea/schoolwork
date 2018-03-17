
import java.util.*;

public class Model extends Thread {
	private Queues c[];
	private int qnr;
	static int id=0;
	private int client_nr;
	private int min_start_time;
	private int max_start_time;
	private int min_serv_time;
	private int max_serv_time;
	private long max_time;
	private long start_time=System.currentTimeMillis();
	private volatile long curr_time=System.currentTimeMillis();
	private int wtime=0;
	
	public Model(int qnr, Queues c[],int client_nr,int minstart, int maxstart, int minserv, int maxserv){
		setName("Scheduler");
		this.qnr=qnr;
		this.c= new Queues[qnr];
		this.client_nr=client_nr;
		for(int i=0;i<qnr;i++){
			this.c[i]=c[i];
		}
		this.min_start_time=minstart;
		this.max_start_time=maxstart;
		this.min_serv_time=minserv;
		this.max_serv_time=maxserv;
		this.max_time=100000;
	}
	
	private Customer generateRandomClient(){
		long st = min_serv_time + (int)(Math.random() * max_serv_time); 
		st*=250;
		long at = min_start_time + (int)(Math.random() * max_start_time);
 
		Customer c = new Customer(++id,System.currentTimeMillis()+at,st);
		return c;
		
	}
	
	private int min_q(){
		int index=0;
		//System.out.println("");
		try{
			int min=c[0].q_length();
			for(int i=1;i<this.qnr;i++){
				int q_lg=c[i].q_length();
				//System.out.print(q_lg+" ");
				if(q_lg<min){
					min=q_lg;
					index=i;
				}
			}
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		//System.out.println("choice: "+index);
		return index;
	}
	

	public void run(){
		try{
			int i=0;
			while( i<client_nr&&inTime()){
				i++;
				Customer cl=this.generateRandomClient();
				int min_index=min_q();
				System.out.println("Client "+id+" enqueued on Queue "+ (min_index+1)+" ["+cl.getTarr()%1000000+"]");
				GUI.ta.append("Client "+id+" enqueued on Queue "+ (min_index+1)+" ["+cl.getTarr()%1000000+"]\n");
				this.wtime+=cl.getStime();
				c[min_index].add_client(cl);
				sleep(160);
			}
			//System.exit(0);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public int getWtime() {
		return this.wtime/client_nr;
	}

	public boolean inTime(){
		if(curr_time>=start_time+max_time){
			return false;
		}
		//System.out.println("finish_time: "+(start_time+max_time)+"  Cur time: "+curr_time);
		return true;
	}
	
}
