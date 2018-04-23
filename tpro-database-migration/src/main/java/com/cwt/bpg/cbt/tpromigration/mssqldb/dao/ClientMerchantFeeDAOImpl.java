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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ClientMerchantFee;

@Repository
public class ClientMerchantFeeDAOImpl implements ClientMerchantFeeDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientMerchantFeeDAOImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<ClientMerchantFee> listMerchantFees() {
		List<ClientMerchantFee> merchantFeeList = new ArrayList<ClientMerchantFee>();
		String sql = "SELECT clientname, proname, clientType, merchfeepct , tfincmf FROM tblClients";
		
		Connection conn = null;

		try {
			logger.info("getting merchantFees from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			String countryCode = System.getProperty("spring.profiles.default");
			while (rs.next()) {
				ClientMerchantFee merchantFee = new ClientMerchantFee();
				merchantFee.setIncludeTransactionFee(rs.getObject("tfincmf") == null ? null : rs.getBoolean("tfincmf"));
				merchantFee.setMerchantFeePct(rs.getObject("merchfeepct") == null ? null : rs.getDouble("merchfeepct"));
				merchantFee.setClientName(rs.getObject("clientname") == null ? null : rs.getString("clientname").trim());
				merchantFee.setClientType(rs.getObject("clientType") == null ? null : rs.getString("clientType").trim());
				merchantFee.setProductName(rs.getObject("proname") == null ? null : rs.getString("proname").trim());
				merchantFee.setCountryCode(countryCode);
				merchantFeeList.add(merchantFee);
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
		logger.info("size of client merchant fee from mssqldb: {}", merchantFeeList.size());

		return merchantFeeList;
	}

}
