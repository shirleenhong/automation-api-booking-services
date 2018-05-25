package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Airport;

import java.util.List;

public interface AirportDAO {

    List<Airport> getAirports();
}
