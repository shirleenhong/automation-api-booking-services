package com.cwt.bpg.cbt.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/{uid}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Returns Finance User.")
    @Internal
    public ResponseEntity<User> getUser(@PathVariable String uid) {
        return new ResponseEntity<>(userService.getUser(uid), HttpStatus.OK);
    }
    
    @GetMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Returns All User.")
    @Internal
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    
    @PutMapping(path = "/user",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(value = "Saves Finance User.")
    @Internal
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{uid}", produces = { MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "Delete Finance User.")
    @Internal
    public ResponseEntity<String> deleteUser(@PathVariable String uid) {
        String result = userService.deleteUser(uid);
        HttpStatus deleteStatus = result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(result, deleteStatus);
    }

}
