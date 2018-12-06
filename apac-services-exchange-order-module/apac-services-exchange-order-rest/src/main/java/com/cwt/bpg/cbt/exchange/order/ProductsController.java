package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Products")
public class ProductsController {
	@Autowired
	private ProductService service;

	@GetMapping(path = "/products/{countryCode}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Returns list of products and vendors for a specific market.")
	public ResponseEntity<List<BaseProduct>> getProducts(
			@PathVariable @ApiParam(value = "Country code of the requested market") String countryCode) {
		return new ResponseEntity<>(service.getProducts(countryCode.toUpperCase()), HttpStatus.OK);
	}

	@PutMapping(path = "/products/{countryCode:^(?!in)[a-zA-Z0-9]{2}}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) product list of a market (non-India).")
    public ResponseEntity<String> saveProduct(
            @PathVariable @ApiParam(value = "Country code of the requested market") String countryCode,
            @RequestParam(required = false) @ApiParam(value = "_true_ when inserting a new Product. _false_ when updating existing one.") boolean insert,
            @RequestBody Product product) {
        String result = service.saveProduct(countryCode.toUpperCase(), product, insert);
        HttpStatus status = result.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(result, status);
    }

    @PutMapping(path = "/products/in", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) product list of India.")
    public ResponseEntity<String> saveIndiaProduct(
            @RequestParam(required = false) @ApiParam(value = "_true_ when inserting a new Product. _false_ when updating existing one.") boolean insert,
            @RequestBody IndiaProduct product) {
        String result = service.saveProduct(Country.INDIA.getCode().toUpperCase(), product, insert);
        HttpStatus status = result.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(result, status);
    }

    @PutMapping(path = "/vendors/{countryCode:[a-zA-Z0-9]{2}}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) vendor info of a market.")
    public ResponseEntity<String> saveVendor(
            @PathVariable @ApiParam(value = "Country code of the requested market") String countryCode,
            @RequestParam(required = false) @ApiParam(value = "Required when _insert_ flag is true.") String productCode,
            @RequestParam(required = false) @ApiParam(value = "_true_ when inserting a new Vendor. _false_ when updating existing one.") boolean insert,
            @RequestBody Vendor vendor) {
        String result = service.saveVendor(countryCode.toUpperCase(), productCode, vendor, insert);
        HttpStatus status = result.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(result, status);
    }


	@DeleteMapping(path = "/products/{countryCode:[a-zA-Z0-9]{2}}/{productCode}")
	@ResponseBody
	@ApiOperation(value = "Deletes product by code.")
	public ResponseEntity<String> removeProductByCode(
			@PathVariable @ApiParam(value = "Country code of the requested market") String countryCode,
			@PathVariable String productCode) {
		String deleteResult = service.removeProduct(countryCode, productCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}

	@DeleteMapping(path = "/vendors/{countryCode:[a-zA-Z0-9]{2}}/{vendorCode}")
	@ResponseBody
	@ApiOperation(value = "Deletes vendor by code. Disassociates given Vendor from all products.")
	public ResponseEntity<String> removeVendorByCode(
	        @PathVariable @ApiParam(value = "Country code of the requested market") String countryCode,
			@PathVariable String vendorCode) {
		String deleteResult = service.removeVendor(countryCode, vendorCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}
}
