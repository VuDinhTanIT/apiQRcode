package com.vku.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vku.models.AttendanceSheet;
import com.vku.models.Log;
import com.vku.repositories.AttendanceSheetRepository;

@Service
public class AttendanceSheetService {

	@Autowired
	private AttendanceSheetRepository attendanceSheetRepository;
	@Autowired
	private LogService logService;

	public List<AttendanceSheet> getAllAttendanceSheets() {
		return attendanceSheetRepository.findAll();
	}

	public AttendanceSheet getAttendanceSheetById(Long id) {
		return attendanceSheetRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public AttendanceSheet createAttendanceSheet(AttendanceSheet attendanceSheet) {
//		Log log = new Log();
////      log.setActor();
//      log.setLog("Thêm attendanceSheet: -courseId: "+ attendanceSheet.getCourseId() + " - ngày: " + attendanceSheet.getTeachDate() );
//      logService.wirteLog(log);
		return attendanceSheetRepository.save(attendanceSheet);
	}

	public AttendanceSheet updateAttendanceSheet(AttendanceSheet attendanceSheet) {
		return attendanceSheetRepository.save(attendanceSheet);
	}

	public void deleteAttendanceSheet(Long id) {
		attendanceSheetRepository.deleteById(id);
	}

	public Boolean checkAttendancedToday(Timestamp date, Long courseId ) {
		
		
		int affectedRows = attendanceSheetRepository.countByCourseIdAndTeachDate(courseId, date);
		System.out.println("date: " + date + "row: " + affectedRows);
		System.out.println("cout listAtt: " + attendanceSheetRepository.findByCourseIdAndTeachDate(courseId, date.toString()));
		// Nếu row đó có ảnh hưởng̣ câu lệnh thực thi đúng thì return true
		return affectedRows > 0 ? true: false;
	}

	public  List<AttendanceSheet> getTeachingSchedule( Long courseId) {
		// TODO Auto-generated method stub
		return attendanceSheetRepository.findByCourseId(courseId);
	}
}
