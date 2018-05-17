package com.cwt.bpg.cbt.exchange.order;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Fares Shop")
public class FaresShopController {

	@GetMapping(path = "/fares-shop")
	@ResponseBody
	@ApiOperation(value = "Pulls client pricing")
	public ResponseEntity<?> getFaresShopOptions() {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PutMapping(path = "/fares-shop")
	@ResponseBody
	@ApiOperation(value = "Put client pricing")
	@Internal
	public ResponseEntity<?> putFaresShopOptions(Supplier<?> supplier) {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@DeleteMapping(path = "/fares-shop")
	@ResponseBody
	@ApiOperation(value = "Remove client pricing")
	@Internal
	public ResponseEntity<?> removeFaresShopOptions(Supplier<?> supplier) {

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
