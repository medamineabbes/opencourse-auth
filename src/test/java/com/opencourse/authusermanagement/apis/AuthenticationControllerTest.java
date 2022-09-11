package com.opencourse.authusermanagement.apis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.authusermanagement.config.SecurityConfig;
import com.opencourse.authusermanagement.services.AuthenticationService;


@WebMvcTest(AuthController.class)
//@ContextConfiguration(classes = SecurityConfig.class)
@Import({SecurityConfig.class})
public class AuthenticationControllerTest {
    
    @MockBean 
    AuthenticationService service;
    @Autowired
    MockMvc mvc;
    private ObjectMapper mapper=new ObjectMapper();
    private String basePath="/api/v1/user/auth";
    @Test
    @DisplayName("")
    public void signupTest() throws Exception{
        Map<String,Object> user=new HashMap<>();
        Map<String,Object> data=new HashMap<>();
        user.put("firstname", "firstname");
        user.put("lastname", "lastname");
        user.put("email", "amine@gmail.com");
        user.put("password", "password");
        user.put("role", "ADMIN");
        user.put("imageUrl", "image");
        data.put("userData", user);
        data.put("cvId", "cvId");

        String payload=mapper.writeValueAsString(data);

        mvc.perform(
            post(basePath + "/signup")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

}
