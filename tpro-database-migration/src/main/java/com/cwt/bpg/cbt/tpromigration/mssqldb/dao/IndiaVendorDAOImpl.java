package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
public class IndiaVendorDAOImpl implements VendorDAO{

    private static final Logger logger = LoggerFactory.getLogger(VendorDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @SuppressWarnings("unchecked")
    @Override
    public List<Vendor> listVendors() {
        List<Vendor> vendorList = new ArrayList<Vendor>();
        String sql = "SELECT * FROM (SELECT DISTINCT a.*, SUBSTRING(\n" +
                "(SELECT ';' + CAST(productid AS varchar)  AS [text()]\n" +
                "FROM tblVendorProduct b\n" +
                "WHERE b.VendorNumber COLLATE SQL_LATIN1_GENERAL_CP1_CI_AS = a.VendorNumber \n" +
                "ORDER BY a.vendornumber\n" +
                "FOR XML PATH ('')\n" +
                "), 2, 1000) [ProductID] \n" +
                "FROM tblVendors a ) x WHERE x.[ProductID] is not null";

        Connection conn = null;

        try {
            logger.info("getting vendors from mssqldb india");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Vendor tblVendor = new Vendor();
                tblVendor.setVendorNumber(rs.getString("VendorNumber"));
                tblVendor.setInterfaceNumber(rs.getString("InterfaceNumber"));

                String data = rs.getString("VendorName");
                Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
                Matcher m = p.matcher(data);
                StringBuffer buf = new StringBuffer(data.length());
                while (m.find()) {
                    String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
                    m.appendReplacement(buf, Matcher.quoteReplacement(ch));
                }
                m.appendTail(buf);
                System.out.println(buf);

                tblVendor.setVendorName(buf.toString().trim().replaceAll(" +", " "));


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

                String productCodesStr = rs.getString("ProductID");
				if(StringUtils.isNotBlank(productCodesStr)) {
					List<String> productCodes = Arrays.asList(productCodesStr.replaceAll(" ", "").split(";"));
					tblVendor.setProductCodes(productCodes);
				}
                
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
