package com.cwt.bpg.cbt.airline;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.airline.model.Airline;
import com.cwt.bpg.cbt.documentation.annotation.Internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Airline")
public class AirlineController
{

    @Autowired
    private AirlineService airlineService;

    @GetMapping(value = "/airlines", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Returns list of all Airlines.")
    public ResponseEntity<List<Airline>> getAirlines(@RequestParam(name = "ticketingCode", required = false) String ticketingCode)
    {
        if (StringUtils.isNotBlank(ticketingCode))
        {
            final List<Airline> airlines = airlineService.getAirlinesByTicketingCode(ticketingCode);
            return new ResponseEntity<>(airlines, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(airlineService.getAirlines(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/airlines/{airlineCode}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Returns Airline given an airline code.")
    public ResponseEntity<Airline> getAirline(@PathVariable("airlineCode") @ApiParam("2-character IATA code") String airlineCode)
    {
        final Airline airline = airlineService.getAirline(airlineCode);
        return new ResponseEntity<>(airline, airline != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @Internal
    @PutMapping(path = "/airlines")
    @ApiOperation(value = "[Maintenance] Saves (inserts/updates) Airline.")
    @ResponseBody
    public ResponseEntity<Airline> putAirline(@Valid @RequestBody Airline airline)
    {
        return new ResponseEntity<>(airlineService.save(airline), HttpStatus.OK);
    }

    @Internal
    @DeleteMapping(path = "/airlines/{airlineCode}")
    @ResponseBody
    @ApiOperation(value = "[Maintenance] Deletes Airline given an airline code.")
    public ResponseEntity<String> removeAirline(
            @PathVariable("airlineCode") @ApiParam("2-character IATA code") String airlineCode)
    {
        String deleteResult = airlineService.delete(airlineCode);
        HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(deleteResult, status);
    }

}
