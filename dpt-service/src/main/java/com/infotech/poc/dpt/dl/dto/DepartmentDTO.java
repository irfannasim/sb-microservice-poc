package com.infotech.poc.dpt.dl.dto;

import com.infotech.poc.dpt.dl.entity.Department;
import com.infotech.poc.dpt.util.AppUtility;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class DepartmentDTO extends BaseDTO<DepartmentDTO, Department> implements Serializable {

    private String name;
    private String code;

    @Override
    public Department convertToEntity() {
        Department department = new Department();
        department.setId(this.id);
        department.setName(this.name);
        department.setCode(this.code);
        department.setCreatedOn(AppUtility.isEmpty(this.createdOn) ? ZonedDateTime.now() : this.createdOn);
        department.setUpdatedOn(AppUtility.isEmpty(this.updatedOn) ? ZonedDateTime.now() : this.updatedOn);
        return department;
    }

    @Override
    public void convertToDTO(Department entity, boolean partialFill) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.updatedOn = entity.getUpdatedOn();
        this.createdOn = entity.getCreatedOn();
    }

    @Override
    public DepartmentDTO convertToNewDTO(Department entity, boolean partialFill) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.convertToDTO(entity, partialFill);
        return departmentDTO;
    }
}
