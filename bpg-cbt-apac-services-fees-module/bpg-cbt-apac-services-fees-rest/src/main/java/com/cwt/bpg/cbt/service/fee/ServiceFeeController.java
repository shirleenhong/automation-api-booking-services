package com.cwt.bpg.cbt.service.fee;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;

@RestController
@RequestMapping("/servicefee")
public class ServiceFeeController {
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public @ResponseBody PriceBreakdown appInfo()
    {
		PriceBreakdown sf = new PriceBreakdown(10,20);
		
        return sf;
    }

}
