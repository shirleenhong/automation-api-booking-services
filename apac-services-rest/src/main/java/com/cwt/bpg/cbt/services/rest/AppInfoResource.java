package com.cwt.bpg.cbt.services.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
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
	
	private Properties props = new Properties();

    public AppInfoResource() throws IOException
    {
        this.props.load(new ClassPathResource("apac-services.build.properties").getInputStream());
    }
    
    @GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Displays application build number and version")
    public Map<String, String> appInfo()
    {
        final Map<String, String> result = new HashMap<>();
        for (Entry<Object, Object> entry : props.entrySet())
        {
            result.put((String) entry.getKey(), (String) entry.getValue());
        }
        return result;
    }

}
