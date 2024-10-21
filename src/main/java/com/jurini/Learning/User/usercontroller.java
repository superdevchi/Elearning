package com.jurini.Learning.User;


import com.jurini.Learning.User.UserData.userdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user/")
public class usercontroller {

    @Autowired
    userservice userservice;

    @GetMapping("/{USEREMAIL}")
    public Optional<userdata> getUser(@PathVariable("USEREMAIL") String USEREMAIL) {
        return userservice.getUser(USEREMAIL);
    }
}
