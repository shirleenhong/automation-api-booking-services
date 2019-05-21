package com.cwt.bpg.cbt.client.gst.controller;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.service.ClientGstInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Client GST Info")
@RequestMapping(path = "client-gst-info")
public class ClientGstInfoController {

    @Autowired
    private ClientGstInfoService clientGstInfoService;

    @GetMapping(path = "{gstin}")
    @ResponseBody
    @ApiOperation("Get client GST information given a GSTIN")
    public ResponseEntity<ClientGstInfo> getClientGstInfo(
            @PathVariable
            @ApiParam(value = "GST Identification Number")
                    String gstin) {
        ClientGstInfo clientGstInfo = clientGstInfoService.getClientGstInfo(gstin);
        return (clientGstInfo != null)?
                new ResponseEntity<>(clientGstInfo, HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Saves client GST information")
    public ResponseEntity<ClientGstInfo> putClientGstInfo(@Valid @RequestBody ClientGstInfo clientGstInfo) {
        return new ResponseEntity<>(clientGstInfoService.save(clientGstInfo), HttpStatus.OK);
    }
}
