package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.exchange.order.model.Remark;
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
public class RemarkDAOIMpl implements RemarkDAO {
    private static final Logger logger = LoggerFactory.getLogger(RemarkDAOIMpl.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Remark> getRemarks() {
        List<Remark> remarks = new ArrayList<>();
        String sql = "SELECT * FROM tblProductRemarks";

        Connection conn = null;

        try {
            logger.info("getting remarks from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Remark remark = new Remark();
                remark.setProductType(rs.getString("ProductType"));
                remark.setRemarkType(rs.getString("RmkType"));
                remark.setText(rs.getString("Text"));
                remarks.add(remark);
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
        logger.info("size of remarks from mssqldb: {}", remarks.size());
        return remarks;
    }
}
