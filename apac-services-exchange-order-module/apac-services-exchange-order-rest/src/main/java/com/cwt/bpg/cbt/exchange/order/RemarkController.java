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
import com.cwt.bpg.cbt.exchange.order.model.Remark;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Remarks")
public class RemarkController {
	
	@Autowired
	private RemarkService service;

	@GetMapping(path = "/remarks")
	@ResponseBody
	@ApiOperation(value = "Pulls remarks")
	public ResponseEntity<List<Remark>> getRemarks() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/remarks/{countryCode}/{productType}/{remarkType}")
	@ResponseBody
	@ApiOperation(value = "Pulls remark by Id")
	public ResponseEntity<List<Remark>> getRemarks(@PathVariable String countryCode, @PathVariable String productType, @PathVariable String remarkType) {
		return new ResponseEntity<>(service.getRemarks(countryCode, productType, remarkType), HttpStatus.OK);
	}
	
	@GetMapping(path = "/remarks/{remarkId}")
	@ResponseBody
	@ApiOperation(value = "Pulls remark by Id")
	public ResponseEntity<Remark> getRemarkById(@PathVariable String remarkId) {
		return new ResponseEntity<>(service.getRemark(remarkId), HttpStatus.OK);
	}

	@PutMapping(path = "/remarks")
	@ResponseBody
	@ApiOperation(value = "Saves remarks")
	@Internal
	public ResponseEntity<Remark> putAirlineRules(@Valid @RequestBody Remark remark) {
		return new ResponseEntity<>(service.save(remark), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/remarks/{remarkId}")
	@ResponseBody
	@ApiOperation(value = "Removes remark")
	@Internal
	public ResponseEntity<String> removeRemark(@PathVariable String remarkId) {
		return new ResponseEntity<>(service.delete(remarkId), HttpStatus.OK);
	}

}
