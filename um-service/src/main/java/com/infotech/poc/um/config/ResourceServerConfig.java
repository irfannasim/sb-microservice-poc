package com.infotech.poc.um.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${infotech.poc.um.oauth.url}")
    private String authServerUrl;
    @Value("${infotech.poc.um.oauth.clientId}")
    private String clientID;
    @Value("${infotech.poc.um.oauth.clientSecret}")
    private String clientSecret;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(this.tokenService());
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(clientID);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setCheckTokenEndpointUrl(authServerUrl);
        return tokenServices;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern(CorsConfiguration.ALL);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}