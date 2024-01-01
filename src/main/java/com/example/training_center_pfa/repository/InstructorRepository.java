package com.example.training_center_pfa.repository;

import com.example.training_center_pfa.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}