package net.outlawsource.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.outlawsource.business.domain.ItemPrice;
import net.outlawsource.business.domain.ItemProperty;
import net.outlawsource.business.domain.ItemSummary;

@Repository
public class PriceDAO {

	@Autowired
	DatabaseManager dataManager;
	
	public Date getLatestPrice() throws Exception {
		String sql = "SELECT createDate FROM osdb.price ORDER BY createDate DESC LIMIT 1";
		Connection conn;
		
		try {
			conn = dataManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getDate("createDate");
			}
			
			return new Date(); //TODO: null instead
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public ItemProperty getItemPrices(String itemName, boolean getHistorical) throws Exception {
		ItemProperty prop = new ItemProperty(itemName);
		
		String sql = "SELECT price.vendorDollars, price.marketDollars, price.createDate FROM price "
				+ "JOIN item ON price.itemUID = item.itemUID "
				+ "WHERE item.name = '" + itemName + "' "
				+ "ORDER BY price.createDate DESC" 
				+ ((getHistorical) ? "" : " LIMIT 0,300");
		Connection conn;
		try {
			conn = dataManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Date createDate = rs.getDate("createDate");
				prop.addVendorPrice(new ItemPrice(rs.getInt("vendorDollars"), createDate));
				prop.addMarketPrice(new ItemPrice(rs.getInt("marketDollars"), createDate));
			}
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return prop;
	}
	
	public Map<String, List<ItemSummary>> getSummary() throws Exception {
		Map<String, List<ItemSummary>> results = new HashMap<String, List<ItemSummary>>();
		results.put("Resource", new ArrayList<ItemSummary>());
		results.put("Product", new ArrayList<ItemSummary>());
		results.put("Loot", new ArrayList<ItemSummary>());
		results.put("Unit", new ArrayList<ItemSummary>());
		
		String sql = "SELECT "
				+ "item.name, item.machineName, type.typeName, price.vendorDollars, price.marketDollars, price_archive.marketAverage, price.rawMaterialDollars, price.recycleYieldDollars "
				+ "FROM price "
				+ "JOIN price_archive ON price.itemUID = price_archive.itemUID "
				+ "JOIN item ON price.itemUID = item.itemUID "
				+ "JOIN type ON item.categoryUID = type.typeUID "
				+ "WHERE price.createDate = (SELECT createDate FROM osdb.price ORDER BY createDate DESC LIMIT 1) "
				+ "AND price_archive.archiveDate = (SELECT archiveDate FROM osdb.price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "ORDER BY item.name";
		Connection conn;
		
		try {
			conn = dataManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ItemSummary item = new ItemSummary();
				item.displayName = rs.getString("name");
				item.machineName = rs.getString("machineName");
				
				Integer marketDollars = NumberUtils.createInteger(rs.getString("marketDollars"));
				item.currentMarketPrice = (marketDollars > 0) 
						? marketDollars
						: NumberUtils.createInteger(rs.getString("vendorDollars"));
				
				item.averagePrice = NumberUtils.createInteger(rs.getString("marketAverage"));
				item.rawMaterialPrice = NumberUtils.createInteger(rs.getString("rawMaterialDollars"));
				item.recycleYieldPrice = NumberUtils.createInteger(rs.getString("recycleYieldDollars"));
				
				results.get(rs.getString("typeName")).add(item);
			}
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return results;
	}
}
