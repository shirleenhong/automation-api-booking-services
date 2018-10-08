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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.RoomType;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
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
	@ApiOperation(value = "Saves new or updates existing exchange order.")
	public ResponseEntity<ExchangeOrder> saveExchangeOrder(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") ExchangeOrder input)
			throws ExchangeOrderNoContentException {
		boolean isSave = input.getEoNumber() == null;
		return new ResponseEntity<>((ExchangeOrder) eoService.save(countryCode, input),
				(isSave ? HttpStatus.CREATED : HttpStatus.OK));
	}

	@GetMapping(path = "/exchange-order/{countryCode:hk|sg}/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls exchange order transaction based on exchange order number (10 digit numeric string).")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>((ExchangeOrder) eoService.get(countryCode, eoNumber),
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
	@ApiOperation(value = "Saves new or updates existing India exchange order.")
	public ResponseEntity<IndiaExchangeOrder> saveIndiaExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") IndiaExchangeOrder input)
			throws ExchangeOrderNoContentException {
		boolean isSave = input.getEoNumber() == null;
		return new ResponseEntity<>((IndiaExchangeOrder) eoService.save(Country.INDIA.getCode(), input),
				(isSave ? HttpStatus.CREATED : HttpStatus.OK));
	}

	@GetMapping(path = "/exchange-order/in/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls India exchange order transaction based on exchange order number (10 digit numeric string).")
	public ResponseEntity<IndiaExchangeOrder> getIndiaExchangeOrder(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>((IndiaExchangeOrder) eoService.get("IN", eoNumber),
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
	@ApiOperation(value = "Updates status and/or raise cheque of exchange order record.")
	public ResponseEntity<Boolean> update(
			@RequestBody @ApiParam(value = "Exchange order to update") ExchangeOrder exchangeOrder)
			throws ExchangeOrderNoContentException {
		final boolean result = eoService.update(exchangeOrder);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exchange-order/room-types", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Return all room types")
	public ResponseEntity<List<RoomType>> getAll() {

		return new ResponseEntity<>((List<RoomType>)
				eoService.getAll(), HttpStatus.OK);
	}
	
	@Internal
	@PutMapping(path = "/exchange-order/room-types")
	@ApiOperation(value = "Save or update room type")
	@ResponseBody
	public ResponseEntity<RoomType> putRoomType(@Valid @RequestBody RoomType roomType) {
		return new ResponseEntity<>(eoService.save(roomType), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/exchange-order/room-types/{code}")
	@ResponseBody
	@ApiOperation(value = "Remove room type")
	public ResponseEntity<String> removeRoomType(@PathVariable String code) {
		
		String deleteResult = eoService.delete(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
		
	}

	@GetMapping(path = "/exchange-order/vmpd")
	@ResponseBody
	@ApiOperation(value = "Pulls all Reason for Issue.")
	public ResponseEntity<List<VmpdReasonCode>> getVmpdReasonCodes() {
		return new ResponseEntity<>(eoService.getAllVmpdReasonCodes(), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/vmpd")
    @ResponseBody
    @ApiOperation(value = "Save or update Reason for Issue.")
    public ResponseEntity<VmpdReasonCode> saveVmpdReasonCode(@Valid @RequestBody VmpdReasonCode reasonCode) {
        return new ResponseEntity<>(eoService.saveVmpdReasonCode(reasonCode), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/vmpd/{code}")
	@ResponseBody
	@ApiOperation(value = "Deletes Reason for Issue data by vmpd code.")
	public ResponseEntity<String> deleteVmpdReasonCode(@PathVariable @ApiParam(value = "VMPD Code") String code) {
		String deleteResult = eoService.deleteVmpdReasonCode(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}
	
	@GetMapping(path = "/exchange-order/car-vendors")
	@ResponseBody
	@ApiOperation(value = "Pulls all Car Vendors.")
	public ResponseEntity<List<CarVendor>> getCarVendors() {
		return new ResponseEntity<>(eoService.getAllCarVendors(), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/car-vendors")
    @ResponseBody
    @ApiOperation(value = "Save or update Car Vendor.")
    public ResponseEntity<CarVendor> saveCarVendor(@Valid @RequestBody CarVendor carVendor) {
        return new ResponseEntity<>(eoService.saveCarVendor(carVendor), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/car-vendors/{code}")
	@ResponseBody
	@ApiOperation(value = "Deletes Car Vendor data by code.")
	public ResponseEntity<String> deleteCarVendor(@PathVariable @ApiParam(value = "Car Vendor Code") String code) {
		String deleteResult = eoService.deleteCarVendor(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}
	
	@GetMapping(path = "/exchange-order/air-misc-info/{clientAccountNumber}")
	@ResponseBody
	@ApiOperation(value = "Pulls all Air Misc Info by client account number.")
	public ResponseEntity<List<AirMiscInfo>> getAirMiscInfo(@PathVariable String clientAccountNumber) {
		return new ResponseEntity<>(eoService.getAirMiscInfo(clientAccountNumber), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/air-misc-info")
    @ResponseBody
    @ApiOperation(value = "[Maintenance] Save or update Air Misc Info.")
    public ResponseEntity<AirMiscInfo> saveAirMiscInfo(@Valid @RequestBody AirMiscInfo airMiscInfo) {
        return new ResponseEntity<>(eoService.saveAirMiscInfo(airMiscInfo), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/air-misc-info/{id}")
	@ResponseBody
	@ApiOperation(value = "[Maintenance] Deletes Air Misc Info by id.")
	public ResponseEntity<String> deleteAirMiscInfo(@PathVariable String id) {
		String deleteResult = eoService.deleteAirMiscInfo(id);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}

	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
