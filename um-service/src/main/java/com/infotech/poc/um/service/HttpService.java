package com.infotech.poc.um.service;

import com.infotech.poc.um.dl.dto.DepartmentDTO;
import com.infotech.poc.um.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.LinkedHashMap;

@Log4j2
@Service
public class HttpService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public DepartmentDTO getRequestForDepartment(String uri) {
        log.info("getRequestForDepartment method called..");

        Object obj = getAuthToken();
        if (!AppUtility.isEmpty(obj)) {
            return webClientBuilder.build().get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + ((LinkedHashMap<?, ?>) obj).get("access_token"))
                    .retrieve()
                    .bodyToMono(DepartmentDTO.class)
                    .block();
        }
        return null;
    }

    private Object getAuthToken() {
        String uri = AppUtility.getAPIUrl("auth.server.token.api.url");
        return webClientBuilder.build().post()
                .uri(uri)
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client:secret".getBytes()))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
