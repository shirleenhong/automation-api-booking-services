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

import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;
import com.cwt.bpg.cbt.exchange.order.model.ContactInfoType;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.MerchantFeeAbsorb;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;

@Repository
public class IndiaVendorDAOImpl implements VendorDAO {

	private static final Logger logger = LoggerFactory.getLogger(VendorDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<Vendor> listVendors() {
		List<Vendor> vendorList = new ArrayList<>();
		String sql = "SELECT * FROM (SELECT DISTINCT a.*, SUBSTRING("
				+ " (SELECT ';' + CAST(productid AS varchar)  AS [text()]" 
				+ " FROM tblVendorProduct b"
				+ " WHERE b.VendorNumber COLLATE SQL_LATIN1_GENERAL_CP1_CI_AS = a.VendorNumber"
				+ " ORDER BY a.vendornumber" 
				+ " FOR XML PATH ('')" 
				+ "), 2, 1000) [ProductID]"
				+ " FROM tblVendors a ) x WHERE x.[ProductID] is not null";

		Connection conn = null;

		try {
			logger.info("getting vendors from mssqldb india");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Vendor tblVendor = new Vendor();
				tblVendor.setCode(rs.getString("VendorNumber"));
				tblVendor.setInterfaceNumber(rs.getString("InterfaceNumber"));
				tblVendor.setName(rs.getString("VendorName").replaceAll("\\u2013", "-")
						.replaceAll("\\u00a0", " ").replaceAll(" +", " ").trim());
				tblVendor.setContactPerson(rs.getString("ContactPerson"));
				tblVendor.setAddress1(rs.getString("Address") != null 
						? rs.getString("Address").replaceAll("\\r\\n", " ")
						: null);
				tblVendor.setCity(rs.getString("City"));
				tblVendor.setPostalCode(rs.getString("PostalCode"));
				tblVendor.setState(rs.getString("State"));
				tblVendor.setCountry(rs.getString("Country"));
				tblVendor.setVendorType(rs.getString("VendorType"));
				tblVendor.setRequireEo(rs.getObject("RequireEO") == null ? null : rs.getBoolean("RequireEO"));
				tblVendor.setRequireAdvanced(
						rs.getObject("RequireAdvanced") == null ? null : rs.getBoolean("RequireAdvanced"));
				tblVendor.setMisc(rs.getObject("Misc") == null ? null : rs.getBoolean("MISC"));
				tblVendor.setHotelFee(rs.getString("HotelFee"));

				String productCodesStr = rs.getString("ProductID");
				if (StringUtils.isNotBlank(productCodesStr)) {
					List<String> productCodes = Arrays.asList(productCodesStr.replaceAll(" ", "").split(";"));
					tblVendor.setProductCodes(productCodes);
				}

				vendorList.add(tblVendor);
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
		logger.info("size of vendors from mssqldb: {}", vendorList.size());
		return vendorList;
	}
	
	
	@Override
    public List<ContactInfo> listVendorContactInfo(){
		List<ContactInfo> contactInfoList = new ArrayList<>();
        String sql = "SELECT * from tblVendorContact";
        
        Connection conn = null;

		try {
			logger.info("getting vendor contacts from mssqldb india");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setVendorNumber(rs.getString("VendorNumber"));
				contactInfo.setDetail(rs.getString("ContactDetail"));
				contactInfo.setPreferred(rs.getBoolean("PreferEOSend"));

				switch (rs.getString("ContactType")) {
					case "EMAIL": contactInfo.setType(ContactInfoType.EMAIL);
						break;
					case "FAX": contactInfo.setType(ContactInfoType.FAX);
						break;
					case "PHONE": contactInfo.setType(ContactInfoType.PHONE);
						break;
				}

                contactInfoList.add(contactInfo);
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
		logger.info("size of vendor contact from mssqldb: {}", contactInfoList.size());
		return contactInfoList;       
	}


	@Override
	public List<MerchantFeeAbsorb> listNoMerchantFee() {
		return new ArrayList<>();
	}
}
