package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

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

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Merchant Fee")
public class MerchantFeeController {

	@Autowired
	private MerchantFeeService service;
	
	@Internal
	@GetMapping(
			path = "/merchant-fee",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls merchant fees")
	public ResponseEntity<List<MerchantFee>> getMerchantFees() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@Internal
	@GetMapping(
			path = "/merchant-fee/{countryCode}/{clientAccountNumber}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls merchant fee based on a [country code | client account number] combination")
	public ResponseEntity<MerchantFee> getMerchantFee(@PathVariable String countryCode,
			@PathVariable String clientAccountNumber) {

		return new ResponseEntity<>(service.getMerchantFee(countryCode.toUpperCase(), clientAccountNumber), HttpStatus.OK);
	}

	@Internal
	@PutMapping(
			path = "/merchant-fee",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves or updates merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> updateMerchantFee(@RequestBody MerchantFee merchantFee) {
		merchantFee.setCountryCode(merchantFee.getCountryCode().toUpperCase());
		MerchantFee mf = service.putMerchantFee(merchantFee);
		return new ResponseEntity<>(mf, HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/merchant-fee")
	@ResponseBody
	@ApiOperation(value = "Remove merchant fee configuration by id")
	public ResponseEntity<String> removeMerchantFee(@RequestParam("id") String id) {
		String deleteResult = service.remove(id);
		return new ResponseEntity<>(deleteResult, HttpStatus.OK);
	}

}
