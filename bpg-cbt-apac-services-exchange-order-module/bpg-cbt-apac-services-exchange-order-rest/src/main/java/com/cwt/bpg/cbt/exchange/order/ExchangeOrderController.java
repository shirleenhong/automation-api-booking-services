package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.Product;

@RestController
public class ExchangeOrderController {
	
	@Autowired
	ExchangeOrderApi exchangeOrderApi;

	@GetMapping(path="/products")
	public @ResponseBody ResponseEntity<List<Product>> getProducts(@RequestParam String countryCode) {
		
		
		return new ResponseEntity<List<Product>>(exchangeOrderApi.getProducts(countryCode), HttpStatus.OK);
	}
	
}
