import java.util.*;

public class Polynome {
	
	private int degree;
	private List<Monome> m= new ArrayList<Monome>();
	
	Polynome(){
		this.degree=0;
	}
	
	Polynome(String str){
	
		str=str.replaceAll("-", "+-");
		String delim="[+]";
		String[] rez= new String[50];
		rez=str.split(delim);
		if(str!=""&&str.charAt(0)=='+'){
			for(int i=1;i<rez.length;i++){
				Monome aux;
				if(rez[i]=="")
					continue;
				else{
					aux=new Monome(rez[i]);
					m.add(aux);
				}
			}
		}
		else
		for(int i=0;i<rez.length;i++){
			Monome aux;
			if(rez[i]=="")
				continue;
			else{
				aux=new Monome(rez[i]);
				m.add(aux);
			}
		}
		
		
		m.sort((a,b)->b.getExp() - a.getExp());
		if(m.isEmpty()==false)
			this.degree=m.get(0).getExp();
	}
	
	
	
	public String printPoly(){
		String str="";
		Monome aux=null;
		m.sort((a,b)->b.getExp() - a.getExp());
		
		for(Monome x: m){
			if(x.getCoef()==0)
				aux=x;
		}
		
		if(aux!=null)	
			this.m.remove(aux);
		
		for(Monome x: m){
			
			if(x.getCoef()<0){
				str=str+x.getCoef()+"x^"+x.getExp();
			}
			else
				if(str==""){
					str=x.getCoef()+"x^"+x.getExp();
				}
				else{
					str=str+"+"+x.getCoef()+"x^"+x.getExp();
				}	
		}
		return str;
	}
	
	public void addMonome(Monome m1){
		boolean ok=false;
		if(this.m==null){
			this.m.add(m1);
		}
		for(Monome x:this.m){
			if(x.getExp()==m1.getExp()){
				ok=true;
				x.setCoef(x.getCoef()+m1.getCoef());
			}
		}
		if(!ok){
			this.m.add(m1);
		}
		this.computeDeg();
	}
	
	public void addM(Monome m1){
		this.m.add(m1);
		
	}
	
	public void computeDeg(){
		this.m.sort((a,b)->b.getExp() - a.getExp());
		if(this.m.size()>0)
			this.degree=m.get(0).getExp();
		else
			this.degree=0;
	}
	
	public void subMonome(Monome m1){
		boolean ok=false;
		
		
		for(Monome x:this.m){
			if(x.getExp()==m1.getExp()){
				ok=true;
				x.setCoef(x.getCoef()-m1.getCoef());
			}
		}
		if(!ok){
			m1.setCoef(-(m1.getCoef()));
			this.m.add(m1);
		}
		
		Monome[] aux= new Monome[m.size()];
		for(int i=0;i<m.size();i++){
			aux[i]=null;
		}
		
		for(Monome x:this.m){
			if(x.getCoef()==0){
				aux[m.indexOf(x)]=x;
			}
		}
		for(int i=0;i<m.size();i++){
			if(aux[i]!=null){
				this.m.remove(aux[i]);
			}
		}
		this.computeDeg();
	}
	
	public void mulMonome(Monome m1){		
		for(Monome x:this.m){
			x.setCoef(m1.getCoef()*x.getCoef());
			x.setExp(x.getExp()+m1.getExp());
		}
		this.computeDeg();
	}
	
	public ArrayList<Monome> getMonoms(){
		return (ArrayList<Monome>) this.m;
	}



	public int getDegree() {
		return degree;
	}

	public void remMonome(int e){
		Monome aux=null;
		for(Monome x:this.m){
			if(x.getExp()==e){
				aux=x;
			}
		}
		if(aux!=null)
			this.m.remove(aux);
		this.computeDeg();
	}
	
	public int maxExp(){
		this.computeDeg();
		int maxdeg=0;
		for(Monome x:this.m){
			if(x.getExp()>maxdeg && x.getCoef()!=0)
				maxdeg=x.getExp();
		}
		return maxdeg;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
}