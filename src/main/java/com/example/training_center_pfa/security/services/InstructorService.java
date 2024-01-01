package com.example.training_center_pfa.security.services;

import com.example.training_center_pfa.models.Domain;
import com.example.training_center_pfa.models.Instructor;
import com.example.training_center_pfa.repository.DomainRepository;
import com.example.training_center_pfa.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private  DomainService domainService;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }
    // add an Instructor
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
    // Get all Instructors exist
    public List<Instructor> getListInstructor() {
        return instructorRepository.findAll();
    }
    // DELETE a specific Instructor
    public void deleteInstructor(Long instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        instructorOptional.ifPresent(instructor -> {
            instructor.setDomain(null);
            instructor.setUser(null);
            instructorRepository.deleteById(instructorId);
        });
    }
    // UPDATE data of user
    public Instructor updateInstructor(Long instructorId, Instructor instructorDetails) {
        Instructor instructor = instructorRepository.findById(Long.valueOf(String.valueOf(instructorId))).orElse(null);

            instructor.setFullName(instructorDetails.getFullName());
            instructor.setAge(instructorDetails.getAge());
            instructor.setYearsOfExperience(instructorDetails.getYearsOfExperience());
            Domain domain = domainService.getDomainByName(String.valueOf(instructorDetails.getDomain()));
            instructor.setDomain(domain);
            return instructorRepository.save(instructor);


    }
}
