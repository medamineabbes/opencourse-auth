package com.opencourse.authusermanagement.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.authusermanagement.dtos.RoleRequestsDto;
import com.opencourse.authusermanagement.services.RequestService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/request")
@AllArgsConstructor
public class RequestController {
    
    private final RequestService service;

    //only admin
    @GetMapping
    public ResponseEntity<List<RoleRequestsDto>> getRequests(){
        List<RoleRequestsDto> requests=service.getRequests();
        return ResponseEntity.ok(requests);
    }

    //only admin
    @PostMapping
    public ResponseEntity<Object> acceptRequest(@RequestParam(required = true) String requestId){
        service.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

    //only admin
    @DeleteMapping
    public ResponseEntity<Object> deleteRequest(@RequestParam(required = true) String requestId){
        service.deleteRequest(requestId);
        return ResponseEntity.ok().build();
    }

}
