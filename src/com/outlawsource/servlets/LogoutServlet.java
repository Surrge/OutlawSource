package com.outlawsource.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public LogoutServlet() {
    	super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if(session != null) {
    		session.setAttribute("user", null);
			session.invalidate();
    	}
    	   
    	messages.add("You have been logged out successfully");
    	request.setAttribute("messages", messages);
    	request.getRequestDispatcher("/Hub").forward(request,response);
    }
}