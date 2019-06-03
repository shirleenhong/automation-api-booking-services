package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@Repository
public class ClientMerchantFeeDAOImpl implements ClientMerchantFeeDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientMerchantFeeDAOImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<MerchantFee> listMerchantFees(String countryCode) {
		List<MerchantFee> merchantFeeList = new ArrayList<>();
		String sql = "SELECT clientname, cn, clientType, merchfeepct , tfincmf FROM tblClients";
		
		Connection conn = null;

		try {
			logger.info("getting merchantFees from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				MerchantFee merchantFee = new MerchantFee();
				//TODO Get Object check doesn't work
				merchantFee.setIncludeTransactionFee(rs.getBoolean("tfincmf"));
				merchantFee.setMerchantFeePercent(rs.getObject("merchfeepct") == null ? null : rs.getDouble("merchfeepct"));
				merchantFee.setClientName(rs.getObject("clientname") == null ? null : rs.getString("clientname").trim().replaceAll(" +", " "));
				merchantFee.setCountryCode(countryCode);
				if(rs.getObject("clientType") != null) {
					merchantFee.setClientType(rs.getString("clientType").trim());
				}
				if(rs.getObject("cn") != null) {
					merchantFee.setClientAccountNumber(StringUtils.stripStart(rs.getString("cn"),"0"));
				}else {
					continue;
				}
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
