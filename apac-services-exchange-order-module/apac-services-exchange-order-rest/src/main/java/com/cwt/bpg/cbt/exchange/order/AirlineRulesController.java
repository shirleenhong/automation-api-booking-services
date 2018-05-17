package com.cwt.bpg.cbt.exchange.order;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Airline Rules")
public class AirlineRulesController {

	@GetMapping(path = "/airline-rules")
	@ResponseBody
	@ApiOperation(value = "Pulls airline rules")
	public ResponseEntity<?> getAirlineRules() {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PutMapping(path = "/airline-rules")
	@ResponseBody
	@ApiOperation(value = "Pulls airline rules")
	@Internal
	public ResponseEntity<?> putAirlineRules(Supplier<?> supplier) {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/airline-rules")
	@ResponseBody
	@ApiOperation(value = "Remove airline rules")
	@Internal
	public ResponseEntity<?> removeAirlineRules(Supplier<?> supplier) {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
