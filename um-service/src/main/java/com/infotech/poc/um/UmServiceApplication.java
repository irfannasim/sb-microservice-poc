package com.infotech.poc.um;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@EnableEurekaClient
public class UmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
