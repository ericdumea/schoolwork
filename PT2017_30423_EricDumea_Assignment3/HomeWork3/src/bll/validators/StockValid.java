package bll.validators;

import model.Product;

public class StockValid implements Validator<Product> {

	@Override
	public void validate(Product t) {
		// TODO Auto-generated method stub
		if(t.getAmount()<0)
			throw new IllegalArgumentException("Stock cannot be negative!");
	}

}
