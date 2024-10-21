package com.jurini.Learning.User.Admin;

import com.jurini.Learning.JWT.jwtservice;
import com.jurini.Learning.User.userloginresponse;
import com.jurini.Learning.User.userregistrationdto;
import com.jurini.Learning.User.UserData.roledata;
import com.jurini.Learning.User.UserData.userdata;
import com.jurini.Learning.User.UserData.userdatarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class adminservice {

    @Autowired
    userdatarepository userdatarepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    jwtservice jwtservice;

    @Autowired
    AuthenticationManager authenticationManager;

//    adminservice(){
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }

    public userloginresponse adminregister(userregistrationdto userregistrationdto) {
        userdata userdata = new userdata();
        userdata.setEmail(userregistrationdto.getUsername());
        userdata.setPassword(passwordEncoder.encode(userregistrationdto.getPassword()));
        userdata.setRole(roledata.ADMIN);
        userdatarepository.save(userdata);

        var jwttoken = jwtservice.generateToken(userdata);

        return userloginresponse.builder()
                .token(jwttoken)
                .build();

    }

    public userloginresponse adminlogin(userregistrationdto userregistrationdto) {
        Authentication authenticationrequest = UsernamePasswordAuthenticationToken.unauthenticated(userregistrationdto.getUsername(), userregistrationdto.getPassword());
        Authentication authenticationresponse = authenticationManager.authenticate(authenticationrequest);

        if (! authenticationresponse.isAuthenticated()) {
            return userloginresponse.builder()
                    .token("INVALID LOGIN").build();
        }
        var userdata = userdatarepository.findbyemail(userregistrationdto.getUsername()).orElseThrow();
        var jwttoken = jwtservice.generateToken(userdata);
        return userloginresponse.builder()
                .token(jwttoken)
                .build();
    }
}
