package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Student_Course;
import com.vku.repositories.Student_CourseRepository;

import java.sql.Timestamp;
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

	public List<Student_Course> getStudentCourseWithCourseId(Long id) {
		// TODO Auto-generated method stub
		return Student_CourseRepository.findByCourseId(id);
	}

	public List<Student_Course> getByExtraSheetAndCourseId(boolean b, Long courseId) {
		// TODO Auto-generated method stub
		return Student_CourseRepository.findByExtraSheetAndCourseId(b, courseId);
	}

	public void setExtraSheetWithCourseId(Long courseId, boolean b) {
		// TODO Auto-generated method stub
		Student_CourseRepository.updateExtraSheetByCourseIdAndStudentCode(courseId, b);
		
	}

	public List<Student_Course> getByCourseIdWithUpdateTimeThanT(Long id,Timestamp updateTime) {
		// TODO Auto-generated method stub
		return Student_CourseRepository.findByCourseIdAndUpdateTimeThanT(id, updateTime);
	}
}