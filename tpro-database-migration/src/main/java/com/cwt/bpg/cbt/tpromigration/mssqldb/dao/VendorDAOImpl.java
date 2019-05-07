package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang33.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.MerchantFeeAbsorb;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;


@Repository
public class VendorDAOImpl implements VendorDAO {

    private static final Logger logger = LoggerFactory.getLogger(VendorDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<MerchantFeeAbsorb> listNoMerchantFee() {
    	List<MerchantFeeAbsorb> listNoMerchantFee = new ArrayList<>();
    	String sql = "SELECT * FROM tblOSNoMF";
    	
    	Connection conn = null;
    	
    	try {
            logger.info("getting tblOSNoMF from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MerchantFeeAbsorb mf = new MerchantFeeAbsorb();
            	mf.setProductCode(rs.getString("ProductCode"));
            	mf.setVendorNumber(rs.getString("VendorNumber"));
             
            	listNoMerchantFee.add(mf);
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
        logger.info("size of tblOSNoMF from mssqldb: {}", listNoMerchantFee.size());
        return listNoMerchantFee;
    	
    }
    @Override
    public List<Vendor> listVendors() {
    	
        List<Vendor> vendorList = new ArrayList<>();
        String sql = "SELECT * FROM tblVendors WHERE ProductCodes is not null and len([ProductCodes]) > 0";

        Connection conn = null;

        try {
            logger.info("getting vendors from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vendor tblVendor = new Vendor();
                tblVendor.setCode(rs.getString("VendorNumber"));
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
                if (StringUtils.isNotBlank(productCodesStr)) {
                    List<String> productCodes = Arrays.asList(productCodesStr.replaceAll(" ", "").split(";"));
                    tblVendor.setProductCodes(productCodes);
                }
                String raiseType = rs.getString("RaiseType");
                if(raiseType == null || raiseType.isEmpty()) {
                	tblVendor.setRaiseType(null);	
                }
                else {
                	tblVendor.setRaiseType(raiseType);
                }
                
                tblVendor.setSortKey(rs.getString("SortKey"));
                tblVendor.setName(rs.getString("VendorName").trim().replaceAll(" +", " "));
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

	@Override
	public List<ContactInfo> listVendorContactInfo() {
		return null;
	}

}
