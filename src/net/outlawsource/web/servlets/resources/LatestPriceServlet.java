package net.outlawsource.web.servlets.resources;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.outlawsource.data.DatabaseManager;
import net.outlawsource.web.servlets.BaseServlet;

@WebServlet("/Resources/GetLatestPrice")
public class LatestPriceServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public LatestPriceServlet() {
    	super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("json");
			
			Date lastUpdate = null;
			ResultSet lastUpdateQuery = DatabaseManager.getConnection().createStatement()
					.executeQuery("SELECT createDate FROM osdb.price ORDER BY createDate DESC LIMIT 1");
			if(lastUpdateQuery.next()) {
				lastUpdate = lastUpdateQuery.getTimestamp("createDate");
			}
			else {
				lastUpdate = new Date();
			}
			
			SimpleDateFormat jsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			TimeZone tz = TimeZone.getTimeZone("UTC");
			jsFormat.setTimeZone(tz);
			response.getWriter().append(jsFormat.format(lastUpdate));
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}