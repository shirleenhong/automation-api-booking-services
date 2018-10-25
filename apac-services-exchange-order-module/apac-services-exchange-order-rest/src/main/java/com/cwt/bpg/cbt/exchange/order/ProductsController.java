package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
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
	@ApiOperation(value = "Pulls list of products and vendors for a specific market")
	public ResponseEntity<List<BaseProduct>> getProducts(
			@PathVariable @ApiParam(value = "Country code of the requested market") String countryCode) {
		return new ResponseEntity<>(service.getProducts(countryCode.toUpperCase()), HttpStatus.OK);
	}

	@PutMapping(path = "/products/{countryCode:[a-zA-Z0-9]{2}}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Updates product list for a country.")
    public ResponseEntity<String> saveProduct(
            @PathVariable @ApiParam(value = "Country code of the requested market") String countryCode,
            @RequestParam(required = false) boolean insert,
            @RequestBody Product product) {
        String result = service.saveProduct(countryCode.toUpperCase(), product, insert);
        getProducts(countryCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(path = "/products/in", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Updates product list for a country.")
    public ResponseEntity<String> saveIndiaProduct(
            @RequestParam(required = false) boolean insert,
            @RequestBody IndiaProduct product) {
        return new ResponseEntity<>(service.saveIndiaProduct(product, insert), HttpStatus.OK);
    }
    
	@DeleteMapping(path = "/products/{countryCode:[a-zA-Z0-9]{2}}/{productCode}")
	@ResponseBody
	@ApiOperation(value = "Removes product by code")
	public ResponseEntity<String> removeProductByCode(@PathVariable String countryCode,
			@PathVariable String productCode) {
		String deleteResult = service.removeProduct(countryCode, productCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		getProducts(countryCode);
		return new ResponseEntity<>(deleteResult, status);
	}
	
	@DeleteMapping(path = "/vendors/{countryCode:[a-zA-Z0-9]{2}}/{vendorCode}")
	@ResponseBody
	@ApiOperation(value = "Removes vendor by code")
	public ResponseEntity<String> removeVendorByCode(@PathVariable String countryCode,
			@PathVariable String vendorCode) {
		String deleteResult = service.removeVendor(countryCode, vendorCode);
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		getProducts(countryCode);
		return new ResponseEntity<>(deleteResult, status);
	}
}
