package com.eduMaintenanceSystem.services;


import com.eduMaintenanceSystem.entity.ClassSchedule;
import com.eduMaintenanceSystem.repository.ClassScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseCompletionService {

    private final ClassScheduleRepository classScheduleRepository;
    private final EmailService emailService;

    public CourseCompletionService(ClassScheduleRepository classScheduleRepository, EmailService emailService) {
        this.classScheduleRepository = classScheduleRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void completeCourse(Long classScheduleId, String scoreDetails) {
        // Find the class schedule record
        ClassSchedule schedule = classScheduleRepository.findById(classScheduleId)
                .orElseThrow(() -> new RuntimeException("Class Schedule not found"));

        // Update the status to COMPLETED
        schedule.setStatus(ClassSchedule.Status.COMPLETED);
        classScheduleRepository.save(schedule);

        // For each student in the class, send an email with score details.
        schedule.getStudents().forEach(student -> {
            // Construct your email subject and message
            String subject = "Course Completion Details";
            String message = "Dear " + student.getName() + ",\n\n" +
                    "Congratulations! You have completed the course " + schedule.getCourse().getName() +
                    ".\nYour score: " + scoreDetails +
                    "\n\nThank you and best regards,\nEdu Maintenance Team";

            // Trigger the email
            emailService.sendCourseCompletionEmail(student.getEmail(), subject, message);
        });
    }
}
