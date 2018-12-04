package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class ExchangeOrderController {

	@Autowired
	private ExchangeOrderService eoService;

	@Autowired
	private ExchangeOrderReportService eoReportService;

	@PostMapping(path = "/exchange-order/{countryCode:hk|sg}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[HK/SG only] Saves new and updates existing exchange orders. EO objects without "
		+ "eoNumber are created. Those with eoNumber are updated.")
	public ResponseEntity<List<ExchangeOrder>> saveExchangeOrder(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@Valid @RequestBody @ApiParam(value = "List of exchange orders to save") List<ExchangeOrder> input)
			throws ExchangeOrderNoContentException {
		return new ResponseEntity(eoService.save(countryCode, input), HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/{countryCode:hk|sg}/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[HK/SG only] Returns exchange order given an exchange order number.")
	public ResponseEntity<ExchangeOrder> getExchangeOrder(
			@PathVariable @ApiParam("2-character country code (lowercase)") String countryCode,
			@PathVariable @ApiParam(value = "10-digit Exchange order number") String eoNumber) {

		return new ResponseEntity<>((ExchangeOrder) eoService.get(countryCode, eoNumber),
				HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/{countryCode:hk|sg}/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[HK/SG only] Returns exchange order given a PNR record locator.")
	public ResponseEntity<List<ExchangeOrder>> getExchangeOrderByRecordLocator(
			@PathVariable @ApiParam("2-character country code") String countryCode,
			@PathVariable @ApiParam(value = "6-character PNR record locator") String recordLocator) {

		return new ResponseEntity<>((List<ExchangeOrder>)
				eoService.getExchangeOrderByRecordLocator(countryCode, recordLocator), HttpStatus.OK);
	}

	@PostMapping(path = "/exchange-order/in", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[India only] Saves new and updates existing exchange orders. EO objects without "
			+ "eoNumber are created. Those with eoNumber are updated.")
	public ResponseEntity<List<IndiaExchangeOrder>> saveIndiaExchangeOrder(
			@Valid @RequestBody @ApiParam(value = "Exchange order to save") List<IndiaExchangeOrder> input)
			throws ExchangeOrderNoContentException {
		return new ResponseEntity(eoService.save(Country.INDIA.getCode(), input), HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/in/{eoNumber:^[0-9]{10}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[India only] Returns exchange order given an exchange order number.")
	public ResponseEntity<IndiaExchangeOrder> getIndiaExchangeOrder(
			@PathVariable @ApiParam(value = "Exchange order number") String eoNumber) {

		return new ResponseEntity<>((IndiaExchangeOrder) eoService.get("IN", eoNumber),
				HttpStatus.OK);
	}

	@GetMapping(path = "/exchange-order/in/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order", value = "[India only] Returns exchange order given a PNR record locator.")
	public ResponseEntity<List<IndiaExchangeOrder>> getIndiaExchangeOrderByRecordLocator(
			@PathVariable @ApiParam(value = "6-character PNR record locator") String recordLocator) {
		return new ResponseEntity<>((List<IndiaExchangeOrder>)
				eoService.getExchangeOrderByRecordLocator(Country.INDIA.getCode(), recordLocator), HttpStatus.OK);
	}

	@GetMapping(value = "/exchange-order/pdf/{eoNumber}", produces = {
			MediaType.APPLICATION_PDF_VALUE })
	@ApiOperation(tags = "Exchange Order", value = "Generates exchange order pdf.")
	public ResponseEntity<byte[]> generatePdf(
			@PathVariable @ApiParam(value = "10-digit Exchange order number") String eoNumber)
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
	@ApiOperation(tags = "Exchange Order", value = "Emails exchange order pdf.")
	public ResponseEntity<EmailResponse> email(
			@RequestBody @ApiParam(value = "10-digit Exchange order number") @PathVariable String eoNumber)
			throws ApiServiceException {

		return new ResponseEntity<>(eoReportService.emailPdf(eoNumber), HttpStatus.OK);
	}

	@GetMapping(value = "/exchange-orders", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ApiOperation(tags = "Exchange Order", value = "Search for exchange orders.")
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
	@ApiOperation(tags = "Exchange Order", value = "Updates status and/or raise cheque of exchange order.")
	public ResponseEntity<Boolean> update(
			@RequestBody @ApiParam(value = "Exchange order to update") ExchangeOrder exchangeOrder) {
		final boolean result = eoService.update(exchangeOrder);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(path = "/exchange-order/room-types", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Hotel Room Types", value = "Returns all hotel room types.")
	public ResponseEntity<List<RoomType>> getAll() {

		return new ResponseEntity<>((List<RoomType>)
				eoService.getAll(), HttpStatus.OK);
	}
	
	@Internal
	@PutMapping(path = "/exchange-order/room-types")
	@ApiOperation(tags = "Exchange Order - Hotel Room Types", value = "Saves (inserts/updates) hotel room type.")
	@ResponseBody
	public ResponseEntity<RoomType> putRoomType(@Valid @RequestBody RoomType roomType) {
		return new ResponseEntity<>(eoService.save(roomType), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/exchange-order/room-types/{code}")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Hotel Room Types", value = "Deletes hotel room type given room type code.")
	public ResponseEntity<String> removeRoomType(@PathVariable String code) {
		String deleteResult = eoService.delete(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
		
	}

	@GetMapping(path = "/exchange-order/vmpd")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - VMPD Reason Codes", value = "Returns all Reasons for Issue.")
	public ResponseEntity<List<VmpdReasonCode>> getVmpdReasonCodes() {
		return new ResponseEntity<>(eoService.getAllVmpdReasonCodes(), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/vmpd")
    @ResponseBody
    @ApiOperation(tags = "Exchange Order - VMPD Reason Codes", value = "Saves (inserts/updates) a 'Reason for Issue'.")
    public ResponseEntity<VmpdReasonCode> saveVmpdReasonCode(@Valid @RequestBody VmpdReasonCode reasonCode) {
        return new ResponseEntity<>(eoService.saveVmpdReasonCode(reasonCode), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/vmpd/{code}")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - VMPD Reason Codes", value = "Deletes 'Reason for Issue' given a vmpd code.")
	public ResponseEntity<String> deleteVmpdReasonCode(@PathVariable @ApiParam(value = "VMPD Code") String code) {
		String deleteResult = eoService.deleteVmpdReasonCode(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}
	
	@GetMapping(path = "/exchange-order/car-vendors")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Car Vendor", value = "Returns all car vendors.")
	public ResponseEntity<List<CarVendor>> getCarVendors() {
		return new ResponseEntity<>(eoService.getAllCarVendors(), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/car-vendors")
    @ResponseBody
    @ApiOperation(tags = "Exchange Order - Car Vendor", value = "Saves (inserts/updates) car vendor.")
    public ResponseEntity<CarVendor> saveCarVendor(@Valid @RequestBody CarVendor carVendor) {
        return new ResponseEntity<>(eoService.saveCarVendor(carVendor), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/car-vendors/{code}")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Car Vendor", value = "Deletes car vendor given vendor code.")
	public ResponseEntity<String> deleteCarVendor(@PathVariable @ApiParam(value = "Car Vendor Code") String code) {
		String deleteResult = eoService.deleteCarVendor(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}

	@GetMapping(path = "/exchange-order/air-misc-info/{clientAccountNumber}")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Air Misc Info", value = "Returns all Air Misc Info for a given client.")
	public ResponseEntity<List<AirMiscInfo>> getAirMiscInfos(
			@PathVariable String clientAccountNumber,
			@RequestParam(value = "reportingFieldTypeIds", required = false) List<String> reportingFieldTypeIds) {
		return new ResponseEntity<>(
				eoService.getAirMiscInfos(clientAccountNumber, reportingFieldTypeIds),
				HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/air-misc-info")
    @ResponseBody
    @ApiOperation(tags = "Exchange Order - Air Misc Info", value = "Saves (inserts/updates) Air Misc Info.")
    public ResponseEntity<AirMiscInfo> saveAirMiscInfo(@Valid @RequestBody AirMiscInfo airMiscInfo) {
        return new ResponseEntity<>(eoService.saveAirMiscInfo(airMiscInfo), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/air-misc-info/{id}")
	@ResponseBody
	@ApiOperation(tags = "Exchange Order - Air Misc Info", value = "Deletes Air Misc Info given an id.")
	public ResponseEntity<String> deleteAirMiscInfo(@PathVariable String id) {
		String deleteResult = eoService.deleteAirMiscInfo(id);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}

	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
