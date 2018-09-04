package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Airline Rules")
public class AirlineRulesController {
	
	@Autowired
	private AirlineRuleService service;

	@GetMapping(path = "/airline-rules")
	@ResponseBody
	@ApiOperation(value = "Pulls airline rules")
	public ResponseEntity<List<AirlineRule>> getAirlineRules() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@PutMapping(path = "/airline-rules")
	@ResponseBody
	@ApiOperation(value = "Pulls airline rules")
	@Internal
	public ResponseEntity<AirlineRule> putAirlineRules(@Valid @RequestBody AirlineRule airlineRule) {
		return new ResponseEntity<>(service.save(airlineRule), HttpStatus.OK);
	}

	@DeleteMapping(path = "/airline-rules/{airlineCode}")
	@ResponseBody
	@ApiOperation(value = "Remove airline rules")
	@Internal
	public ResponseEntity<String> removeAirlineRules(@PathVariable String airlineCode) {
		String deleteResult = service.delete(airlineCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}

}
