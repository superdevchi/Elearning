package com.jurini.Learning.User.UserData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userdata")
public class userdata implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String Email;

    @JsonIgnore
    private String Password;

    @Enumerated(EnumType.STRING)
    private roledata role;


    @JsonIgnore()
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore()
    @Override
    public String getUsername() {
        return Email;
    }

    @JsonIgnore()
    @Override
    public String getPassword() {
        return Password;
    }
    @JsonIgnore()
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore()
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore()
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore()
    @Override
    public boolean isEnabled() {
        return true      ;
    }
}
