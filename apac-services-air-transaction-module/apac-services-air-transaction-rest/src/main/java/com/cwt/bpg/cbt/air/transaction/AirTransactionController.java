package com.cwt.bpg.cbt.air.transaction;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.air.transaction.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Air Transaction")
public class AirTransactionController {

	@Autowired
	private AirTransactionService airTransService;

	@GetMapping(value = "/air-transactions", produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns passthroughType value: CWT (Non-Passthrough) or Airline (Full Passthrough) based on "
			+ "country code, airline code, booking class(es), ccvendor code, cc type. Client Account Number is optional.")
	public ResponseEntity<AirTransactionOutput> getAirTransaction(
			@RequestParam("countryCode") @ApiParam(value="2-character country code", required = true) String countryCode,
			@RequestParam("airlineCode") @ApiParam(value="2-character airline code", required = true) String airlineCode,
			@RequestParam("bookingClasses") List<String> bookingClasses,
			@RequestParam("ccVendorCode") @ApiParam(value="e.g. MC, VI", required = true) String ccVendorCode,
			@RequestParam("ccType") @ApiParam(value="e.g. UATP, NRCC", required = true)  String ccType,
			@RequestParam(value = "clientAccountNumber", required = false) String clientAccountNumber)
			throws AirTransactionNoContentException {

		AirTransactionInput input = formAirTransactionInput(countryCode, airlineCode, bookingClasses,
				ccVendorCode, ccType, clientAccountNumber);

		return new ResponseEntity<>(airTransService.getAirTransaction(input), HttpStatus.OK);
	}

	@GetMapping(value = "/air-transactions/{countryCode}/{airlineCode}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Pulls air transactions based on country code, airline code and client account number.")
	public ResponseEntity<List<AirTransaction>> getAirTransactions(
			@PathVariable("countryCode") @ApiParam("2-character country code") String countryCode,
			@PathVariable("airlineCode") @ApiParam("2-character airline code") String airlineCode,
			@RequestParam(value = "clientAccountNumber", required = false) String clientAccountNumber) {

		AirTransactionInput input = formAirTransactionInput(countryCode, airlineCode, null,
				null, null, clientAccountNumber);
    
		return new ResponseEntity<>(airTransService.getAirTransactionList(input),
				HttpStatus.OK);
	}

	@PutMapping(path = "/air-transactions")
	@ApiOperation(value = "[Maintenance] Saves (inserts/updates) Air Transaction.")
	@ResponseBody
	public ResponseEntity<List<AirTransaction>> putAirTransaction(@Valid @RequestBody List<AirTransaction> airTransaction) {
		return new ResponseEntity<>(airTransService.save(airTransaction), HttpStatus.OK);
	}

	@DeleteMapping(path = "/air-transactions/{id}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes Air Transaction.")
	public ResponseEntity<String> removeAirTransaction(
			@PathVariable("id") String id) {
		String deleteResult = airTransService.delete(id);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}

	private AirTransactionInput formAirTransactionInput(String countryCode,String airlineCode,
			List<String> bookingClasses, String ccVendorCode, String ccType,
			String clientAccountNumber) {

		AirTransactionInput input = new AirTransactionInput();
		input.setBookingClasses(bookingClasses);
		input.setAirlineCode(airlineCode);
		input.setCcVendorCode(ccVendorCode);
		input.setCcType(ccType);
		input.setClientAccountNumber(clientAccountNumber);
		input.setCountryCode(countryCode);
		return input;
	}

}
