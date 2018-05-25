package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Airport;
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
public class AirportDAOImpl implements AirportDAO {
    private static final Logger logger = LoggerFactory.getLogger(AirportDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @SuppressWarnings("unchecked")
    @Override
    public List<Airport> getAirports() {
        List<Airport> airports = new ArrayList<Airport>();
        String sql = "SELECT * FROM CWTStandardData.dbo.tblCity";

        Connection conn = null;

        try {
            logger.info("getting airports from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Airport airport = new Airport();
                airport.setName(rs.getString("Airport"));
                airport.setCode(rs.getString("AirportCode"));
                airport.setCityCode(rs.getString("CityCode"));
                airport.setRegionCode(rs.getString("RegionCode"));
                airport.setCountryCode(rs.getString("CountryCode"));
                airports.add(airport);
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
        logger.info("size of airports from mssqldb: {}", airports.size());
        return airports;
    }
}
