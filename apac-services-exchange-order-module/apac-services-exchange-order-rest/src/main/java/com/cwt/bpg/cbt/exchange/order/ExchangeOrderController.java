package com.cwt.bpg.cbt.exchange.order;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.validator.FopTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order")
public class ExchangeOrderController {

	@Autowired
	private ExchangeOrderService eoService;

	@Autowired
	private ExchangeOrderReportService eoReportService;

	@Autowired
	private FopTypeValidator fopTypeValidator;

	@PostMapping(
			path = "/exchange-order",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves new exchange order transaction.")
	public ResponseEntity<Map<String, Object>> saveExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input) 
					throws ExchangeOrderNoContentException {

		fopTypeValidator.validate(input);
		final ExchangeOrder saveExchangeOrder = eoService.saveExchangeOrder(input);
		Map<String, Object> response = new HashMap<>(1);
		response.put("eoNumber", saveExchangeOrder.getEoNumber());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/{eoNumber:^[0-9]{10}$}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction based on exchange order number (10 digit numeric string).")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>(eoService.getExchangeOrder(eoNumber), HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/{pnr:^[a-zA-Z0-9]{6}$}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction based on PNR (6 digit alphanumeric string).")
	public ResponseEntity<ExchangeOrder> getExchangeOrderByPnr(
			@PathVariable @ApiParam(value = "PNR number") String pnr) {

		return new ResponseEntity<>(eoService.getExchangeOrderByPnr(pnr), HttpStatus.OK);
	}

	@GetMapping(
			value = "/exchange-order/pdf/{eoNumber}",
			produces = { MediaType.APPLICATION_PDF_VALUE })
	@ApiOperation(value = "Generates exchange order pdf.")
	public ResponseEntity<byte[]> generatePdf(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) 
					throws ExchangeOrderNoContentException, ApiServiceException {

		final String filename = eoNumber + ".pdf";
		final HttpHeaders headers = new HttpHeaders();
		byte[] body = eoReportService.generatePdf(eoNumber);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"");
		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}

	@Internal
	@GetMapping(path = "/exchange-order/email/{eoNumber}")
	@ResponseBody
	@ApiOperation(value = "Emails exchange order pdf of the specified eoNumber")
	public ResponseEntity<EmailResponse> email(
			@RequestBody @ApiParam(
					value = "EoNumber of the exchange order to email") @PathVariable String eoNumber) 
							throws ApiServiceException {

		return new ResponseEntity<>(eoReportService.emailPdf(eoNumber), HttpStatus.OK);
	}
}
