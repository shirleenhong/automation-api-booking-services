package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionOutput;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "AirTransaction")
public class AirTransactionController {

	@Autowired
	private AirTransactionService airTransService;

	@GetMapping(value = "/air-transaction", consumes = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public ResponseEntity<AirTransactionOutput> getClient(
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam("bookingClass") String bookingClass,
			@RequestParam("ccVendorCode") String ccVendorCode,
			@RequestParam("countryCode") String countryCode,
			@RequestParam("clientAccountNumber") String clientAccountNumber)
			throws ExchangeOrderNoContentException {

		AirTransactionInput input = formAirTransactionInput(airlineCode, bookingClass,
				ccVendorCode, countryCode, clientAccountNumber);
		
		return new ResponseEntity<>(airTransService.getPassthroughType(input), HttpStatus.OK);
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
