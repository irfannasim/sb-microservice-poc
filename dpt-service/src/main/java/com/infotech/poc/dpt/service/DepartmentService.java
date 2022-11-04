package com.infotech.poc.dpt.service;

import com.infotech.poc.dpt.dl.dto.DepartmentDTO;
import com.infotech.poc.dpt.dl.entity.Department;
import com.infotech.poc.dpt.repository.DepartmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAllDepartments() {
        log.info("getAllDepartments method called..");

        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        List<Department> departmentList = departmentRepository.findAll();
        for (Department dpt : departmentList) {
            DepartmentDTO dto = new DepartmentDTO();
            dto.convertToDTO(dpt, false);
            departmentDTOList.add(dto);
        }
        return departmentDTOList;
    }

    public DepartmentDTO getDepartmentById(Long id) {
        log.info("getDepartmentById method called..");

        DepartmentDTO departmentDTO = new DepartmentDTO();
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            departmentDTO.convertToDTO(department.get(), false);
        }
        return departmentDTO;
    }

    public Department createDepartment(DepartmentDTO dptDTO) {
        log.info("createDepartment method called..");

        return departmentRepository.save(dptDTO.convertToEntity());
    }
}
