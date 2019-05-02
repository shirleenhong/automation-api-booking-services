package com.cwt.bpg.cbt.smartflow.controller;

import com.cwt.bpg.cbt.smartflow.model.Codif;
import com.cwt.bpg.cbt.smartflow.service.CodifService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "Codif")
public class CodifController {

    @Autowired
    private CodifService codifService;

    @GetMapping(
            path = "/codif/harpno/{gdsPropId}/{keyType}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    @ApiOperation(value = "Returns list of all Codif harp numbers")
    public ResponseEntity<List<String>> getHarpNos(@PathVariable("gdsPropId") @ApiParam("GDSPropId") String gdsPropId,
                                                 @PathVariable("keyType") @ApiParam("Key Type") String keyType) {
        final List<String> harpList = new ArrayList<>();
        codifService.getCodifsByGdsPropIdAndKeyType(gdsPropId, keyType).forEach(codif -> {
            harpList.add(String.format("%6s", codif.getHarpNo()).replace(' ', '0'));
        });
        return new ResponseEntity<>(harpList, HttpStatus.OK);
    }

    @GetMapping(
            path = "/codif/{gdsPropId}/{keyType}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    @ApiOperation(value = "Returns Codif based on a [gdsPropId | keyType] combination.", hidden = true)
    public ResponseEntity<Codif> getCodif(@PathVariable("gdsPropId") @ApiParam("GDSPropId") String gdsPropId,
                                          @PathVariable("keyType") @ApiParam("Key Type") String keyType) {
        Codif codif = codifService.getCodif(gdsPropId, keyType);
        return new ResponseEntity<>(codif, codif != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @PutMapping(
            path = "/codif")
    @ResponseBody
    @ApiOperation(value = "[Maintenance] Save (insert/update) codif info.")
    public ResponseEntity<Codif> putCodif(@Valid @RequestBody Codif codif) {
        return new ResponseEntity<>(codifService.save(codif), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "/codif/{id}")
    @ResponseBody
    @ApiOperation(value = "[Maintenance] Delete Codif by id.")
    public ResponseEntity<String> removeCodif(@PathVariable("id") @ApiParam("id") String id) {
        String deleteResult = codifService.remove(id);
        HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(deleteResult, status);
    }

    @DeleteMapping(
            path = "/codif/{gdsPropId}/{keyType}")
    @ResponseBody
    @ApiOperation(value = "[Maintenance] Delete Codif by gdsPropId and keyType.")
    public ResponseEntity<String> removeCodif(@PathVariable("gdsPropId") @ApiParam("GDSPropId") String gdsPropId,
                                              @PathVariable("keyType") @ApiParam("Key Type") String keyType) {
        String deleteResult = codifService.remove(gdsPropId, keyType);
        HttpStatus status = deleteResult.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(deleteResult, status);
    }
}
