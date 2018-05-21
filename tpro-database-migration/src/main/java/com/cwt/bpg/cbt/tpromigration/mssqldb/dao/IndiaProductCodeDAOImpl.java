package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Product;
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
public class IndiaProductCodeDAOImpl implements ProductCodeDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductCodeDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Product> listProductCodes() {
        List<Product> productCodeList = new ArrayList<Product>();
        String sql = "SELECT * FROM tblProducts";

        Connection conn = null;

//		@Column(name="Number")
//		@Column(name="Name")
//		@Column(name="GDSCode")
//		@Column(name="Type")
//		@Column(name="TktPrefix")
//		@Column(name="GST")
//		@Column(name="OT1")
//		@Column(name="OT2")
//		@Column(name="RequireCDR")
//		@Column(name="RequireTicket")
//		@Column(name="SuppressMI")
//		@Column(name="FeeCode")
//		@Column(name="GSTFormula")
//		@Column(name="AppMF")
//		@Column(name="OverridePC")
//		@Column(name="HotelFee")

        try {
            logger.info("getting product codes from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product productCode = new Product();

                productCode.setNumber(rs.getObject("Number") == null ? null : rs.getInt("Number"));
                productCode.setName(rs.getString("Name"));
                productCode.setgDSCode(rs.getString("GDSCode"));
                productCode.setType(rs.getString("Type"));
                productCode.setTktPrefix(rs.getString("TktPrefix"));
                productCode.setGst(rs.getObject("GST") == null ? null : rs.getInt("GST"));
                productCode.setoT1(rs.getObject("OT1") == null ? null : rs.getInt("OT1"));
                productCode.setoT2(rs.getObject("OT2") == null ? null : rs.getInt("OT2"));
                productCode.setRequireCDR(rs.getObject("RequireCDR") == null ? null : rs.getBoolean("RequireCDR"));
                productCode.setRequireTicket(rs.getObject("RequireTicket") == null ? null : rs.getBoolean("RequireTicket"));
                productCode.setSuppressMI(rs.getObject("SuppressMI") == null ? null : rs.getBoolean("SuppressMI"));
                productCode.setFeeCode(rs.getFloat("FeeCode"));
                productCode.setgSTFormula(rs.getString("GSTFormula"));
                productCode.setAppMF(rs.getObject("AppMF") == null ? null : rs.getBoolean("AppMF"));
                productCode.setOverridePC(rs.getObject("OverridePC") == null ? null : rs.getBoolean("OverridePC"));
                productCode.setHotelFee(rs.getObject("HotelFee") == null ? null : rs.getBoolean("HotelFee"));
                productCodeList.add(productCode);
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
        logger.info("size of product codes from mssqldb: {}", productCodeList.size());

        return productCodeList;
    }
}
