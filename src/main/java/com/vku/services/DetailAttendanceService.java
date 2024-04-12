package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.AttendanceSheet;
import com.vku.models.DetailAttendance;
import com.vku.repositories.DetailAttendanceRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DetailAttendanceService {
	@Autowired
	private DetailAttendanceRepository detailAttendanceRepository;

	public List<DetailAttendance> getAllDetailAttendances() {
		return detailAttendanceRepository.findAll();
	}

	public DetailAttendance getDetailAttendanceById(Long id) {
		return detailAttendanceRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public DetailAttendance createDetailAttendance(DetailAttendance detailAttendance) {
		return detailAttendanceRepository.save(detailAttendance);
	}

	public DetailAttendance updateDetailAttendance(DetailAttendance detailAttendance) {
		return detailAttendanceRepository.save(detailAttendance);
	}

	public void deleteDetailAttendance(Long id) {
		detailAttendanceRepository.deleteById(id);
	}

	public DetailAttendance getFirstByAttendanceIdWithLatestUpdateTime(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteByAttendanceIdAndStudentCodeAndDateOff(Long courseId, String studentCode, Timestamp date) {
		// TODO Auto-generated method stub
		int affectedRows = detailAttendanceRepository.deleteByCourseIdAndStudentCodeAndDate(courseId, studentCode, date);
		// Nếu row đó có ảnh hưởng̣ câu lệnh thực thi đúng thì return true
		return affectedRows > 0 ? true: false;

	}

	public DetailAttendance getFirstByCourseIdWithLatestUpdateTime(Long courseId) {
		// TODO Auto-generated method stub
		return detailAttendanceRepository.findFirstByCourseIdOrderByUpdateTimeDesc(courseId);
	}

//	public boolean getByCourseIdAndStudentCode(Long courseId, String studentCode, Timestamp dayOff) {
//		// TODO Auto-generated method stub
//		detailAttendanceRepository.deleteByCourseIdAndStudentCodeAndDate(courseId, studentCode, dayOff);
//	}

}