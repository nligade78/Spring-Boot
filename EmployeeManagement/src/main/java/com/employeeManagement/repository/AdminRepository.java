package com.employeeManagement.repository;

import com.employeeManagement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

   Optional<Admin> findByEmailAndPassword(String email, String password);
   Optional<Admin> findByEmail(String email); // ✅ use this now instead

}
