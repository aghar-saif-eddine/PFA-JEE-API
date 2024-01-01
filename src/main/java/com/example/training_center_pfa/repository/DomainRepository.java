package com.example.training_center_pfa.repository;


import com.example.training_center_pfa.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, String> {
}

