package com.cwt.bpg.cbt.service.fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/service-fees")
@Api(tags = "Service Fees")
public class ServiceFeeController
{
    @Autowired
    private ServiceFeeService service;

    @PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Calculates different service fees")
    public ResponseEntity<PriceBreakdown> calculatePriceInput(
            @RequestBody
            @ApiParam(value = "Values needed for calculation") PriceCalculationInput input)
    {
        return new ResponseEntity<>(service.calculate(input), HttpStatus.OK);
    }
}
