package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.Remark;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Remarks")
public class RemarkController {
	
	@Autowired
	private RemarkService service;

	@GetMapping(path = "/remarks")
	@ResponseBody
	@ApiOperation(value = "Pulls all remarks.")
	public ResponseEntity<List<Remark>> getRemarks() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/remarks/{countryCode}/{productType}/{remarkType}")
	@ResponseBody
	@ApiOperation(value = "Pulls remarks given country code, product type, remark type.")
	public ResponseEntity<List<String>> getRemarks(
			@PathVariable @ApiParam(value = "2-character country code", required = true) String countryCode,
			@PathVariable @ApiParam(value= "Product type. Possible values: CT, CX, HL, TR, VI", required = true) String productType,
			@PathVariable @ApiParam(value = "Remark type. Possible vales: E, I", required = true) String remarkType) {
		return new ResponseEntity<>(service.getRemarks(countryCode, productType, remarkType), HttpStatus.OK);
	}
	
	@GetMapping(path = "/remarks/{remarkId}")
	@ResponseBody
	@ApiOperation(value = "Pulls remark by id.")
    @Internal
	public ResponseEntity<Remark> getRemarkById(
	        @PathVariable @ApiParam(value = "Remark Id") String remarkId) {
		return new ResponseEntity<>(service.getRemark(remarkId), HttpStatus.OK);
	}

	@PutMapping(path = "/remarks")
	@ResponseBody
	@ApiOperation(value = "Saves remark.")
	public ResponseEntity<Remark> putRemark(
			@Valid @RequestBody @ApiParam(value = "Remark object to save") Remark remark) {
		return new ResponseEntity<>(service.save(remark), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/remarks/{remarkId}")
	@ResponseBody
	@ApiOperation(value = "Deletes remark.")
	@Internal
	public ResponseEntity<String> removeRemark(
			@PathVariable @ApiParam(value = "Remark Id") String remarkId) {
		return new ResponseEntity<>(service.delete(remarkId), HttpStatus.OK);
	}

}
