package com.opencourse.authusermanagement.apis;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.authusermanagement.dtos.UserDataWithCvDto;
import com.opencourse.authusermanagement.services.AuthenticationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/auth")
@AllArgsConstructor
public class AuthController {
    
    private final AuthenticationService service;

    //all 
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(required = true) String email,@RequestParam(required = true) String password){
        String token=service.login(email, password);
        return ResponseEntity.ok(token);
    }

    //all
    @PostMapping("/glogin")
    public ResponseEntity<String> loginWithGoogle(@RequestParam(required = true) String googleToken){
        String token=service.loginwithGoogle(googleToken);
        return ResponseEntity.ok(token);
    }

    //all
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody(required = true) UserDataWithCvDto data){
        service.signup(data.getUserData(), data.getCvId());
        return ResponseEntity.ok().build();
    }

    //all
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam(required = true) String token){
        Boolean isValid=service.tokenIsValid(token);
        return ResponseEntity.ok(isValid);
    }
    
}
