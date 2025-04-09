package com.eduMaintenanceSystem.controller;

import com.eduMaintenanceSystem.entity.ClassSchedule;
import com.eduMaintenanceSystem.services.ClassScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/class-schedules")
public class ClassScheduleController {

    // Declare the field for dependency injection
    private final ClassScheduleService classScheduleService;

    // Constructor injection (no need for @Autowired if there's one constructor)
    public ClassScheduleController(ClassScheduleService classScheduleService) {
        this.classScheduleService = classScheduleService;
    }

    /**
     * Schedules a class with a given course, mentor, multiple student IDs and time slot.
     * Example: POST /api/class-schedules/schedule?courseId=1&mentorId=1&studentIds=1,2,3&timeSlot=07:00   dfddxfgv
     */
    @PostMapping("/schedule")
    public ResponseEntity<ClassSchedule> scheduleClass(
            @RequestParam Long courseId,
            @RequestParam Long mentorId,
            @RequestParam Set<Long> studentIds,
            @RequestParam String timeSlot) {

        LocalTime slot = LocalTime.parse(timeSlot); // e.g., "07:00"
        ClassSchedule schedule = classScheduleService.scheduleClass(courseId, mentorId, studentIds, slot);
        return ResponseEntity.ok(schedule);
    }

    // Get All Class Schedules
    @GetMapping
    public ResponseEntity<List<ClassSchedule>> getAllClassSchedules() {
        return ResponseEntity.ok(classScheduleService.getAllClassSchedules());
    }

    // Get Class Schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedule> getClassScheduleById(@PathVariable Long id) {
        Optional<ClassSchedule> classSchedule = classScheduleService.getClassScheduleById(id);
        return classSchedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Class Schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSchedule(@PathVariable Long id) {
        classScheduleService.deleteClassSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
