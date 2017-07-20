package net.outlawsource.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class MessageFilter implements Filter {
	protected static Map<String, List<String>> userMessages;
	
	public MessageFilter() {
		userMessages = new HashMap<String, List<String>>();
	}
	
	private static List<String> getUserMessages(String userId) {
		if(userMessages.containsKey(userId)) {
			return userMessages.get(userId);
		}
		
		return new ArrayList<String>();
	}
	
	public static void addUserMessage(String userId, String... messages) {
		if(userMessages.containsKey(userId)) {
			List<String> existingMessages = getUserMessages(userId);
			existingMessages.addAll(Arrays.asList(messages));
			userMessages.replace(userId, existingMessages);
		}
		else {
			userMessages.put(userId, Arrays.asList(messages));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String userId = ((HttpServletRequest) request).getRemoteUser();
		
		if(userId != null) {
			List<String> messages = getUserMessages(userId);
			request.setAttribute("cachedMessages", messages);
			userMessages.remove(userId);
		}
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
