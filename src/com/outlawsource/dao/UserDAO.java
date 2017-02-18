package com.outlawsource.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.outlawsource.DatabaseManager;
import com.outlawsource.domain.User;

public class UserDAO {
	public static void addUser(String userId, String password, String role) throws SQLException {
		String userSql = "INSERT INTO user VALUES(?, ?)";
		String roleSql = "INSERT INTO user_role VALUES(?, ?)";
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			PreparedStatement userStmt = connection.prepareStatement(userSql);
			userStmt.setString(1, userId);
			userStmt.setString(2, password);
			userStmt.executeUpdate(userSql);
			
			PreparedStatement roleStmt = connection.prepareStatement(roleSql);
			roleStmt.setString(1, userId);
			roleStmt.setString(2, role);
			roleStmt.executeUpdate(roleSql);
			
			connection.close();
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	public static User getUser(String userId) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM user "
				+ "JOIN user_role ON user.user_id = user_role.user_id "
				+ "WHERE user.user_id='"+userId+"'";
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				user = populateUser(rs);
			}
			
			connection.close();
			return user;
		}
		catch(SQLException ex) {
			throw ex;
		}
	}
	
	private static User populateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getString("user_id"));
		user.setRole(rs.getString("role"));
		return user;
	}
}