package com.vku.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.controllers.QRcode;
import com.vku.dtos.ErrorResponse;
import com.vku.dtos.QRData;
import com.vku.models.SchoolYear_Semester;
import com.vku.services.SchoolYear_SemesterService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/admin/school_year_semesters")
public class SchoolYear_SemesterManagement{

    @Autowired
    private SchoolYear_SemesterService schoolYear_SemesterService;


    @GetMapping
    public ResponseEntity<List<SchoolYear_Semester>> getAllSchoolYear_Semesters() {
        List<SchoolYear_Semester> schoolYear_Semesters = schoolYear_SemesterService.getAllSchoolYear_Semesters();
//        System.out.println("get week of Year :"+ schoolYear_SemesterService.getWeekOfCurrentSchoolYear());
        return new ResponseEntity<>(schoolYear_Semesters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSchoolYear_SemesterById(@PathVariable("id") Integer id) {
        try {
            SchoolYear_Semester schoolYear_Semester = schoolYear_SemesterService.getSchoolYear_SemesterById(id);
            return ResponseEntity.ok(schoolYear_Semester);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<SchoolYear_Semester> createSchoolYear_Semester(@RequestBody SchoolYear_Semester schoolYear_Semester) throws Exception {
        schoolYear_Semester.setId(0);
    	SchoolYear_Semester createdSchoolYear_Semester = schoolYear_SemesterService.createSchoolYear_Semester(schoolYear_Semester);
        return new ResponseEntity<>(createdSchoolYear_Semester, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchoolYear_Semester(@PathVariable("id") Integer id, @RequestBody SchoolYear_Semester schoolYear_Semester, HttpServletRequest request) {
        try {
        	System.out.println("updateSchoolYear_Semester: " + schoolYear_Semester);
        	SchoolYear_Semester existingSchoolYear_Semester = schoolYear_SemesterService.getSchoolYear_SemesterById(id);
//        	if(existingSchoolYear_Semester.getUrlSchoolYear_Semester() != null && schoolYear_Semester.getUrlSchoolYear_Semester() != null && existingSchoolYear_Semester.getUrlSchoolYear_Semester() == (schoolYear_Semester.getUrlSchoolYear_Semester())) {

        	schoolYear_Semester.setId(id);
            SchoolYear_Semester updatedSchoolYear_Semester = schoolYear_SemesterService.updateSchoolYear_Semester(schoolYear_Semester);
            return new ResponseEntity<>(updatedSchoolYear_Semester, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
        	
//        	e.printStackTrace();	
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolYear_Semester(@PathVariable("id") Integer id) {
        SchoolYear_Semester existingSchoolYear_Semester = schoolYear_SemesterService.getSchoolYear_SemesterById(id);
        if (existingSchoolYear_Semester == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        schoolYear_SemesterService.deleteSchoolYear_Semester(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
