package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.exchange.order.model.Client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping(path = "/clients/{clientAccountNumber}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls all clients")
	public ResponseEntity<Client> getClient(@PathVariable String clientAccountNumber) {

		return new ResponseEntity<>(clientService.getClient(clientAccountNumber), HttpStatus.OK);
	}

	@PutMapping(path = "/clients", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Put client")
	public ResponseEntity<Client> putClient(@RequestBody Client client) {

		return new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
	}

	@DeleteMapping(path = "/clients/{clientAccountNumber}")
	@ResponseBody
	@ApiOperation(value = "Remove client by clientAccountNumber")
	public ResponseEntity<String> removeClient(@PathVariable String clientAccountNumber) {
		String deleteResult = clientService.delete(clientAccountNumber);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}

}
