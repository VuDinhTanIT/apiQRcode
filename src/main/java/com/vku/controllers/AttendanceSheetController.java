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
import com.vku.models.AttendanceSheet;
import com.vku.services.AttendanceSheetService;

@RestController
@RequestMapping("/attendance-sheets")
public class AttendanceSheetController {
	@Autowired
    private AttendanceSheetService attendanceSheetService;

    public AttendanceSheetController(AttendanceSheetService attendanceSheetService) {
        this.attendanceSheetService = attendanceSheetService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceSheet>> getAllAttendanceSheets() {
        List<AttendanceSheet> attendanceSheets = attendanceSheetService.getAllAttendanceSheets();
        return new ResponseEntity<>(attendanceSheets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendanceSheetById(@PathVariable("id") Long id) {
        try {
            AttendanceSheet attendanceSheet = attendanceSheetService.getAttendanceSheetById(id);
            return ResponseEntity.ok(attendanceSheet);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<AttendanceSheet> createAttendanceSheet(@RequestBody AttendanceSheet attendanceSheet) {
        AttendanceSheet createdAttendanceSheet = attendanceSheetService.createAttendanceSheet(attendanceSheet);
        return new ResponseEntity<>(createdAttendanceSheet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendanceSheet(@PathVariable("id") Long id, @RequestBody AttendanceSheet attendanceSheet) {
        try {
        	AttendanceSheet existingAttendanceSheet = attendanceSheetService.getAttendanceSheetById(id);
        	
        	attendanceSheet.setId(id);
        	AttendanceSheet updatedAttendanceSheet = attendanceSheetService.updateAttendanceSheet(attendanceSheet);
        	return new ResponseEntity<>(updatedAttendanceSheet, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendanceSheet(@PathVariable("id") Long id) {
        AttendanceSheet existingAttendanceSheet = attendanceSheetService.getAttendanceSheetById(id);
        if (existingAttendanceSheet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attendanceSheetService.deleteAttendanceSheet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}