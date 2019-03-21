package com.cwt.bpg.cbt.finance;

import io.swagger.annotations.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Api(tags = "Users")
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(String uid) {
        return repository.get(uid);
    }

    public User saveUser(User user) {
        return repository.put(user);
    }

    public String deleteUser(String uid) {
        return repository.remove(uid);
    }
    
    public List<User> getAll()
    {
        return repository.getAll();
    }
    
}
