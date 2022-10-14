package com.opencourse.authusermanagement.externalservices;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.opencourse.authusermanagement.dtos.UserDataDto;
import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class GoogleService {
    
    private GoogleIdTokenVerifier verifier;
    private JsonFactory jsonFactory;
    private HttpTransport transport;

    @PostConstruct
    public void init(){
        jsonFactory=JacksonFactory.getDefaultInstance();
        transport=new NetHttpTransport();
        verifier=new GoogleIdTokenVerifier.Builder(transport,jsonFactory)
        .setAudience(List.of("779824097278-m4tkkenan7f5vorvijl6idosl7lgvue1.apps.googleusercontent.com"))
        .build();
    }

    //verify if token is valid
    public UserDataDto getUserData(String token){
        GoogleIdToken idToken;
        try {
            idToken=verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            throw new CustomAuthenticationException("something went wrong");
        }

        if(idToken != null){
            Payload payload=idToken.getPayload();
            UserDataDto userData=new UserDataDto();
            userData.setEmail(payload.getEmail());
            userData.setFirstname(payload.get("given_name").toString());
            userData.setLastname(payload.get("family_name").toString());
            userData.setImageUrl(payload.get("picture").toString());
            
            return userData;
        }else{
            return null;
        }
    }

}
