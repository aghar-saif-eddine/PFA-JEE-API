package com.example.training_center_pfa.controllers;

import com.example.training_center_pfa.models.*;
import com.example.training_center_pfa.payload.request.InstructorRequest;
import com.example.training_center_pfa.payload.response.MessageResponse;
import com.example.training_center_pfa.repository.InstructorRepository;
import com.example.training_center_pfa.repository.RoleRepository;
import com.example.training_center_pfa.repository.UserRepository;
import com.example.training_center_pfa.security.services.DomainService;
import com.example.training_center_pfa.security.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private DomainService domainService;
    @PostMapping("/instructors/add")
    public ResponseEntity<?> addInstructor(@Valid @RequestBody InstructorRequest instructorRequest) {

        if (userRepository.existsByEmail(instructorRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create User
        User user = new User(instructorRequest.getFullName(), instructorRequest.getEmail(), encoder.encode("admin"));
        Set<Role> roles = new HashSet<>();
        Role roleInstructor = roleRepository.findByName(ERole.ROLE_INSTRUCTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(roleInstructor);
        user.setRoles(roles);
        // Save User
        userRepository.save(user);

        // Create Instructor
        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setYearsOfExperience(instructorRequest.getYearsOfExperience());
        instructor.setFullName(instructorRequest.getFullName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setAge(instructorRequest.getAge());
        // Set Domain
        String domainId = instructorRequest.getDomain();  // Assuming you have a method to get domain name from the request
        Domain domain = domainService.getDomainByName(domainId);
        instructor.setDomain(domain);
        // Save Instructor
        instructorService.addInstructor(instructor);

        return ResponseEntity.ok(new MessageResponse("Instructor added successfully!"));
    }

    @GetMapping("/instructors/all")
    public ResponseEntity<?> getListInstructor() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Instructor> instructorList = instructorService.getListInstructor();
            response.put("status", "200");
            response.put("message", "Success");
            response.put("data", instructorList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception if needed
            response.put("status", "500");
            response.put("message", "Failed to fetch domain list");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @DeleteMapping("/instructors/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteInstructor(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            instructorService.deleteInstructor(id);
            response.put("status", "200");
            response.put("message", "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception if needed
            response.put("status", "500");
            response.put("message", "Failed to delete domain");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/instructors/edit/{id}")
    public ResponseEntity<Map<String, String>> updateInstructor(
            @PathVariable("id") Long instructorId,
            @RequestBody Instructor instructor) {
        Map<String, String> response = new HashMap<>();
        try {
            Instructor updatedInstructor = instructorService.updateInstructor(instructorId, instructor);
            response.put("status", "200");
            response.put("message", "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception if needed
            response.put("status", "500");
            response.put("message", "Failed to update domain");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}