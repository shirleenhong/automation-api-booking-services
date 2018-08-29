package com.cwt.bpg.cbt.obt;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.cwt.bpg.cbt.obt.model.ObtList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "OBT List")
public class ObtListController {

    @Autowired
    private ObtListService service;

    @GetMapping(path = "/obt-list/{countryCode}")
    @ResponseBody
    @ApiOperation(value = "Pulls OBT List for a specific country")
    public ResponseEntity<ObtList> getObtList(
            @PathVariable
            @ApiParam(value = "Country Code") String countryCode) {
        return new ResponseEntity<>(service.get(countryCode.toUpperCase()), HttpStatus.OK);
    }

    @PutMapping(path = "/obt-list")
    @ResponseBody
    @ApiOperation(value = "Save OBT List for a specific country")
    @Internal
    public ResponseEntity<ObtList> putObtList(@Valid @RequestBody ObtList obtList) {
        return new ResponseEntity<>(service.save(obtList), HttpStatus.OK);
    }

    @DeleteMapping(path = "/obt-list/{countryCode}")
    @ResponseBody
    @ApiOperation(value = "Remove OBT List of a specific country")
    @Internal
    public ResponseEntity<String> removeObtList(@PathVariable
                                                @ApiParam(value = "Country Code") String countryCode) {
        return new ResponseEntity<>(service.delete(countryCode.toUpperCase()), HttpStatus.OK);
    }

}
