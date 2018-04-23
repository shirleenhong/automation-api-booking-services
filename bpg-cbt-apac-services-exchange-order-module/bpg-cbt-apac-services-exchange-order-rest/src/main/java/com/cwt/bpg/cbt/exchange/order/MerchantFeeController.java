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
import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@RestController
public class MerchantFeeController {
	
	@Autowired
	private ExchangeOrderService exchangeOrderService;
	
	// TODO: for testing only, remove this, will not expose outside
    @Internal
	@GetMapping(path="/merchant/{countryCode}/{clientType}/{productName}")
	@ResponseBody
	public ResponseEntity<MerchantFee> getMerchantFee(
			@PathVariable String countryCode,
			@PathVariable String clientType,
			@PathVariable String productName) {		
		
		return new ResponseEntity<>(exchangeOrderService.getMerchantFee(countryCode, clientType, productName), HttpStatus.OK);
	}

	// TODO: for testing only, remove this, will not expose outside
    @Internal
    @GetMapping(path="/merchant/currency/{currencyCode}")
	@ResponseBody
	public ResponseEntity<CurrencyCodeRoundRule> getCurrency(
			@PathVariable String currencyCode) {

		return new ResponseEntity<>(exchangeOrderService.getRoundingRule(currencyCode), HttpStatus.OK);
	}

	
	/**
	 * Internal use, for QA's only
	 * @param countryCode
	 * @return
	 */
    @Internal
	@PostMapping(path="/merchant/{countryCode}")
	@ResponseBody
	public ResponseEntity<ClientMerchantFee> updateMerchantFee(
			@PathVariable String countryCode, @RequestBody ClientMerchantFee merchantFee) {		
		
		return new ResponseEntity<>(exchangeOrderService.putMerchantFee(merchantFee), HttpStatus.OK);
	}

}
