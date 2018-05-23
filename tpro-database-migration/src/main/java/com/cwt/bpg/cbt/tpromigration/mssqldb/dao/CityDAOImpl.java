package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.City;
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
public class CityDAOImpl implements CityDAO {
    private static final Logger logger = LoggerFactory.getLogger(CityDAOImpl.class);

    @Autowired
    private DataSource dataSource;

    @SuppressWarnings("unchecked")
    @Override
    public List<City> getCities() {
        List<City> cities = new ArrayList<City>();
        String sql = "SELECT * FROM CWTStandardData.dbo.tblCity";

        Connection conn = null;
//      @Column(name="City")->name
//		@Column(name="CityCode")->code
//		@Column(name="RegionCode")
//		@Column(name="CountryCode")
//		@Column(name="AirportCode")

        try {
            logger.info("getting cities from mssqldb");
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                City city = new City();
                city.setName(rs.getString("City"));
                city.setCode(rs.getString("CityCode"));
                city.setRegionCode(rs.getString("RegionCode"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setAirportCode(rs.getString("AirportCode"));
                cities.add(city);
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
        logger.info("size of cities from mssqldb: {}", cities.size());
        return cities;
    }
}
