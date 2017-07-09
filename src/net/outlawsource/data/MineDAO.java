package net.outlawsource.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.outlawsource.business.domain.Item;
import net.outlawsource.business.domain.Mine;

public class MineDAO {
	public static Mine getUserMine(String userId, String resourceName) throws Exception {
		Mine mine = null;
		String sql = "SELECT ref_minestats.name AS mineName,"
				+ "	item.name AS resourceName, "
				+ " item.machineName, "
				+ " resourceItem.marketAverage, "
				+ " ref_minestats.maxProduction, "
				+ " ref_minestats.baseCost, "
				+ " ref_minestats.costFactor, "
				+ " ref_minestats.requiredLevel, "
				+ " user_mines.* "
				+ "FROM osdb.ref_minestats "
				+ "LEFT JOIN user_mines ON ref_minestats.resourceUID = user_mines.mine_resourceUID AND user_mines.user_id = '"+userId+"' "
				+ "JOIN item ON ref_minestats.resourceUID = item.itemUID AND item.machineName = '" + resourceName + "' "
				+ "JOIN price_archive resourceItem ON ref_minestats.resourceUID = resourceItem.itemUID AND resourceItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "ORDER BY user_mines.mine_quantity DESC";
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				mine = populateMine(rs);
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return mine;
	}
	
	public static List<Mine> getUserMines(String userId) throws Exception {
		List<Mine> mines = new ArrayList<Mine>();
		String sql = "SELECT ref_minestats.name AS mineName,"
				+ "	item.name AS resourceName, "
				+ " item.machineName, "
				+ " resourceItem.marketAverage, "
				+ " ref_minestats.maxProduction, "
				+ " ref_minestats.baseCost, "
				+ " ref_minestats.costFactor, "
				+ " ref_minestats.requiredLevel, "
				+ " user_mines.* "
				+ "FROM osdb.ref_minestats "
				+ "LEFT JOIN user_mines ON ref_minestats.resourceUID = user_mines.mine_resourceUID AND user_mines.user_id = '"+userId+"' "
				+ "JOIN item ON ref_minestats.resourceUID = item.itemUID "
				+ "JOIN price_archive resourceItem ON ref_minestats.resourceUID = resourceItem.itemUID AND resourceItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "ORDER BY user_mines.mine_quantity DESC";
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				mines.add(populateMine(rs));
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return mines;
	}
	
	public static void updateUserMine(String userId, String resourceName, Integer quantity, Integer hourlyYield) throws Exception {
		String getSql = "SELECT * FROM user_mines "
				+ "WHERE user_id = '"+userId+"' "
				+ "AND mine_resourceUID = (SELECT itemUID FROM item WHERE machineName = '"+resourceName+"' LIMIT 1) ";
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(getSql);
			
			if(rs.next()) {
				// Update
				String updateSql = "UPDATE user_mines ";
				if(quantity == null && hourlyYield != null) {
					updateSql += "SET mine_hourlyYield = " + hourlyYield + " ";
				}
				else if (quantity != null && hourlyYield == null) {
					updateSql += "SET mine_quantity = " + quantity + " ";
				}
				else {
					updateSql += "SET mine_quantity = " + quantity + ", mine_hourlyYield = " + hourlyYield + " ";
				}
				updateSql += "WHERE user_id = '"+userId+"' "
					+ "AND mine_resourceUID = (SELECT itemUID FROM item WHERE machineName = '"+resourceName+"' LIMIT 1) ";
				
				Statement updateStmt = connection.createStatement();
				updateStmt.executeUpdate(updateSql);
			} else {
				// Insert
				String insertSql = "INSERT INTO user_mines VALUES( "
						+ "'"+userId+"', "
						+ "(SELECT itemUID FROM item WHERE machineName = '"+resourceName+"' LIMIT 1), "
						+ quantity + ", "
						+ hourlyYield + ")";
				
				Statement insertStmt = connection.createStatement();
				insertStmt.executeUpdate(insertSql);
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public static Mine populateMine(ResultSet rs) throws SQLException {
		Mine mine = new Mine();
		
		mine.setDisplayName(rs.getString("mineName"));
		mine.setBaseCost(rs.getInt("baseCost"));
		mine.setCostFactor(rs.getDouble("costFactor"));
		mine.setMaxProduction(rs.getInt("maxProduction"));
		mine.setRequiredLevel(rs.getInt("requiredLevel"));
		
		Item resource = new Item();
		resource.setDisplayName(rs.getString("resourceName"));
		resource.setMachineName(rs.getString("machineName"));
		resource.setMarketPrice(rs.getInt("marketAverage"));
		mine.setResource(resource);
		
		mine.setUserQuantity(rs.getInt("mine_quantity"));
		mine.setUserHourlyYield(rs.getInt("mine_hourlyYield"));
		
		return mine;
	}
}
