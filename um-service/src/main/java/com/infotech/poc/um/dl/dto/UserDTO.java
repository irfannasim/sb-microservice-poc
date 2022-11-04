package com.infotech.poc.um.dl.dto;

import com.infotech.poc.um.dl.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO extends BaseDTO<UserDTO, User> implements Serializable {

    private String name;
    private String username;
    private String email;
    private DepartmentDTO departmentDTO;

    @Override
    public User convertToEntity() {
        return null;
    }

    @Override
    public void convertToDTO(User entity, boolean partialFill) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.createdOn = entity.getCreatedOn();
        this.updatedOn = entity.getUpdatedOn();
    }

    @Override
    public UserDTO convertToNewDTO(User entity, boolean partialFill) {
        UserDTO userDTO = new UserDTO();
        userDTO.convertToDTO(entity, partialFill);
        return userDTO;
    }
}
