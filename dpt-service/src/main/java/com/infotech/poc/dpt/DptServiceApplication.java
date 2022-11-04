package com.infotech.poc.dpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@SpringBootApplication
@EnableEurekaClient
public class DptServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DptServiceApplication.class, args);
    }

}
