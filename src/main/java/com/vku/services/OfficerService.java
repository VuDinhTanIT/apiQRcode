package com.vku.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Officer;
import com.vku.repositories.OfficerRepository;

@Service
public class OfficerService {
	@Autowired
    private  OfficerRepository officerRepository;


    public List<Officer> getAllOfficers() {
        return officerRepository.findAll();
    }

    public Officer getOfficerById(int officerId) {
        return officerRepository.findById(officerId)
                .orElseThrow(() -> new NoSuchElementException("Officer not found with id: " + officerId));
    }

    public Officer createOfficer(Officer officer) {
        // Add any necessary business logic before saving the officer
        return officerRepository.save(officer);
    }

    public Officer updateOfficer(int officerId, Officer updatedOfficer) {
        updatedOfficer.setId(officerId);
        // Update the existing officer with the new data from updatedOfficer
        // Add any necessary business logic before updating the officer
        return officerRepository.save(updatedOfficer);
    }

    public void deleteOfficer(int officerId) {
        Officer officer = getOfficerById(officerId);
        // Add any necessary business logic before deleting the officer
        officerRepository.delete(officer);
    }
}
