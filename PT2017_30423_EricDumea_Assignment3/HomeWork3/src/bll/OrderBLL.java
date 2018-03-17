package bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.OrderValid;
import bll.validators.StockValid;
import bll.validators.Validator;
import connection.ConnectionFactory;
import dao.OrderDAO;
import model.Order;
import model.Client;
import model.Product;

public class OrderBLL {
	private List<Validator<Order>> validators;

	public OrderBLL() {
		validators = new ArrayList<Validator<Order>>();
		validators.add(new OrderValid());
	}

	public Order findOrderByName(String name) {
		Order st = OrderDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The order " + name + " was not found!");
		}
		return st;
	}

	public int insertOrder(Order c) {
		for (Validator<Order> v : validators) {
			v.validate(c);
		}
		return OrderDAO.insert(c);
	}
	
	public boolean delOrder(String c){
		Order p = this.findOrderByName(c);
		OrderDAO.delete(p);
		
		return true;
	}
}
