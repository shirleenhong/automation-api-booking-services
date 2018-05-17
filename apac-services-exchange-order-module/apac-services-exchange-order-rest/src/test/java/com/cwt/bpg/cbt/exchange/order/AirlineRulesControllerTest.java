package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AirlineRulesControllerTest {
	
	private AirlineRulesController airlineRulesController = new AirlineRulesController();

	@Test
	public void canGetAirlineRules() {
		ResponseEntity<?> airlineRules = airlineRulesController.getAirlineRules();
		assertEquals(HttpStatus.OK,  airlineRules.getStatusCode());
	}

}
