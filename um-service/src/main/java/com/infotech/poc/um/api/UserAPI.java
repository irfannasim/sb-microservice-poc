package com.infotech.poc.um.api;

import com.infotech.poc.um.dl.dto.UserDTO;
import com.infotech.poc.um.dl.entity.User;
import com.infotech.poc.um.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserAPI {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        log.info("getAllUsers API initiated...");

        List<UserDTO> userDTOList = null;
        try {
            userDTOList = userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(HttpServletRequest request,
                                        @RequestBody User userRequest) {
        log.info("createUser API initiated...");
        User user = null;
        try {
            user = userService.createUser(userRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
