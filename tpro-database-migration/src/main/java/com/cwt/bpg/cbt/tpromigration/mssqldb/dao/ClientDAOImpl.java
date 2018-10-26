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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.AirVariables;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Client;

import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

@Repository
public class ClientDAOImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDAOImpl.class);

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DataSource middlewareDataSource;
	
	
	public List<Client> getClientsWithGstin() {
		List<Client> clients = new ArrayList<>();
		String sql = "select CNCode, GSTIN from ClientMaster where GSTIN is not NULL and GSTIN <> ''";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting clients with gstin from mssqldb middleware");
			conn = middlewareDataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Client client = new Client();
				client.setClientAccountNumber(rs.getString("CNCode"));
				client.setGstin(rs.getString("GSTIN"));
				clients.add(client);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading clients from mssqldb middleware, {}", e);
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

		LOGGER.info("Size of clients from mssqldb middleware: {}", clients.size());
		return clients;
	}
	
	public List<Client> getClients() {

		List<Client> clients = new ArrayList<>();

		String sql = "select "+
			"clientmasterpricing.cmpid, clientmaster.clientid, clientmaster.name, clientmaster.clientnumber, clientmasterpricing.pricingid, exempttax, "+
			"clientmaster.standardmfproduct, clientmaster.applymfcc, clientmaster.applymfbank, clientmaster.merchantfee, "+
			"airpricingformula.lccsameasint, airpricingformula.intddlfeeapply, airpricingformula.lccddlfeeapply "+
			"from "+
			"tblclientmaster clientmaster left join tblclientmasterpricing clientmasterpricing on clientmasterpricing.clientid = clientmaster.clientid, "+
			"tblAirPricingFormula airpricingformula "+
			"where clientmasterpricing.clientid = clientmaster.clientid "+
			"and airpricingformula.airpricingid=clientmasterpricing.pricingid "+
			"group by "+
				"clientmasterpricing.cmpid, clientmaster.clientid, clientmaster.name, clientmasterpricing.pricingid, exempttax, "+
				"clientmaster.standardmfproduct, clientmaster.applymfcc, clientmaster.applymfbank,clientmaster.clientid,clientmaster.merchantfee, "+
				"airpricingformula.lccsameasint, airpricingformula.intddlfeeapply, airpricingformula.lccddlfeeapply, clientmaster.clientnumber "+
			"order by clientmaster.clientid ";

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
				client.setClientAccountNumber(rs.getString("clientnumber"));
				client.setName(rs.getString("name"));
				client.setPricingId(rs.getInt("pricingid"));
				client.setExemptTax(rs.getBoolean("exempttax"));
				client.setApplyMfBank(rs.getBoolean("applymfbank"));
				client.setApplyMfCc(rs.getBoolean("applymfcc"));
				client.setCmpid(rs.getInt("cmpid"));
				client.setStandardMfProduct(rs.getObject("standardmfproduct") != null
												? rs.getBoolean("standardmfproduct") 
												: true);
				client.setMerchantFee(rs.getDouble("merchantfee"));
				client.setLccSameAsInt(rs.getBoolean("lccsameasint"));
				client.setIntDdlFeeApply(rs.getString("intddlfeeapply"));
				client.setLccDdlFeeApply(rs.getString("lccddlfeeapply"));
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
				item.setProductCode(rs.getString("ProductCode"));
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

	public List<CreditCardVendor> getCcs() {
		
		List<CreditCardVendor> resultList = new ArrayList<>();

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
				CreditCardVendor item = new CreditCardVendor();

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

		String sql = "Select * from tblMFByBank";

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


	public List<AirVariables> getAirVariables() {
		List<AirVariables> airVariables = new ArrayList<>();
		
		String sql = "Select FieldID, FieldName from cwtstandarddata.dbo.tblAirVariables";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting air variables from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				AirVariables airVariable = new AirVariables();
				airVariable.setFieldId(rs.getInt("FieldID"));
				airVariable.setFieldName(rs.getString("FieldName"));
				airVariables.add(airVariable);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading air variables, {}", e);
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

		LOGGER.info("Size of air variables from mssqldb: {}", airVariables.size());
		return airVariables;
		
	}
	
	public List<ClientPricing> getClientPricings() {

		List<ClientPricing> resultList = new ArrayList<>();

		String sql = "Select * from tblClientPricing";

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
				clientPricing.setFieldId(rs.getInt("fieldid"));
				clientPricing.setCmpid(rs.getInt("cmpid"));
				
				String value = rs.getString("value");
				if (value != null) {
					String valueString = value.split("%")[0].trim();
					clientPricing.setValue(Integer.parseInt(valueString));
				}
				
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
				transactionFee.setAmount(rs.getBigDecimal("tfamount"));
				transactionFee.setStartAmount(rs.getBigDecimal("startamount"));
				transactionFee.setEndAmount(rs.getBigDecimal("endamount"));
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
				transactionFee.setStartCoupon(rs.getInt("startcoupon"));
				transactionFee.setEndCoupon(rs.getInt("endcoupon"));
				transactionFee.setAmount(rs.getBigDecimal("tfamount"));
				transactionFee.setThreshold(rs.getInt("threshold"));
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
				transactionFee.setAmount(rs.getBigDecimal("tfamount"));
				transactionFee.setExtraAmount(rs.getBigDecimal("extraamount"));
				transactionFee.setPerAmount(rs.getDouble("peramount"));
				transactionFee.setMinAmount(rs.getBigDecimal("minamount"));
				transactionFee.setMaxAmount(rs.getBigDecimal("maxamount"));
				transactionFee.setStartAmount(rs.getBigDecimal("startamount"));
				transactionFee.setEndAmount(rs.getBigDecimal("endamount"));
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
	
	public List<AirMiscInfo> getAirMiscInfo() {
		
		List<AirMiscInfo> airMiscInfoList = new ArrayList<>();
		
		String sql = "select c.GDSFormat, a.Description, a.DataType, a.Min, a.Max, a.ReportingListID,  a.SampleValue, "
				+ "a.FieldType, a.Mandatory, a.ClientID, d.ClientNumber, b.ReportingFieldTypeID, b.ReportingFieldType "
				+ "from tblReportingList a "
				+ "left join tblReportingFieldType b on b.ReportingFieldTypeID = a.FieldType "
				+ "left join tblGDSMapping c on c.ReportingListID = a.ReportingListID "
				+ "left join tblClientMaster d on d.ClientID = a.ClientID";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			LOGGER.info("Getting client misc info from mssqldb");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				AirMiscInfo airMiscInfo = new AirMiscInfo();
				airMiscInfo.setGdsFormat(rs.getString("GDSFormat"));
				airMiscInfo.setDescription(rs.getString("Description"));
				airMiscInfo.setDataType(rs.getString("DataType"));
				airMiscInfo.setMin(rs.getString("Min"));
				airMiscInfo.setMax(rs.getString("Max"));
				airMiscInfo.setReportingListId(rs.getString("ReportingListID"));
				airMiscInfo.setSample(rs.getString("SampleValue"));
				airMiscInfo.setReportingFieldType(rs.getString("FieldType"));
				airMiscInfo.setMandatory(rs.getString("Mandatory"));
				airMiscInfo.setClientId(rs.getString("ClientID"));
				airMiscInfo.setClientAccountNumber(rs.getString("ClientNumber"));
				airMiscInfo.setReportingFieldTypeId(rs.getString("ReportingFieldTypeID"));
				airMiscInfo.setReportingFieldType(rs.getString("ReportingFieldType"));
				airMiscInfoList.add(airMiscInfo);
			}
		}
		catch (SQLException e) {
			LOGGER.error("Error reading air misc info from mssqldb middleware, {}", e);
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

		LOGGER.info("Size of air misc info from mssqldb : {}", airMiscInfoList.size());
		return airMiscInfoList;
		
	}
}
