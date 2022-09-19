package com.opencourse.authusermanagement.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;
import com.opencourse.authusermanagement.security.JwtProvider;

import lombok.Setter;

@Setter
public class CustomAuthenticatFilter extends BasicAuthenticationFilter{
    
    private JwtProvider provider;

    public CustomAuthenticatFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                
        String token=request.getHeader("Authentication");
        
        if(token!=null){
            //token is invalid
            if(!provider.isValid(token))
            throw new CustomAuthenticationException();
            
            //token is valid
            Authentication auth=provider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        
        }else{//no token
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        
        chain.doFilter(request, response);
    }
    
}