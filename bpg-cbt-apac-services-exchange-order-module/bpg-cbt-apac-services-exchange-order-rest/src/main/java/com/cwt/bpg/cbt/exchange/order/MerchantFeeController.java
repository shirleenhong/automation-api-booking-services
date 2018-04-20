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

import com.cwt.bpg.cbt.exchange.order.model.ClientMerchantFee;

@RestController
public class MerchantFeeController {
	
	@Autowired
	private MerchantFeeApi mechantFeeApi;
	
	@GetMapping(path="/merchant/{countryCode}")
	@ResponseBody
	public ResponseEntity<ClientMerchantFee> getMerchantFee(
			@PathVariable String countryCode) {		
		
		return new ResponseEntity<>(mechantFeeApi.getMerchantFee(countryCode), HttpStatus.OK);
	}
	
	
	/**
	 * internal use, for QA's only
	 * @param countryCode
	 * @return
	 */
	@PostMapping(path="/merchant/{countryCode}")
	@ResponseBody
	public ResponseEntity<ClientMerchantFee> updateMerchantFee(
			@PathVariable String countryCode, @RequestBody ClientMerchantFee merchantFee) {		
		
		return new ResponseEntity<>(mechantFeeApi.putMerchantFee(merchantFee), HttpStatus.OK);
	}

}
