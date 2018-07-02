package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order")
public class ExchangeOrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderController.class);
	
	@Autowired
	private ExchangeOrderService eoService;
	
	@Autowired
	private ExchangeOrderReportService eoReportService;
	
	
	@PostMapping(path = "/exchange-order", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves new exchange order transaction.")
	public ResponseEntity<ExchangeOrder> saveExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input) {

		try {
			return new ResponseEntity<>(eoService.saveExchangeOrder(input), HttpStatus.OK);
		}
		catch (ExchangeOrderException e) {
			LOGGER.error(e.getMessage());
			HttpHeaders headers = new HttpHeaders();
			headers.set("Error", "Exchange order not found!");
			return new ResponseEntity<>(input, headers, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(path = "/exchange-order/{eoNumber}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction.")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(@PathVariable String eoNumber) {

		return new ResponseEntity<>(eoService.getExchangeOrder(eoNumber), HttpStatus.OK);
	}

	@RequestMapping(value = "/exchange-order/generatePdf/{eoNumber}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<byte[]> generatePdf(@PathVariable String eoNumber) {
		final HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		try {
			return new ResponseEntity<>(eoReportService.generatePdf(eoNumber), headers,
					HttpStatus.OK);
		}
		catch (ExchangeOrderException e) {
			LOGGER.error(e.getMessage());
			headers.set("Error", "Unable to generate report for exchange order number: " + eoNumber);
			return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			headers.set("Error", "Unable to generate report for exchange order number: " + eoNumber);
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
