package com.outlawsource.servlets.resources;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.outlawsource.DatabaseManager;
import com.outlawsource.servlets.BaseServlet;

@WebServlet("/Resources/GetItems")
public class ItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public ItemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json"); 
		
		try {
			String sql = "SELECT DISTINCT name FROM item ORDER BY name";
			java.sql.Statement sqlStatement = DatabaseManager.getConnection().createStatement();
			ResultSet sqlResult = sqlStatement.executeQuery(sql);
			
			List<String> names = new ArrayList<String>();
			while(sqlResult.next()) {
				names.add(sqlResult.getString("name"));
			}
			
			response.getWriter().append(new JSONArray(names).toString());			
		} catch (SQLException e) {
			response.getWriter().append("SQLException: " + e.getMessage());
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json"); 
		
		try {			
			String sql = "SELECT DISTINCT name FROM item ORDER BY name";
			java.sql.Statement sqlStatement = DatabaseManager.getConnection().createStatement();
			ResultSet sqlResult = sqlStatement.executeQuery(sql);
			
			List<String> names = new ArrayList<String>();
			while(sqlResult.next()) {
				names.add(sqlResult.getString("name"));
			}
			
			response.getWriter().append(new JSONArray(names).toString());			
		} catch (SQLException e) {
			response.getWriter().append("SQLException: " + e.getMessage());
		}				
	}
}