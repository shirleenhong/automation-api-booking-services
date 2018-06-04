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

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

@Repository
public class AirlineRuleDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirlineRuleDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<AirlineRule> list() {
		
		List<AirlineRule> airlineRuleList = new ArrayList<>();
		
		String sql = "SELECT AirlineCode, IncludeYQComm FROM tblAirlineRule";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting airline rules from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AirlineRule airlineRule = new AirlineRule();
				airlineRule.setCode(rs.getString("AirlineCode"));
				airlineRule.setIncludeYqCommission(rs.getBoolean("IncludeYQComm"));
				airlineRuleList.add(airlineRule);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading airline rules, {}", e);
		}
		finally {

			try {

				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException e) {
				//
			}
		}

		LOGGER.info("Size of airline rules from mssqldb: {}", airlineRuleList.size());

		return airlineRuleList;
	}

}
