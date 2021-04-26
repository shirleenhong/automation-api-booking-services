package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsOutput;
import com.cwt.bpg.cbt.exchange.order.validator.IndiaAirFeesValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/other-service-fees")
@Api(tags = "Other Service Fees")
public class OtherServiceFeesController {

	@Autowired
	private OtherServiceFeesService service;

	@Autowired
	private IndiaAirFeesValidator indiaAirFeesValidator;

	@PostMapping(
			path = "/non-air-fees/in",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[India only] Computes non air fees.")
	public ResponseEntity<IndiaNonAirFeesBreakdown> computeNonAirFees(@Valid @RequestBody @ApiParam(
			value = "Values needed for calculation") IndiaNonAirFeesInput input) {

		return new ResponseEntity<>(service.calculateIndiaNonAirFees(input), HttpStatus.OK);
	}

	@PostMapping(
			path = "/non-air-fees/{countryCode:hk|sg|th}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[HK/SG/TH only] Computes non air fees.")
	public ResponseEntity<NonAirFeesBreakdown> computeNonAirFees(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NonAirFeesInput input){

		return new ResponseEntity<>(service.calculateNonAirFees(input, countryCode.toUpperCase()), HttpStatus.OK);
	}

	@PostMapping(
			path = "/air-fees/in",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[India only] Computes air fees.")
	public ResponseEntity<IndiaAirFeesBreakdown> computeIndiaAirFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") IndiaAirFeesInput input) {

		indiaAirFeesValidator.validate(input);
		return new ResponseEntity<>(service.calculateIndiaAirFees(input), HttpStatus.OK);
	}

	@PostMapping(
			path = "/air-fees/{countryCode:hk|sg|th}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[HK/SG/TH only] Computes air fees.")
	public ResponseEntity<AirFeesBreakdown> computeAirFees(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") AirFeesInput input) {

		return new ResponseEntity<>(service.calculateAirFees(input, countryCode.toUpperCase()), HttpStatus.OK);
	}

	@PostMapping(
			path = "/air-fees/in/default-values",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[India only] Returns default values for Air product.")
	public ResponseEntity<AirFeesDefaultsOutput> getAirFeesDefaults(
			@Valid @RequestBody @ApiParam(value = "Values needed to get air fees defaults") AirFeesDefaultsInput input) {
		return new ResponseEntity<>(service.getAirFeesDefaults(input), HttpStatus.OK);
	}


	@PostMapping(
			path = "/visa-fees",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "[SG only] Computes visa processing fees. Not be used for visa cost and handling fee.")
	public ResponseEntity<VisaFeesBreakdown> computeVisaFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") VisaFeesInput input) {

		return new ResponseEntity<>(service.calculateVisaFees(input), HttpStatus.OK);
	}

	@PostMapping(
			path = "/nett-cost",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes nett cost.")
	public ResponseEntity<AirFeesBreakdown> computeNettCost(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NettCostInput input) {

		return new ResponseEntity<>(service.calculateNettCost(input), HttpStatus.OK);
	}

}
