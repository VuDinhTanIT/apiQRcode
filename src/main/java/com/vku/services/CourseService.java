package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Course;
import com.vku.repositories.CourseRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {
	@Autowired
    private  CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

	public List<Course> getCoursesByOfficerId(int officerId) {
		// TODO Auto-generated method stub		
		return  courseRepository.findByOfficerId(officerId);
	}
}