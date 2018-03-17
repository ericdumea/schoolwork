package start;

import presentation.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ProductBLL;
import model.Product;

import bll.ClientBLL;
import model.Client;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class Start {
	//protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		/*Client c = new Client("Ion Ionescu SEFU","Ionestilor 3","yoyo_yonutz@yahoo.com");
		Product p = new Product("produs1", 100,15.3);
		Product p1 = new Product("produs1111", 100,15.3);
		
		ClientBLL clientBll= new ClientBLL();
		int cid=-3, pid=-2;
		cid=clientBll.insertClient(c);
		if(cid>0){
			clientBll.findClientById(cid);
		}
		
		try{
			clientBll.findClientById(1);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		ProductBLL pbll = new ProductBLL();
		pid=pbll.insertProduct(p);
		if(pid>0){
			pbll.findProductByName(p.getName());
		}
		
		try{
			pbll.findProductByName("produs1");
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		try{
			pbll.delProduct("produs1");
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		try{
			clientBll.delClient(3);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		*/
		GUI g = new GUI();
		
		//obtain field-value pairs for object through reflection
		//ReflectionExample.retrieveProperties(student);
	}
	
	

}
