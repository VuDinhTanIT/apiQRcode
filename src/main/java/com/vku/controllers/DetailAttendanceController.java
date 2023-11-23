package com.vku.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vku.dtos.ErrorResponse;
import com.vku.models.DetailAttendance;
import com.vku.services.DetailAttendanceService;

@RestController
@RequestMapping("/detail-attendance")
public class DetailAttendanceController {
	@Autowired
    private DetailAttendanceService detailAttendanceService;

    public DetailAttendanceController(DetailAttendanceService detailAttendanceService) {
    	
        this.detailAttendanceService = detailAttendanceService;
    }

    @GetMapping
    public ResponseEntity<List<DetailAttendance>> getAllDetailAttendances() {
        List<DetailAttendance> detailAttendances = detailAttendanceService.getAllDetailAttendances();
        return new ResponseEntity<>(detailAttendances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailAttendanceById(@PathVariable("id") Long id) {
        try {
            DetailAttendance detailAttendance = detailAttendanceService.getDetailAttendanceById(id);
            return ResponseEntity.ok(detailAttendance);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<DetailAttendance> createDetailAttendance(@RequestBody DetailAttendance detailAttendance) {
        DetailAttendance createdDetailAttendance = detailAttendanceService.createDetailAttendance(detailAttendance);
        return new ResponseEntity<>(createdDetailAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetailAttendance(@PathVariable("id") Long id, @RequestBody DetailAttendance detailAttendance) {
        try {
        	detailAttendanceService.getDetailAttendanceById(id);
        	
        	detailAttendance.setId(id);
        	DetailAttendance updatedDetailAttendance = detailAttendanceService.updateDetailAttendance(detailAttendance);
        	return new ResponseEntity<>(updatedDetailAttendance, HttpStatus.OK);
        } catch (NoSuchElementException e) {
//            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
//            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailAttendance(@PathVariable("id") Long id) {
        DetailAttendance existingDetailAttendance = detailAttendanceService.getDetailAttendanceById(id);
        if (existingDetailAttendance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        detailAttendanceService.deleteDetailAttendance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}