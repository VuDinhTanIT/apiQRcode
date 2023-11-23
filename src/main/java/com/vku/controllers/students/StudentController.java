package com.vku.controllers.students;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vku.controllers.QRcode;
import com.vku.models.AttendanceSheet;
import com.vku.models.DetailAttendance;
import com.vku.models.Student_Course;
import com.vku.services.AttendanceSheetService;
import com.vku.services.DetailAttendanceService;
import com.vku.services.Student_CourseService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("api/student")
public class StudentController {
	@Autowired
	private Student_CourseService student_CourseService;
	@Autowired
	private QRcode qrCode;
	@Autowired
	private AttendanceSheetService attendanceSheetService;
	@Autowired
	private DetailAttendanceService detailAttendanceService; 
	// Cập nhập cho điểm danh phụ với courseId thành true
	@PutMapping("/extraSheet/{courseId}")
	public ResponseEntity<?> setExtraSheetFalseWithCourseId(@PathVariable("courseId") Long courseId) {
		try {
//			List<Student_Course> listStudent_CoursesWithCoureId = student_CourseService
//					.getStudentCourseWithCourseId(courseId);
//			for (Student_Course student_Course : listStudent_CoursesWithCoureId) {
//				if(!student_Course.isExtraSheet()) {
//					
//					student_Course.setExtraSheet(true);
//					student_CourseService.updateStudent_Course(student_Course);
//				}
//			}
			student_CourseService.setExtraSheetWithCourseId(courseId, false);
			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			return ResponseEntity.ok(false);
		}
	}

//	@PostMapping("/attendanceQR")
//	public ResponseEntity<?> createSheetQRcode(@RequestParam("courseId") String courseId,@RequestParam("lessonContent") String lessonContent,
//			HttpServletRequest request) throws Exception {
//		LocalDateTime attendanceDate = LocalDateTime.now();
//		System.out.println("attendanceDate: " + attendanceDate);
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		AttendanceSheet attendanceSheet = new AttendanceSheet();
//		attendanceSheet.setCourseId(Long.parseLong(courseId));
//		attendanceSheet.setLessonContent(lessonContent);
//		attendanceSheet.setTeachDate(attendanceDate.format(formatter));
//		try {
//			setExtraSheetTrueWithCourseId(Long.parseLong(courseId));
//			AttendanceSheet attendanceSheetCreated = attendanceSheetService.createAttendanceSheet(attendanceSheet);
//			String QRcodeInfo = attendanceSheetCreated.getId() + "|" + attendanceDate.format(formatter);
//			String attendanceQRImageBase64 = qrCode.generateQRcodeWithExpirationDays(QRcodeInfo, request,
//					attendanceSheetCreated.getId()+"" , 2);
////        Student_Course createdStudent_Course = student_CourseService.createStudent_Course(Student_Course);
//			
//			return new ResponseEntity<>(attendanceQRImageBase64, HttpStatus.OK);
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//			return ResponseEntity.ok(null);
//			
//		} 
//	}

}
