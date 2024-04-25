package com.vku.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.models.AttendanceSheet;

@Repository
public interface AttendanceSheetRepository extends JpaRepository<AttendanceSheet, Long> {

	@Query("SELECT COUNT(a) FROM AttendanceSheet a WHERE a.courseId = :courseId AND DATE(a.teachDate) = DATE(:date)  GROUP BY a.courseId, a.teachDate")
	int countByCourseIdAndTeachDate(@Param("courseId") Long courseId, @Param("date") Timestamp date);    
	
	List<AttendanceSheet> findByCourseIdAndTeachDate(Long courseId, String date);

	List<AttendanceSheet> findByCourseId(Long courseId);
			
}
