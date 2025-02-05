package com.cwt.bpg.cbt.obt;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.obt.model.ObtList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "OBT List")
public class ObtListController {

    @Autowired
    private ObtListService service;

    @GetMapping(path = "/obt-list/{countryCode}")
    @ResponseBody
    @ApiOperation(value = "Returns OBT List for a specific country.")
    public ResponseEntity<ObtList> getObtList(
            @PathVariable
            @ApiParam(value = "Country Code") String countryCode) {
        return new ResponseEntity<>(service.get(countryCode.toUpperCase()), HttpStatus.OK);
    }

    @PutMapping(path = "/obt-list")
    @ResponseBody
    @ApiOperation(value = "Saves (inserts/updates) OBT List for a specific country.")
    @Internal
    public ResponseEntity<ObtList> putObtList(@Valid @RequestBody ObtList obtList) {
        return new ResponseEntity<>(service.save(obtList), HttpStatus.OK);
    }

    @DeleteMapping(path = "/obt-list/{countryCode}")
    @ResponseBody
    @ApiOperation(value = "Deletes OBT List of a specific country.")
    @Internal
    public ResponseEntity<String> removeObtList(@PathVariable
                                                @ApiParam(value = "Country Code") String countryCode) {
        String deleteResult = service.delete(countryCode.toUpperCase());
        HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(deleteResult, status);
    }

}
