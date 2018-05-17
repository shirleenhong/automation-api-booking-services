package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	
	@Test
	public void canPutAirlineRules() {
		ResponseEntity<?> client = airlineRulesController.putAirlineRules(ArrayList::new);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}
	
	@Test
	public void canRemoveAirlineRules() {
		ResponseEntity<?> client = airlineRulesController.removeAirlineRules(ArrayList::new);
		assertEquals(HttpStatus.OK, client.getStatusCode());
	}

}
