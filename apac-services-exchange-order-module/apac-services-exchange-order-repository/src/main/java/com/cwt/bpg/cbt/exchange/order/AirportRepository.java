package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Airport;

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