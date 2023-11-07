package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Position;
import com.vku.repositories.PositionRepository;

import java.util.List;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Position getPositionById(int id) {
        return positionRepository.findById(id).orElse(null);
    }

    public Position createPosition(Position position) {
    	position.setId(0); 
        return positionRepository.save(position);
    }

    public Position updatePosition(Position position) {
    	
        return positionRepository.save(position);
    }

    public void deletePosition(int id) {
        positionRepository.deleteById(id);
    }
}