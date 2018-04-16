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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;

@Repository
public class VendorDAOImpl implements VendorDAO {

	private static final Logger logger = LoggerFactory.getLogger(VendorDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> listVendors() {
		List<Vendor> vendorList = new ArrayList<Vendor>();
		String sql = "SELECT * FROM tblVendors";

		Connection conn = null;
//		@Column(name="VendorNumber")
//		@Column(name="Address1")
//		@Column(name="Address2")
//		@Column(name="City")
//		@Column(name="ContactNo")
//		@Column(name="Country")
//		@Column(name="CreditTerms")
//		@Column(name="Email")
//		@Column(name="FaxNumber")
//		@Column(name="MISC")
//		@Column(name="ProductCodes")
//		@Column(name="RaiseType")
//		@Column(name="SortKey")
//		@Column(name="VendorName")

		try {
			logger.info("getting vendors from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Vendor tblVendor = new Vendor();
				tblVendor.setVendorNumber(rs.getString("VendorNumber"));
				tblVendor.setAddress1(rs.getString("Address1"));
				tblVendor.setAddress2(rs.getString("Address2"));
				tblVendor.setCity(rs.getString("City"));
				tblVendor.setContactNo(rs.getString("ContactNo"));
				tblVendor.setCountry(rs.getString("Country"));
				tblVendor.setCreditTerms(rs.getObject("CreditTerms") == null ? null : rs.getInt("CreditTerms"));
				tblVendor.setEmail(rs.getString("Email"));
				tblVendor.setFaxNumber(rs.getString("FaxNumber"));
				tblVendor.setMisc(rs.getObject("MISC") == null ? null : rs.getBoolean("MISC"));
				String productCodesStr = rs.getString("ProductCodes");
				if(StringUtils.isNotBlank(productCodesStr)) {
					List<String> productCodes = Arrays.asList(productCodesStr.replaceAll(" ", "").split(";"));
					tblVendor.setProductCodes(productCodes);
				}
				tblVendor.setRaiseType(rs.getString("RaiseType"));
				tblVendor.setSortKey(rs.getString("SortKey"));
				tblVendor.setVendorName(rs.getString("VendorName").trim().replaceAll(" +", " "));
				vendorList.add(tblVendor);
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
		logger.info("size of vendors from mssqldb: {}", vendorList.size());
		return vendorList;
	}

}
