package com.opencourse.authusermanagement.externalservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.properties.EmailProperties;

import lombok.AllArgsConstructor;
import sendinblue.*;
import sendinblue.auth.*;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

@Service
@AllArgsConstructor
public class EmailService {

    private static Long CONFIRMATION_EMAIL_TEMPLATE=1L;
    private static Long ACCEPTANCE_EMAIL_TEMPLATE=2L;
    private static Long REJECTION_EMAIL_TEMPLATE=3L;

    private final EmailProperties emailProperties;

    public void sendEmailConfirmation(User user,String uuid) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(emailProperties.getApiKey());
        

        List<SendSmtpEmailTo> recipents=new ArrayList<>();
        SendSmtpEmailTo recipent=new SendSmtpEmailTo();
        recipent.email(user.getEmail());
        recipent.name(user.getFirstname());
        recipents.add(recipent);

        Map<String,Object> params=new HashMap<>();
        params.put("uuid", uuid);
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());
        params.put("userid", user.getId());

        SendSmtpEmail sendInBlue=new SendSmtpEmail();
        sendInBlue.setTemplateId(CONFIRMATION_EMAIL_TEMPLATE); 
        sendInBlue.setTo(recipents);
        sendInBlue.setParams(params);  
        
        TransactionalEmailsApi api=new TransactionalEmailsApi();

        try {
            api.sendTransacEmail(sendInBlue);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    public void sendAcceptanceEmail(User user){
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(emailProperties.getApiKey());
        

        List<SendSmtpEmailTo> recipents=new ArrayList<>();
        SendSmtpEmailTo recipent=new SendSmtpEmailTo();
        recipent.email(user.getEmail());
        recipent.name(user.getFirstname());
        recipents.add(recipent);

        Map<String,Object> params=new HashMap<>();
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());

        SendSmtpEmail sendInBlue=new SendSmtpEmail();
        sendInBlue.setTemplateId(ACCEPTANCE_EMAIL_TEMPLATE); 
        sendInBlue.setTo(recipents);
        sendInBlue.setParams(params);  
        
        TransactionalEmailsApi api=new TransactionalEmailsApi();

        try {
            api.sendTransacEmail(sendInBlue);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void sendRejectionEmail(User user){
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(emailProperties.getApiKey());
        

        List<SendSmtpEmailTo> recipents=new ArrayList<>();
        SendSmtpEmailTo recipent=new SendSmtpEmailTo();
        recipent.email(user.getEmail());
        recipent.name(user.getFirstname());
        recipents.add(recipent);

        Map<String,Object> params=new HashMap<>();
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());

        SendSmtpEmail sendInBlue=new SendSmtpEmail();
        sendInBlue.setTemplateId(REJECTION_EMAIL_TEMPLATE); 
        sendInBlue.setTo(recipents);
        sendInBlue.setParams(params);  
        
        TransactionalEmailsApi api=new TransactionalEmailsApi();

        try {
            api.sendTransacEmail(sendInBlue);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

}
