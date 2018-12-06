package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	@ApiOperation(value = "Returns list of all insurance plans.")
	public ResponseEntity<List<InsurancePlan>> getInsurancePlanList() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@Internal
	@PutMapping(
			path = "/insurance",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@ApiOperation(value = "Saves (inserts/updates) insurance plan.")
	public ResponseEntity<InsurancePlan> updateInsurancePlan(@Valid @RequestBody InsurancePlan insurance) {

		InsurancePlan updatedResult = service.putInsurancePlan(insurance);
		return new ResponseEntity<>(updatedResult, HttpStatus.OK);
	}

	@Internal
	@DeleteMapping(path = "/insurance/{id}")
	@ResponseBody
	@ApiOperation(value = "Deletes insurance plan given id.")
	public ResponseEntity<String> removeInsurancePlan(@PathVariable String id) {

		String deleteResult = service.remove(id);
		return new ResponseEntity<>(deleteResult, HttpStatus.OK);
	}

}
