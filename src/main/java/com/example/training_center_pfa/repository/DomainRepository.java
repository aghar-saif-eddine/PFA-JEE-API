package com.example.training_center_pfa.repository;


import com.example.training_center_pfa.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, String> {
}

