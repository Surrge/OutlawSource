package net.outlawsource.web.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.outlawsource.data.UserDAO;

@WebServlet("/Register")
public class RegisterServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doPost(request, response);
			messages = new ArrayList<String>();
			
			if(getUser(request) != null) {
				messages.add("You are already logged in, please logout before registering another account.");
			}
			
			String userId = request.getParameter("user_id");
			String password = request.getParameter("pw");
			
			// Validate
			if(userId == null) {
				messages.add("User ID is required");
			}
			else if(userId.length() > 25 || userId.length() < 4) {
				messages.add("User ID must be between 4 and 25 characters");
			}
			if(password == null) {
				messages.add("Password is requried");
			}
			else if(password.length() > 35 || password.length() < 4) {
				messages.add("Password must be between 4 and 35 characters");
			}
			if(UserDAO.getUser(userId) != null) {
				messages.add("User account " + userId + " already exists, please try another name");
			}
			
			// Add User
			if(messages.isEmpty()) {
				try {
					UserDAO.addUser(userId, password, "webuser");
					messages.add("Account Created Succesfully");
				}
				catch(SQLException ex) {
					messages.add("An error occured while creating your account, username or password format my be unsupported");
				}
			}
				
			setCachedMessages(request, messages);
			response.sendRedirect(request.getContextPath() + "/Stats");		
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}