package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Bank;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.BankVendor;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ClientPricing;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductMerchantFee;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.TransactionFee;

@Repository
public class ClientDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDAOImpl.class);

	@Autowired
	private DataSource dataSource;
	
	public List<Client> getClients() {

		List<Client> clients = new ArrayList<>();

		String sql = "select \n" + 
				"    clientmasterpricing.cmpid, clientmaster.clientid, clientmaster.name, clientmapping.profilename, clientmasterpricing.pricingid, exempttax,\n" + 
				"	clientmaster.standardmfproduct, clientmaster.applymfcc, clientmaster.applymfbank,clientmaster.clientid,clientmaster.merchantfee\n" + 
				"from \n" + 
				"	tblclientmaster clientmaster left join tblclientmasterpricing clientmasterpricing on clientmasterpricing.clientid = clientmaster.clientid,  \n" + 
				"	tblconfiguration config, \n" + 
				"	cwtstandarddata.dbo.tblcsp_linedefclientmapping clientmapping, \n" + 
				"	cwtstandarddata.dbo.configinstances configinstance  \n" + 
				"where clientmaster.configurationid = config.configurationid \n" + 
				"	and clientmaster.clientid = clientmapping.clientid \n" + 
				"	and clientmasterpricing.clientid = clientmaster.clientid\n" + 
				"	and configinstance.countrycode = 'IN'\n" + 
				"	and clientmapping.configinstancekeyid=configinstance.keyid\n" + 
				"group by \n" + 
				"    clientmasterpricing.cmpid, clientmaster.clientid, clientmaster.name, clientmapping.profilename, clientmasterpricing.pricingid, exempttax,\n" + 
				"	clientmaster.standardmfproduct, clientmaster.applymfcc, clientmaster.applymfbank,clientmaster.clientid,clientmaster.merchantfee\n" + 
				"order by clientmaster.clientid";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting clients from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Client client = new Client();
				client.setClientId(rs.getInt("clientid"));
				client.setName(rs.getString("name"));
				client.setProfileName(rs.getString("profilename"));
				client.setPricingId(rs.getInt("pricingid"));
				client.setCmpid(rs.getInt("cmpid"));
				client.setExemptTax(rs.getBoolean("exempttax"));
				client.setApplyMfBank(rs.getBoolean("applymfbank"));
				client.setApplyMfCc(rs.getBoolean("applymfcc"));
				client.setStandardMfProduct(rs.getBoolean("standardmfproduct"));
				client.setMerchantFee(rs.getDouble("merchantfee"));
				clients.add(client);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading clients, {}", e);
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

		LOGGER.info("Size of clients from mssqldb: {}", clients.size());

		return clients;
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


	public List<ClientPricing> getClientPricings() {

		List<ClientPricing> resultList = new ArrayList<>();

		String sql = "Select * from tblClientPricing where fieldid = 5";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting client pricing from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				ClientPricing clientPricing = new ClientPricing();
				clientPricing.setCmpid(rs.getInt("cmpid"));
				clientPricing.setGroup(rs.getInt("value"));
				clientPricing.setTripType(rs.getString("triptype"));
				clientPricing.setFeeOption(rs.getString("valueoption"));
				resultList.add(clientPricing);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading client pricing, {}", e);
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
			}
		}

		LOGGER.info("Size of client pricing from mssqldb: {}", resultList.size());

		return resultList;
	}


	public List<TransactionFee> getTransactionFeeByPNR() {

		List<TransactionFee> resultList = new ArrayList<>();

		String sql = "select feeid, territorycode, tfamount, startamount, endamount from tbltransactionfeebypnr order by transid";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting transaction fees from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				TransactionFee transactionFee = new TransactionFee();
				transactionFee.setFeeId(rs.getInt("feeid"));
				String territoryCodesStr = rs.getString("territorycode");
				if(StringUtils.isNotBlank(territoryCodesStr)) {
					List<String> territoryCodes = Arrays.asList(territoryCodesStr.split(";"));
					transactionFee.setTerritoryCodes(territoryCodes);
				}
				transactionFee.setTfAmount(rs.getDouble("tfamount"));
				transactionFee.setStartAmount(rs.getDouble("startamount"));
				transactionFee.setEndAmount(rs.getDouble("endamount"));
				resultList.add(transactionFee);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading transaction fees, {}", e);
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
			}
		}
		LOGGER.info("Size of transaction fees from mssqldb: {}", resultList.size());
		return resultList;
	}


	public List<TransactionFee> getTransactionFeeByCoupon() {

		List<TransactionFee> resultList = new ArrayList<>();

		String sql = "select feeid, type, endcoupon, startcoupon, tfamount, threshold from tbltransactionfeebycoupon order by type desc, startcoupon asc";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting transaction fees from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				TransactionFee transactionFee = new TransactionFee();
				transactionFee.setFeeId(rs.getInt("feeid"));
				transactionFee.setType(rs.getString("type"));
				transactionFee.setStartCoupon(rs.getDouble("startcoupon"));
				transactionFee.setEndCoupon(rs.getDouble("endcoupon"));
				transactionFee.setTfAmount(rs.getDouble("tfamount"));
				transactionFee.setThreshold(rs.getDouble("threshold"));
				resultList.add(transactionFee);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading transaction fees, {}", e);
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
			}
		}
		LOGGER.info("Size of transaction fees from mssqldb: {}", resultList.size());
		return resultList;
	}


	public List<TransactionFee> getTransactionFeeByTicket() {

		List<TransactionFee> resultList = new ArrayList<>();

		String sql = "select feeid, territorycode, operator, tfamount, extraamount, peramount, minamount, maxamount, startamount, endamount from tbltransactionfeebyticket"; 

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting transaction fees from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				TransactionFee transactionFee = new TransactionFee();
				transactionFee.setFeeId(rs.getInt("feeid"));
				String territoryCodesStr = rs.getString("territorycode");
				if(StringUtils.isNotBlank(territoryCodesStr)) {
					List<String> territoryCodes = Arrays.asList(territoryCodesStr.split(";"));
					transactionFee.setTerritoryCodes(territoryCodes);
				}
				transactionFee.setOperator(rs.getString("operator"));
				transactionFee.setTfAmount(rs.getDouble("tfamount"));
				transactionFee.setExtraAmount(rs.getDouble("extraamount"));
				transactionFee.setPerAmount(rs.getDouble("peramount"));
				transactionFee.setMinAmount(rs.getDouble("minamount"));
				transactionFee.setMaxAmount(rs.getDouble("maxamount"));
				transactionFee.setStartAmount(rs.getDouble("startamount"));
				transactionFee.setEndAmount(rs.getDouble("endamount"));
				resultList.add(transactionFee);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading transaction fees, {}", e);
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
			}
		}
		LOGGER.info("Size of transaction fees from mssqldb: {}", resultList.size());
		return resultList;
	}

}
