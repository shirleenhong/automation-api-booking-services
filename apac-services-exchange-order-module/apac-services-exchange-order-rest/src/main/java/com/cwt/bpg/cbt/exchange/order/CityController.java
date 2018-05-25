package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.City;
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
@Api(tags = "City")
public class CityController {

    @Autowired
    private CityService service;

    @GetMapping(path = "/cities")
    @ResponseBody
    @ApiOperation(value = "Pulls cities")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/cities/{cityCode}")
    @ResponseBody
    @ApiOperation(value = "Pulls list of cities for a specific city code")
    public ResponseEntity<List<City>> getCities(
            @PathVariable
            @ApiParam(value = "Country code of the requested market") String cityCode) {
        return new ResponseEntity<>(service.getCities(cityCode), HttpStatus.OK);
    }

    @PutMapping(path = "/cities")
    @ResponseBody
    @ApiOperation(value = "Add city")
    @Internal
    public ResponseEntity<City> putCity(@Valid @RequestBody City city) {
        return new ResponseEntity<>(service.save(city), HttpStatus.OK);
    }

    @DeleteMapping(path = "/cities")
    @ResponseBody
    @ApiOperation(value = "Remove city")
    @Internal
    public ResponseEntity<String> removeCity(@Valid @RequestBody City city) {
        return new ResponseEntity<>(service.delete(city), HttpStatus.OK);
    }

}
