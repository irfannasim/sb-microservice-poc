package com.infotech.poc.um.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    SecurityConfig() {
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers("/un-auth/**");
    }
}

