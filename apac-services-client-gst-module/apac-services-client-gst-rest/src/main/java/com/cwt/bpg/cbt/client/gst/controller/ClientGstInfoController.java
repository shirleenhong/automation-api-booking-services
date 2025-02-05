package com.cwt.bpg.cbt.client.gst.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cwt.bpg.cbt.client.gst.exception.ClientGstInfoUploadException;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.client.gst.service.ClientGstInfoService;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exceptions.FileUploadException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;

@RestController
@Api(tags = "Client GST Info")
@RequestMapping(path = "client-gst-info")
public class ClientGstInfoController {

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
    public ResponseEntity<ClientGstInfo> getClientGstInfo(
            @PathVariable @ApiParam(value = "GST Identification Number") String gstin) {
        ClientGstInfo response = clientGstInfoService.getClientGstInfo(gstin);
        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Saves client GST information")
    public ResponseEntity<ClientGstInfo> putClientGstInfo(@Valid @RequestBody  ClientGstInfo clientGstInfo) {
        return new ResponseEntity<>(clientGstInfoService.save(clientGstInfo), HttpStatus.OK);
    }

    @DeleteMapping(path = "{gstin}")
    @ResponseBody
    @ApiOperation("[Maintenance] Delete client GST information given a GSTIN")
    public ResponseEntity<String> removeClientGstInfo(@PathVariable @ApiParam(value = "GST Identification Number") String gstin) {
        return new ResponseEntity<>(clientGstInfoService.remove(gstin), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @ApiOperation("[Maintenance] Saves client GST information from excel file")
    public ResponseEntity<Map<String, String>> uploadClientGstInfo(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "validate", defaultValue = "true") boolean validate) throws ClientGstInfoUploadException, FileUploadException, IOException  {
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        clientGstInfoService.saveFromFile(file.getInputStream(), extension, validate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "saved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("csv")
    @ApiOperation("Download client GST  information in csv format")
    public ResponseEntity<byte[]> downloadClientGstInfo() throws ApiServiceException {
        WriteClientGstInfoFileResponse response = clientGstInfoService.writeFile();
        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFilename() + "\"");
        return new ResponseEntity<>(response.getData(), headers, HttpStatus.OK);
    }
}
