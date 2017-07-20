package net.outlawsource.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.outlawsource.business.domain.Factory;
import net.outlawsource.business.domain.Item;

@Repository
public class FactoryDAO {
	
	@Autowired
	DatabaseManager dataManager;
	
	public Factory getUserFactory(String userId, String productName) throws Exception {
		Factory factory = null;
		
		String sql = "SELECT ref_factorylevel.name AS factoryName,"
				+ "	item.name AS productName, "
				+ "    item.machineName AS productMachineName, "
				+ "    productItem.marketAverage AS productItemAverage, "
				+ "    productItem.rawMaterialAverage AS productBuildAverage, "
				+ "    ref_factorylevel.production, "
				+ "    ref_factorylevel.moneyRate AS moneyUpgradeRate, "
				+ "    firstItem.marketAverage AS firstItemAverage, "
				+ "    ref_factorylevel.firstItemRate AS firstItemUpgradeRate, "
				+ "    secondItem.marketAverage AS secondItemAverage, "
				+ "    ref_factorylevel.secondItemRate AS secondItemUpgradeRate, "
				+ "    thirdItem.marketAverage AS thirdItemAverage, "
				+ "    ref_factorylevel.thirdItemRate AS thirdItemUpgradeRate, "
				+ "    user_factories.factory_level "
				+ "FROM osdb.ref_factorylevel "
				+ "LEFT JOIN user_factories ON ref_factorylevel.productUID = user_factories.factory_productUID AND user_factories.user_id = '"+userId+"' "
				+ "JOIN item ON ref_factorylevel.productUID = item.itemUID AND item.machineName = '" + productName + "' "
				+ "JOIN price_archive productItem ON ref_factorylevel.productUID = productItem.itemUID AND productItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "JOIN price_archive firstItem ON ref_factorylevel.firstItemUID = firstItem.itemUID AND firstItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "LEFT JOIN price_archive secondItem ON ref_factorylevel.secondItemUID = secondItem.itemUID AND secondItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "LEFT JOIN price_archive thirdItem ON ref_factorylevel.thirdItemUID = thirdItem.itemUID AND thirdItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "ORDER BY user_factories.factory_level DESC";
		
		Connection connection = null;
		try {
			connection = dataManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				factory = populateFactory(rs);
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return factory;
	}
	
	public List<Factory> getUserFactories(String userId) throws Exception {
		List<Factory> factories = new ArrayList<Factory>();
		String sql = "SELECT ref_factorylevel.name AS factoryName,"
				+ "	item.name AS productName, "
				+ "    item.machineName AS productMachineName, "
				+ "    productItem.marketAverage AS productItemAverage, "
				+ "    productItem.rawMaterialAverage AS productBuildAverage, "
				+ "    ref_factorylevel.production, "
				+ "    ref_factorylevel.moneyRate AS moneyUpgradeRate, "
				+ "    firstItem.marketAverage AS firstItemAverage, "
				+ "    ref_factorylevel.firstItemRate AS firstItemUpgradeRate, "
				+ "    secondItem.marketAverage AS secondItemAverage, "
				+ "    ref_factorylevel.secondItemRate AS secondItemUpgradeRate, "
				+ "    thirdItem.marketAverage AS thirdItemAverage, "
				+ "    ref_factorylevel.thirdItemRate AS thirdItemUpgradeRate, "
				+ "    user_factories.factory_level "
				+ "FROM osdb.ref_factorylevel "
				+ "LEFT JOIN user_factories ON ref_factorylevel.productUID = user_factories.factory_productUID AND user_factories.user_id = '"+userId+"' "
				+ "JOIN item ON ref_factorylevel.productUID = item.itemUID "
				+ "JOIN price_archive productItem ON ref_factorylevel.productUID = productItem.itemUID AND productItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "JOIN price_archive firstItem ON ref_factorylevel.firstItemUID = firstItem.itemUID AND firstItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "LEFT JOIN price_archive secondItem ON ref_factorylevel.secondItemUID = secondItem.itemUID AND secondItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "LEFT JOIN price_archive thirdItem ON ref_factorylevel.thirdItemUID = thirdItem.itemUID AND thirdItem.archiveDate = (SELECT archiveDate FROM price_archive ORDER BY archiveDate DESC LIMIT 1) "
				+ "ORDER BY user_factories.factory_level DESC";
		
		Connection connection = null;
		try {
			connection = dataManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				factories.add(populateFactory(rs));
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return factories;
	}
	
	public void updateFactoryLevel(String userId, String productName, int level) throws Exception {
		String getSql = "SELECT * FROM user_factories "
				+ "WHERE user_id = '"+userId+"' "
				+ "AND factory_productUID = (SELECT itemUID FROM item WHERE machineName = '"+productName+"' LIMIT 1) ";
		
		Connection connection = null;
		try {
			connection = dataManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(getSql);
			
			if(rs.next()) {
				// Update
				String updateSql = "UPDATE user_factories "
						+ "SET factory_level = "+level+" "
						+ "WHERE user_id = '"+userId+"' "
						+ "AND factory_productUID = (SELECT itemUID FROM item WHERE machineName = '"+productName+"' LIMIT 1) ";
				
				Statement updateStmt = connection.createStatement();
				updateStmt.executeUpdate(updateSql);
			} else {
				// Insert
				String insertSql = "INSERT INTO user_factories VALUES( "
						+ "'"+userId+"', "
						+ "(SELECT itemUID FROM item WHERE machineName = '"+productName+"' LIMIT 1), "
						+ ""+level+")";
				
				Statement insertStmt = connection.createStatement();
				insertStmt.executeUpdate(insertSql);
			}
			
			connection.close();
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	
	public Factory populateFactory(ResultSet rs) throws SQLException {
		Factory fac = new Factory();
		fac.setDisplayName(rs.getString("factoryName"));
		int level = rs.getInt("factory_level");
		fac.setUserLevel(level != 0 ? level : 1);
		
		Item product = new Item();
		product.setDisplayName(rs.getString("productName"));
		product.setMachineName(rs.getString("productMachineName"));
		product.setMarketPrice(rs.getInt("productItemAverage"));
		product.setFactoryCostPrice(rs.getInt("productBuildAverage"));
		fac.setProduct(product);
		
		fac.setBaseProduction(rs.getInt("production"));
		fac.setMoneyRate(rs.getInt("moneyUpgradeRate"));
		fac.setFirstItemPrice(rs.getInt("firstItemAverage"));
		fac.setFirstItemRate(rs.getInt("firstItemUpgradeRate"));		
		fac.setSecondItemPrice(rs.getInt("secondItemAverage"));
		fac.setSecondItemRate(rs.getInt("secondItemUpgradeRate"));
		fac.setThirdItemPrice(rs.getInt("thirdItemAverage"));
		fac.setThirdItemRate(rs.getInt("thirdItemUpgradeRate"));	
		return fac;
	}
}