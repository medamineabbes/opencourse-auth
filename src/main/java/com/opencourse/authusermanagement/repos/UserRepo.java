package com.opencourse.authusermanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;

@Repository
public interface UserRepo extends MongoRepository<User,Long>{

    @Query("{'email' : ?0}")
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(Role role);

}
