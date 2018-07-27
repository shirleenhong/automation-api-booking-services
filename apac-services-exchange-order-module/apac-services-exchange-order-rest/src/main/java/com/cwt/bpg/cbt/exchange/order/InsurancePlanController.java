package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Insurance")
public class InsurancePlanController {

	@Autowired
	private InsurancePlanService service;

	@GetMapping(path = "/insurance", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Pulls insurance list")
	public ResponseEntity<List<InsurancePlan>> getInsurancePlanList() {

		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@Internal
	@PutMapping(
			path = "/insurance",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Add/Updates insurance plan")
	public ResponseEntity<InsurancePlan> updateInsurancePlan(@Valid @RequestBody InsurancePlan insurance) {

		return new ResponseEntity<>(service.putInsurancePlan(insurance), HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/insurance/{type}")
	@ResponseBody
	@ApiOperation(value = "Remove insurance plan")
	public ResponseEntity<String> removeInsurancePlan(@PathVariable String type) {

		return new ResponseEntity<>(service.remove(type), HttpStatus.OK);
	}

}