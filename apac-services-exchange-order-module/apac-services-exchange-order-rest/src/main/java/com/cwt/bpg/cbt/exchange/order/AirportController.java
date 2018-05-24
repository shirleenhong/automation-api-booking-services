package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.Airport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Airport")
public class AirportController {

    @Autowired
    private AirportService service;

    @Internal
    @GetMapping(path = "/airports")
    @ResponseBody
    @ApiOperation(value = "Pulls airports")
    public ResponseEntity<List<Airport>> getAirports() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/airports/{airportCode}")
    @ResponseBody
    @ApiOperation(value = "Pulls airport for a specific airport code")
    public ResponseEntity<Airport> getAirport(
            @PathVariable
            @ApiParam(value = "Airport Code") String airportCode) {
        return new ResponseEntity<>(service.getAirport(airportCode), HttpStatus.OK);
    }

    @PutMapping(path = "/airports")
    @ResponseBody
    @ApiOperation(value = "Add airport")
    @Internal
    public ResponseEntity<Airport> putAirport(@Valid @RequestBody Airport airport) {
        return new ResponseEntity<>(service.save(airport), HttpStatus.OK);
    }

    @DeleteMapping(path = "/airports/{airportCode}")
    @ResponseBody
    @ApiOperation(value = "Remove airport")
    @Internal
    public ResponseEntity<String> removeAirport(@PathVariable
                                                @ApiParam(value = "Airport Code") String airportCode) {
        return new ResponseEntity<>(service.delete(airportCode), HttpStatus.OK);
    }

}
