package com.opencourse.authusermanagement.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.authusermanagement.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.Data;
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
        if(token!=null){
            //token is invalid
            if(!provider.isValid(token))
            {
                ServletOutputStream stream=response.getOutputStream();
                ObjectMapper mapper=new ObjectMapper();
                ApiError error=new ApiError();
                error.setErrorMsg("your session has expired");
                error.setStatus(HttpStatus.UNAUTHORIZED);
                stream.print(mapper.writeValueAsString(error));
                return;
            }
            
            //token is valid
            Authentication auth=provider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("role is " + auth.getAuthorities().toString());
        }else{//no token
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        
        chain.doFilter(request, response);
    }
    
}
@Data
class ApiError{
    private HttpStatus status;
    private String errorMsg;
    private List<String> errors;
}
