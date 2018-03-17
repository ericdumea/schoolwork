public class Operation {

	public static Polynome add(Polynome a, Polynome b){
		Polynome r=new Polynome(b.printPoly());
		Polynome a1=new Polynome(a.printPoly());
		for(Monome x: a1.getMonoms() ){
			r.addMonome(x);		
		}
		return r;
	}
	
	public static Polynome sub(Polynome a, Polynome b){
		Polynome r=new Polynome(a.printPoly());
		Polynome s=new Polynome(b.printPoly());
		for(Monome x: s.getMonoms() ){
			r.subMonome(x);
		}
		return r;
	}
	
	public static Polynome multiply(Polynome a, Polynome b){
		Polynome x= new Polynome(a.printPoly());
		Polynome y= new Polynome(b.printPoly());
		Polynome r=new Polynome("0x^0");		
		for(Monome m1:x.getMonoms()){
			for(Monome m2:y.getMonoms()){
				Monome aux=new Monome();
				aux.setCoef(m1.getCoef()*m2.getCoef());
				aux.setExp(m1.getExp()+m2.getExp());
				r.addMonome(aux);
			}
		}
		return r;
	}
	
	
	public static Polynome derive(Polynome x){
		Polynome rez= new Polynome(x.printPoly());
		Monome aux = null;
		boolean ok=false;
		for(Monome m:rez.getMonoms()){
			if(m.getExp()==0){
				ok=true;
				aux=m;
			} 
			else{
				m.setCoef(m.getCoef()*m.getExp());
				m.setExp(m.getExp()-1);
			}
		}
		if(ok==true){
			rez.getMonoms().remove(aux);
		}
		return rez;
	}	
	
	public static int cmmdc(int a, int b){
		if (b==0) return a;
		   return cmmdc(b,a%b);
	}
	
	public static String integrate(Polynome a){
		Polynome x= new Polynome(a.printPoly());
		String str="";
		int[] y= new int[100];
		
		for(Monome m1:x.getMonoms()){
			m1.setExp(m1.getExp()+1);
			y[x.getMonoms().indexOf(m1)]=m1.getExp();
			int c=cmmdc(y[x.getMonoms().indexOf(m1)],m1.getCoef());
			if(c!=1){
				y[x.getMonoms().indexOf(m1)]=y[x.getMonoms().indexOf(m1)]/c;
				m1.setCoef(m1.getCoef()/c);
			}
			
		}
		for(Monome m1:x.getMonoms()){
			if(y[x.getMonoms().indexOf(m1)]<0){
				y[x.getMonoms().indexOf(m1)]=0-y[x.getMonoms().indexOf(m1)];
				m1.setCoef(-(m1.getCoef()));
			}
				if(m1.getCoef()<0){
					if(y[x.getMonoms().indexOf(m1)]==1){
						str=str+m1.getCoef()+"x^"+m1.getExp();
					}
					else{
						str=str+m1.getCoef()+"/"+y[x.getMonoms().indexOf(m1)]+"x^"+m1.getExp();
					}
					
				}
				else
					if(str==""){
						if(y[x.getMonoms().indexOf(m1)]==1){
							str=m1.getCoef()+"x^"+m1.getExp();
						}
						else{
						str=m1.getCoef()+"/"+y[x.getMonoms().indexOf(m1)]+"x^"+m1.getExp();
						}
					}
					else{
						if(y[x.getMonoms().indexOf(m1)]==1){
							str=str+"+"+m1.getCoef()+"x^"+m1.getExp();
						}
						else{
							str=str+"+"+m1.getCoef()+"/"+y[x.getMonoms().indexOf(m1)]+"x^"+m1.getExp();
						}
						
					}	
			}
		return str;
	}
	
	public static String divide(Polynome a, Polynome b){
		String str=null;
		Polynome x, y, c, r, td;
		c=new Polynome();
		r=new Polynome();
		
		if(a.getDegree()>=b.getDegree()){
			x=new Polynome(a.printPoly());
			y=new Polynome(b.printPoly());
		}
		else{
			x= new Polynome(b.printPoly());
			y= new Polynome(a.printPoly());
		}	
		//while(y.maxExp()!=0){
			c=new Polynome();
			r=new Polynome(y.printPoly());
			while(x.maxExp()!=0 && x.maxExp()>=y.maxExp()){
				r= new Polynome(y.printPoly());
				Monome t=new Monome();
				t.setCoef((x.getMonoms().get(0).getCoef())/(y.getMonoms().get(0).getCoef()));
				t.setExp((x.getMonoms().get(0).getExp())-(y.getMonoms().get(0).getExp()));
				c.addMonome(t);
				r.mulMonome(t);
				x=Operation.sub(x, r);
				if(t.getExp()==0&&t.getCoef()==0){
					break;
				}
			}
		//}
		str="Quotient:"+c.printPoly()+ "  Remainder:"+ x.printPoly();
		return str;
	}
	
}

