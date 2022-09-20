package com.opencourse.authusermanagement.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.authusermanagement.dtos.UserRequestDto;
import com.opencourse.authusermanagement.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    //all
    @GetMapping("/{userId}")
    public ResponseEntity<UserRequestDto> getUser(@PathVariable(required = true) Long userId){
        UserRequestDto dto=service.getUser(userId);
        return ResponseEntity.ok(dto);
    }

    //all
    @GetMapping("/mentors")
    public ResponseEntity<List<UserRequestDto>> getAllMentors(){
        List<UserRequestDto> mentors=service.getAllMentors();
        return ResponseEntity.ok(mentors);
    }

    //authentic users only
    @PostMapping("/mentor")
    public ResponseEntity<Object> selectMentor(@RequestParam(required = true) Long mentorId){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        Long userId=Long.valueOf(auth.getName());
        service.selectMentor(userId, mentorId);
        return ResponseEntity.ok().build();
    }

    //only admin
    @PostMapping("/ban")
    public ResponseEntity<Object> banUser(@RequestParam(required = true) Long userId){
        service.banUser(userId);
        return ResponseEntity.ok().build();
    }

    //all
    @GetMapping("/{uuid}/{userId}")
    public ResponseEntity<Object> confirmEmail(@PathVariable String uuid,@PathVariable Long userId){
        service.confirmEmail(userId, uuid);
        return ResponseEntity.ok().build();
    }

}
