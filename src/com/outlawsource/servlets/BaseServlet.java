package com.outlawsource.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.outlawsource.DatabaseManager;
import com.outlawsource.domain.User;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected List<String> messages;
	
	public void init(ServletConfig config) throws ServletException {
		try {
		    super.init(config);
			messages = new ArrayList<String>();
			DatabaseManager.initConnection(config.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		messages = getCachedMessages(request); 
		request.setAttribute("cachedMessages", messages);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	messages = getCachedMessages(request); 
    	request.setAttribute("cachedMessages", messages);
    }

	protected static User getUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	protected static void setCachedMessages(HttpServletRequest request, List<String> messages) {
		request.getSession().setAttribute("cachedMessages", messages);
	}
	
	@SuppressWarnings("unchecked")
	protected static List<String> getCachedMessages(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> messages = (List<String>) session.getAttribute("cachedMessages");
		session.setAttribute("cachedMessages", new ArrayList<String>());	//Clear
		
		return messages != null
				? messages
				: new ArrayList<String>();
	}
}