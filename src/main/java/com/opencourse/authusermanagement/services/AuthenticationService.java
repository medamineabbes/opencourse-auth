package com.opencourse.authusermanagement.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opencourse.authusermanagement.dtos.UserDataDto;
import com.opencourse.authusermanagement.dtos.mappers.UserDataMapper;
import com.opencourse.authusermanagement.entities.EmailConfirmationsRequest;
import com.opencourse.authusermanagement.entities.MentorTeacherRequest;
import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;
import com.opencourse.authusermanagement.exceptions.EmailAlreadyExistsException;
import com.opencourse.authusermanagement.externalservices.EmailService;
import com.opencourse.authusermanagement.externalservices.GoogleService;
import com.opencourse.authusermanagement.repos.EmailConfirmationRepo;
import com.opencourse.authusermanagement.repos.MentorTeacherRequestRepo;
import com.opencourse.authusermanagement.repos.UserRepo;
import com.opencourse.authusermanagement.security.JwtProvider;
import com.opencourse.authusermanagement.util.SequeceGenerator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    
    private final UserRepo repo;
    private final EmailConfirmationRepo emailRepo;
    private final MentorTeacherRequestRepo mtRepo;
    private final EmailService emailService;
    private final GoogleService google;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserDataMapper dataMapper;
    private final SequeceGenerator seqGen;

    //all users
    public String login(String email,String password){
        log.info("email is " + email);
        log.info("password is " + password);

        //incorrect email
        User user=repo.findByEmail(email)
        .orElseThrow(()->new CustomAuthenticationException());

        //display passwords
        log.info("encoded passs " + passwordEncoder.encode(password));
        log.info("user password " + user.getPassword());

        
        //incorrect password
        if(!passwordEncoder.matches(password, user.getPassword()))
        throw new CustomAuthenticationException();

        //account not active
        if(!user.getIsActive())
        throw new CustomAuthenticationException("account not active");

        //email not confirmned
        if(!user.getIsConfirmed())
        throw new CustomAuthenticationException("email not verified");

        //banned users
        if(user.getIsBanned())
        throw new CustomAuthenticationException("user banned");


        //generate token
        String token=jwtProvider.createToken(user);
        return token;
    }


    //all users
    public String loginwithGoogle(String googleToken){

        //get user info from google service
        UserDataDto userDataDto = google.getUserData(googleToken);
        userDataDto.setRole(Role.STUDENT.toString());
        //check if user exists
        //if not then add to database
        Optional<User> opUser=repo.findByEmail(userDataDto.getEmail());
        if(opUser.isEmpty()){
            User user=dataMapper.toUser(userDataDto);
            user.setId(seqGen.generate(User.sequenceNama));
            repo.save(user);
            //return token
            return jwtProvider.createToken(user);
        }

        return jwtProvider.createToken(opUser.get());
    
    }
    

    // all users
    public void signup(UserDataDto userData,String cvId){

        User user=dataMapper.toUser(userData);
        Optional<User> exists=repo.findByEmail(userData.getEmail());
        if(exists.isPresent())
        throw new EmailAlreadyExistsException(userData.getEmail()); 

        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(seqGen.generate(User.sequenceNama));
        
        //if user is student then activate
        if(user.getRole().compareTo(Role.STUDENT)==0)
        user.setIsActive(true);
        else
        user.setIsActive(false);
        user.setIsBanned(false);
        user.setIsConfirmed(false);
    

        //save user
        repo.save(user);
        log.info("user saved");

        //add montor teacher request
        if(user.getRole().compareTo(Role.STUDENT)!=0){
            MentorTeacherRequest activationRequest=new MentorTeacherRequest();
            activationRequest.setDate(LocalDateTime.now());
            activationRequest.setIsAccepted(false);
            activationRequest.setUser(user);
            activationRequest.setCvId(cvId);
            mtRepo.save(activationRequest);
        }

        //send confirmation request
        String uuid=UUID.randomUUID().toString();
        EmailConfirmationsRequest request=new EmailConfirmationsRequest();
        request.setCode(uuid);
        request.setSentAt(LocalDateTime.now());
        request.setUser(user);
        emailRepo.save(request);
        log.info("sending confirmation email");       
        //send code via email
        Map<String,Object> data=new HashMap<>();
        data.put("firstname", user.getFirstname());
        data.put("lastname", user.getLastname());
        data.put("uuid",uuid);
        data.put("userid", user.getId());
        
        //email Sent
        emailService.sendEmailConfirmation(user,uuid);
    }

    //all users
    public Boolean tokenIsValid(String token){
        return jwtProvider.isValid(token);
    }

}
