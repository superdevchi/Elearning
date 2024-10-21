package com.jurini.Learning.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class jwtauthenticationfilter extends OncePerRequestFilter {

    @Autowired
    private jwtservice jwtService;

    //    remember to run on bean application config
    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authhHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authhHeader == null || !authhHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authhHeader.substring(7);

        userEmail = jwtService.getUsernameEmailFromJWT(jwt);
        System.out.println(userEmail + "user email for userdetails");
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//        trying to get userinfo from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

//        check if token is valid
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
