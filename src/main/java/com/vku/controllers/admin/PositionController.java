package com.vku.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.models.Log;
import com.vku.models.Position;
import com.vku.services.LogService;
import com.vku.services.PositionService;

import java.util.List;

@RestController
@RequestMapping("api/admin/positions")
public class PositionController {
	@Autowired
    private  PositionService positionService;
	@Autowired 
	private LogService logService;
  

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable("id") int id) {
        Position position = positionService.getPositionById(id);
        if (position == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        Position createdPosition = positionService.createPosition(position);
        Log log = new Log();
//        log.setActor();
        log.setLog("Thêm position: "+ position.getPositionCode() + " - " + position.getName() );
        logService.wirteLog(log);
        
        
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable("id") int id, @RequestBody Position position) {
        Position existingPosition = positionService.getPositionById(id);
        if (existingPosition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        position.setId(id); // Set the ID of the existing position
        Position updatedPosition = positionService.updatePosition(position);
        return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable("id") int id) {
        Position existingPosition = positionService.getPositionById(id);
        if (existingPosition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}