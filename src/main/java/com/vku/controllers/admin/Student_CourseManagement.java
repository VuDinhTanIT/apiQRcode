package com.vku.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.dtos.ErrorResponse;
import com.vku.models.Student_Course;
import com.vku.services.Student_CourseService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/admin/Student_Courses")
public class Student_CourseManagement {
	@Autowired
    private  Student_CourseService student_CourseService;


    @GetMapping
    public ResponseEntity<List<Student_Course>> getAllStudent_Courses() {
        List<Student_Course> Student_Courses = student_CourseService.getAllStudent_Courses();
        return new ResponseEntity<>(Student_Courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student_Course> getStudent_CourseById(@PathVariable("id") Long id) {
        Student_Course Student_Course = student_CourseService.getStudent_CourseById(id);
        if (Student_Course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Student_Course, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student_Course> createStudent_Course(@RequestBody Student_Course Student_Course) {
        Student_Course createdStudent_Course = student_CourseService.createStudent_Course(Student_Course);
        return new ResponseEntity<>(createdStudent_Course, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent_Course(@PathVariable("id") Long id, @RequestBody Student_Course Student_Course) {
        
    	try {
    		Student_Course existingStudent_Course = student_CourseService.getStudent_CourseById(id);
    		Student_Course.setId(id);
    		Student_Course updatedStudent_Course = student_CourseService.updateStudent_Course(Student_Course);
    		return new ResponseEntity<>(updatedStudent_Course, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent_Course(@PathVariable("id") Long id) {
        Student_Course existingStudent_Course = student_CourseService.getStudent_CourseById(id);
        if (existingStudent_Course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        student_CourseService.deleteStudent_Course(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}