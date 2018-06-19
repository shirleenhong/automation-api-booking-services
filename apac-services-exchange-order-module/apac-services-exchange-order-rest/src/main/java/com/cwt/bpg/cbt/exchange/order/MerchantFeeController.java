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
	private MerchantFeeService exchangeOrderService;

	@Internal
	@GetMapping(path = "/merchant/{countryCode}/{profileName}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(
			value = "Pulls merchant fee based on a [country code | profile name] combination")
	public ResponseEntity<MerchantFee> getMerchantFee(@PathVariable String countryCode,
			@PathVariable String profileName) {

		return new ResponseEntity<>(exchangeOrderService.getMerchantFee(countryCode, profileName), HttpStatus.OK);
	}

	@Internal
	@PutMapping(path = "/merchant", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Updates merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> updateMerchantFee(@RequestBody MerchantFee merchantFee) {

		return new ResponseEntity<>(exchangeOrderService.putMerchantFee(merchantFee), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/merchant", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "remove merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> removeMerchantFee(@RequestBody MerchantFee merchantFee) {

		return new ResponseEntity<>(exchangeOrderService.remove(merchantFee), HttpStatus.OK);
	}

}
