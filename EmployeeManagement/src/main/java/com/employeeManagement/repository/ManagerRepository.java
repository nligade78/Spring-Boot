package com.employeeManagement.repository;

import com.employeeManagement.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByEmailIdAndPassword(String emailId, String password);
    Optional<Manager> findByEmailId(String emailId);

}

