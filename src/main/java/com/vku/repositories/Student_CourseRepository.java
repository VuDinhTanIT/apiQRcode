package com.vku.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.models.Student_Course;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface Student_CourseRepository extends JpaRepository<Student_Course, Long> {

	List<Student_Course> findByCourseId(Long id);

	List<Student_Course> findByExtraSheet(boolean bool);

	List<Student_Course> findByExtraSheetAndCourseId(boolean bool, Long courseId);

	Student_Course findFirstByCourseIdOrderByUpdateTimeDesc(Long courseId);

	@Modifying
	@Query("UPDATE Student_Course sc SET sc.extraSheet = :bool  WHERE sc.courseId = :courseId")
	void updateExtraSheetByCourseIdAndStudentCode(@Param("courseId") Long courseId, @Param("bool") boolean bool);

	@Modifying
	@Query("UPDATE Student_Course sc SET sc.extraSheet = true WHERE sc.courseId = :courseId AND sc.studentCode = :studentCode")
	void updateExtraSheetByCourseIdAndStudentCode(@Param("courseId") Long courseId,
			@Param("studentCode") String studentCode);

	@Modifying
	@Query("SELECT d FROM Student_Course d WHERE d.courseId = :courseId AND d.updateTime > :t")
	List<Student_Course> findByCourseIdAndUpdateTimeThanT(@Param("courseId") Long courseId,
			@Param("t") Timestamp t);

}