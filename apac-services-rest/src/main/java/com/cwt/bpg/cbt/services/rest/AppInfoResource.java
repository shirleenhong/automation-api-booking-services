package com.cwt.bpg.cbt.services.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app-info")
@Api(tags = "App Info")
public class AppInfoResource {
	
	@Value("${com.bpg.cbt.apac.service.buildNumber}")
	private String buildNumer;

	@Value("${com.bpg.cbt.apac.service.version}")
	private String applicationVersion;

    
    
    @GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Displays application build number and version")
    public Map<String, String> appInfo()
    {
    	 final Map<String, String> result = new HashMap<>();
         result.put("com.bpg.cbt.apac.service.buildNumber", buildNumer);
         result.put("com.bpg.cbt.apac.service.version", applicationVersion);
         result.put("test.check", "Ok");
         return result;
    }

}
