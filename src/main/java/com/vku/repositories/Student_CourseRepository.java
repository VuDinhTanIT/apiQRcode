package com.vku.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.dtos.StudentAttendanceForCourse;
import com.vku.dtos.StudentCourseInfoDTO;
import com.vku.models.Student_Course;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface Student_CourseRepository extends JpaRepository<Student_Course, Long> {
	@Query("SELECT new com.vku.dtos.StudentAttendanceForCourse(sc.studentCode, s.className, s.name, sc.extraSheet, COUNT(CASE WHEN sc.extraSheet = false THEN 1 END)) " +
	        "FROM Student_Course sc " +
	        "JOIN Student s ON sc.studentCode = s.studentCode " +
	        "WHERE sc.courseId = :id " +
	        "GROUP BY sc.studentCode, s.className, s.name, sc.status")
	List<StudentAttendanceForCourse> getInfoByCourseId(@Param("id") Long id);
	
	@Query("SELECT new com.vku.dtos.StudentCourseInfoDTO(sc.id, sc.studentCode, s.className, s.name, c.name, c.courseCode, sc.courseId, sc.status) " +
	        "FROM Student_Course sc " +
	        "JOIN Student s ON sc.studentCode = s.studentCode " +
	        "JOIN Course c ON c.id =  sc.courseId " +
	        "WHERE s.status = true " +
	        "GROUP BY sc.studentCode, s.className, s.name")
	List<StudentCourseInfoDTO> getAllStudentCourseInfo();
	List<Student_Course> findByCourseId(Long id);

	List<Student_Course> findByExtraSheet(boolean bool);

	List<Student_Course> findByExtraSheetAndCourseId(boolean bool, Long courseId);

	Student_Course findFirstByCourseIdOrderByUpdateTimeDesc(Long courseId);

	@Modifying
	@Query("UPDATE Student_Course sc SET sc.extraSheet = :bool  WHERE sc.courseId = :courseId")
	void updateExtraSheetByCourseId(@Param("courseId") Long courseId, @Param("bool") boolean bool);

	@Modifying
	@Query("UPDATE Student_Course sc SET sc.extraSheet = :bool WHERE sc.courseId = :courseId AND sc.studentCode = :studentCode")
	int updateExtraSheetByCourseIdAndStudentCode(@Param("courseId") Long courseId,
			@Param("studentCode") String studentCode, @Param("bool") boolean bool);

	@Modifying
	@Query("SELECT d FROM Student_Course d WHERE d.courseId = :courseId AND d.updateTime > :t")
	List<Student_Course> findByCourseIdAndUpdateTimeThanT(@Param("courseId") Long courseId, @Param("t") Timestamp t);

}