package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValid;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

public class ClientBLL {

	private List<Validator<Client>> validators;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValid());
	}

	public Client findClientById(int id) {
		Client st = ClientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return st;
	}
	
	public Client findClientByName(String name) {
		Client st = ClientDAO.findByName(name);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + name + " was not found!");
		}
		return st;
	}

	public int insertClient(Client c) {
		for (Validator<Client> v : validators) {
			v.validate(c);
		}
		return ClientDAO.insert(c);
	}
	
	public void updateClient(Client c) {
		for (Validator<Client> v : validators) {
			v.validate(c);
		}
		ClientDAO.upd(c);
	}
	
	
	public static ArrayList<Client> selectAll(){
		ArrayList<Client> p= ClientDAO.selAll();
		return p;
	}
	
	public boolean delClient(int id){
		Client c = ClientDAO.findById(id);
		
		ClientDAO.delete(c);
		
		return true;
	}
}
