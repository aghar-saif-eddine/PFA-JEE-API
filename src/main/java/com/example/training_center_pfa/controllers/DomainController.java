package com.example.training_center_pfa.controllers;


import com.example.training_center_pfa.models.Domain;
import com.example.training_center_pfa.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/domain/add")
    public ResponseEntity<Map<String, String>> saveDomain(@RequestBody Domain domain) {
        Map<String, String> response = new HashMap<>();
        try {
            Domain savedDomain = domainService.addDomain(domain);
            response.put("status", "200");
            response.put("message", "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception if needed
            response.put("status", "500");
            response.put("message", "Failed to add domain");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/domain/all")
    public ResponseEntity<?> fetchDomainList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Domain> domainList = domainService.getListDomain();
            response.put("status", "200");
            response.put("message", "Success");
            response.put("data", domainList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception if needed
            response.put("status", "500");
            response.put("message", "Failed to fetch domain list");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/domain/edit/{id}")
    public ResponseEntity<Map<String, String>> updateDomain(
            @PathVariable("id") Long domainId,
            @RequestBody Domain domain) {
        Map<String, String> response = new HashMap<>();
        try {
            Domain updatedDomain = domainService.updateDomain(domainId, domain);
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

    @DeleteMapping("/domain/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteDomainById(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            domainService.deleteDomain(id);
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
}
