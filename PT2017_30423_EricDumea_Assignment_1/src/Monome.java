
public class Monome {
	
	private int exp;
	private int c;
	
	Monome(){
		this.exp=0;
		this.c=0;
	}
	
	Monome(String str){
		
		String delim="[x^]";
		String[] res=new String[13];
		int aux;
		int i=0;
		res=str.split(delim);
		aux=Integer.parseInt(res[i]);
		this.c=aux;
		aux=Integer.parseInt(res[i+2]);
		this.exp=aux;
	}
	
	public void setExp(int x){
		this.exp=x;
	}
	
	public int getExp(){
		return this.exp;
	}
	
	public void setCoef(int x){
		this.c=x;
	}
	
	public int getCoef(){
		return this.c;
	}
	
	
	
}
