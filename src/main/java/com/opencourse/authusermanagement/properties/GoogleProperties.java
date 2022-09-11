package com.opencourse.authusermanagement.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "google")
@Component
public class GoogleProperties {
    
    private String clientId;
    private String clientSecret;
    private String frontEndClientId;
}
