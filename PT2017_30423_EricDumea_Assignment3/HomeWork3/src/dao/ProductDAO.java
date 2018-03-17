package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectionFactory;
import model.Product;

public class ProductDAO {

	private final static String findStatementString = "SELECT * FROM product where name = ?";
	private final static String findStatementStringAll = "SELECT * FROM product";
	private final static String insertStatementString = "INSERT INTO product (name,amount,price)"
			+ " VALUES (?,?,?)";
	private final static String delStatementString = "DELETE FROM product WHERE id = ?";
	private final static String updateStatementString = "UPDATE shop.product SET name = ?, amount = ?, price = ? WHERE id = ?";

	public static Product findByName(String name) {
		Product p = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			st = dbConnection.prepareStatement(findStatementString);
			st.setString(1, name);
			rs = st.executeQuery();
			rs.next();

			int id=rs.getInt("id");
			
			double price=rs.getDouble("price");
			int quantity=rs.getInt("amount");
			p = new Product(id, name, quantity, price);
			
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING,"StudentDAO:findById " +
			// e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(st);
			ConnectionFactory.close(dbConnection);
		}
		return p;
	}
	
	public static ArrayList<Product> selAll() {
		ArrayList<Product> toReturn = new ArrayList<Product>();
		Product p = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			
			
			st = dbConnection.prepareStatement(findStatementStringAll);
			rs = st.executeQuery();
			
			while(rs.next()){

			int id=rs.getInt("id");
			String name= rs.getString("name");
			double price=rs.getDouble("price");
			int quantity=rs.getInt("amount");
			p = new Product(id, name, quantity, price);
			toReturn.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING,"StudentDAO:findById " +
			// e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(st);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	

	public static int insert(Product p) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, p.getName());
			insertStatement.setInt(2, p.getAmount());
			insertStatement.setDouble(3, p.getPrice());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING, "StudentDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	
	public static void update(Product p) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.setString(1, p.getName());
			updateStatement.setInt(2, p.getAmount());
			updateStatement.setDouble(3, p.getPrice());
			updateStatement.setInt(4, p.getId());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 finally {
		ConnectionFactory.close(updateStatement);
		ConnectionFactory.close(dbConnection);
	}
	}
	public static void delete(Product p){
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement delStatement = null;
		int id=p.getId();
		try {
			delStatement = dbConnection.prepareStatement(delStatementString, Statement.RETURN_GENERATED_KEYS);
			delStatement.setInt(1,id);
			
			delStatement.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING, "StudentDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(delStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
}

