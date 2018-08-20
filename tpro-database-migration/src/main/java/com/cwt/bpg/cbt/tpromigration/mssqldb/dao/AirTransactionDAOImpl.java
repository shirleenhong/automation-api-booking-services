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

import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;

@Repository
public class AirTransactionDAOImpl {
	private static final Logger logger = LoggerFactory.getLogger(AirTransactionDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<AirTransaction> getList() {
		List<AirTransaction> airTransactions = new ArrayList<>();
		String sql =
				" select h.countryCode, f.BkClass, a.AirlineCode, c.AirlineDescription, a.CCVendorCode, d.CCVendorName, a.CCType, "+
				" 	case CCType "+
				" 		when 'UATP' then case PassthroughType "+
				" 		when 'FP' then 'Airline' "+
				" 		when 'NP' then 'CWT' "+
				" 	end "+
				" 	when 'NRCC' then "+
				" 	case PassthroughType "+
				" 	when 'FP' then 'Airline' "+
				" 	when 'NP' then 'CWT' "+
				" 	end "+
				" 	end as PassthroughType, "+
				" 	e.clientNumber "+
				" from tblAirlineCC a "+
				" 	left join CWTStandardData.dbo.tblAirlines c on c.AirlineCode = a.AirlineCode "+
				" 	left join CWTStandardData.dbo.tblCCVendor d on d.CCVendorCode = a.CCVendorCode "+
				" 	left join tblClientMaster e on e.clientid = a.clientid "+
				" 	left join CWTStandardData.dbo.tblBookingClass f on f.carrier = a.AirlineCode "+
				" 	left join cwtstandarddata.dbo.tblcsp_linedefclientmapping g on g.clientid = e.clientid "+
				" 	left join cwtstandarddata.dbo.configinstances h on h.keyId = g.configInstanceKeyId "+
				" where h.countryCode = 'IN' ";

		Connection conn = null;

		try {
			logger.info("getting airTransactions from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AirTransaction airTransaction = new AirTransaction();
				airTransaction.setCountryCode(rs.getString("countryCode"));
				airTransaction.setBookingClass(rs.getString("BkClass"));
				airTransaction.setAirlineCode(rs.getString("AirlineCode"));
				airTransaction.setAirlineDescription(rs.getString("AirlineDescription"));
				airTransaction.setCcVendorCode(rs.getString("CCVendorCode"));
				airTransaction.setCcVendorName(rs.getString("CCVendorName"));
				airTransaction.setCcType(rs.getString("CCType"));
				airTransaction.setPassthroughType(rs.getString("PassthroughType"));
				airTransaction.setClientAccountNumber(rs.getString("clientNumber"));
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
