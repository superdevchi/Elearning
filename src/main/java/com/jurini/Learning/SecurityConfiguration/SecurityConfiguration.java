package com.jurini.Learning.SecurityConfiguration;


import com.jurini.Learning.JWT.jwtauthenticationfilter;
import com.jurini.Learning.User.UserData.roledata;
import com.jurini.Learning.User.UserData.userdatarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    userdatarepository repository;

    @Autowired
    jwtauthenticationfilter jwtAuthenticationfilter;

    @Autowired
    customaccessdeniedhandler customaccessdeniedhandler;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findbyemail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        roledata roledata;
        http
                .authorizeRequests( auth -> {
                auth.requestMatchers("/api/user-registration/**", "/swagger-ui/**", "/javainuse-openapi/**", "/v3/api-docs/**").permitAll();
                auth.requestMatchers("/admin/**").permitAll();
                auth.requestMatchers("/course/**").authenticated();
                auth.anyRequest().authenticated();
                });
        http.exceptionHandling(exception -> {
            exception.accessDeniedHandler(customaccessdeniedhandler);
            exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        });
        http.authenticationProvider(authenticationProvider());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.addFilterBefore(jwtAuthenticationfilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }




    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://app-backend.com", "http://localhost:2000")); //TODO: update backend url
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
