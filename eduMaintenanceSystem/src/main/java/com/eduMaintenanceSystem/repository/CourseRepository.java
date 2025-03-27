package com.eduMaintenanceSystem.repository;

import com.eduMaintenanceSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom query methods (if needed) can be added here
}