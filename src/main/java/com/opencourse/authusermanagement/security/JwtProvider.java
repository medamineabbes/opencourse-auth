package com.opencourse.authusermanagement.security;

import java.util.Base64;
import java.util.Date;
import java.util.Base64.Decoder;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.opencourse.authusermanagement.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtProvider {
   
    private String secretKey="secretkey1258";
    private Long validityInMilliseconds=3600000L;
    private SignatureAlgorithm sa;

    @PostConstruct
    public void init(){
        sa=SignatureAlgorithm.HS256;
        secretKey=Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user){

        Claims claims=Jwts.claims().setSubject(user.getEmail());

        //jwt data
        claims.put("id", user.getId());
        claims.put("role", user.getRole().toString());
        Date now=new Date();
        Date validity=new Date(now.getTime()+ validityInMilliseconds);

        return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(sa, secretKey)
        .compact();
    }

    public boolean isValid(String token){
        try{
            Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(token)
            .getBody();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){

        Decoder decoder=Base64.getDecoder();
        String[] chunks=token.split("\\.");
        String payload=new String(decoder.decode(chunks[1]));
        JSONObject body=new JSONObject(payload);
        JwtAuthentication authentication=new JwtAuthentication(body.getLong("id"), 
        body.getString("sub"), 
        body.getString("role"), 
        true);
        return authentication;
    }

}
