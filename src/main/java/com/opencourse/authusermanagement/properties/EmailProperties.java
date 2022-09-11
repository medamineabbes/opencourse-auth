package com.opencourse.authusermanagement.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "email")
@Component
public class EmailProperties {
    private int evailabilityMin;
    private String apiKey;
}
