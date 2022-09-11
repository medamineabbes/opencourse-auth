package com.opencourse.authusermanagement.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.authusermanagement.entities.MentorTeacherRequest;

@Repository
public interface MentorTeacherRequestRepo extends MongoRepository<MentorTeacherRequest,String>{
    List<MentorTeacherRequest> findByIsAccepted(Boolean accepted);
}
