package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.Client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Client Pricing")
public class ClientPricingController {

	@Autowired
	private ClientPricingService clientPricingService;
	
	@GetMapping(path = "/client-pricing/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls client pricing")
	public ResponseEntity<Client> getClient(@PathVariable int id) {

		return new ResponseEntity<>(clientPricingService.getClient(id), HttpStatus.OK);
	}

	@PutMapping(path = "/client-pricing", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Put client pricing")
	@Internal
	public ResponseEntity<Client> putClientPricing(@RequestBody Client client) {

		return new ResponseEntity<>(clientPricingService.save(client), HttpStatus.OK);
	}

	@DeleteMapping(path = "/client-pricing/{id}")
	@ResponseBody
	@ApiOperation(value = "Remove client pricing")
	@Internal
	public ResponseEntity<String> removeClientPricing(@PathVariable int id) {

		return new ResponseEntity<>(clientPricingService.delete(id), HttpStatus.OK);
	}

}
