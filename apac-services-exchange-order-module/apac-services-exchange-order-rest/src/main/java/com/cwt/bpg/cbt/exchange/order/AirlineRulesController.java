package com.cwt.bpg.cbt.exchange.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Airline Rules")
public class AirlineRulesController {
	
	@GetMapping(path="/airline-rules")
	@ResponseBody
    @ApiOperation(value = "Pulls airline rules")
	public ResponseEntity<?> getAirlineRules() {		
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
