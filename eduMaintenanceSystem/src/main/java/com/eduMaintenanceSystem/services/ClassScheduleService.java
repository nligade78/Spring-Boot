package com.eduMaintenanceSystem.services;

import com.eduMaintenanceSystem.entity.ClassSchedule;
import com.eduMaintenanceSystem.entity.Course;
import com.eduMaintenanceSystem.entity.Mentor;
import com.eduMaintenanceSystem.entity.Student;
import com.eduMaintenanceSystem.repository.ClassScheduleRepository;
import com.eduMaintenanceSystem.repository.CourseRepository;
import com.eduMaintenanceSystem.repository.MentorRepository;
import com.eduMaintenanceSystem.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ClassScheduleService {

    private final ClassScheduleRepository classScheduleRepository;
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;

    public ClassScheduleService(ClassScheduleRepository classScheduleRepository,
                                CourseRepository courseRepository,
                                MentorRepository mentorRepository,
                                StudentRepository studentRepository) {
        this.classScheduleRepository = classScheduleRepository;
        this.courseRepository = courseRepository;
        this.mentorRepository = mentorRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Schedules a class after validating mentor and student availability.
     *
     * @param courseId   the course ID
     * @param mentorId   the mentor ID
     * @param studentIds a set of student IDs (max 5)
     * @param timeSlot   the time slot for the class
     * @return the created ClassSchedule
     */
    @Transactional
    public ClassSchedule scheduleClass(Long courseId, Long mentorId, Set<Long> studentIds, LocalTime timeSlot) {
        log.info("📌 Scheduling class: Course ID={}, Mentor ID={}, TimeSlot={}", courseId, mentorId, timeSlot);

        // Ensure Course and Mentor Exist
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("❌ Course not found"));
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("❌ Mentor not found"));

        // Check if Mentor is Already Booked for the Given Time Slot
        if (classScheduleRepository.existsByTimeSlotAndMentor(timeSlot, mentor)) {
            throw new RuntimeException("❌ Mentor is already booked for this time slot");
        }

        // Validate Maximum Student Capacity
        if (studentIds.size() > 5) {
            throw new RuntimeException("❌ Cannot schedule more than 5 students in one class");
        }

        // Fetch Students and Check Scheduling Conflicts
        Set<Student> students = new HashSet<>();
        for (Long studentId : studentIds) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("❌ Student with ID " + studentId + " not found"));
            if (classScheduleRepository.existsByTimeSlotAndStudentsContaining(timeSlot, student)) {
                throw new RuntimeException("❌ Student " + student.getName() + " is already scheduled for this time slot");
            }
            students.add(student);
        }

        // ✅ Create and Save the New Class Schedule
        ClassSchedule schedule = new ClassSchedule();
        schedule.setCourse(course);
        schedule.setMentor(mentor);
        schedule.setTimeSlot(timeSlot);
        schedule.setStatus(ClassSchedule.Status.NEW);
        schedule.setStudents(students);

        // Save and ensure ID generation
        ClassSchedule savedSchedule = classScheduleRepository.saveAndFlush(schedule);
        log.info("✅ Class scheduled successfully with ID={}", savedSchedule.getId());

        return savedSchedule;
    }

    /**
     * Retrieves all class schedules.
     *
     * @return list of ClassSchedule
     */
    public List<ClassSchedule> getAllClassSchedules() {
        return classScheduleRepository.findAll();
    }

    /**
     * Retrieves a class schedule by its ID.
     *
     * @param id the class schedule ID
     * @return an optional ClassSchedule
     */
    public Optional<ClassSchedule> getClassScheduleById(Long id) {
        return classScheduleRepository.findById(id);
    }

    /**
     * Deletes a class schedule by its ID.
     *
     * @param id the class schedule ID
     */
    public void deleteClassSchedule(Long id) {
        if (!classScheduleRepository.existsById(id)) {
            throw new RuntimeException("❌ Class schedule with ID " + id + " not found");
        }
        classScheduleRepository.deleteById(id);
        log.info("🗑️ Deleted class schedule with ID={}", id);
    }
}
