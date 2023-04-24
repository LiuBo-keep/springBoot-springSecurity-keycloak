package com.example.springbootspringsecuritykeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @author aidan.liu
 */
@EnableConfigurationProperties
@SpringBootApplication
public class SpringBootSpringSecurityKeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSpringSecurityKeycloakApplication.class, args);
    }

}
