package com.cwt.bpg.cbt.finance;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
