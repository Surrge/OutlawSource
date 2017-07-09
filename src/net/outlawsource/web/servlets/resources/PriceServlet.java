package net.outlawsource.web.servlets.resources;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.outlawsource.business.domain.ItemConversion;
import net.outlawsource.business.domain.ItemPrice;
import net.outlawsource.business.domain.ItemProperty;
import net.outlawsource.data.DatabaseManager;
import net.outlawsource.web.servlets.BaseServlet;

@WebServlet("/Resources/Chart")
public class PriceServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			super.doGet(request, response);
			request.getRequestDispatcher("/WEB-INF/pages/Chart.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");  
          
        String itemname = request.getParameter("itemname");
        boolean getHistorical = Boolean.valueOf(request.getParameter("getHistorical"));
        boolean getResources = Boolean.valueOf(request.getParameter("getResources"));
        boolean getRecycle = Boolean.valueOf(request.getParameter("getRecycle"));
        
        try {
			// Get Item Prices
			String sql = "SELECT price.vendorDollars, price.marketDollars, price.createDate FROM price "
					+ "JOIN item ON price.itemUID = item.itemUID "
					+ "WHERE item.name = '" + itemname + "' "
					+ "ORDER BY price.createDate DESC" 
					+ ((getHistorical) ? "" : " LIMIT 0,300");
			java.sql.Statement sqlStatement = DatabaseManager.getConnection().createStatement();
			ResultSet sqlResult = sqlStatement.executeQuery(sql);
						
			ItemProperty item = new ItemProperty(itemname);			
			while(sqlResult.next()) {
				Integer vDollar = sqlResult.getInt("vendorDollars");
				Integer mDollar = sqlResult.getInt("marketDollars");
				Date createDate = new Date(sqlResult.getTimestamp("createDate").getTime());

				item.addVendorPrice(new ItemPrice(vDollar, createDate));
				item.addMarketPrice(new ItemPrice(mDollar, createDate));
			}
			
			sqlStatement.close();
			sqlResult.close();
			
			// Get Resource Prices
			String resourceCalculation = "";
			if(getResources && item.itemHasResources()) {
				resourceCalculation += "Factory Cost = ";
				List<ItemConversion> conversions = item.getItemResources();
				
				List<ItemProperty> resourceItems = new ArrayList<ItemProperty>();
				for(ItemConversion conversion : conversions) {
					String conversionItem = conversion.getItemName();
					if(conversionItem == "money") {	
						resourceCalculation += conversion.getConversionRatio() + "($$)";
					}
					else {
						resourceCalculation += " + " + conversion.getConversionRatio() + "(" + conversion.getItemName() + ")";
						
						sql = "SELECT price.vendorDollars, price.marketDollars, price.createDate FROM price "
									+ "JOIN item ON price.itemUID = item.itemUID "
									+ "WHERE item.name = '" + conversion.getItemName() + "' "
									+ "ORDER BY price.createDate DESC" 
									+ ((getHistorical) ? "" : " LIMIT 0,300");
						 
						 sqlStatement = DatabaseManager.getConnection().createStatement();
						 sqlResult = sqlStatement.executeQuery(sql);
						 
						 ItemProperty resourceItem = new ItemProperty(conversionItem);
						 while(sqlResult.next()) {
							//Integer vDollar = sqlResult.getInt("vendorDollars");
							Integer mDollar = sqlResult.getInt("marketDollars");
							Date createDate = new Date(sqlResult.getTimestamp("createDate").getTime());
							
							resourceItem.addMarketPrice(new ItemPrice(mDollar, createDate));
						 }
						 
						 resourceItems.add(resourceItem);
						 
						 sqlStatement.close();
						 sqlResult.close();
					}
				}
				
				// Link Resource prices back to Product(item)
				for(int dateIndex = 0; dateIndex < item.getMarketPrices().size(); dateIndex++) {
					float resourceCost = conversions.get(0).getConversionRatio();	// money
					
					for(int itemIndex = 1; itemIndex < conversions.size(); itemIndex++) {
						ItemProperty resourceItem = resourceItems.get(itemIndex - 1);
						float conversionRate = conversions.get(itemIndex).getConversionRatio();
						double resourcePrice = (resourceItem.getMarketPrices().get(dateIndex).getPrice() > 0)
								? resourceItem.getMarketPrices().get(dateIndex).getPrice() * conversionRate
								: resourceItem.getVendorPrices().get(dateIndex).getPrice() * conversionRate;
						
						resourceCost += resourcePrice;
					}
					
					item.addResourcePrice(new ItemPrice(
							Math.round(resourceCost),
							item.getMarketPrices().get(dateIndex).getCreateDate()
					));
				}
			}		
			
			// Get Recycle Yield
			String recycleCalculation = "";
			if(getRecycle && item.itemHasRecycle()) {
				recycleCalculation += "Recycle Yield = ";
				List<ItemConversion> conversions = item.getItemRecycle();
				
				List<ItemProperty> recycleItems = new ArrayList<ItemProperty>();
				for(ItemConversion conversion : conversions) {
					String conversionItem = conversion.getItemName();
					recycleCalculation += " + " + conversion.getConversionRatio() + "(" + conversion.getItemName() + ")";
					
					sql = "SELECT price.vendorDollars, price.marketDollars, price.createDate FROM price "
								+ "JOIN item ON price.itemUID = item.itemUID "
								+ "WHERE item.name = '" + conversion.getItemName() + "' "
								+ "ORDER BY price.createDate DESC" 
								+ ((getHistorical) ? "" : " LIMIT 0,300");
					 
					 sqlStatement = DatabaseManager.getConnection().createStatement();
					 sqlResult = sqlStatement.executeQuery(sql);
					 
					 ItemProperty resourceItem = new ItemProperty(conversionItem);
					 while(sqlResult.next()) {
						//Integer vDollar = sqlResult.getInt("vendorDollars");
						Integer mDollar = sqlResult.getInt("marketDollars");
						Integer vDollar = sqlResult.getInt("vendorDollars");
						Date createDate = new Date(sqlResult.getTimestamp("createDate").getTime());
						
						resourceItem.addMarketPrice(new ItemPrice(mDollar, createDate));
						resourceItem.addVendorPrice(new ItemPrice(vDollar, createDate));
					 }
					 
					 recycleItems.add(resourceItem);
					 
					 sqlStatement.close();
					 sqlResult.close();
				}
				
				// Link Resource prices back to Product(item)
				for(int dateIndex = 0; dateIndex < item.getMarketPrices().size(); dateIndex++) {
					float recycleYield = 0;
					
					for(int itemIndex = 0; itemIndex < conversions.size(); itemIndex++) {
						ItemProperty resourceItem = recycleItems.get(itemIndex);
						float conversionRate = conversions.get(itemIndex).getConversionRatio();
						double recyclePrice = resourceItem.getMarketPrices().get(dateIndex).getPrice() > 0
								? resourceItem.getMarketPrices().get(dateIndex).getPrice()
								: resourceItem.getVendorPrices().get(dateIndex).getPrice();
						
						recycleYield += recyclePrice * conversionRate;
					}
					
					item.addResourcePrice(new ItemPrice(
							Math.round(recycleYield),
							item.getMarketPrices().get(dateIndex).getCreateDate()
					));
				}
			}
			
			// Convert to JSON
			JSONArray sources = new JSONArray();
			
			//mandatory due to shift
			sources.put(resourceCalculation);
			sources.put(recycleCalculation);
			
			JSONObject marketSource = new JSONObject();
			marketSource.put("name", "Market");
			marketSource.put("prices", ConvertPriceArrayToJson(item.getMarketPrices()));
			sources.put(marketSource);
			
			JSONObject vendorSource = new JSONObject();
			vendorSource.put("name", "Vendor");
			vendorSource.put("prices", ConvertPriceArrayToJson(item.getVendorPrices()));
			sources.put(vendorSource);			
			
			if(getResources && item.itemHasResources()) {
				JSONObject resourceSource = new JSONObject();
				resourceSource.put("name", "Factory Cost");
				resourceSource.put("prices", ConvertPriceArrayToJson(item.getResourcePrices()));
				sources.put(resourceSource);
			}
			
			if(getRecycle && item.itemHasRecycle()) {
				JSONObject recycleSource = new JSONObject();
				recycleSource.put("name", "Recycle Yield");
				recycleSource.put("prices", ConvertPriceArrayToJson(item.getResourcePrices()));
				sources.put(recycleSource);
			}
			
			response.getWriter().append(sources.toString());			
		} catch (SQLException e) {
			response.getWriter().append("SQLException: " + e.getMessage());
		} catch (JSONException e) {
			response.getWriter().append("JSONException: " + e.getMessage());
		}
	}
	
	private JSONArray ConvertPriceArrayToJson(ArrayList<ItemPrice> prices) throws JSONException {
		JSONArray result = new JSONArray();
		
		for(ItemPrice price : prices) {
			JSONObject itemPrice = new JSONObject();
			itemPrice.put("price", price.getPrice() == 0 ? JSONObject.NULL : price.getPrice());
			itemPrice.put("createDate", price.getCreateDate());
			result.put(itemPrice);
		}
		
		return result;
	}
}