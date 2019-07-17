package com.cwt.bpg.cbt.client.gst.controller;

import static com.cwt.bpg.cbt.client.gst.service.ClientGstInfoReaderConfig.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.cwt.bpg.cbt.client.gst.service.ClientGstInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
    public ResponseEntity<Map<String, String>> uploadClientGstInfo(@RequestParam("file") MultipartFile file)
            throws Exception {
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	if (!(extension.equals(EXCEL_WORKBOOK) || extension.equals(MACRO_ENABLED_WORKBOOK) ||
                extension.equals(CSV))) {
    		throw new IllegalArgumentException("File must be in excel or csv format");
        }
        clientGstInfoService.saveFromFile(file.getInputStream(), extension);
        Map<String, String> response = new HashMap<>();
        response.put("message", "saving in progress");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("csv")
    @ApiOperation("Download client GST  information in csv format")
    public ResponseEntity<byte[]> downloadClientGstInfo() throws Exception {
        WriteClientGstInfoFileResponse response = clientGstInfoService.writeFile();
        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFilename() + "\"");
        return new ResponseEntity<>(response.getData(), headers, HttpStatus.OK);
    }
}
