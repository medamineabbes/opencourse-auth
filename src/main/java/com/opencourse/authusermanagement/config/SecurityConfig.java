package com.opencourse.authusermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
 
    //allow all requests
    @Bean
    public SecurityFilterChain authorisationServerFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable();
        http.cors().disable();
        http.httpBasic().disable();
        http.authorizeRequests().mvcMatchers("**/auth/**").permitAll();
        http.authorizeRequests().mvcMatchers("/api/v1/user/request").hasRole("ADMIN");
        http.authorizeRequests().mvcMatchers("/api/v1/user/ban").hasRole("ADMIN");
        http.authorizeRequests().mvcMatchers(HttpMethod.POST,"/api/v1/user/mentor").hasAnyRole("ADMIN","MENTOR","STUDENT","TEACHER");
        http.authorizeRequests().mvcMatchers("/api/v1/user/**").permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
