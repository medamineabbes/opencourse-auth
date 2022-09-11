package com.opencourse.authusermanagement.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.authusermanagement.dtos.mappers.UserRequestMapper;
import com.opencourse.authusermanagement.entities.EmailConfirmationsRequest;
import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.exceptions.EmailConfirmationException;
import com.opencourse.authusermanagement.properties.EmailProperties;
import com.opencourse.authusermanagement.repos.UserRepo;

public class UserServiceTest {
    private UserRepo repo=mock(UserRepo.class);
    private UserRequestMapper requestMapper=new UserRequestMapper();
    private EmailProperties emailProp=mock(EmailProperties.class);
    private UserService service;

    @BeforeEach
    public void init(){
        service=new UserService(repo,requestMapper,emailProp);
    }

    @Test
    @DisplayName("should confirm Email")
    public void confirmEmailTest(){
        User user=new User();
        EmailConfirmationsRequest request=new EmailConfirmationsRequest();
        String code=UUID.randomUUID().toString();

        user.setEmail("email");
        user.setFirstname("firstname");
        user.setId(1L);
        user.setImageUrl("imageUrl");
        user.setIsActive(true);
        user.setIsBanned(false);
        user.setIsConfirmed(false);
        user.setLastname("lastname");
        user.setMentor(null);
        user.setPassword("password");
        user.setRole(Role.STUDENT);
        user.setStudents(null);
        user.setConfirmationRequests(List.of(request));

        request.setCode(code);
        request.setId("id");
        request.setUser(user);
        request.setSentAt(LocalDateTime.now().minusMinutes(14));

        when(repo.findById(1L)).thenReturn(Optional.of(user));
        when(emailProp.getEvailabilityMin()).thenReturn(15);
        service.confirmEmail(1L, code);
        user.setIsConfirmed(true);
        verify(repo).save(user);
    }

    @Test
    @DisplayName("should throw confirmation exception")
    public void confirmEmailTest2(){
        User user=new User();
        EmailConfirmationsRequest request=new EmailConfirmationsRequest();
        String code=UUID.randomUUID().toString();

        user.setEmail("email");
        user.setFirstname("firstname");
        user.setId(1L);
        user.setImageUrl("imageUrl");
        user.setIsActive(true);
        user.setIsBanned(false);
        user.setIsConfirmed(false);
        user.setLastname("lastname");
        user.setMentor(null);
        user.setPassword("password");
        user.setRole(Role.STUDENT);
        user.setStudents(null);
        user.setConfirmationRequests(List.of(request));

        request.setCode(code);
        request.setId("id");
        request.setUser(user);
        request.setSentAt(LocalDateTime.now().minusMinutes(16));

        when(emailProp.getEvailabilityMin()).thenReturn(15);
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(EmailConfirmationException.class, ()->{
            service.confirmEmail(1L, code);
        });
    }

    @Test
    @DisplayName("should throw confirmation exception")
    public void confirmEmailTest3(){
        User user=new User();
        EmailConfirmationsRequest request=new EmailConfirmationsRequest();
        String code=UUID.randomUUID().toString();

        user.setEmail("email");
        user.setFirstname("firstname");
        user.setId(1L);
        user.setImageUrl("imageUrl");
        user.setIsActive(true);
        user.setIsBanned(false);
        user.setIsConfirmed(false);
        user.setLastname("lastname");
        user.setMentor(null);
        user.setPassword("password");
        user.setRole(Role.STUDENT);
        user.setStudents(null);
        user.setConfirmationRequests(List.of(request));

        request.setCode(code);
        request.setId("id");
        request.setUser(user);
        request.setSentAt(LocalDateTime.now().minusMinutes(14));
        when(emailProp.getEvailabilityMin()).thenReturn(15);
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(EmailConfirmationException.class, ()->{
            service.confirmEmail(1L, "sdvs");
        });
    }

}
