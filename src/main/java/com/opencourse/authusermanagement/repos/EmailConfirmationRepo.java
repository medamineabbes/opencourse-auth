package com.opencourse.authusermanagement.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.authusermanagement.entities.EmailConfirmationsRequest;


@Repository
public interface EmailConfirmationRepo extends MongoRepository<EmailConfirmationsRequest,String>{
    
}
