package com.cwt.bpg.cbt.service.fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

@RestController
@RequestMapping("/servicefee")
public class ServiceFeeController {
	
	private ServiceFeeApi serviceFee;
	
	@Autowired
	public ServiceFeeController(ServiceFeeApi serviceFee) {
		this.serviceFee = serviceFee;
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<PriceBreakdown> calculatePriceInput(@RequestBody PriceCalculationInput input) {
		PriceBreakdown sf = new PriceBreakdown();
		return new ResponseEntity<>(sf, HttpStatus.OK);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody PriceBreakdown xxx() {
		PriceBreakdown sf = new PriceBreakdown();
		return sf;
	}

}
