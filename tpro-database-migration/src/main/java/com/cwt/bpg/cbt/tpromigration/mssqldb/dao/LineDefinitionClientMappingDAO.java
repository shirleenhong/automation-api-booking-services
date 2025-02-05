package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.LineDefinitionClientMapping;

import groovy.json.JsonOutput;

@Repository
public class LineDefinitionClientMappingDAO {

	private static final Logger logger = LoggerFactory.getLogger(LineDefinitionClientMappingDAO.class);

	@Autowired
	private DataSource dataSource;

	public List<LineDefinitionClientMapping> getLineDefinitionClientMapping() {
		final String sql = "select ldcm.*, t.ClientNumber, t.clientNumberCount from [CWTStandardData].[dbo].[tblCSP_LineDefClientMapping] ldcm "
				+ "left join  (select t1.clientid, t1.clientnumber, t2.clientNumberCount from [CWTMasterDB_IN].[dbo].[tblClientMaster] t1 "
				+ "inner join (SELECT  count(clientNumber) clientNumberCount, clientnumber "
				+ "FROM [CWTMasterDB_IN].[dbo].[tblClientMaster] group by clientnumber) t2 on t1.ClientNumber = t2.ClientNumber) "
				+ "t on ldcm.clientid = t.clientid";
		List<LineDefinitionClientMapping> lineDefs = new ArrayList<>();
		Connection conn = null;

		try {
			logger.info("getting line definition client mapping from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LineDefinitionClientMapping lineDefinition = new LineDefinitionClientMapping();
				lineDefinition.setClientId(rs.getInt("ClientID"));
				lineDefinition.setMappingId(rs.getInt("MappingID"));
				lineDefinition.setConfigInstanceKeyId(rs.getInt("ConfigInstanceKeyID"));
				lineDefinition.setDummyBar(rs.getString("DummyBar"));
				lineDefinition.setLineDefMasterId(rs.getInt("LineDefMasterID"));
				lineDefinition.setProfilePcc(rs.getString("ProfilePCC"));
				lineDefinition.setProfileName(rs.getString("ProfileName"));
				lineDefinition.setPreferred(rs.getInt("Preferred"));
				if (rs.getInt("clientNumberCount")== 1) {
					lineDefinition.setClientAccountNumber(rs.getString("ClientNumber"));
				}
				lineDefs.add(lineDefinition);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		logger.info("size of line definition mapping from mssqldb: {}", lineDefs.size());
		Map<String, Object> m = new HashMap<>();
		
		m.put("payload", lineDefs);
		String json = JsonOutput.toJson(m);
		
		logger.info(JsonOutput.prettyPrint(json));
		return lineDefs;
	}

}
