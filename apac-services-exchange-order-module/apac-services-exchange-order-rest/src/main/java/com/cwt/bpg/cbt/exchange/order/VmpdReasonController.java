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
import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order - VMPD Reason Codes")
public class VmpdReasonController {

	@Autowired
	private ExchangeOrderService eoService;

	@GetMapping(path = "/exchange-order/vmpd")
	@ResponseBody
	@ApiOperation(value = "Returns all Reasons for Issue.")
	public ResponseEntity<List<VmpdReasonCode>> getVmpdReasonCodes() {
		return new ResponseEntity<>(eoService.getAllVmpdReasonCodes(), HttpStatus.OK);
	}

	@Internal
    @PutMapping(path = "/exchange-order/vmpd")
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) a 'Reason for Issue'.")
    public ResponseEntity<VmpdReasonCode> saveVmpdReasonCode(@Valid @RequestBody VmpdReasonCode reasonCode) {
        return new ResponseEntity<>(eoService.saveVmpdReasonCode(reasonCode), HttpStatus.OK);
    }
	
	@Internal
	@DeleteMapping(path = "/exchange-order/vmpd/{code}")
	@ResponseBody
	@ApiOperation(value = "Deletes 'Reason for Issue' given a vmpd code.")
	public ResponseEntity<String> deleteVmpdReasonCode(@PathVariable @ApiParam(value = "VMPD Code") String code) {
		String deleteResult = eoService.deleteVmpdReasonCode(code);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}
	
	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
