package com.cwt.bpg.cbt.air.transaction;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Air Transaction")
public class AirTransactionController {

	@Autowired
	private AirTransactionService airTransService;

	@Internal
	@GetMapping(value = "/air-transaction", produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns CWT (Non-Passthrough) or Airline (Full Passthrough) based on airline code, "
			+ "booking class, ccvendor code, country code, and client number.")
	public ResponseEntity<AirTransactionOutput> getAirTransaction(
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam("bookingClasses") List<String> bookingClasses,
			@RequestParam("ccVendorCode") String ccVendorCode,
			@RequestParam("ccType") String ccType,
			@RequestParam(value = "clientAccountNumber", required = false) String clientAccountNumber)
			throws AirTransactionNoContentException {

		AirTransactionInput input = formAirTransactionInput(airlineCode, bookingClasses,
				ccVendorCode, ccType, clientAccountNumber);

		return new ResponseEntity<>(airTransService.getAirTransaction(input), HttpStatus.OK);
	}

	@Internal
	@GetMapping(value = "/air-transactions", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Pulls air transactions based on airline code and client number.")
	public ResponseEntity<List<AirTransaction>> getAirTransactions(
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam(value = "clientAccountNumber", required = false) String clientAccountNumber) {

		AirTransactionInput input = formAirTransactionInput(airlineCode, null,
				null, null, clientAccountNumber);
    
		return new ResponseEntity<>(airTransService.getAirTransactionList(input),
				HttpStatus.OK);
	}

	@Internal
	@PutMapping(path = "/air-transactions")
	@ApiOperation(value = "[Maintenance] Save or update Air Transaction")
	@ResponseBody
	public ResponseEntity<AirTransaction> putAirTransaction(@Valid @RequestBody AirTransaction airTransaction) {
		return new ResponseEntity<>(airTransService.save(airTransaction), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/air-transactions")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Remove air transaction")
	public ResponseEntity<String> removeAirTransaction(
			@RequestParam("id") String id) {
		return new ResponseEntity<>(airTransService.delete(id), HttpStatus.OK);
	}

	private AirTransactionInput formAirTransactionInput(String airlineCode,
			List<String> bookingClasses, String ccVendorCode, String ccType,
			String clientAccountNumber) {

		AirTransactionInput input = new AirTransactionInput();
		input.setBookingClasses(bookingClasses);
		input.setAirlineCode(airlineCode);
		input.setCcVendorCode(ccVendorCode);
		input.setCcType(ccType);
		input.setClientAccountNumber(clientAccountNumber);
		return input;
	}

}
