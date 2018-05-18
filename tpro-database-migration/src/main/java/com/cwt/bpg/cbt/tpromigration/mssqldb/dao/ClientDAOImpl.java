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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Bank;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.BankVendor;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductMerchantFee;

@Repository
public class ClientDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirlineRuleDAOImpl.class);

	@Autowired
	private DataSource dataSource;
	
	public List<Integer> getClientIds() {

		List<Integer> resultList = new ArrayList<>();

		String sql = "Select ClientID from tblClientMaster";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting products from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				resultList.add(rs.getInt("ClientID"));
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

		LOGGER.info("Size of airline rules from mssqldb: {}", resultList.size());

		return resultList;
	}


	public List<ProductMerchantFee> getProducts() {

		List<ProductMerchantFee> resultList = new ArrayList<>();

		String sql = "Select * from tblMFByProduct";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting products from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProductMerchantFee item = new ProductMerchantFee();

				item.setClientId(rs.getInt("ClientID"));
				item.setProductCode(rs.getInt("ProductCode"));
				item.setSubjectToMf(rs.getBoolean("SubjectToMF"));
				item.setStandard(rs.getBoolean("Standard"));

				resultList.add(item);
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

		LOGGER.info("Size of airline rules from mssqldb: {}", resultList.size());

		return resultList;
	}

	public List<BankVendor> getVendors() {
		
		List<BankVendor> resultList = new ArrayList<>();

		String sql = "SELECT * FROM tblMFByCC";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting bank vendros from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				BankVendor item = new BankVendor();

				item.setClientId(rs.getInt("ClientID"));
				item.setVendorName(rs.getString("Vendor"));
				item.setPercentage(rs.getDouble("Percentage"));
				item.setStandard(rs.getBoolean("Standard"));

				resultList.add(item);
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

		LOGGER.info("Size of bank vendors from mssqldb: {}", resultList.size());

		return resultList;
	}

	public List<Bank> getBanks() {
	
		List<Bank> resultList = new ArrayList<>();

		String sql = "Select * from tblMFByCC";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting banks from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Bank item = new Bank();
				item.setClientId(rs.getInt("ClientID"));
				item.setBankId(rs.getInt("MFBankID"));
				item.setBankName(rs.getString("Bank"));
				item.setCcNumberPrefix(rs.getString("CCNumber"));
				item.setPercentage(rs.getDouble("Percentage"));
				item.setStandard(rs.getBoolean("Standard"));

				resultList.add(item);
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

		LOGGER.info("Size of banks from mssqldb: {}", resultList.size());

		return resultList;
	}

}
