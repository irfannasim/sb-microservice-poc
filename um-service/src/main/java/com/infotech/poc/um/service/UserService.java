package com.infotech.poc.um.service;

import com.infotech.poc.um.dl.dto.DepartmentDTO;
import com.infotech.poc.um.dl.dto.UserDTO;
import com.infotech.poc.um.dl.entity.User;
import com.infotech.poc.um.repository.UserRepository;
import com.infotech.poc.um.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpService httpService;

    public List<UserDTO> getAllUsers() {
        log.info("getAllUsers method called..");

        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User u : userList) {
            UserDTO userDTO = new UserDTO();
            userDTO.convertToDTO(u, false);

            String url = AppUtility.getAPIUrl("dpt.service.get.dpt.id") + u.getDptId();
            DepartmentDTO dpt = httpService.getRequestForDepartment(url);

            userDTO.setDepartmentDTO(dpt);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public User createUser(User user) {
        log.info("createUser method called..");

        return userRepository.save(user);
    }
}
