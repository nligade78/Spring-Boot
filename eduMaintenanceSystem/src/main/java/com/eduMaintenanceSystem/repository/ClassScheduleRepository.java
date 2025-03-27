package com.eduMaintenanceSystem.repository;

import com.eduMaintenanceSystem.entity.ClassSchedule;
import com.eduMaintenanceSystem.entity.Mentor;
import com.eduMaintenanceSystem.entity.Student;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    // Check if a mentor is already booked for a time slot
    boolean existsByTimeSlotAndMentor(LocalTime timeSlot, Mentor mentor);

    // Check if a student is already booked in any class at the given time slot
    boolean existsByTimeSlotAndStudentsContaining(LocalTime timeSlot, Student student);
}
