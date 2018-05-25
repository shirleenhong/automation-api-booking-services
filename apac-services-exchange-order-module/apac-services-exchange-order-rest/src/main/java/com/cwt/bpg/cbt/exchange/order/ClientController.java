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
@Api(tags = "Clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@GetMapping(path = "/clients/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls all clients")
	public ResponseEntity<Client> getClient(@PathVariable int id) {

		return new ResponseEntity<>(clientService.getClient(id), HttpStatus.OK);
	}

	@PutMapping(path = "/clients", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Put client")
	@Internal
	public ResponseEntity<Client> putClient(@RequestBody Client client) {

		return new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
	}

	@DeleteMapping(path = "/clients/{id}")
	@ResponseBody
	@ApiOperation(value = "Remove client by id")
	@Internal
	public ResponseEntity<String> removeClient(@PathVariable int id) {

		return new ResponseEntity<>(clientService.delete(id), HttpStatus.OK);
	}

}
