package model;
/**
 * 
 * @author dumea
 * Client Class
 */
public class Client {
	private int id;
	private String name;
	private String address;
	private String email;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param email
	 */
	public Client(int id, String name, String address, String email){
		super();
		this.id=id;
		this.name=name;
		this.address=address;
		this.email=email;
	}
	/**
	 * 
	 * @param name
	 * @param address
	 * @param email
	 */
	public Client( String name, String address, String email){
		super();
		this.name=name;
		this.address=address;
		this.email=email;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 
	 * @return id
	 */
	

	public int getId() {
		return id;
	}
/**
 * 
 * @param id
 */
	
	public void setId(int id) {
		this.id = id;
	}
/**
 * 
 * @return name
 */
	public String getName() {
		return name;
	}
/**
 * 
 * @param name
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * 
 * @return address
 */
	public String getAddress() {
		return address;
	}
/**
 * 
 * @param address
 */
	public void setAddress(String address) {
		this.address = address;
	}
	
}
