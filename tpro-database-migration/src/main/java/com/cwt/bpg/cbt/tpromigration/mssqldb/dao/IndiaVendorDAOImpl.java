package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class IndiaVendorDAOImpl implements VendorDAO{

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
//		@Column(name="InterfaceNumber")
//		@Column(name="VendorName")
//		@Column(name="ContactPerson")
//		@Column(name="Address")
//		@Column(name="City")
//		@Column(name="PostalCode")
//		@Column(name="State")
//		@Column(name="Country")
//		@Column(name="VendorType")
//		@Column(name="RequireEO")
//		@Column(name="RequireAdvanced")
//		@Column(name="Misc")
//		@Column(name="HotelFee")

        try {
            logger.info("getting vendors from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vendor tblVendor = new Vendor();
                tblVendor.setVendorNumber(rs.getString("VendorNumber"));
                tblVendor.setInterfaceNumber(rs.getString("InterfaceNumber"));
                tblVendor.setVendorName(rs.getString("VendorName").trim().replaceAll(" +", " "));
                tblVendor.setContactPerson(rs.getString("ContactPerson"));
                tblVendor.setAddress(rs.getString("Address"));
                tblVendor.setCity(rs.getString("City"));
                tblVendor.setPostalCode(rs.getString("PostalCode"));
                tblVendor.setState(rs.getString("State"));
                tblVendor.setCountry(rs.getString("Country"));
                tblVendor.setVendorType(rs.getString("VendorType"));
                tblVendor.setRequireEO(rs.getObject("RequireEO") == null ? null : rs.getBoolean("RequireEO"));
                tblVendor.setRequireAdvanced(rs.getObject("RequireAdvanced") == null ? null : rs.getBoolean("RequireAdvanced"));
                tblVendor.setMisc(rs.getObject("Misc") == null ? null : rs.getBoolean("MISC"));
                tblVendor.setHotelFee(rs.getString("HotelFee"));
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
