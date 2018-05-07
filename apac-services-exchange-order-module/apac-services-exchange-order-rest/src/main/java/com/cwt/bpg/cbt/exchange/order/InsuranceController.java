package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.Insurance;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Exchange Order")
public class InsuranceController {
	
	@Autowired
	private InsuranceService service;
	
	@GetMapping(path="/insurance")
	@ResponseBody
    @ApiOperation(value = "Pulls insurance list")
	public ResponseEntity<List<Insurance>> getInsuranceList() {		
		
		return new ResponseEntity<>(service.getInsuranceList(), HttpStatus.OK);
	}
	
	@Internal
	@PutMapping(path="/insurance")
	@ResponseBody
    @ApiOperation(value = "Add/Updates insurance plan")
	public ResponseEntity<Insurance> updateInsurance(
			@Valid @RequestBody Insurance insurance) {		
		
		return new ResponseEntity<>(service.putInsurance(insurance), HttpStatus.OK);
	}

}
