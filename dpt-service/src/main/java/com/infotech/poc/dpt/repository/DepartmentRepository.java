package com.infotech.poc.dpt.repository;

import com.infotech.poc.dpt.dl.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
