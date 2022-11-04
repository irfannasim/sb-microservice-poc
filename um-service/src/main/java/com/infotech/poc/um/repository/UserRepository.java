package com.infotech.poc.um.repository;

import com.infotech.poc.um.dl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
