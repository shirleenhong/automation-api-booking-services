package com.cwt.bpg.cbt.client.gst.controller;



import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoResponse;
import com.cwt.bpg.cbt.client.gst.service.ClientGstInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@Api(tags = "Client GST Info")
@RequestMapping(path = "client-gst-info")
public class ClientGstInfoController {

    private static final String EXCEL_WORKBOOK = ".xlsx";
    private static final String MACRO_ENABLED_WORKBOOK = ".xlsm";

    @Autowired
    private ClientGstInfoService clientGstInfoService;
    
    @GetMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Get all client GST information")
    public ResponseEntity<List<ClientGstInfo>> getAllClientGstInfo() {
        return new ResponseEntity<>(clientGstInfoService.getAllClientGstInfo(), HttpStatus.OK);
    }

    @GetMapping(path = "{gstin}")
    @ResponseBody
    @ApiOperation("Get client GST information given a GSTIN")
    public ResponseEntity<ClientGstInfoResponse> getClientGstInfo(
            @PathVariable @ApiParam(value = "GST Identification Number") String gstin,
            @RequestParam @ApiParam(value= "Airline code", required = true) List<String> airlineCodes) {
        if(airlineCodes == null || airlineCodes.isEmpty()) {
            throw new IllegalArgumentException("airlineCode is required");
        }
        ClientGstInfoResponse response = clientGstInfoService.getClientGstInfo(gstin, airlineCodes);
        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(response.getAirlineCodes().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Saves client GST information")
    public ResponseEntity<ClientGstInfo> putClientGstInfo(@Valid @RequestBody ClientGstInfo clientGstInfo) {
        return new ResponseEntity<>(clientGstInfoService.save(clientGstInfo), HttpStatus.OK);
    }

    @DeleteMapping(path = "{gstin}")
    @ResponseBody
    @ApiOperation("[Maintenance] Delete client GST information given a GSTIN")
    public ResponseEntity<String> removeClientGstInfo( @PathVariable @ApiParam(value = "GST Identification Number") String gstin) {
        return new ResponseEntity<>(clientGstInfoService.remove(gstin), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Saves client GST information from excel file")
    public ResponseEntity<Map<String, String>> uploadClientGstInfo(@RequestParam("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") boolean includeGstAirlines)
            throws Exception {
//        if (!(file.getOriginalFilename().endsWith(EXCEL_WORKBOOK) || file.getOriginalFilename()
//                .endsWith(MACRO_ENABLED_WORKBOOK))) {
//            throw new IllegalArgumentException("File must be in excel format");
//        }
        clientGstInfoService.saveFromExcelFile(new BufferedInputStream(file.getInputStream()), includeGstAirlines);
        Map<String, String> response = new HashMap<>();
        response.put("message", "saving in progress");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
