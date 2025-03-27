package com.eduMaintenanceSystem.controller;

import com.eduMaintenanceSystem.entity.Mentor;
import com.eduMaintenanceSystem.repository.MentorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    private final MentorRepository mentorRepository;

    public MentorController(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    // Get all mentors
    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    // Get a mentor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        return mentor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new mentor
    @PostMapping
    public Mentor createMentor(@RequestBody Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    // Update an existing mentor
    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateMentor(@PathVariable Long id, @RequestBody Mentor mentorDetails) {
        return mentorRepository.findById(id)
                .map(mentor -> {
                    mentor.setName(mentorDetails.getName());
                    mentor.setExpertise(mentorDetails.getExpertise());
                    return ResponseEntity.ok(mentorRepository.save(mentor));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a mentor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        if (!mentorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mentorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
