package net.outlawsource.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.outlawsource.SpringAppInitalizer;
import net.outlawsource.data.UserDAO;
import net.outlawsource.web.filters.MessageFilter;

@Controller
public class RootController {
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping("")
	public String index() {
		SpringAppInitalizer.log.debug("root controller, default route");
		
		return "index";
	}
	
	@RequestMapping("Register")
	public String doRegister(@RequestParam String user_id, @RequestParam String pw) {
		try {
			List<String> messages = new ArrayList<String>();
			
			// Validate
			if(user_id == null) {
				messages.add("User ID is required");
			}
			else if(user_id.length() > 25 || user_id.length() < 4) {
				messages.add("User ID must be between 4 and 25 characters");
			}
			if(pw == null) {
				messages.add("Password is requried");
			}
			else if(pw.length() > 35 || pw.length() < 4) {
				messages.add("Password must be between 4 and 35 characters");
			}
			if(userDao.getUser(user_id) != null) {
				messages.add("User account " + user_id + " already exists, please try another name");
			}
			
			// Add User
			if(messages.isEmpty()) {
				try {
					userDao.addUser(user_id, pw, "webuser");
					messages.add("Account Created Succesfully");
				}
				catch(SQLException ex) {
					messages.add("An error occured while creating your account, username or password format my be unsupported");
				}
			}
			
			// TODO: support anon users
			MessageFilter.addUserMessage(user_id, messages.toArray(new String[messages.size()]));
				
			return "Stats";	
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
