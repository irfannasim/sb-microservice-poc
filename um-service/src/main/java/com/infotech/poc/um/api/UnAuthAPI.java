package com.infotech.poc.um.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/un-auth")
@Log4j2
public class UnAuthAPI {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getSuccessResponse(HttpServletRequest request) {
        log.info("getAllUsers API initiated...");

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
