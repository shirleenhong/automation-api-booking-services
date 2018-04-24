package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/other-service-fees")
@Api(tags = "Exchange Order")
public class OtherServiceFeesController {
	
	@Autowired
	private OtherServiceFeesApi otherServiceFeesApi;
	
	/**
	 * Applicable car, hotel and other ticket
	 * @param input
	 * @return
	 */
	@PostMapping(
			path="/misc-fees", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
    @ApiOperation(value = "Computes miscellaneous fee")
	public ResponseEntity<FeesBreakdown> computeMiscellaneousFee(
			@Valid
            @RequestBody
            @ApiParam(value = "Values needed for calculation") OtherServiceFeesInput input) {
		
		return new ResponseEntity<>(otherServiceFeesApi.calculateMiscFee(input), HttpStatus.OK);
	}	
	
	/**
	 * Applicable for air
	 * @param input
	 * @return
	 */
	@PostMapping(
			path="/air-fees", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public ResponseEntity<FeesBreakdown> computeAirFees(
			@Valid @RequestBody OtherServiceFeesInput input) {		
		
		return new ResponseEntity<>(otherServiceFeesApi.calculateMiscFee(input), HttpStatus.OK);
	}	
}
