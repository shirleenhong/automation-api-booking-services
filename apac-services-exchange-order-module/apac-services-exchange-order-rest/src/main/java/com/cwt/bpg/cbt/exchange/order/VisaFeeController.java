package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order")
public class VisaFeeController {
	
	@PostMapping(
			path="/visa-fees", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
    @ApiOperation(value = "Computes visa processing fees")
	public ResponseEntity<FeesBreakdown> computeVisaFee(
			@Valid
            @RequestBody
            @ApiParam(value = "Values needed for calculation") MiscFeesInput input) {
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	

}
