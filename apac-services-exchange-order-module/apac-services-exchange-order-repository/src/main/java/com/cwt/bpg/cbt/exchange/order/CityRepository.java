package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
import com.mongodb.WriteResult;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityRepository extends CommonRepository<City, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityRepository.class);

    public static final String KEY_COLUMN = "code";

    public CityRepository() {
        super(City.class, KEY_COLUMN);
    }

    public List<City> getCities(String cityCode) {

        List<City> cities = new ArrayList<>();
        try {

            cities = morphia.getDatastore().createQuery(City.class)
                    .field("code")
                    .equal(cityCode).asList();

        } catch (Exception e) {
            LOGGER.error("Unable to parse product list for {} {}", cityCode, e.getMessage());
        }


        return cities;
    }

    public String remove(City city) {
        Query<City> query = morphia.getDatastore().createQuery(City.class)
                .field("code")
                .equal(city.getCode())
                .field("airportCode").equal(city.getAirportCode());

        WriteResult delete = morphia.getDatastore().delete(query);

        LoggerFactory.getLogger(City.class).info("Delete Result: {}", delete);

        return delete.toString();
    }
}
