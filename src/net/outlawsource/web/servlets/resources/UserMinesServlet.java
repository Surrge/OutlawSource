package net.outlawsource.web.servlets.resources;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.outlawsource.business.domain.Mine;
import net.outlawsource.business.domain.User;
import net.outlawsource.data.MineDAO;
import net.outlawsource.web.servlets.BaseServlet;

@WebServlet("/Resources/Stats/UserMines")
public class UserMinesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");  
    	
		try {
			JSONObject result = new JSONObject();
			
			User user = getUser(request);
			List<Mine> mines = MineDAO.getUserMines(user.getUserId());
			
			// Calculate
			int totalQuantity = 0;
			int totalHourlyYield = 0;
			BigInteger totalHourlyProfit = BigInteger.ZERO;
			BigInteger totalBuildCost = BigInteger.ZERO;
			BigInteger totalBuildPayoff = BigInteger.ZERO;
			for(Mine mine : mines) {
				totalQuantity += mine.getUserQuantity();
				totalHourlyYield += mine.getUserHourlyYield();
			}
			for(Mine mine : mines) {
				mine.Calculate(totalQuantity);
				totalHourlyProfit = totalHourlyProfit.add(mine.getHourlyProfit());
				totalBuildCost = totalBuildCost.add(mine.getBuildCost());
				totalBuildPayoff = totalBuildPayoff.add(mine.getBuildPayoff());
			}

			result.put("mines", mines);
			
			JSONObject totals = new JSONObject();
			totals.put("totalQuantity", totalQuantity);
			totals.put("totalHourlyYield", totalHourlyYield);
			totals.put("totalHourlyProfit", totalHourlyProfit);
			totals.put("totalBuildCost", totalBuildCost);
			totals.put("totalBuildPayoff", totalBuildPayoff);
			
			result.put("totals", totals);
			
			response.getWriter().append(result.toString());
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");  
    	
		try {
			JSONObject result = new JSONObject();
			
			User user = getUser(request);
			String item = request.getParameter("item");
			String quantity = request.getParameter("quantity");
			String hourlyYield = request.getParameter("hourlyYield");
			try {
				if(quantity != null) {
					Integer quantityNum = Integer.parseInt(quantity);					
					MineDAO.updateUserMine(user.getUserId(), item, quantityNum, null);
					
					result.put("success", true);
				}
				else if(hourlyYield != null) {
					Integer hourlyYieldNum = Integer.parseInt(hourlyYield);					
					MineDAO.updateUserMine(user.getUserId(), item, null, hourlyYieldNum);
					
					result.put("success", true);
				}
				else {
					result.put("success", false);
					result.put("message", "Missing required parameter");
				}
			}
			catch(NumberFormatException ex) {
				result.put("success", false);
				result.put("message", "Submitted value is unrecognized, expected format is numbers only");
			}
			
			response.getWriter().append(result.toString());
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}