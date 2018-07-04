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
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
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

	private static final String ERROR = "Error";

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
			headers.set(ERROR, "Exchange order not found!");
			return new ResponseEntity<>(input, headers, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(path = "/exchange-order/{eoNumber}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction.")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>(eoService.getExchangeOrder(eoNumber), HttpStatus.OK);
	}

	@RequestMapping(value = "/exchange-order/pdf/{eoNumber}", method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_PDF_VALUE })
	@ApiOperation(value = "Generates exchange order pdf.")
	public ResponseEntity<byte[]> generatePdf(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		String filename = eoNumber + ".pdf";
		final HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		byte[] body = null;

		try {
			body = eoReportService.generatePdf(eoNumber);
		}
		catch (ExchangeOrderException e) {
			handleError(eoNumber, e.getMessage(), headers);
			status = HttpStatus.NO_CONTENT;
		}
		catch (Exception e) {
			handleError(eoNumber, e.getMessage(), headers);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		if (status == HttpStatus.OK) {
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + filename + "\"");
			headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
		}

		return new ResponseEntity<>(body, headers, status);
	}

	@PostMapping(path = "/exchange-order/email/{eoNumber}",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Emails exchange order pdf of the specified eoNumber")
	public ResponseEntity<EmailResponse> email(
			@PathVariable @RequestBody @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>(eoReportService.emailPdf(eoNumber), HttpStatus.OK);
	}

	private void handleError(String eoNumber, String message, final HttpHeaders headers) {

		LOGGER.error(message);
		headers.set(ERROR, "Unable to generate report for exchange order number: " + eoNumber);
	}

}
