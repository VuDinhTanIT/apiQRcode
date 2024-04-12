package com.vku.repositories;


import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vku.models.DetailAttendance;

import jakarta.transaction.Transactional;

@Repository
public interface DetailAttendanceRepository extends JpaRepository<DetailAttendance, Long> {
	DetailAttendance findFirstByCourseIdOrderByUpdateTimeDesc(Long courseId);
	@Transactional
	@Modifying
    @Query("DELETE FROM DetailAttendance d WHERE d.courseId = :courseId AND d.studentCode = :studentCode AND DATE(d.updateTime) = :date")
    int deleteByCourseIdAndStudentCodeAndDate(@Param("courseId") Long courseId, @Param("studentCode") String studentCode, @Param("date") Timestamp date);
//	void deleteByAttendanceSheetIdAndStudentCode(Long attendanceSheetId, String studentCode);
//    @Modifying
//	@Query("SELECT d FROM DetailAttendance d WHERE d.attendanceSheetId = :attId AND d.updateTime < :t")
//    List<DetailAttendance> findByAttendanceIdAndUpdateTimeLessT(@Param("attId") Long attId, @Param("t") Timestamp t);
}