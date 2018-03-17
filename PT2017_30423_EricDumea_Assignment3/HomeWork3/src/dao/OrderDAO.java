package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionFactory;
import model.Order;

public class OrderDAO {
	private final static String findStatementString = "SELECT * FROM order where name = ?";
	private final static String insertStatementString = "INSERT INTO shop.order (pname,cname,amount) VALUES (?,?,?)";
	private final static String delStatementString = "DELETE FROM order WHERE id = ?";

	public static Order findByName(String name) {
		Order p = null;
		PreparedStatement st = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		
		try {
			st = dbConnection.prepareStatement(findStatementString);
			st.setString(1, name);
			rs = st.executeQuery();
			rs.next();

			int id=rs.getInt("id");
			
			String pname=rs.getString("pname");
			String cname=rs.getString("cname");
			int am=rs.getInt("amount");
			p = new Order(id, pname, cname,am);
			
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

	public static int insert(Order p) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, p.getPname());
			insertStatement.setString(2, p.getCname());
			insertStatement.setInt(3, p.getAm());
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
	
	public static void delete(Order p){
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
