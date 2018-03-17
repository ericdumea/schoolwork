import java.util.Observable;
import java.util.Observer;

public class Person  implements java.io.Serializable, Observer  {
	String name;
	long cnp;
	String addr;
	
	public Person(String name, long cnp, String addr){
		this.name=name;
		this.cnp=cnp;
		this.addr=addr;
		System.out.println();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCnp() {
		return cnp;
	}

	public void setCnp(long cnp) {
		this.cnp = cnp;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addr == null) ? 0 : addr.hashCode());
		result = prime * result + (int) (cnp ^ (cnp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		if (cnp != other.cnp)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		//if(arg0 instanceof Account){
			//Account acc = (Account) arg0;
			System.out.println("Observer observed stuff");
		//}
	}
	
	
}
