package com.outlawsource.servlets.resources;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.outlawsource.dao.FactoryDAO;
import com.outlawsource.domain.Factory;
import com.outlawsource.domain.User;
import com.outlawsource.servlets.BaseServlet;

@WebServlet("/Resources/Stats/UserFactories")
public class UserFactoriesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");  
    	
		try {
			JSONObject result = new JSONObject();
			
			User user = getUser(request);
			List<Factory> factories = FactoryDAO.getUserFactories(user.getUserId());
			
			// Calculate
			int levelTotal = 0;
			BigInteger hourlyProfitTotal = BigInteger.ZERO;
			BigInteger upgradeCostTotal = BigInteger.ZERO;
			BigInteger upgradePayoffTotal = BigInteger.ZERO;
			for(Factory factory : factories) {
				factory.setHourlyProfit();
				factory.setUpgradeCost();
				factory.setUpgradePayoff();
				
				levelTotal += factory.getUserLevel();
				hourlyProfitTotal = hourlyProfitTotal.add(factory.getHourlyProfit());
				upgradeCostTotal = upgradeCostTotal.add(factory.getUpgradeCost());
				upgradePayoffTotal = upgradePayoffTotal.add(factory.getUpgradePayoff());
			}

			result.put("factories", factories);
			
			JSONObject totals = new JSONObject();
			totals.put("levelTotal", levelTotal);
			totals.put("hourlyProfitTotal", hourlyProfitTotal);
			totals.put("upgradeCostTotal", upgradeCostTotal);
			totals.put("upgradePayoffTotal", upgradePayoffTotal);
			
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
			try {
				Integer level = Integer.parseInt(request.getParameter("level"));
				
				FactoryDAO.updateFactoryLevel(user.getUserId(), item, level);
				Factory updatedFactory = FactoryDAO.getUserFactory(user.getUserId(), item);
				updatedFactory.setHourlyProfit();
				updatedFactory.setUpgradeCost();
				updatedFactory.setUpgradePayoff();
				
				result.put("success", true);
				result.put("message", updatedFactory.getDisplayName() + " successfully upgraded to Level " + level);
				result.put("factory", new JSONObject(updatedFactory));
			}
			catch(NumberFormatException ex) {
				result.put("success", false);
				result.put("message", "Submitted level is unrecognized, expected format is numbers only");
			}
			
			response.getWriter().append(result.toString());
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}