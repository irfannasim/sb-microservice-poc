package com.infotech.poc.um.dl.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentDTO implements Serializable {

    private Long id;
    private String name;
    private String code;
}
