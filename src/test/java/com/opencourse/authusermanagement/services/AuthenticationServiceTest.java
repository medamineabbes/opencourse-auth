package com.opencourse.authusermanagement.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.opencourse.authusermanagement.dtos.UserDataDto;
import com.opencourse.authusermanagement.dtos.mappers.UserDataMapper;
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

public class AuthenticationServiceTest {

    private UserRepo repo=mock(UserRepo.class);
    private EmailConfirmationRepo emailRepo=mock(EmailConfirmationRepo.class);
    private MentorTeacherRequestRepo mtRepo=mock(MentorTeacherRequestRepo.class);
    private EmailService emailService=mock(EmailService.class);
    private JwtProvider jwtProvider=new JwtProvider();
    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private UserDataMapper dataMapper=new UserDataMapper();
    private SequeceGenerator seGen=mock(SequeceGenerator.class);
    private GoogleService google=mock(GoogleService.class);

    private AuthenticationService service;

    @BeforeEach
    public void init(){
        service=new AuthenticationService(repo, emailRepo, mtRepo, emailService, google, jwtProvider, passwordEncoder, dataMapper, seGen);
    }

    @Test
    @DisplayName("should throw authentication Exception")
    public void loginTest(){
        assertThrows(CustomAuthenticationException.class, ()->{
            service.login("amineabbes@gmail.com", "password");
        });
    }

    @Test
    @DisplayName("should throw authentication Exception")
    public void loginTest2(){
        User user=new User();
        user.setEmail("email");
        user.setPassword(passwordEncoder.encode("password"));
        when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThrows(CustomAuthenticationException.class, ()->{
            service.login("email", "password123");
        });
    }

    @Test
    @DisplayName("should return token")
    public void loginTest3(){
        User user=new User();
        user.setEmail("email");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("firstname");
        user.setImageUrl("imageId");
        user.setIsActive(true);
        user.setId(1L);
        user.setIsBanned(false);
        user.setIsConfirmed(true);
        user.setLastname("lastname");
        user.setRole(Role.STUDENT);
        when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        
        service.login("email", "password");
    }

    @Test
    @DisplayName("account not active")
    public void logintest4(){
        User user=new User();
        user.setEmail("email");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("firstname");
        user.setImageUrl("imageId");
        user.setId(1L);

        user.setIsActive(false);
        user.setIsBanned(false);
        user.setIsConfirmed(true);
        
        user.setLastname("lastname");
        user.setRole(Role.STUDENT);
        when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(CustomAuthenticationException.class, ()->{
            service.login("email", "password");
        });
    }

    @Test
    @DisplayName("account is banned")
    public void logintest5(){
        User user=new User();
        user.setEmail("email");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("firstname");
        user.setImageUrl("imageId");
        user.setId(1L);

        user.setIsActive(true);
        user.setIsBanned(true);
        user.setIsConfirmed(true);
        
        user.setLastname("lastname");
        user.setRole(Role.STUDENT);
        when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(CustomAuthenticationException.class, ()->{
            service.login("email", "password");
        });
    }

    @Test
    @DisplayName("email is not confirmed")
    public void logintest6(){
        User user=new User();
        user.setEmail("email");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("firstname");
        user.setImageUrl("imageId");
        user.setId(1L);

        user.setIsActive(true);
        user.setIsBanned(false);
        user.setIsConfirmed(false);
        
        user.setLastname("lastname");
        user.setRole(Role.STUDENT);
        when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(CustomAuthenticationException.class, ()->{
            service.login("email", "password");
        });
    }


    @Test
    @DisplayName("should add user ")
    public void signupTest(){
        UserDataDto dto=new UserDataDto();   
        dto.setEmail("amine@gmail.com");
        dto.setFirstname("firstname");
        dto.setImageUrl("imageId");
        dto.setLastname("lastname");
        dto.setPassword("password");
        dto.setRole("ADMIN");
        
        service.signup(dto,"cvId");

        verify(repo).save(any(User.class));
        verify(emailService).sendEmailConfirmation(any(User.class), any(String.class));
        verify(mtRepo).save(any(MentorTeacherRequest.class));
    }

    @Test
    @DisplayName("should throw Email already exists exception")
    public void signupTest2(){
        UserDataDto dto=new UserDataDto();   
        dto.setEmail("amine@gmail.com");
        dto.setFirstname("firstname");
        dto.setImageUrl("imageId");
        dto.setLastname("lastname");
        dto.setPassword("password");
        dto.setRole("ADMIN");

        when(repo.findByEmail("amine@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, ()->{
            service.signup(dto,"cvID");
        });
    }

    @Test
    @DisplayName("should add user to database")
    public void loginWithGoogleTest(){
        String validToken="token";
        UserDataDto dto=new UserDataDto();
        dto.setEmail("email");
        dto.setFirstname("firstname");
        dto.setImageUrl("imageUrl");
        dto.setLastname("lastname");
        
        when(google.getUserData(validToken)).thenReturn(dto);
        when(repo.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        service.loginwithGoogle(validToken);
        verify(repo).save(any(User.class));
    }

}
