package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order")
public class ExchangeOrderController
{

    @Autowired
    private ExchangeOrderService eoService;

    @Autowired
    private ExchangeOrderReportService eoReportService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(path = "/exchange-order/{countryCode:hk|sg|th}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[HK/SG/TH only] Saves new and updates existing exchange orders. EO objects without "
            + "eoNumber are created. Those with eoNumber are updated.")
    public ResponseEntity<List<ExchangeOrder>> saveExchangeOrder(
            @PathVariable @ApiParam("2-character country code") String countryCode,
            @Valid @RequestBody @ApiParam(value = "List of exchange orders to save") List<ExchangeOrder> input)
            throws ExchangeOrderNoContentException
    {
        return new ResponseEntity(eoService.save(countryCode, input), HttpStatus.OK);
    }

    @GetMapping(path = "/exchange-order/{countryCode:hk|sg|th}/{eoNumber:^[0-9]{10}$}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[HK/SG/TH only] Returns exchange order given an exchange order number.")
    public ResponseEntity<ExchangeOrder> getExchangeOrder(
            @PathVariable @ApiParam("2-character country code (lowercase)") String countryCode,
            @PathVariable @ApiParam(value = "10-digit Exchange order number") String eoNumber)
    {

        return new ResponseEntity<>((ExchangeOrder) eoService.get(countryCode, eoNumber),
                HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(path = "/exchange-order/{countryCode:hk|sg|th}/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[HK/SG/TH only] Returns exchange order given a PNR record locator.")
    public ResponseEntity<List<ExchangeOrder>> getExchangeOrderByRecordLocator(
            @PathVariable @ApiParam("2-character country code") String countryCode,
            @PathVariable @ApiParam(value = "6-character PNR record locator") String recordLocator)
    {

        return new ResponseEntity<>((List<ExchangeOrder>) eoService.getExchangeOrderByRecordLocator(countryCode, recordLocator), HttpStatus.OK);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(path = "/exchange-order/in", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[India only] Saves new and updates existing exchange orders. EO objects without "
            + "eoNumber are created. Those with eoNumber are updated.")
    public ResponseEntity<List<IndiaExchangeOrder>> saveIndiaExchangeOrder(
            @Valid @RequestBody @ApiParam(value = "Exchange order to save") List<IndiaExchangeOrder> input)
            throws ExchangeOrderNoContentException
    {
        return new ResponseEntity(eoService.save(Country.INDIA.getCode(), input), HttpStatus.OK);
    }

    @GetMapping(path = "/exchange-order/in/{eoNumber:^[0-9]{10}$}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[India only] Returns exchange order given an exchange order number.")
    public ResponseEntity<IndiaExchangeOrder> getIndiaExchangeOrder(
            @PathVariable @ApiParam(value = "Exchange order number") String eoNumber)
    {

        return new ResponseEntity<>((IndiaExchangeOrder) eoService.get("IN", eoNumber),
                HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(path = "/exchange-order/in/{recordLocator:^[a-zA-Z0-9]{6}$}", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "[India only] Returns exchange order given a PNR record locator.")
    public ResponseEntity<List<IndiaExchangeOrder>> getIndiaExchangeOrderByRecordLocator(
            @PathVariable @ApiParam(value = "6-character PNR record locator") String recordLocator)
    {
        return new ResponseEntity<>((List<IndiaExchangeOrder>) eoService.getExchangeOrderByRecordLocator(Country.INDIA.getCode(), recordLocator), HttpStatus.OK);
    }

    @GetMapping(path = "/exchange-order/pdf/{eoNumber}", produces = {
            MediaType.APPLICATION_PDF_VALUE })
    @ApiOperation(value = "Generates exchange order pdf.")
    public ResponseEntity<byte[]> generatePdf(
            @PathVariable @ApiParam(value = "10-digit Exchange order number") String eoNumber)
            throws ExchangeOrderNoContentException, ApiServiceException
    {

        final String filename = eoNumber + ".pdf";
        final HttpHeaders headers = new HttpHeaders();
        byte[] body = eoReportService.generatePdf(eoNumber);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"");
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/exchange-order/email/{eoNumber}")
    @ResponseBody
    @ApiOperation(value = "Emails exchange order pdf.")
    public ResponseEntity<EmailResponse> email(
            @RequestBody @ApiParam(value = "10-digit Exchange order number") @PathVariable String eoNumber)
            throws ApiServiceException
    {

        return new ResponseEntity<>(eoReportService.emailPdf(eoNumber), HttpStatus.OK);
    }

    @GetMapping(path = "/exchange-orders", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ApiOperation(value = "Search for exchange orders.")
    public List<ExchangeOrder> search(final ExchangeOrderSearchDTO p)
    {
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
        param.setProductCode(p.getProductCode());
        return eoService.search(param);
    }

    @PutMapping(path = "/exchange-order", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Updates status and/or raise cheque of exchange order.")
    public ResponseEntity<Boolean> update(
            @RequestBody @ApiParam(value = "Exchange order to update") ExchangeOrder exchangeOrder)
    {
        final boolean result = eoService.update(exchangeOrder);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
