package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.mongodb.WriteResult;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AirportRepository extends CommonRepository<Airport, String> {

    public static final String KEY_COLUMN = "code";

    public AirportRepository() {
        super(Airport.class, KEY_COLUMN);
    }

    public Airport getAirport(String airportCode) {
        return morphia.getDatastore().createQuery(Airport.class).field(KEY_COLUMN).equal(airportCode).get();
    }
}