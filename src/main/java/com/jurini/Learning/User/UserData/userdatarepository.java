package com.jurini.Learning.User.UserData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface userdatarepository extends JpaRepository<userdata,Integer> {
    @Query(value = "select * from userdata where email=?1 ", nativeQuery = true)
    Optional<userdata> findbyemail(String email);
}
