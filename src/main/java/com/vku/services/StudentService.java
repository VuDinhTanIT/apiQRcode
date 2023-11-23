package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vku.models.Student;
import com.vku.repositories.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByStudentCode(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found: " + id));
    }

    public Student createStudent(Student student) {
    	
    	student.setPassDefault();
    	student.setStatus(true);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

	public Student findByStudentCode(String username) {
		// TODO Auto-generated method stub
		return studentRepository.findByStudentCode(username);
	}
}
