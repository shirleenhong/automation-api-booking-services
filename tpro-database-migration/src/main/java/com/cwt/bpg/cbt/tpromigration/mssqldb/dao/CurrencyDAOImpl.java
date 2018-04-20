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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Currency;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyDAOImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<Currency> listCurrencies() {
		
		List<Currency> currencyList = new ArrayList<Currency>();
		String sql = "SELECT * FROM tblCurrency where RoundUnit != '9.9999998E'";
		
		Connection conn = null;

		try {
			logger.info("getting currencies from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Currency currency = new Currency();
				currency.setCurrencyCode(rs.getObject("CurrencyCode") == null ? null : rs.getString("CurrencyCode").trim());
				currency.setDescription(rs.getObject("Description") == null ? null : rs.getString("Description").trim());
				currency.setDecimal(rs.getObject("Decimal") == null ? null : rs.getInt("Decimal"));
				currency.setRoundRule(rs.getObject("RoundRule") == null ? null : rs.getString("RoundRule").trim());
				currency.setRoundUnit(rs.getObject("RoundUnit") == null ? null : rs.getDouble("RoundUnit"));
				currencyList.add(currency);
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
		logger.info("size of currencies from mssqldb: {}", currencyList.size());

		return currencyList;
	}
	

}
