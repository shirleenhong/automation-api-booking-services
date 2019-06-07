package com.cwt.bpg.cbt.air.contract;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.air.contract.model.AirContract;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Air Contract")
public class AirContractController {

	@Autowired
	private AirContractService airContractService;

	@GetMapping(value = "/air-contract/{countryCode}/{airlineCode}/{clientAccountNumber}", produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ApiOperation(value = "Returns Air Contract, with BCODE, given country code, "
			+ "airline code, and client account number.")
	@ResponseBody
	public ResponseEntity<AirContract> getAirContract(
			@PathVariable("countryCode") @ApiParam("2-character country code") String countryCode,
			@PathVariable("airlineCode") @ApiParam("2-character airline code") String airlineCode,
			@PathVariable("clientAccountNumber") @ApiParam("Client account number") String clientAccountNumber) {

		return new ResponseEntity<>(airContractService.get(countryCode, airlineCode, clientAccountNumber), HttpStatus.OK);
	}

	@GetMapping(value = "/air-contract/{id}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ApiOperation(value = "[Maintenance] Returns Air Contract record given an id.")
	@ResponseBody
	public ResponseEntity<AirContract> getAirContract(
			@PathVariable("id") @ApiParam("Air Contract ID") String id) {

		return new ResponseEntity<>(airContractService.get(id), HttpStatus.OK);
	}

	@PutMapping(path = "/air-contract")
	@ApiOperation(value = "[Maintenance] Saves (inserts/updates) Air Contract record.")
	@ResponseBody
	public ResponseEntity<List<AirContract>> putAirContract(@Valid @RequestBody List<AirContract> airContracts) {
		return new ResponseEntity<>(airContractService.save(airContracts), HttpStatus.OK);
	}

	@DeleteMapping(path = "/air-contract/{id}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes Air Contract record given an id.")
	public ResponseEntity<String> removeAirContract(
			@PathVariable("id") @ApiParam("Air Contract ID") String id) {
		String deleteResult = airContractService.delete(id);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}
}
