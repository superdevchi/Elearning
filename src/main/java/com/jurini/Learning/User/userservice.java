package com.jurini.Learning.User;

import com.jurini.Learning.JWT.jwtservice;
import com.jurini.Learning.User.UserData.roledata;
import com.jurini.Learning.User.UserData.userdata;
import com.jurini.Learning.User.UserData.userdatarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class userservice {

    @Autowired
    userdatarepository userrepository;

    @Autowired
    jwtservice jwtservice;

    PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    userservice(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public userloginresponse userregister(userregistrationdto userregistrationdto) {
        userdata userdata = new userdata();

        userdata.setEmail(userregistrationdto.getUsername());
        userdata.setPassword(passwordEncoder.encode(userregistrationdto.getPassword()));
        userdata.setRole(roledata.USER);
        userrepository.save(userdata);
        var jwttoken = jwtservice.generateToken(userdata);

        return userloginresponse.builder()
                .token(jwttoken)
                .build();
    }

    public userloginresponse login(userregistrationdto userregistrationdto) {
        Authentication authenticationrequest = UsernamePasswordAuthenticationToken.unauthenticated(userregistrationdto.getUsername(), userregistrationdto.getPassword());
        Authentication authenticationresponse = authenticationManager.authenticate(authenticationrequest);

        if (! authenticationresponse.isAuthenticated()) {
            return userloginresponse.builder()
                    .token("INVALID LOGIN").build();
        }
        var userdata = userrepository.findbyemail(userregistrationdto.getUsername()).orElseThrow();
        var jwttoken = jwtservice.generateToken(userdata);
        return userloginresponse.builder()
                .token(jwttoken)
                .build();
    }

    public Optional<userdata> getUser(String email){
        return userrepository.findbyemail(email);
    }
}
