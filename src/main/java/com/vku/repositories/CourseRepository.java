package com.vku.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vku.models.Course;

import jakarta.transaction.Transactional;
@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Các phương thức tùy chỉnh khác có thể được khai báo ở đây nếu cần
	List<Course> findByOfficerId(int officerId);

	List<Course> findByOfficerIdAndDayOfWeek(int officerId, String dayOfWeek);

	@Query("SELECT c FROM Course c " +
			"JOIN Student_Course sc ON c.id =  sc.courseId " +
	        "JOIN Student s ON sc.studentCode = s.studentCode " +
	        "WHERE c.status = true AND sc.studentCode = :studentCode")
//	        "GROUP BY s.dayOffWeek")
	List<Course> getCoursesByStudentCode(String studentCode);
	@Query("SELECT c FROM Course c " +
			"JOIN Student_Course sc ON c.id =  sc.courseId " +
	        "JOIN Student s ON sc.studentCode = s.studentCode " +
	        "WHERE c.status = true AND sc.studentCode = :studentCode AND c.dayOfWeek = :dayOfWeek")
//	        "GROUP BY s.dayOffWeek")
	List<Course> getCoursesByStudentCodeAndDayOfWeek(String studentCode, String dayOfWeek);
	
}