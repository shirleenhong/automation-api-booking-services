package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Merchant Fee")
public class MerchantFeeController {
	
	@Autowired
	private ExchangeOrderService exchangeOrderService;
	
	@Internal
	@GetMapping(path="/merchant/{countryCode}/{clientType}/{profileName}")
	@ResponseBody
    @ApiOperation(value = "Pulls merchant fee based on a [country code | client type | profile name] combination")
	public ResponseEntity<MerchantFee> getMerchantFee(
			@PathVariable String countryCode,
			@PathVariable String clientType,
			@PathVariable String profileName) {		
		
		return new ResponseEntity<>(exchangeOrderService.getMerchantFee(countryCode, clientType, profileName), HttpStatus.OK);
	}
	
    @Internal
	@GetMapping(path="/merchant/currency/{currencyCode}")
	@ResponseBody
    @ApiOperation(value = "Pulls currency info based on currency code")
	public ResponseEntity<CurrencyCodeRoundRule> getCurrency(
			@PathVariable String currencyCode) {		
		
		return new ResponseEntity<>(exchangeOrderService.getRoundingRule(currencyCode), HttpStatus.OK);
	}
	
	
	@Internal
	@PostMapping(path="/merchant")
	@ResponseBody
    @ApiOperation(value = "Updates merchant fee configuration of a given market")
	public ResponseEntity<MerchantFee> updateMerchantFee(
			@RequestBody MerchantFee merchantFee) {		
		
		return new ResponseEntity<>(exchangeOrderService.putMerchantFee(merchantFee), HttpStatus.OK);
	}

}
