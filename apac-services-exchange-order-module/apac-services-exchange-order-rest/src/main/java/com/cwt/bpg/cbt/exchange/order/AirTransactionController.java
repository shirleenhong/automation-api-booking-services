package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.exception.AirTransactionNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionOutput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@RestController
@Api(tags = "AirTransaction")
public class AirTransactionController {

	@Autowired
	private AirTransactionService airTransService;

	@GetMapping(value = "/air-transaction", produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns CWT (Non-Passthrough) or Airline (Full Passthrough) based on airline code, "
			+ "booking class, ccvendor code, country code, and client number.")
	public ResponseEntity<AirTransactionOutput> getAirTransaction(
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam("bookingClass") String bookingClass,
			@RequestParam("ccVendorCode") String ccVendorCode,
			@RequestParam(value = "countryCode", required = false) String countryCode,
			@RequestParam(value = "clientAccountNumber", required = false) String clientAccountNumber)
			throws AirTransactionNoContentException {

		AirTransactionInput input = formAirTransactionInput(airlineCode, bookingClass,
				ccVendorCode, countryCode, clientAccountNumber);
		
		return new ResponseEntity<>(airTransService.getAirTransaction(input), HttpStatus.OK);
	}

	@Internal
	@GetMapping(value = "/air-transactions", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Pulls air transactions based on airline code and client number.")
	public ResponseEntity<List<AirTransaction>> getAirTransactions(
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam("clientAccountNumber") String clientAccountNumber)
			throws AirTransactionNoContentException {

		AirTransactionInput input = formAirTransactionInput(airlineCode, null,
				null, null, clientAccountNumber);

		return new ResponseEntity<>(airTransService.getAirTransactionList(input),
				HttpStatus.OK);
	}

	@PutMapping(path = "/air-transaction")
	@ApiOperation(value = "Save Air Transaction")
	@ResponseBody
	public ResponseEntity<AirTransaction> putAirTransaction(@Valid @RequestBody AirTransaction airTrans) {
		return new ResponseEntity<>(airTransService.save(airTrans), HttpStatus.OK);
	}

	private AirTransactionInput formAirTransactionInput(String airlineCode, String bookingClass,
			String ccVendorCode, String countryCode, String clientAccountNumber) {
		
		AirTransactionInput input = new AirTransactionInput();
		input.setBookingClass(bookingClass);
		input.setAirlineCode(airlineCode);
		input.setCcVendorCode(ccVendorCode);
		input.setCountryCode(countryCode);
		input.setClientAccountNumber(clientAccountNumber);
		
		return input;
	}

}
