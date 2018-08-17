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
import com.cwt.bpg.cbt.exchange.order.model.PassthroughInput;
import com.cwt.bpg.cbt.exchange.order.model.PassthroughOutput;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "Passthrough")
public class PassthroughController {

	@Autowired
	private PassthroughService passService;

	@GetMapping(value = "/passthrough", consumes = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public ResponseEntity<PassthroughOutput> getClient(
			@RequestParam("bookingClass") String bookingClass,
			@RequestParam("airlineCode") String airlineCode,
			@RequestParam("ccVendorCode") String ccVendorCode,
			@RequestParam("countryCode") String countryCode,
			@RequestParam("clientAccountNumber") String clientAccountNumber)
			throws ExchangeOrderNoContentException {

		PassthroughInput input = formPassthroughInput(bookingClass, airlineCode,
				ccVendorCode, countryCode, clientAccountNumber);
		
		return new ResponseEntity<>(passService.getPassThroughType(input), HttpStatus.OK);
	}

	private PassthroughInput formPassthroughInput(String bookingClass, String airlineCode,
			String ccVendorCode, String countryCode, String clientAccountNumber) {
		
		PassthroughInput input = new PassthroughInput();
		input.setBookingClass(bookingClass);
		input.setAirlineCode(airlineCode);
		input.setCcVendorCode(ccVendorCode);
		input.setCountryCode(countryCode);
		input.setClientAccountNumber(clientAccountNumber);
		
		return input;
	}

}
