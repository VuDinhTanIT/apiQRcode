package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Student_Course;
import com.vku.repositories.Student_CourseRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class Student_CourseService {
	@Autowired
    private  Student_CourseRepository Student_CourseRepository;

    public List<Student_Course> getAllStudent_Courses() {
        return Student_CourseRepository.findAll();
    }

    public Student_Course getStudent_CourseById(Long id) {
        return Student_CourseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public Student_Course createStudent_Course(Student_Course Student_Course) {
        return Student_CourseRepository.save(Student_Course);
    }

    public Student_Course updateStudent_Course(Student_Course Student_Course) {
        return Student_CourseRepository.save(Student_Course);
    }

    public void deleteStudent_Course(Long id) {
        Student_CourseRepository.deleteById(id);
    }
}