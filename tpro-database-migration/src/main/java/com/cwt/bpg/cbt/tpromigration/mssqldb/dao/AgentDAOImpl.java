package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.TblAgent;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.TblAgentConfig;

@Repository
public class AgentDAOImpl {
	private static final Logger logger = LoggerFactory.getLogger(AgentDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<TblAgent> getAgentList() {
		List<TblAgent> agents = new ArrayList<>();
		logger.info("setting up query");
		String sql = "select AgentName, LastName, DIV from tblAgents";


		Connection conn = null;

		try {
			logger.info("getting tblAgents from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TblAgent agent = new TblAgent();
				agent.setFirstName(rs.getString("AgentName"));
				agent.setLastName(rs.getString("LastName"));
				agent.setDivision(rs.getString("DIV"));			
				agents.add(agent);
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e) {
				}
			}
		}
		logger.info("size of airContracts from mssqldb: {}", agents.size());
		return agents;
	}
	
	public List<TblAgentConfig> getAgentConfigList() {
		List<TblAgentConfig> agentConfigs = new ArrayList<>();
		logger.info("setting up query");
		String sql = "select DIV, tel, CountryCode from tblConfiguration";


		Connection conn = null;

		try {
			logger.info("getting tblConfiguration from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TblAgentConfig agentConfig = new TblAgentConfig();
				agentConfig.setDivision(rs.getString("DIV"));
				agentConfig.setPhone(rs.getString("tel"));
				agentConfig.setCountryCode(rs.getString("CountryCode"));
				agentConfigs.add(agentConfig);
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e) {
				}
			}
		}
		logger.info("size of tblConfiguration from mssqldb: {}", agentConfigs.size());
		return agentConfigs;
	}
	
}
