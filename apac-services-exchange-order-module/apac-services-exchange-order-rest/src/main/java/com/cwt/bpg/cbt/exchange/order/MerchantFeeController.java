package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	@GetMapping(path = "/merchant/{countryCode}/{clientAccountNumber}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(
			value = "Pulls merchant fee based on a [country code | client account number] combination")
	public ResponseEntity<MerchantFee> getMerchantFee(@PathVariable String countryCode,
			@PathVariable String clientAccountNumber) {

		return new ResponseEntity<>(service.getMerchantFee(countryCode, clientAccountNumber), HttpStatus.OK);
	}

	@Internal
	@PutMapping(path = "/merchant", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Updates merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> updateMerchantFee(@RequestBody MerchantFee merchantFee) {

		return new ResponseEntity<>(service.putMerchantFee(merchantFee), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/merchant", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "remove merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> removeMerchantFee(@RequestBody MerchantFee merchantFee) {

		return new ResponseEntity<>(service.remove(merchantFee), HttpStatus.OK);
	}

}
