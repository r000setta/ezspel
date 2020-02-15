package com.jqy.ezspel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.yml")
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {

    private String username;
    private String password;
    private String encParam1;
    private String encParam2;
}
