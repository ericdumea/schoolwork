package model;

public class Order {
	private String cname;
	private String pname;
	private int id;
	private int am;
	
	public Order(int id, String cname, String pname, int am){
		this.id=id;
		this.cname=cname;
		this.pname=pname;
		this.am=am;
	}
	
	public Order(String cname, String pname, int am){
		this.cname=cname;
		this.pname=pname;
		this.am=am;
	}

	

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAm() {
		return am;
	}

	public void setAm(int am) {
		this.am = am;
	}
	
	
	
}
