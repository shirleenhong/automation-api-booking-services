package com.cwt.bpg.cbt.service.fee;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicefee")
public class ServiceFeeController {
	
	@GetMapping(produces = { MediaType.TEXT_PLAIN_VALUE })
    public @ResponseBody String appInfo()
    {
        return UUID.randomUUID().toString();
    }

}
