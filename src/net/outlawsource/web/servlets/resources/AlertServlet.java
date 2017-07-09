package net.outlawsource.web.servlets.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.outlawsource.web.servlets.BaseServlet;

@WebServlet("/Resources/Alert")
public class AlertServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			super.doGet(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/Alert.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }

}