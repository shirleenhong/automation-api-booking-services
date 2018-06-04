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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.InProduct;

@Repository
public class IndiaProductCodeDAOImpl implements ProductCodeDAO<InProduct> {
    private static final Logger logger = LoggerFactory.getLogger(ProductCodeDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<InProduct> listProductCodes() {
        List<InProduct> productCodeList = new ArrayList<>();
        String sql = "SELECT * FROM tblProducts";

        Connection conn = null;

        try {
            logger.info("getting product codes from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
            	InProduct productCode = new InProduct();
                productCode.setProductCode((rs.getObject("Number") == null ? null : rs.getInt("Number")).toString());
                productCode.setDescription(rs.getString("Name"));
                productCode.setType(rs.getString("Type"));
                productCode.setTicketPrefix(rs.getString("TktPrefix"));
                productCode.setGdsCode(rs.getString("GDSCode"));
                productCode.setGst(rs.getObject("GST") == null ? null : rs.getDouble("GST"));
                productCode.setOt1(rs.getObject("OT1") == null ? null : rs.getDouble("OT1"));
                productCode.setOt2(rs.getObject("OT2") == null ? null : rs.getDouble("OT2"));
                productCode.setRequireCdr(rs.getObject("RequireCDR") == null ? null : rs.getBoolean("RequireCDR"));
                productCode.setRequireTicket(rs.getObject("RequireTicket") == null ? null : rs.getBoolean("RequireTicket"));
                productCode.setSuppressMi(rs.getObject("SuppressMI") == null ? null : rs.getBoolean("SuppressMI"));
                productCode.setFeeCode(rs.getObject("FeeCode") == null ? null : rs.getDouble("FeeCode"));
                productCode.setGstFormula(rs.getString("GSTFormula"));
                productCode.setAppMf(rs.getObject("AppMF") == null ? null : rs.getBoolean("AppMF"));
                productCode.setOverridePc(rs.getObject("OverridePC") == null ? null : rs.getBoolean("OverridePC"));
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
