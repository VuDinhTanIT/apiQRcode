package com.vku.controllers.students;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vku.controllers.QRcode;
import com.vku.dtos.AttendanceRequest_StudentDTO;
import com.vku.dtos.ErrorResponse;
import com.vku.models.AttendanceSheet;
import com.vku.models.DetailAttendance;
import com.vku.models.Student;
import com.vku.models.Student_Course;
import com.vku.services.AttendanceSheetService;
import com.vku.services.DetailAttendanceService;
import com.vku.services.StudentService;
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
	private StudentService studentService;
	@Autowired
	private AttendanceSheetService attendanceSheetService; 

	
	
	@PostMapping("/scanAttendanceQR")
	public ResponseEntity<?> ScanAttendanceQRcode(@RequestBody AttendanceRequest_StudentDTO attendanceRequest,
			HttpServletRequest request) throws Exception {
		
		try {
//			System.out.println("Request Att: " + attendanceRequest.toString());
			String QRcodeInfo = attendanceRequest.getQrCodeInfo();
			String studentCode = attendanceRequest.getStudentCode(); 
			boolean isValid = qrCode.isQRCodeValid(QRcodeInfo);
			if(!isValid) {
				throw new Exception("Time out");
			}
			String courseId = QRcodeInfo.trim().split("\\|")[0];
			boolean check = student_CourseService.setExtraSheetWithCourseIdAndStudentCode(Long.parseLong(courseId),studentCode, true);
			if(!check) {
				throw new Exception("Dữ liệu điểm danh không hợp lệ!");
			}
			return new ResponseEntity<>(true, HttpStatus.OK);		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			System.out.println(e.getMessage());
			return ResponseEntity.ok(e.getMessage() );
			
		} 
	}
	 @GetMapping("/{id}")
	    public ResponseEntity<?> getStudentById(@PathVariable("id") String id) {
	        try {
	            Student student = studentService.getStudentByStudentCode(id);
	            return ResponseEntity.ok(student);
	        } catch (NoSuchElementException e) {  
	            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	        } catch (Exception e) {
	            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    } 
	 @GetMapping("/teachingSchedule/{courseId}")
	    public ResponseEntity<?> getTeachingSchedule(@PathVariable Long courseId) {
	        try {
	        	List<AttendanceSheet> attendanceSheets = attendanceSheetService.getTeachingSchedule(courseId);
	        	return ResponseEntity.ok(attendanceSheets);
	        } catch (NoSuchElementException e) {  
	            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	        } catch (Exception e) {
	            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
	        try {
	            Student existingStudent = studentService.getStudentByStudentCode(id);
	            if(existingStudent != null) {
	            	student.setStudentCode(id);
	            }
	            Student updatedStudent = studentService.updateStudent(student);
	            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	        } catch (Exception e) {
	            ErrorResponse errorResponse = new ErrorResponse("An error occurred", 500);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }
}
