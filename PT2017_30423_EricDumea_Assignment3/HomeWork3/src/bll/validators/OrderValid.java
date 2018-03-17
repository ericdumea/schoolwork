package bll.validators;

import bll.ProductBLL;
import model.Order;
import model.Product;

public class OrderValid implements Validator<Order> {

	@Override
	public void validate(Order t) {
		// TODO Auto-generated method stub
		Product p = ProductBLL.findProductByName(t.getPname());
		if(t.getAm()>p.getAmount()){
			throw new IllegalArgumentException("Too many products, not enough in stock.");
		}
	}
	
}
