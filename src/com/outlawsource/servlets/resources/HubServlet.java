package com.outlawsource.servlets.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outlawsource.servlets.BaseServlet;

@WebServlet("/Resources/Hub")
public class HubServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doGet(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/Hub.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doPost(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/Hub.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}