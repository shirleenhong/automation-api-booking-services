package com.cwt.bpg.cbt.exchange.order;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Report Header")
public class ReportHeaderController {

    @Autowired
    private ReportHeaderService service;

    @GetMapping(path = "/report-headers/{countryCode}")
    @ResponseBody
    @ApiOperation(value = "Returns report header for a specific country.")
    public ResponseEntity<ReportHeader> getReportHeader(
            @PathVariable
            @ApiParam(value = "Country Code") String countryCode) {
        return new ResponseEntity<>(service.getHeaderReport(countryCode.toUpperCase()), HttpStatus.OK);
    }

    @PutMapping(path = "/report-headers")
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) report header.")
    @Internal
    public ResponseEntity<ReportHeader> putReportHeader(@Valid @RequestBody ReportHeader header) {
    	
    	String countryCode = header.getCountryCode();
    	header.setCountryCode(countryCode.toUpperCase());
    	
        return new ResponseEntity<>(service.save(header), HttpStatus.OK);
    }

	@DeleteMapping(path = "/report-headers/{countryCode}")
	@ResponseBody
	@ApiOperation(value = "Deletes report header of a specific country.")
	@Internal
	public ResponseEntity<String> removeReportHeader(
			@PathVariable @ApiParam(value = "Country Code") String countryCode) {
		String deleteResult = service.delete(countryCode.toUpperCase());
		HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deleteResult, status);
	}
}
