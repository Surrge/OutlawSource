package net.outlawsource.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDAO {
	
	@Autowired
	DatabaseManager dataManager;
	
	public List<String> getItemNames() throws Exception {
		List<String> results = new ArrayList<String>();
		
		String sql = "SELECT DISTINCT name FROM item ORDER BY name";
		Connection conn = null;
		try {
			conn = dataManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				results.add(rs.getString("name"));
			}
		}
		catch(Exception ex) {
			throw ex;
		}
		
		return results;
	}
}
