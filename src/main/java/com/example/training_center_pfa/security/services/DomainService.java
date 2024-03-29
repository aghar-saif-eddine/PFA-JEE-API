package com.example.training_center_pfa.security.services;

import com.example.training_center_pfa.models.Domain;
import com.example.training_center_pfa.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;
    public Domain getDomainById(Integer domainID) {
        return domainRepository.findById(String.valueOf(domainID))
                .orElseThrow(() -> new RuntimeException("Error: Domain not found with name " + domainID));
    } // add a domain
    public Domain addDomain(Domain domain) {
        return domainRepository.save(domain);
    }
    // Get all domain exist
    public List<Domain> getListDomain() {
        return domainRepository.findAll();
    }
    // DELETE a specific Domain
    public void deleteDomain(Long domainId) {
        domainRepository.deleteById(String.valueOf(domainId));
    }
    // UPDATE data of user
    public Domain updateDomain(Long domainId, Domain domainDetails) {
        Domain domain = domainRepository.findById(String.valueOf(domainId)).orElse(null);
        if (domain != null) {
            domain.setLibelle(domainDetails.getLibelle());
            return domainRepository.save(domain);
        }
        return null; // or throw an exception, handle accordingly
    }
}
