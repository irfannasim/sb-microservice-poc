package com.infotech.poc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

    @Value("${infotech.poc.auth.oauth.clientId}")
    private String clientID;
    @Value("${infotech.poc.auth.oauth.clientSecret}")
    private String clientSecret;
    @Value("${infotech.poc.auth.oauth.accessTokenValidity}")
    private int accessTokenValidity;
    @Value("${infotech.poc.auth.oauth.refreshTokenValidity}")
    private int refreshTokenValidity;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").allowFormAuthenticationForClients();

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientID)
                .secret(new BCryptPasswordEncoder().encode(clientSecret))
                .accessTokenValiditySeconds(accessTokenValidity)
                .refreshTokenValiditySeconds(refreshTokenValidity)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }
}