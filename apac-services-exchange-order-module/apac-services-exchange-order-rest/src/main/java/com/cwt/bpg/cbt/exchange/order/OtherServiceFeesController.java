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

import com.cwt.bpg.cbt.exchange.order.model.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/other-service-fees")
@Api(tags = "Exchange Order")
public class OtherServiceFeesController {

	@Autowired
	private OtherServiceFeesService service;

	@PostMapping(path = "/misc-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes miscellaneous fees as well as visa cost and visa handling fees. Applicable to car, hotel and other ticket (train and ferry).")
	public ResponseEntity<MiscFeesBreakdown> computeMiscellaneousFee(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") MiscFeesInput input) {

		return new ResponseEntity<>((MiscFeesBreakdown) service.calculateMiscFee(input), HttpStatus.OK);
	}
	
	@PostMapping(path = "/non-air-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes non air fees.")
	public ResponseEntity<MiscFeesBreakdown> computeNonAirFee(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") InMiscFeesInput input) {

		return new ResponseEntity<>((MiscFeesBreakdown) service.calculateNonAirFee(input), HttpStatus.OK);
	}

	@PostMapping(path = "/air-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes air fees. Applicable to air products.")
	public ResponseEntity<AirFeesBreakdown> computeAirFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") AirFeesInput input) {

		return new ResponseEntity<>((AirFeesBreakdown) service.calculateAirFee(input), HttpStatus.OK);
	}

	@PostMapping(path = "/visa-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes visa processing fees, not be used for visa cost and handling fee")
	public ResponseEntity<VisaFeesBreakdown> computeVisaFee(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") VisaFeesInput input) {

		return new ResponseEntity<>((VisaFeesBreakdown) service.calculateVisaFees(input), HttpStatus.OK);
	}

	@PostMapping(path = "/nett-cost", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes nett cost")
	public ResponseEntity<AirFeesBreakdown> computeNettCost(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NettCostInput input) {

		return new ResponseEntity<>((AirFeesBreakdown) service.calculateNettCost(input), HttpStatus.OK);
	}

}
