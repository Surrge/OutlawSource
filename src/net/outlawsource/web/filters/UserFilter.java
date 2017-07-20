package net.outlawsource.web.filters;

import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.outlawsource.business.domain.User;
import net.outlawsource.data.UserDAO;

@Component
public class UserFilter implements Filter {
	
	@Autowired
	UserDAO userDao;
	
	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
		String userId = ((HttpServletRequest) request).getRemoteUser();
		
		if(userId == null) {
			throw new RuntimeException("Not Authenticated");
		}
		else {
			try {
				User user = userDao.getUser(userId);
				if(user == null) {
					throw new RuntimeException("Unknown User");
				}
				
				HttpSession session = ((HttpServletRequest) request).getSession();
				User sessionUser = (User) session.getAttribute("user");
				if(sessionUser == null) {
					session.setAttribute("user", user);	
				}						
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		chain.doFilter(request,response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}