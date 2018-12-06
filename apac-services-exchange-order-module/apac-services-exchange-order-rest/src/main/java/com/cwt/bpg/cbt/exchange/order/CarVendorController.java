package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.CarVendor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order - Car Vendor")
public class CarVendorController {

	@Autowired
	private ExchangeOrderService eoService;
	
	@GetMapping(path = "/exchange-order/car-vendors")
	@ResponseBody
	@ApiOperation(value = "Returns all car vendors.")
	public ResponseEntity<List<CarVendor>> getCarVendors() {
		return new ResponseEntity<>(eoService.getAllCarVendors(), HttpStatus.OK);
	}
	
	@Internal
    @PutMapping(path = "/exchange-order/car-vendors")
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) car vendor.")
    public ResponseEntity<CarVendor> saveCarVendor(@Valid @RequestBody CarVendor carVendor) {
        return new ResponseEntity<>(eoService.saveCarVendor(carVendor), HttpStatus.OK);
    }

	@Internal
	@DeleteMapping(path = "/exchange-order/car-vendors/{code}")
	@ResponseBody
	@ApiOperation(value = "Deletes car vendor given vendor code.")
	public ResponseEntity<String> deleteCarVendor(@PathVariable @ApiParam(value = "Car Vendor Code") String code) {
		String deleteResult = eoService.deleteCarVendor(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}

	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
