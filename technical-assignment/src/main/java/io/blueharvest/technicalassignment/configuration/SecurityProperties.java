package io.blueharvest.technicalassignment.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityProperties {

    private String secret;
    private long expirationDate;
    private String tokenPrefix;
    private String headerValue;
    private String issuer;
}
