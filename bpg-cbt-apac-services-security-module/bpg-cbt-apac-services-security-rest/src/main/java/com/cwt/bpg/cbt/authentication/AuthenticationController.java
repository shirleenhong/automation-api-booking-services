package com.cwt.bpg.cbt.authentication;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class AuthenticationController {
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public @ResponseBody String login()
    {
        return "Test";
    }
}
