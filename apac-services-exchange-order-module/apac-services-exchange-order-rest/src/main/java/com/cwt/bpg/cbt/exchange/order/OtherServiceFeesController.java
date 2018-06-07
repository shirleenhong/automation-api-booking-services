package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/other-service-fees")
@Api(tags = "Exchange Order")
public class OtherServiceFeesController {

	@Autowired
	private OtherServiceFeesService service;


	@PostMapping(path = "/non-air-fees/in", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes non air fees.")
	public ResponseEntity<NonAirFeesBreakdown> computeNonAirFee(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") InNonAirFeesInput input) {
		input.setCountryCode(Country.INDIA.getCode());
		return new ResponseEntity<>(service.calculateInNonAirFee(input), HttpStatus.OK);
	}

	@PostMapping(path = "/non-air-fees/{countryCode:HK|SG|hk|sg}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes non air fees.")
	public ResponseEntity<NonAirFeesBreakdown> computeNonAirFee(
			@PathVariable String countryCode,
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NonAirFeesInput input) {
		input.setCountryCode(countryCode.toUpperCase());
		return new ResponseEntity<>(service.calculateNonAirFee(input), HttpStatus.OK);
	}

	@PostMapping(path = "/air-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes air fees. Applicable to air products.")
	public ResponseEntity<AirFeesBreakdown> computeAirFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") AirFeesInput input) {

		return new ResponseEntity<>(service.calculateAirFee(input), HttpStatus.OK);
	}

	@PostMapping(path = "/visa-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes visa processing fees, not be used for visa cost and handling fee")
	public ResponseEntity<VisaFeesBreakdown> computeVisaFee(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") VisaFeesInput input) {

		return new ResponseEntity<>(service.calculateVisaFees(input), HttpStatus.OK);
	}

	@PostMapping(path = "/nett-cost", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes nett cost")
	public ResponseEntity<AirFeesBreakdown> computeNettCost(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NettCostInput input) {

		return new ResponseEntity<>(service.calculateNettCost(input), HttpStatus.OK);
	}

}
