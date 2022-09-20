package com.opencourse.authusermanagement.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;
import com.opencourse.authusermanagement.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class CustomAuthenticatFilter extends OncePerRequestFilter{
    
    private final JwtProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                
        String token=request.getHeader("Authentication");
        log.info("filter is running");
        if(token!=null){
            //token is invalid
            if(!provider.isValid(token))
            throw new CustomAuthenticationException();
            
            //token is valid
            Authentication auth=provider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("role is " + auth.getAuthorities().toString());
        }else{//no token
            SecurityContextHolder.getContext().setAuthentication(null);
            log.warn("no token found");
        }
        
        chain.doFilter(request, response);
    }
    
}