package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
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

	@PostMapping(path = "/exchange-order/{countryCode:hk|sg}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves new exchange order transaction.")
	public ResponseEntity<ExchangeOrder> saveExchangeOrder(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input)
			throws ExchangeOrderNoContentException {
		boolean isSave = input.getEoNumber() == null;
		return new ResponseEntity<>((ExchangeOrder) eoService.saveExchangeOrder(countryCode, input),
				(isSave ? HttpStatus.CREATED : HttpStatus.OK));
	}

	@GetMapping(path = "/exchange-order/{countryCode:hk|sg}/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction based on exchange order number (10 digit numeric string).")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>((ExchangeOrder) eoService.getExchangeOrder(countryCode, eoNumber),
				HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/{countryCode:hk|sg}/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction based on Record Locator (6 digit alphanumeric string).")
	public ResponseEntity<List<ExchangeOrder>> getExchangeOrderByRecordLocator(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@PathVariable @ApiParam(value = "Record Locator") String recordLocator) {

		return new ResponseEntity<>((List<ExchangeOrder>)
				eoService.getExchangeOrderByRecordLocator(countryCode, recordLocator), HttpStatus.OK);
	}

	@PostMapping(path = "/exchange-order/in", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves new exchange order transaction.")
	public ResponseEntity<IndiaExchangeOrder> saveIndiaExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") IndiaExchangeOrder input)
			throws ExchangeOrderNoContentException {
		boolean isSave = input.getEoNumber() == null;
		return new ResponseEntity<>((IndiaExchangeOrder) eoService.saveExchangeOrder(Country.INDIA.getCode(), input),
				(isSave ? HttpStatus.CREATED : HttpStatus.OK));
	}

	@GetMapping(path = "/exchange-order/in/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls India exchange order transaction based on exchange order number (10 digit numeric string).")
	public ResponseEntity<IndiaExchangeOrder> getIndiaExchangeOrder(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>((IndiaExchangeOrder) eoService.getExchangeOrder("IN", eoNumber),
				HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/in/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls India exchange order transaction based on Record Locator (6 digit alphanumeric string).")
	public ResponseEntity<List<IndiaExchangeOrder>> getIndiaExchangeOrderByRecordLocator(
			@PathVariable @ApiParam(value = "Record Locator") String recordLocator) {
		return new ResponseEntity<>((List<IndiaExchangeOrder>)
				eoService.getExchangeOrderByRecordLocator(Country.INDIA.getCode(), recordLocator), HttpStatus.OK);
	}

	@GetMapping(value = "/exchange-order/pdf/{eoNumber}", produces = {
			MediaType.APPLICATION_PDF_VALUE })
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

	@GetMapping(path = "/exchange-order/email/{eoNumber}")
	@ResponseBody
	@ApiOperation(value = "Emails exchange order pdf of the specified eoNumber")
	public ResponseEntity<EmailResponse> email(
			@RequestBody @ApiParam(value = "EoNumber of the exchange order to email") @PathVariable String eoNumber)
			throws ApiServiceException {

		return new ResponseEntity<>(eoReportService.emailPdf(eoNumber), HttpStatus.OK);
	}

	@GetMapping(value = "/exchange-orders", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ApiOperation(value = "Search for exchange orders.")
	public List<ExchangeOrder> search(final ExchangeOrderSearchDTO p) {
		final ExchangeOrderSearchParam param = new ExchangeOrderSearchParam();
		param.setEoNumber(p.getEoNumber());
		param.setCountryCode(p.getCountryCode());
		final Vendor vendor = new Vendor();
		vendor.setCode(p.getVendorCode());
		vendor.setRaiseType(p.getRaiseType());
		param.setVendor(vendor);
		param.setRecordLocator(p.getRecordLocator());
		param.setStatus(EoStatus.find(p.getStatus()));
		param.setStartCreationDate(p.getStartCreationDate());
		param.setEndCreationDate(p.getEndCreationDate());
		return eoService.search(param);
	}

	@PutMapping(path = "/exchange-order", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Update exchange order transaction.")
	public ResponseEntity<Boolean> update(
			@RequestBody @ApiParam(value = "Exchange order to update") ExchangeOrder param) {
		final boolean result = eoService.update(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exchange-order/room-types/", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Return all room types")
	public ResponseEntity<List<RoomType>> getRoomTypes() {

		return new ResponseEntity<>((List<RoomType>)
				eoService.getRoomTypes(), HttpStatus.OK);
	}
	
	@Internal
	@PutMapping(path = "/exchange-order/room-type/")
	@ApiOperation(value = "Save or update room type")
	@ResponseBody
	public ResponseEntity<RoomType> putRoomType(@Valid @RequestBody RoomType roomType) {
		return new ResponseEntity<>(eoService.save(roomType), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/exchange-order/room-type/")
	@ResponseBody
	@ApiOperation(value = "Remove room type")
	public ResponseEntity<String> removeRoomType(
			@RequestParam("code") String code) {
		return new ResponseEntity<>(eoService.delete(code), HttpStatus.OK);
	}

}
