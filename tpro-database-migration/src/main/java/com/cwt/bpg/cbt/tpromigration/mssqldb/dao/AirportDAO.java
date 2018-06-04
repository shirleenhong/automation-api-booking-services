package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.Airport;

public interface AirportDAO {

    List<Airport> getAirports();
}
