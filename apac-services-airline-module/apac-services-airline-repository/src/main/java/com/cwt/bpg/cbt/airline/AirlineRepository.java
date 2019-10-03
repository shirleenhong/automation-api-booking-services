package com.cwt.bpg.cbt.airline;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.airline.model.Airline;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AirlineRepository extends CommonRepository<Airline, String>
{

    private static final String IATA_CODE = "iataCode";
    private static final String TICKETING_CODE = "ticketingCode";

    public AirlineRepository()
    {
        super(Airline.class, IATA_CODE);
    }

    public List<Airline> getAirlines(String airlineCode)
    {
        return getAirlines(airlineCode, null);
    }

    public List<Airline> getAirlinesByTicketingCode(String ticketingCode)
    {
        return getAirlines(null, ticketingCode);
    }
    
    private List<Airline> getAirlines(String airlineCode, String ticketingCode)
    {
        final Query<Airline> query = morphia.getDatastore().createQuery(Airline.class);
        if (StringUtils.isNotBlank(airlineCode))
        {
            query.field(IATA_CODE).equalIgnoreCase(airlineCode);
        }
        if (StringUtils.isNotBlank(ticketingCode))
        {
            query.field(TICKETING_CODE).equalIgnoreCase(ticketingCode);
        }
        return query.asList();
    }

}
