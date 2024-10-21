package com.jurini.Learning.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-registration")
public class userauthcontroller {
    @Autowired
    userservice userservice;

    @PostMapping("/register")
    public ResponseEntity<userloginresponse>register( @RequestBody userregistrationdto userregistrationdto){

        if(userregistrationdto.getUsername().isEmpty()){
//            return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userservice.userregister(userregistrationdto));
    }

    @PostMapping("/user-login")
    public ResponseEntity<userloginresponse>login( @RequestBody userregistrationdto userregistrationdto){
        userservice.login(userregistrationdto);
        if(userregistrationdto.getUsername().isEmpty()){
//            return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userservice.login(userregistrationdto));
    }

}
