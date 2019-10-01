package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.FlatTransactionFee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Clients")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private FlatTransactionFeeService clientTransactionFeeService;
	
	@GetMapping(path = "/clients", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Returns all clients.")
	public ResponseEntity<List<Client>> getAllClients() {

		return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/clients/{clientAccountNumber}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns client by client account number.")
	public ResponseEntity<Client> getClient(@PathVariable String clientAccountNumber) {

		return new ResponseEntity<>(clientService.getClient(clientAccountNumber), HttpStatus.OK);
	}

	@PutMapping(path = "/clients", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Saves (inserts/updates) client.")
	public ResponseEntity<Client> putClient(@RequestBody Client client) {
		Client updatedClient = clientService.save(client);
		return new ResponseEntity<>(updatedClient, HttpStatus.OK);
	}

	@DeleteMapping(path = "/clients/{clientAccountNumber}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes client by client account number.")
	public ResponseEntity<String> removeClient(@PathVariable String clientAccountNumber) {
		String deleteResult = clientService.delete(clientAccountNumber);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}

	@Internal
	@GetMapping(path = "/clients/{clientAccountNumber}/transaction-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Returns client transaction fee.")
	public ResponseEntity<FlatTransactionFee> getClientTransactionFee(@PathVariable String clientAccountNumber)
	{
		FlatTransactionFee transactionFee = clientTransactionFeeService.getTransactionFee(clientAccountNumber);
		HttpStatus status = (transactionFee == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		return new ResponseEntity<>(transactionFee, status);
	}

	@Internal
	@PutMapping(path = "/clients/{clientAccountNumber}/transaction-fees", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
		MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Saves (inserts/updates) client transaction fees.")
	public ResponseEntity<FlatTransactionFee> putClientTransactionFee(@PathVariable String clientAccountNumber, @RequestBody FlatTransactionFee clientTransactionFee)
	{
		clientTransactionFee.setClientAccountNumber(clientAccountNumber);
		clientTransactionFee.setId(clientAccountNumber);
		FlatTransactionFee updatedClient = clientTransactionFeeService.save(clientTransactionFee);
		return new ResponseEntity<>(updatedClient, HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/clients/{clientAccountNumber}/transaction-fees")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes client transaction fees.")
	public ResponseEntity<String> removeClientTransactionFee(@PathVariable
			@ApiParam(value = "ClientTransactionFee clientAccountNumber to delete") String clientAccountNumber)
	{
		return new ResponseEntity<>(clientTransactionFeeService.delete(clientAccountNumber), HttpStatus.OK);
	}
}
