package com.cwt.bpg.cbt.service.fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

@RestController
@RequestMapping("/service-fee")
public class ServiceFeeController {
	
	@Autowired
	private ServiceFeeApi serviceFee;
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public @ResponseBody ResponseEntity<PriceBreakdown> calculatePriceInput(@RequestBody PriceCalculationInput input) {
		return new ResponseEntity<>(serviceFee.calculate(input), HttpStatus.OK);
	}
}
