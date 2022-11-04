package com.infotech.poc.dpt.api;

import com.infotech.poc.dpt.dl.dto.DepartmentDTO;
import com.infotech.poc.dpt.dl.entity.Department;
import com.infotech.poc.dpt.service.DepartmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/department")
@Log4j2
public class DepartmentAPI {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDepartments(HttpServletRequest request) {
        log.info("getAllDepartments API initiated...");

        List<DepartmentDTO> departmentDTOList = null;
        try {
            departmentDTOList = departmentService.getAllDepartments();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(departmentDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartmentById(HttpServletRequest request,
                                               @PathVariable(name = "id") Long id) {
        log.info("getDepartmentById API initiated...");

        DepartmentDTO dptDTO = null;
        try {
            dptDTO = departmentService.getDepartmentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(dptDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartment(HttpServletRequest request,
                                              @RequestBody DepartmentDTO dptRequest) {
        log.info("createDepartment API initiated...");
        Department department = null;
        try {
            department = departmentService.createDepartment(dptRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
