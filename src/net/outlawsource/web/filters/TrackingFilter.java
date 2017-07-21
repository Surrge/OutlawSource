package net.outlawsource.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.outlawsource.SpringApp;
import net.outlawsource.business.domain.User;

public class TrackingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		User user = (User) httpRequest.getSession().getAttribute("user");
		
		String remoteAddr = "";
        if (request != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        
        String str = "URL: " + httpRequest.getRequestURI();
		str += " USER: " + (user != null ? user.getUserId() : "NULL");
        str += " IP: " + remoteAddr;
        
        SpringApp.log.info(str);
        
        chain.doFilter(request, response);
	}

}
