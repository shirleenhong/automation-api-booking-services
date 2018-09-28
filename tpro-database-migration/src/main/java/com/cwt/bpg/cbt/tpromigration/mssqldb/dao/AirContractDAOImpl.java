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

import com.cwt.bpg.cbt.air.contract.model.AirContract;

@Repository
public class AirContractDAOImpl {
	private static final Logger logger = LoggerFactory.getLogger(AirContractDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<AirContract> getList() {
		List<AirContract> airContracts = new ArrayList<>();
		logger.info("setting up query");
		String sql = "select CN, Carrier, FOPCode from tblClientAirContracts";


		Connection conn = null;

		try {
			logger.info("getting airContracts from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AirContract airContract = new AirContract();
				airContract.setAirlineCode(rs.getString("Carrier"));
				airContract.setClientAccountNumber(rs.getString("CN"));
				airContract.setFopCode(rs.getString("FOPCode"));
				airContracts.add(airContract);
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
		logger.info("size of airContracts from mssqldb: {}", airContracts.size());
		return airContracts;
	}
	
}
