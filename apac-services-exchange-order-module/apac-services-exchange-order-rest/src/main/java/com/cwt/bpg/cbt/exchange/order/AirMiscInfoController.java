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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Exchange Order - Air Misc Info")
public class AirMiscInfoController {

	@Autowired
	private ExchangeOrderService eoService;
	
	@GetMapping(path = "/exchange-order/air-misc-info/{clientAccountNumber}")
	@ResponseBody
	@ApiOperation(value = "Returns all Air Misc Info for a given client.")
	public ResponseEntity<List<AirMiscInfo>> getAirMiscInfos(
			@PathVariable String clientAccountNumber,
			@RequestParam(value = "reportingFieldTypeIds", required = false) List<String> reportingFieldTypeIds) {
		return new ResponseEntity<>(
				eoService.getAirMiscInfos(clientAccountNumber, reportingFieldTypeIds),
				HttpStatus.OK);
	}

	@Internal
    @PutMapping(path = "/exchange-order/air-misc-info")
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) Air Misc Info.")
    public ResponseEntity<AirMiscInfo> saveAirMiscInfo(@Valid @RequestBody AirMiscInfo airMiscInfo) {
        return new ResponseEntity<>(eoService.saveAirMiscInfo(airMiscInfo), HttpStatus.OK);
    }

	@Internal
	@DeleteMapping(path = "/exchange-order/air-misc-info/{id}")
	@ResponseBody
	@ApiOperation(value = "Deletes Air Misc Info given an id.")
	public ResponseEntity<String> deleteAirMiscInfo(@PathVariable String id) {
		String deleteResult = eoService.deleteAirMiscInfo(id);
		HttpStatus status = checkDeleteResult(deleteResult);
		return new ResponseEntity<>(deleteResult, status);
	}

	private HttpStatus checkDeleteResult(String deleteResult) {
		return deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	}
}
