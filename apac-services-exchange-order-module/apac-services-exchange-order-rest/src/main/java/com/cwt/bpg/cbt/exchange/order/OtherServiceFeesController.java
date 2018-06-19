package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.calculator.model.Country;
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
	
	@Autowired
	private ExchangeOrderService eoService;

	@PostMapping(path = "/non-air-fees/in", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes non air fees. Applicable to India air products.")
	public ResponseEntity<NonAirFeesBreakdown> computeNonAirFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") IndiaNonAirFeesInput input) {
		input.setCountryCode(Country.INDIA.getCode());
		return new ResponseEntity<>(service.calculateIndiaNonAirFees(input), HttpStatus.OK);
	}

	@PostMapping(path = "/non-air-fees/{countryCode:hk|sg}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes non air fees.")
	public ResponseEntity<NonAirFeesBreakdown> computeNonAirFees(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NonAirFeesInput input) {
		input.setCountryCode(countryCode.toUpperCase());
		return new ResponseEntity<>(service.calculateNonAirFees(input), HttpStatus.OK);
	}

    @PostMapping(path = "/air-fees/in", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Computes air fees. Applicable to India air products.")
    public ResponseEntity<IndiaAirFeesBreakdown> computeIndiaAirFees(
            @Valid @RequestBody @ApiParam(value = "Values needed for calculation") IndiaAirFeesInput input) {

        return new ResponseEntity<>(service.calculateIndiaAirFees(input), HttpStatus.OK);
    }

    @PostMapping(path = "/air-fees/{countryCode:hk|sg}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes air fees. Applicable to non-India air products.")
	public ResponseEntity<AirFeesBreakdown> computeAirFees(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") AirFeesInput input) {

		input.setCountryCode(countryCode.toUpperCase());
		return new ResponseEntity<>(service.calculateAirFees(input), HttpStatus.OK);
	}

	@PostMapping(path = "/visa-fees", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes visa processing fees. Not be used for visa cost and handling fee.")
	public ResponseEntity<VisaFeesBreakdown> computeVisaFees(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") VisaFeesInput input) {

		return new ResponseEntity<>(service.calculateVisaFees(input), HttpStatus.OK);
	}

	@PostMapping(path = "/nett-cost", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Computes nett cost.")
	public ResponseEntity<AirFeesBreakdown> computeNettCost(
			@Valid @RequestBody @ApiParam(value = "Values needed for calculation") NettCostInput input) {

		return new ResponseEntity<>(service.calculateNettCost(input), HttpStatus.OK);
	}
	
	@PostMapping(path = "/exchange-order", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Save new exchange order transaction")
	public ResponseEntity<String> saveExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input) {

		return new ResponseEntity<>(service.saveExchangeOrder(input), HttpStatus.OK);
	}
}
