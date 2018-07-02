package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api(tags = "Exchange Order")
public class ExchangeOrderController {

	
	@Autowired
	private ExchangeOrderService eoService;
	
	
	@PostMapping(path = "/exchange-order", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves new exchange order transaction.")
	public ResponseEntity<ExchangeOrder> saveExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input) {

		return new ResponseEntity<>(eoService.saveExchangeOrder(input), HttpStatus.OK);
	}
	
	@GetMapping(path = "/exchange-order/{eoNumber}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction.")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(@PathVariable String eoNumber) {

		return new ResponseEntity<>(eoService.getExchangeOrder(eoNumber), HttpStatus.OK);
	}

	@RequestMapping(value = "/exchange-order/generatePdf/{eoNumber}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<byte[]> generatePdf(@PathVariable String eoNumber) throws IOException
	{
        String filename = eoNumber+".pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\""+filename+"\"");
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		InputStream fileInputStream = eoService.generatePdf(eoNumber);
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(fileInputStream), headers, HttpStatus.OK);
	}
	
}
