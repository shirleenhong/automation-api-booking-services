package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.Product;

@RestController
public class ProductsController {
	
	@Autowired
	ProductsApi exchangeOrderApi;

	@GetMapping(path="/products/{countryCode}")
	@ResponseBody
	public ResponseEntity<List<Product>> getProducts(
			@PathVariable String countryCode) {		
		
		return new ResponseEntity<>(exchangeOrderApi.getProducts(countryCode), HttpStatus.OK);
	}
	
}
