package com.opencourse.authusermanagement.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencourse.authusermanagement.dtos.RoleRequestsDto;
import com.opencourse.authusermanagement.dtos.mappers.RoleRequestMapper;
import com.opencourse.authusermanagement.entities.MentorTeacherRequest;
import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.externalservices.EmailService;
import com.opencourse.authusermanagement.repos.MentorTeacherRequestRepo;
import com.opencourse.authusermanagement.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestService {
    
    private final UserRepo repo;
    private final MentorTeacherRequestRepo mtRepo; 
    private final EmailService emailService;
    private final RoleRequestMapper rMapper;
    
    //only admin
    public void deleteRequest(String requestId){
        MentorTeacherRequest request=mtRepo.findById(requestId)
        .orElseThrow(()-> new RuntimeException("not found"));
        
        //email notification
        User user=request.getUser();
        emailService.sendRejectionEmail(user);

        mtRepo.delete(request);
    }

    //only admin
    public List<RoleRequestsDto> getRequests(){
        List<MentorTeacherRequest> requests=mtRepo.findByIsAccepted(false);
        List<RoleRequestsDto> requestsDto=requests.stream()
        .map((request)->rMapper.toDto(request))
        .collect(Collectors.toList());
        return requestsDto;
    }

    //only admin
    public void acceptRequest(String requestId){ 
        //accept request
        MentorTeacherRequest request=mtRepo.findById(requestId)
        .orElseThrow(()-> new RuntimeException("request not found"));
        request.setIsAccepted(true);
        mtRepo.save(request);
    
        //activate user
        User user=request.getUser();
        user.setIsActive(true);
        repo.save(user);
        
       //send email notification
       emailService.sendAcceptanceEmail(user);
    }

}
