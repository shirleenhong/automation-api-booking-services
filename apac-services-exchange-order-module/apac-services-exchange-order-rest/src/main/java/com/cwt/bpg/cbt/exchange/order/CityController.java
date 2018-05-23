package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "City")
public class CityController {

    @Autowired
    private CityService service;

    @Internal
    @GetMapping(path = "/cities")
    @ResponseBody
    @ApiOperation(value = "Pulls cities")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
