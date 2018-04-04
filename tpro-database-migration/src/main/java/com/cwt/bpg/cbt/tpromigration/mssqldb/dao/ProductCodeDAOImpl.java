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

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ProductCode;

@Repository
public class ProductCodeDAOImpl implements ProductCodeDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProductCodeDAOImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<ProductCode> listProductCodes() {
		List<ProductCode> productCodeList = new ArrayList<ProductCode>();
		String sql = "SELECT * FROM tblProductCodes";
		
		Connection conn = null;

//		@Column(name="ProductCode")
//		@Column(name="Description")
//		@Column(name="EnableCCFOP")
//		@Column(name="FullComm")
//		@Column(name="GST")
//		@Column(name="MI")
//		@Column(name="SortKey")
//		@Column(name="TktNo")
//		@Column(name="TktPrefix")
//		@Column(name="Type")

		try {
			logger.info("getting product codes from mssqldb");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductCode productCode = new ProductCode();
				productCode.setProductCode(rs.getString("ProductCode"));
				productCode.setDescription(rs.getString("Description"));
				productCode.setEnableCCFOP(rs.getObject("EnableCCFOP") == null ? null : rs.getBoolean("EnableCCFOP"));
				productCode.setFullComm(rs.getObject("FullComm") == null ? null : rs.getBoolean("FullComm"));
				productCode.setGst(rs.getObject("GST") == null ? null : rs.getInt("GST"));
				productCode.setMi(rs.getObject("MI") == null ? null : rs.getBoolean("MI"));
				productCode.setSortKey(rs.getString("SortKey"));
				productCode.setTktNo(rs.getObject("TktNo") == null ? null : rs.getBoolean("TktNo"));
				productCode.setTktPrefix(rs.getString("TktPrefix"));
				productCode.setType(rs.getString("Type"));
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
