package com.jurini.Learning.User.Admin;


import com.jurini.Learning.User.userloginresponse;
import com.jurini.Learning.User.userregistrationdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class admincontroller {

    @Autowired
    adminservice adminservice;

    @PostMapping("/register")
    public ResponseEntity<userloginresponse>create(@RequestBody userregistrationdto userregistrationdto) {
        return ResponseEntity.ok(adminservice.adminregister(userregistrationdto));
    }
    @PostMapping("/admin-login")
    public ResponseEntity<userloginresponse>login(@RequestBody userregistrationdto userregistrationdto) {
        return ResponseEntity.ok(adminservice.adminlogin(userregistrationdto));
    }
}
