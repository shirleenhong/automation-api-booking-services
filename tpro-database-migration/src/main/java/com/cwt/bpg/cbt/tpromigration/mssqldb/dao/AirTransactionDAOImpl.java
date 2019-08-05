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

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

@Repository
public class AirTransactionDAOImpl {
	private static final Logger logger = LoggerFactory.getLogger(AirTransactionDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<AirTransaction> getList(boolean isSP) {
		List<AirTransaction> airTransactions = new ArrayList<>();
		logger.info("setting up query");
		
		String sql = 
		 "SELECT DISTINCT a.AirlineCode,c.AirlineDescription, a.CCVendorCode, a.CCType, x.Countrycode, x.ClientNumber, "
		+ "CASE a.CCVendorCode WHEN 'TP' THEN 'Airplus' "
		+ "WHEN 'DC' THEN 'Diners' ELSE d.CCVendorName  "
		+ "END as CCVendorName,  "
		+ "CASE WHEN PassthroughType = 'FP' THEN 'Airline' " 
		+ "WHEN PassthroughType = 'NP' THEN 'CWT' "
		+ "WHEN PassthroughType = 'SP' THEN "
		+ "CASE  WHEN a.CCVendorCode = 'DC' OR a.CCVendorCode = 'TP' THEN 'CWT' " 
		+ "WHEN a.AirlineCode = 'SQ' THEN 'Airline' "
		+ "WHEN a.AirlineCode = 'MI' THEN  "
		+ "CASE WHEN a.CCVendorCode='CA' OR a.CCVendorCode='VI' THEN " 
		+ "'CWT' WHEN a.CCVendorCode='AX' THEN 'Airline' END "
		+ "END END as PassthroughType FROM tblAirlineCC a  "
		+ "left join CWTStandardData.dbo.tblAirlines c ON c.AirlineCode = a.AirlineCode " 
		+ "left join CWTStandardData.dbo.tblCCVendor d ON d.CCVendorCode = a.CCVendorCode "  
		+ "left join (SELECT DISTINCT e.clientNumber,e.clientid,h.countrycode FROM tblClientMaster e " 
			+ "left join cwtstandarddata.dbo.tblcsp_linedefclientmapping g ON g.clientid = e.clientid " 
			+ "left join cwtstandarddata.dbo.configinstances h ON h.keyId = g.configInstanceKeyId "
			+ ") x ON x.clientid = a.clientid "
		+ "WHERE a.passThroughType "+(isSP?"=":"<>") +" 'SP' "
		+ "AND x.countrycode IS NOT NULL ";
		
		Connection conn = null;

		try {
			logger.info("getting airTransactions from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AirTransaction airTransaction = new AirTransaction();
				airTransaction.setCountryCode(rs.getString("CountryCode"));
				airTransaction.setAirlineCode(rs.getString("AirlineCode"));
				airTransaction.setAirlineDescription(rs.getString("AirlineDescription"));
				airTransaction.setCcVendorCode(rs.getString("CCVendorCode"));
				airTransaction.setCcVendorName(rs.getString("CCVendorName"));
				airTransaction.setPassthroughType(PassthroughType.fromString(rs.getString("PassthroughType")));
				airTransactions.add(airTransaction);
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
		logger.info("size of airTransactions from mssqldb: {}", airTransactions.size());
		return airTransactions;
	}
	
}
