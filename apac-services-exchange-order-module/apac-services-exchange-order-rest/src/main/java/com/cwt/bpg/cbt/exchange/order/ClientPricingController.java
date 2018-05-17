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
@Api(tags = "Client Pricing")
public class ClientPricingController {
	
	@GetMapping(path="/client-pricing/{id}")
	@ResponseBody
    @ApiOperation(value = "Pulls client pricing")
	public ResponseEntity<?> getClient(@PathVariable int id) {		
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
