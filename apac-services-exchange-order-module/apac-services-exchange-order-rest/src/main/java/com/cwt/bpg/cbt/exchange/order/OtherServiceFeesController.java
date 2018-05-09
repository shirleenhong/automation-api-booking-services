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

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.cwt.bpg.cbt.exchange.order.model.VisaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/other-service-fees")
@Api(tags = "Exchange Order")
public class OtherServiceFeesController {
	
	@Autowired
	private OtherServiceFeesService service;
	
	/**
	 * Applicable car, hotel and other ticket (train and ferry)
	 * @param input
	 * @return
	 */
	@PostMapping(
			path="/misc-fees", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
    @ApiOperation(value = "Computes miscellaneous fees")
	public ResponseEntity<FeesBreakdown> computeMiscellaneousFee(
			@Valid
            @RequestBody
            @ApiParam(value = "Values needed for calculation") MiscFeesInput input) {
		
		return new ResponseEntity<>(service.calculateMiscFee(input), HttpStatus.OK);
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
	@ApiOperation(value = "Computes air fees")
	public ResponseEntity<AirFeesBreakdown> computeAirFees(
			@Valid 
			@RequestBody 
			@ApiParam(value = "Values needed for calculation") AirFeesInput input) {
		
		return new ResponseEntity<>((AirFeesBreakdown)service.calculateAirFee(input), HttpStatus.OK);
	}
	
	@PostMapping(
			path="/nett-cost", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes nett cost")
	public ResponseEntity<AirFeesBreakdown> computeNettCost(
			@Valid 
			@RequestBody 
			@ApiParam(value = "Values needed for calculation") NettCostInput input) {
		
		return new ResponseEntity<>((AirFeesBreakdown)service.calculateNettCost(input), HttpStatus.OK);
	}
	
	@PostMapping(
			path="/visa-fees", 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
    @ApiOperation(value = "Computes visa processing fees")
	public ResponseEntity<FeesBreakdown> computeVisaFee(
			@Valid
            @RequestBody
            @ApiParam(value = "Values needed for calculation") VisaInput input) {
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	

}
