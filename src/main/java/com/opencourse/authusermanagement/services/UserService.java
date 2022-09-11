package com.opencourse.authusermanagement.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencourse.authusermanagement.dtos.UserRequestDto;
import com.opencourse.authusermanagement.dtos.mappers.UserRequestMapper;
import com.opencourse.authusermanagement.entities.EmailConfirmationsRequest;
import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.exceptions.EmailConfirmationException;
import com.opencourse.authusermanagement.exceptions.UserNotFoundException;
import com.opencourse.authusermanagement.properties.EmailProperties;
import com.opencourse.authusermanagement.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final UserRequestMapper requestMapper;
    private EmailProperties emailProp;
    
    

    //only admin
    public void banUser(Long userId){
        
        User user=repo.findById(userId)
        .orElseThrow(()->new UserNotFoundException(userId));

        user.setIsBanned(true);

        //send email notification
    }

    //all
    public void confirmEmail(Long userId,String uuid){

        User user=repo.findById(userId)
        .orElseThrow(()->new UserNotFoundException(userId));

        //get requests from database
        EmailConfirmationsRequest request=
        user.getConfirmationRequests()
        .stream()
        .filter(req->req.getCode().equals(uuid))
        .findAny()
        .orElseThrow(()->new EmailConfirmationException());
        
        //check if the time is correct
        LocalDateTime now=LocalDateTime.now();
        if(request.getSentAt().plusMinutes(emailProp.getEvailabilityMin()).isBefore(now))
        throw new EmailConfirmationException();
        
        //confirm email
        user.setIsConfirmed(true);
        repo.save(user);

    }
    
    //all
    public UserRequestDto getUser(Long userId){
        
        User user=repo.findById(userId)
        .orElseThrow(()->new UserNotFoundException(userId));

        UserRequestDto dto=requestMapper.toUserRequestDto(user);

        return dto;
    }

    //authentic users
    public void selectMentor(Long userId,Long mentorId){
        User user=repo.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        User mentor=repo.findById(mentorId).orElseThrow(()->new UserNotFoundException(mentorId));
        user.setMentor(mentor);
        repo.save(user);
    }

    //all
    public List<UserRequestDto> getAllMentors(){
        List<User> Mentors = repo.findByRole(Role.MENTOR);
        return Mentors.stream()
        .map((mentor)->requestMapper.toUserRequestDto(mentor))
        .collect(Collectors.toList());
    }

}
