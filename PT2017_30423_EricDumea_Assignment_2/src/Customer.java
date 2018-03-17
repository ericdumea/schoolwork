import java.util.*;

public class Customer {
	
	private long id;
	private long tarr;
	private long stime;
	
	Customer(){
		this.id=0;
		this.tarr=0;
		this.stime=0;
	}
	
	Customer(long id, long tarr,long stime){
		this.id=id;
		this.tarr=tarr;
		this.stime=stime;
	}

	public long getStime() {
		return stime;
	}

	public long getId() {
		return id;
	}


	public long getTarr() {
		return tarr;
	}

		
}
