package com.cwt.bpg.cbt.exchange.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeOrderController {

	@GetMapping(path="/products")
	public @ResponseBody ResponseEntity<String> getProducts(@RequestParam String countryCode) {
		return new ResponseEntity<>("Products", HttpStatus.OK);
	}
}
