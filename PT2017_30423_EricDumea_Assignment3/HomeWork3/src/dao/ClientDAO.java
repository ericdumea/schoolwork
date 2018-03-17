package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

public class ClientDAO {

	private final static String findStatementString = "SELECT * FROM client where id = ?";
	private final static String findStatementStringName = "SELECT * FROM client where name = ?";
	private final static String findStatementStringAll = "SELECT * FROM client";
	private final static String updateStatementString = "UPDATE shop.client SET name = ?, address = ?, email = ? WHERE id = ?";
	private final static String insertStatementString = "INSERT INTO client (name,address,email) VALUES (?,?,?)";
	private final static String delStatementString = "DELETE FROM client WHERE id = ?";

	public static Client findById(int clientId) {
		Client c = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			st = dbConnection.prepareStatement(findStatementString);
			st.setLong(1, clientId);
			rs = st.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			String email = rs.getString("email");
			c = new Client(clientId, name, address, email);
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING,"StudentDAO:findById " +
			// e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(st);
			ConnectionFactory.close(dbConnection);
		}
		return c;
	}

	public static Client findByName(String clientid) {
		Client c = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			st = dbConnection.prepareStatement(findStatementStringName);
			st.setString(1, clientid);
			rs = st.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			String address = rs.getString("address");
			String email = rs.getString("email");
			c = new Client(id, clientid, address, email);
		} catch (SQLException e) {
			e.printStackTrace();
			// LOGGER.log(Level.WARNING,"StudentDAO:findById " +
			// e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(st);
			ConnectionFactory.close(dbConnection);
		}
		return c;
	}
	
	public static ArrayList<Client> selAll() {
		ArrayList<Client> toReturn = new ArrayList<Client>();
		Client p = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			
			
			st = dbConnection.prepareStatement(findStatementStringAll);
			rs = st.executeQuery();
			
			while(rs.next()){

			int id=rs.getInt("id");
			String name= rs.getString("name");
			String email = rs.getString("email");
			String address = rs.getString("address");
			p = new Client(id, name, address, email);
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
	
	
	public static int insert(Client c) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, c.getName());
			insertStatement.setString(2, c.getAddress());
			insertStatement.setString(3, c.getEmail());
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
	
	public static void upd(Client c){
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.setString(1, c.getName());
			updateStatement.setString(2, c.getAddress());
			updateStatement.setString(3, c.getEmail());
			updateStatement.setInt(4, c.getId());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void delete(Client c){
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement delStatement = null;
		int id=c.getId();
		
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