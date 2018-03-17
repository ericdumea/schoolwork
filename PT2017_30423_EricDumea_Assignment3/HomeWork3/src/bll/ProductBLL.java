package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.StockValid;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

public class ProductBLL {

	private static List<Validator<Product>> validators;

	public ProductBLL() {
		validators = new ArrayList<Validator<Product>>();
		validators.add(new StockValid());
	}

	public static Product findProductByName(String name) {
		Product st = ProductDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The product with id =" + name + " was not found!");
		}
		return st;
	}

	public static int insertProduct(Product c) {
		for (Validator<Product> v : validators) {
			v.validate(c);
		}
		return ProductDAO.insert(c);
	}
	
	public static ArrayList<Product> selectAll(){
		ArrayList<Product> p= ProductDAO.selAll();
		return p;
	}
	
	public void updateProduct(Product p) {
		for (Validator<Product> v : validators) {
			v.validate(p);
		}
		ProductDAO.update(p);
	}
	
	public static boolean delProduct(String c){
		Product p = ProductBLL.findProductByName(c);
		ProductDAO.delete(p);
		
		return true;
	}
	
}
