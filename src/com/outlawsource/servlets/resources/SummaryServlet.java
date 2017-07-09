package com.outlawsource.servlets.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import com.outlawsource.DatabaseManager;
import com.outlawsource.domain.ItemSummary;
import com.outlawsource.servlets.BaseServlet;

@WebServlet("/Resources/Summary")
public class SummaryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {		
			super.doGet(request, response);
			
			List<ItemSummary> resources = new ArrayList<ItemSummary>(), products = new ArrayList<ItemSummary>(), loot = new ArrayList<ItemSummary>(), units = new ArrayList<ItemSummary>();
			
			ResultSet itemQuery = DatabaseManager.getConnection().createStatement().executeQuery("SELECT "
					+ "item.name, item.machineName, type.typeName, price.vendorDollars, price.marketDollars, price_archive.marketAverage, price.rawMaterialDollars, price.recycleYieldDollars "
					+ "FROM price "
					+ "JOIN price_archive ON price.itemUID = price_archive.itemUID "
					+ "JOIN item ON price.itemUID = item.itemUID "
					+ "JOIN type ON item.categoryUID = type.typeUID "
					+ "WHERE price.createDate = (SELECT createDate FROM osdb.price ORDER BY createDate DESC LIMIT 1) "
					+ "AND price_archive.archiveDate = (SELECT archiveDate FROM osdb.price_archive ORDER BY archiveDate DESC LIMIT 1) "
					+ "ORDER BY item.name");
			
			while(itemQuery.next()) {
				ItemSummary item = new ItemSummary();
				item.displayName = itemQuery.getString("name");
				item.machineName = itemQuery.getString("machineName");
				
				Integer marketDollars = NumberUtils.createInteger(itemQuery.getString("marketDollars"));
				item.currentMarketPrice = (marketDollars > 0) 
						? marketDollars
						: NumberUtils.createInteger(itemQuery.getString("vendorDollars"));
				
				item.averagePrice = NumberUtils.createInteger(itemQuery.getString("marketAverage"));
				item.rawMaterialPrice = NumberUtils.createInteger(itemQuery.getString("rawMaterialDollars"));
				item.recycleYieldPrice = NumberUtils.createInteger(itemQuery.getString("recycleYieldDollars"));
				
				switch(itemQuery.getString("typeName")) {
				case "Resource":
					resources.add(item);
					break;
				case "Product":
					products.add(item);
					break;
				case "Loot":
					loot.add(item);
					break;
				case "Unit":
					units.add(item);
					break;
				}				
			}
			
			request.setAttribute("resources", resources);
			request.setAttribute("products", products);
			request.setAttribute("loot", loot);
			request.setAttribute("units", units);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/Summary.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/Summary.jsp").forward(request, response);
	}
	
	private String ConvertDateToMySQLFormat(Date input, int dayOffset) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(dayOffset != 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(input);
			c.add(Calendar.DATE, dayOffset);
			input.setTime(c.getTime().getTime());
		}
		
		return "'" + sdf.format(input) + "'";
	}
	
	private String CreateAverageSummaryPrice(double averagePrice, int currentPrice, DecimalFormat df) {
		String summaryStr = "";
		
		if(currentPrice > 0) {
			if(averagePrice > 0) {
				summaryStr = df.format(currentPrice / averagePrice * 100) + "%";
			}
			else {
				summaryStr = "-";
			}			
		}
			
		return summaryStr;
	}
}
